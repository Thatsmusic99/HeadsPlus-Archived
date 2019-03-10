package io.github.thatsmusic99.headsplus.config.headsx;


import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.challenges.HPChallengeRewardTypes;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.*;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.ChallengeSection;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.HeadSection;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class HeadInventory {

    public abstract String getDefaultTitle();

    public abstract String getDefaultItems();

    public abstract String getDefaultId();

    public static HeadInventory getInventoryByName(String s) {
        for (HeadInventory h : getInventories()) {
            if (s.equalsIgnoreCase(h.getName())) {
                return h;
            }
        }
        return null;
    }

    public static List<HeadInventory> getInventories() {
        List<HeadInventory> inventories = new ArrayList<>();
        inventories.add(new ChallengesMenu());
        inventories.add(new ChallengeSection());
        inventories.add(new FavouritesMenu());
        inventories.add(new HeadMenu());
        inventories.add(new HeadSection());
        inventories.add(new SellheadMenu());
        return inventories;
    }

    public abstract String getName();

    public int getDefaultSize() {
        return 54;
    }

    public Icon[] getIconArray(int page) {
        FileConfiguration fc = HeadsPlus.getInstance().getItems().getConfig();
        Icon[] icons = new Icon[getSize()];
        String[] s = fc.getString("inventories."+ getName() + ".icons").split(":");
        String l;
        try {
            l = s[page - 1];
        } catch (IndexOutOfBoundsException e) {
            l = s[0];
        }
        for (int i = 0; i < getSize(); i++) {
            icons[i] = Icon.getIconFromSingleLetter(String.valueOf(l.charAt(i)));
        }
        return icons;
    }

    public String getTitle() {
        return HeadsPlus.getInstance().getItems().getConfig().getString("inventories." + getName() + ".title");
    }

    public int getSize() {
        return HeadsPlus.getInstance().getItems().getConfig().getInt("inventories." + getName() + ".size");
    }

    public Inventory build(PagedLists<ItemStack> list, Player sender) {
        InventoryManager inv = InventoryManager.getIM(sender);
        Inventory i = Bukkit.createInventory(null, getSize(), getTitle()
                .replaceAll("\\{page}", String.valueOf(inv.getPage()))
                .replaceAll("\\{pages}", list == null ? "" : String.valueOf(list.getTotalPages()))
                .replaceAll("\\{section}", inv.getSection()));
        HeadsPlus hp = HeadsPlus.getInstance();
        NMSManager nms = hp.getNMS();
        int h = 0;
        for (int o = 0; o < getSize(); o++) {
            if (getIconArray(inv.getPage())[o] instanceof Head || getIconArray(inv.getPage())[o] instanceof io.github.thatsmusic99.headsplus.config.headsx.icons.HeadSection) {
                try {
                    ItemStack is = list.getContentsInPage(inv.getPage()).get(h);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(getIconArray(inv.getPage())[o].getDisplayName().replaceAll("\\{head-name}", is.getItemMeta().getDisplayName()));
                    if (this instanceof SellheadMenu) {
                        im.setDisplayName(im.getDisplayName().replaceAll("\\{default}",
                                HeadsPlus.getInstance().getHeadsConfig().getDisplayName(nms.getType(is))));
                    }
                    List<String> l = new ArrayList<>();
                    for (String s : getIconArray(inv.getPage())[o].getLore()) {
                        l.add(ChatColor.translateAlternateColorCodes('&', s.replaceAll("\\{price}", String.valueOf(nms.getPrice(is)))
                                .replaceAll("\\{favourite}", HPPlayer.getHPPlayer(sender).hasHeadFavourited(nms.getId(is)) ? ChatColor.GOLD + "Favourite!" : "")));
                    }
                    im.setLore(l);
                    String s = "";
                    if (this instanceof SellheadMenu) {
                        s = nms.getType(is);

                    }
                    is.setItemMeta(im);
                    is = nms.setIcon(is, getIconArray(inv.getPage())[o]);
                    is = nms.setType(s, is);
                    i.setItem(o, is);
                    h++;
                } catch (IndexOutOfBoundsException ex) {
                    Icon ic = getIconArray(inv.getPage())[o].getReplacementIcon();
                    ItemStack is = new ItemStack(ic.getMaterial(), 1, (byte) hp.getItems().getConfig().getInt("icons." + ic.getIconName() + ".data-value"));
                    try {
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ic.getDisplayName());
                        im.setLore(ic.getLore());
                        is.setItemMeta(im);
                    } catch (NullPointerException ignored) {

                    }
                    is = nms.setIcon(is, ic);
                    i.setItem(o, is);
                }
            } else if (getIconArray(inv.getPage())[o] instanceof Challenge) {
                try {
                    ItemStack is = list.getContentsInPage(inv.getPage()).get(h); // Already set
                    ItemMeta im = is.getItemMeta();

                    List<String> lore = new ArrayList<>();
                    io.github.thatsmusic99.headsplus.api.Challenge c = nms.getChallenge(is);
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getIconArray(inv.getPage())[o].getDisplayName().replaceAll("(\\{challenge-name})", c.getChallengeHeader())));
                    for (int z = 0; z < getIconArray(inv.getPage())[o].getLore().size(); z++) {
                        if (getIconArray(inv.getPage())[o].getLore().get(z).contains("{challenge-lore}")) {
                            for (String s : c.getDescription()) {
                                lore.add(ChatColor.translateAlternateColorCodes('&', s));
                            }
                        }
                        if (getIconArray(inv.getPage())[o].getLore().get(z).contains("{challenge-reward}")) {
                            StringBuilder sb = new StringBuilder();
                            HPChallengeRewardTypes re = c.getRewardType();
                            if (re == HPChallengeRewardTypes.ECO) {
                                sb.append("$").append(c.getRewardValue().toString());
                            } else if (re == HPChallengeRewardTypes.GIVE_ITEM) {
                                try {
                                    Material.valueOf(c.getRewardValue().toString());
                                    sb.append(c.getRewardItemAmount()).append(" ").append(WordUtils.capitalize(c.getRewardValue().toString().toLowerCase().replaceAll("_", " "))).append("(s)");
                                } catch (IllegalArgumentException ignored) {

                                }
                            } else if (re == HPChallengeRewardTypes.ADD_GROUP) {
                                sb.append("Group ").append(c.getRewardValue().toString()).append(" addition");
                            } else if (re == HPChallengeRewardTypes.REMOVE_GROUP) {
                                sb.append("Group ").append(c.getRewardValue().toString()).append(" removal");
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', getIconArray(inv.getPage())[o].getLore().get(z).replace("{challenge-reward}", sb.toString())));
                        }
                        if (getIconArray(inv.getPage())[o].getLore().get(z).contains("{completed}")) {
                            if (c.isComplete(sender)) {
                                lore.add(HeadsPlus.getInstance().getMessagesConfig().getString("challenge-completed"));
                            }
                        }
                        if (getIconArray(inv.getPage())[o].getLore().get(z).contains("{challenge-xp}")) {
                            lore.add(ChatColor.translateAlternateColorCodes('&', getIconArray(inv.getPage())[o].getLore().get(z).replace("{challenge-xp}", String.valueOf(c.getGainedXP()))));
                        }
                    }
                    im.setLore(lore);
                    is.setItemMeta(im);
                    is = nms.setIcon(is, getIconArray(inv.getPage())[o]);
                    i.setItem(o, is);
                    h++;
                } catch (IndexOutOfBoundsException ex) {
                    Icon ic = getIconArray(inv.getPage())[o].getReplacementIcon();
                    ItemStack is = new ItemStack(ic.getMaterial(), 1, (byte) hp.getItems().getConfig().getInt("icons." + ic.getIconName() + ".data-value"));
                    try {
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', ic.getDisplayName()));
                        List<String> ls = new ArrayList<>();
                        for (String s : ic.getLore()) {
                            ls.add(ChatColor.translateAlternateColorCodes('&', s));
                        }
                        im.setLore(ls);
                        is.setItemMeta(im);
                    } catch (NullPointerException ignored) { // Air

                    }
                    is = nms.setIcon(is, ic);
                    i.setItem(o, is);
                }

            } else if (getIconArray(inv.getPage())[o] instanceof Stats) {

                ItemStack is;
                if (nms instanceof NewNMSManager) {
                    is = new ItemStack(getIconArray(inv.getPage())[o].getMaterial(), 1);
                } else {
                    is = new ItemStack(getIconArray(inv.getPage())[o].getMaterial(), 1,
                            (byte) hp.getItems().getConfig().getInt("icons.stats.data-value"));
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getIconArray(inv.getPage())[o].getDisplayName()));
                List<String> ls = new ArrayList<>();
                for (String s : getIconArray(inv.getPage())[o].getLore()) {
                    ls.add(ChatColor.translateAlternateColorCodes('&', s.replaceAll("\\{heads}",String.valueOf(inv.getHeads()))
                            .replaceAll("\\{pages}", String.valueOf(list.getTotalPages()))
                            .replaceAll("\\{sections}", String.valueOf(inv.getSections()))
                            .replaceAll("\\{balance}", (hp.econ() ? HeadsPlus.getInstance().getConfiguration().fixBalanceStr(hp.getEconomy().getBalance(sender)) : ""))
                            .replaceAll("\\{section}", inv.getSection()) ));
                }
                im.setLore(ls);
                is.setItemMeta(im);
                is = nms.setIcon(is, getIconArray(inv.getPage())[o]);
                i.setItem(o, is);
            } else if ((getIconArray(inv.getPage())[o] instanceof Next)
                    || (getIconArray(inv.getPage())[o] instanceof Back)
                    || (getIconArray(inv.getPage())[o] instanceof Menu)) {
                ItemStack is;
                Icon oof;
                if (getIconArray(inv.getPage())[o] instanceof Next) {
                    if (inv.getPages() == inv.getPage()) {
                        oof = getIconArray(inv.getPage())[o].getReplacementIcon();
                    } else {
                        oof = getIconArray(inv.getPage())[o];
                    }
                } else if (getIconArray(inv.getPage())[o] instanceof Back) {
                    if (inv.getPage() == 1) {
                        oof = getIconArray(inv.getPage())[o].getReplacementIcon();
                    } else {
                        oof = getIconArray(inv.getPage())[o];
                    }
                } else {
                    if ((this instanceof SellheadMenu)
                            || (this instanceof ChallengesMenu)
                            || (this instanceof HeadMenu)) {
                        oof = getIconArray(inv.getPage())[o].getReplacementIcon();
                    } else {
                        oof = getIconArray(inv.getPage())[o];
                    }
                }

                if (HeadsPlus.getInstance().getNMS() instanceof NewNMSManager) {
                    is = new ItemStack(oof.getMaterial(), 1);
                } else {
                    is = new ItemStack(oof.getMaterial(), 1,
                                (byte) hp.getItems().getConfig().getInt("icons." + oof.getIconName() + ".data-value"));


                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', oof.getDisplayName()));
                List<String> ls = new ArrayList<>();
                for (String s : oof.getLore()) {
                    ls.add(ChatColor.translateAlternateColorCodes('&', s.replaceAll("\\{heads}",String.valueOf(inv.getHeads()))
                            .replaceAll("\\{pages}", String.valueOf(list.getTotalPages()))
                            .replaceAll("\\{sections}", String.valueOf(inv.getSections()))
                            .replaceAll("\\{balance}", String.valueOf(hp.getEconomy().getBalance(sender)))
                            .replaceAll("\\{section}", inv.getSection())));
                }
                im.setLore(ls);
                is.setItemMeta(im);
                is = nms.setIcon(is, oof);
                i.setItem(o, is);
            } else {
                try {
                    ItemStack is = new ItemStack(getIconArray(inv.getPage())[o].getMaterial(), 1, (byte) hp.getItems().getConfig().getInt("icons." + getIconArray(inv.getPage())[o].getIconName() + ".data-value"));
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getIconArray(inv.getPage())[o].getDisplayName() ));
                    List<String> ls = new ArrayList<>();
                    for (String s : getIconArray(inv.getPage())[o].getLore()) {
                        ls.add(ChatColor.translateAlternateColorCodes('&', s));
                    }
                    im.setLore(ls);
                    is.setItemMeta(im);
                    is = nms.setIcon(is, getIconArray(inv.getPage())[o]);
                    i.setItem(o, is);
                } catch (NullPointerException ignored) {
                }

            }
        }
        return i;
    }

}
