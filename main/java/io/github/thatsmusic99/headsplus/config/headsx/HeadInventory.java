package io.github.thatsmusic99.headsplus.config.headsx;


import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.*;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class HeadInventory {

    public abstract String getDefaultTitle();

    public abstract Icon[] getDefaultItems();

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

    public Icon[] getIconArray() {
        FileConfiguration fc = HeadsPlus.getInstance().getItems().getConfig();
        Icon[] icons = new Icon[getSize()];
        for (int i = 0; i < getSize(); i++) {
            icons[i] = Icon.getIconFromName(fc.getStringList("inventories." + getName() + ".icons").get(i));
        }
        return icons;
    }

    public String getTitle() {
        return HeadsPlus.getInstance().getItems().getConfig().getString("inventories." + getName() + ".title");
    }

    public int getSize() {
        return HeadsPlus.getInstance().getItems().getConfig().getInt("inventories." + getName() + ".size");
    }

    public Inventory build(PagedLists<ItemStack> list, Player sender, InventoryManager inv) {
        Inventory i = Bukkit.createInventory(null, getSize(), getTitle()
                .replaceAll("\\{page}", String.valueOf(inv.getPage()))
                .replaceAll("\\{pages}", String.valueOf(list.getTotalPages())));
        int h = 0;
        for (int o = 0; o < getSize(); o++) {
            if (getIconArray()[o] instanceof Head) {
                ItemStack is = list.getContentsInPage(inv.getPage()).get(h);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(getIconArray()[o].getDisplayName().replaceAll("\\{head-name}", is.getItemMeta().getDisplayName()));
                List<String> l = im.getLore();
                for (String s : is.getItemMeta().getLore()) {
                    l.add(s.replaceAll("\\{price}", String.valueOf(HeadsPlus.getInstance().getNMS().getPrice(is))
                    .replaceAll("\\{favourite}", HPPlayer.getHPPlayer(sender).hasHeadFavourited(HeadsPlus.getInstance().getNMS().getId(is)) ? ChatColor.GOLD + "Favourite!" : "")));
                }
                im.setLore(l);
                is.setItemMeta(im);
                is = HeadsPlus.getInstance().getNMS().setIcon(is, getIconArray()[o]);
                i.setItem(o, list.getContentsInPage(inv.getPage()).get(h));
                h++;
            } else if (getIconArray()[o] instanceof Challenge) {
                ItemStack is = list.getContentsInPage(inv.getPage()).get(h);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(getIconArray()[o].getDisplayName().replaceAll("\\{challenge-name}", is.getItemMeta().getDisplayName()));
                for (int z = 0; z < im.getLore().size(); z++) {
                    if (!im.getLore().get(z).contains("{challenge-lore}")) {
                        im.setLore(getIconArray()[o].getLore());
                    }
                }
                is.setItemMeta(im);
                is = HeadsPlus.getInstance().getNMS().setIcon(is, getIconArray()[o]);
                i.setItem(o, is);
                h++;
            } else if (getIconArray()[o] instanceof Stats) {

                ItemStack is;
                if (HeadsPlus.getInstance().getNMS() instanceof NewNMSManager) {
                    is = new ItemStack(getIconArray()[o].getMaterial(), 1);
                } else {
                    is = new ItemStack(getIconArray()[o].getMaterial(), 1,
                            (byte) HeadsPlus.getInstance().getItems().getConfig().getInt("icons.stats.data-value"));
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(getIconArray()[o].getDisplayName());
                List<String> ls = new ArrayList<>();
                for (String s : getIconArray()[o].getLore()) {
                    ls.add(s.replaceAll("\\{heads}",String.valueOf(inv.getHeads()))
                            .replaceAll("\\{pages}", String.valueOf(list.getTotalPages()))
                            .replaceAll("\\{sections}", String.valueOf(inv.getSections()))
                            .replaceAll("\\{balance}", String.valueOf(HeadsPlus.getInstance().getEconomy().getBalance(sender)))
                            .replaceAll("\\{section}", inv.getSection()));
                }
                im.setLore(ls);
                is.setItemMeta(im);
                is = HeadsPlus.getInstance().getNMS().setIcon(is, getIconArray()[o]);
                i.setItem(o, is);
            } else if ((getIconArray()[o] instanceof Next)
                    || (getIconArray()[o] instanceof Back)
                    || (getIconArray()[o] instanceof Menu)) {
                ItemStack is;
                Icon oof;
                if (getIconArray()[o] instanceof Next) {
                    if (inv.getPages() == inv.getPage()) {
                        oof = getIconArray()[o].getReplacementIcon();
                    } else {
                        oof = getIconArray()[o];
                    }
                } else if (getIconArray()[o] instanceof Back) {
                    if (inv.getPage() == 1) {
                        oof = getIconArray()[o].getReplacementIcon();
                    } else {
                        oof = getIconArray()[o];
                    }
                } else {
                    if ((this instanceof SellheadMenu)
                            || (this instanceof ChallengesMenu)
                            || (this instanceof HeadMenu)) {
                        oof = getIconArray()[o].getReplacementIcon();
                    } else {
                        oof = getIconArray()[o];
                    }
                }

                if (HeadsPlus.getInstance().getNMS() instanceof NewNMSManager) {
                    is = new ItemStack(oof.getMaterial(), 1);
                } else {
                    is = new ItemStack(oof.getMaterial(), 1,
                                (byte) HeadsPlus.getInstance().getItems().getConfig().getInt("icons." + oof.getReplacementIcon().getMaterial() + ".data-value"));


                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(oof.getDisplayName());
                List<String> ls = new ArrayList<>();
                for (String s : oof.getLore()) {
                    ls.add(s.replaceAll("\\{heads}",String.valueOf(inv.getHeads()))
                            .replaceAll("\\{pages}", String.valueOf(list.getTotalPages()))
                            .replaceAll("\\{sections}", String.valueOf(inv.getSections()))
                            .replaceAll("\\{balance}", String.valueOf(HeadsPlus.getInstance().getEconomy().getBalance(sender)))
                            .replaceAll("\\{section}", inv.getSection()));
                }
                im.setLore(ls);
                is.setItemMeta(im);
                is = HeadsPlus.getInstance().getNMS().setIcon(is, oof);
                i.setItem(o, is);
            } else {
                ItemStack is = list.getContentsInPage(inv.getPage()).get(h);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(getIconArray()[o].getDisplayName());
                im.setLore(getIconArray()[o].getLore());
                is.setItemMeta(im);
                is = HeadsPlus.getInstance().getNMS().setIcon(is, getIconArray()[o]);
                i.setItem(o, is);
            }
        }
        return i;
    }

}
