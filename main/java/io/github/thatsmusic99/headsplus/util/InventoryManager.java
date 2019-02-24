package io.github.thatsmusic99.headsplus.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.*;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class InventoryManager {

    public InventoryManager(String t) {
        this.type = t;
    }

    private final String type;
    private int pages;
    private int heads;
    private int cPage = 0;
    private int sections = 0;
    private String cSection = "menu";
    private HeadInventory inventory;
    private final HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().getHeadsXConfig();
    public static final HashMap<Player, InventoryManager> pls = new HashMap<>();
    public int getPages() { return pages; }
    public int getPage() { return cPage; }
    public int getHeads() { return heads; }
    public String getSection() { return cSection; }
    public int getSections() { return sections; }
    public void setSection(String s) { cSection = s; }
    public String getType() {
        return type;
    }

    public HeadInventory getInventory() {
        return inventory;
    }

    public Inventory changePage(boolean next, boolean start, Player p, String section) throws NoSuchFieldException, IllegalAccessException {
        cSection = section;
        HeadsPlus hp = HeadsPlus.getInstance();
        PagedLists<ItemStack> paged;
        if (next) {
            cPage++;
        } else {
            cPage--;
        }
        if (start) {
            cPage = 1;
        }
        if (type.equalsIgnoreCase("heads")) {
            sections = hpchx.getConfig().getConfigurationSection("sections").getKeys(false).size();
            heads = hpchx.getConfig().getConfigurationSection("heads").getKeys(false).size();


            if (section.equalsIgnoreCase("menu")) {
                HeadMenu headmenu = new HeadMenu();
                inventory = headmenu;
                if (hpchx.getConfig().getBoolean("options.advent-calendar")) {
                    sections++;
                }
                List<ItemStack> heads = new ArrayList<>();
                 for (String str : new ArrayList<>(hpchx.getConfig().getConfigurationSection("sections").getKeys(false))) {
                    if (hpchx.isHPXSkull(hpchx.getConfig().getString("sections." + str + ".texture"))) {
                        ItemStack is = hpchx.getSkull(hpchx.getConfig().getString("sections." + str + ".texture"));
                        SkullMeta im = (SkullMeta) is.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("sections." + str + ".display-name")));
                        is.setItemMeta(im);
                        is = hp.getNMS().addSection(is, str);
                        heads.add(is);
                    } else {
                        ItemStack is = hp.getNMS().getSkullMaterial(1);
                        SkullMeta sm = (SkullMeta) is.getItemMeta();
                        sm = hp.getNMS().setSkullOwner(str, sm);
                        is.setItemMeta(sm);
                        is = hp.getNMS().addSection(is, str);
                        heads.add(is);
                    }
                }
            /*    if (hpchx.getConfig().getBoolean("options.advent-calendar")) {
                     ItemStack is;
                    if (hpchx.getConfig().getString("options.advent-texture").startsWith("HP#")) {
                         is = hpchx.getSkull(hpchx.getConfig().getString("options.advent-texture"));
                     } else {
                        is = hpchx.setTexture(hpchx.getConfig().getString("options.advent-texture"), hp.getNMS().getSkull(3));
                    }

                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("options.advent-display-name")));
                    is.setItemMeta(sm);
                    is = hp.getNMS().addSection(is, "advent-calendar");
                    heads.add(is);
                }*/
                paged = new PagedLists<>(heads, charOccurance(hp.getItems().getConfig().getString("inventories.headmenu.icons"), "L"));
                pages = paged.getTotalPages();

                return headmenu.build(paged, p);

            } else if (section.startsWith("search:")) {
                String term = section.split(":")[1];
                List<String> c = new ArrayList<>();
                HashMap<String, String> s = new HashMap<>();
                for (String str : hpchx.getConfig().getConfigurationSection("heads").getKeys(false)) {
                    String sr = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("heads." + str + ".displayname"))).replace("[", "").replace("]", "");
                    if (sr.contains(term)) {
                        c.add(sr);
                        s.put(sr, str);
                    }
                 }
                s.entrySet().removeIf(stringStringEntry -> !c.contains(stringStringEntry.getKey()));
                heads = s.size();
                List<ItemStack> heads = new ArrayList<>();
                for (Object str : new ArrayList<>(s.values())) {
                    if (hpchx.getConfig().getBoolean("heads." + str + ".database")) {
                        heads.add(skull(String.valueOf(str), p));
                    }
                }
                paged = new PagedLists<>(heads, charOccurance(hp.getItems().getConfig().getString("inventories.headsection.icons"), "H"));
                pages = paged.getTotalPages();
                inventory = new HeadSection();
                return new HeadSection().build(paged, p);
            } else if (section.equalsIgnoreCase("favourites")) {
                HPPlayer hps = HPPlayer.getHPPlayer(p);
                List<ItemStack> heads = new ArrayList<>();
                for (Object str : hps.getFavouriteHeads()) {
                    if (hpchx.getConfig().getBoolean("heads." + str + ".database")) {
                        heads.add(skull(String.valueOf(str), p));
                    }
                }
                paged = new PagedLists<>(heads, charOccurance(hp.getItems().getConfig().getString("inventories.favourites.icons"), "H"));
                pages = paged.getTotalPages();
                inventory = new FavouritesMenu();
                return new FavouritesMenu().build(paged, p);
            } else {
                List<String> l = new ArrayList<>();
                if (!section.equalsIgnoreCase("advent-calendar")) {
                    for (String str : hpchx.getConfig().getConfigurationSection("heads").getKeys(false)) {
                        if (hpchx.getConfig().getString("heads." + str + ".section").equalsIgnoreCase(section)) {
                            l.add(str);
                        }
                    }
                    heads = l.size();
                    List<ItemStack> items = new ArrayList<>();
                    for (Object str : l) {
                        if (hpchx.getConfig().getBoolean("heads." + str + ".database")) {
                            items.add(skull(String.valueOf(str), p));
                        }
                    }
                    paged = new PagedLists<>(items, charOccurance(hp.getItems().getConfig().getString("inventories.headsection.icons"), "H"));
                    pages = paged.getTotalPages();
                    inventory = new HeadSection();
                    return new HeadSection().build(paged, p);
                } else {
                    List<ItemStack> items = new ArrayList<>();
                    for (AdventCManager acm : AdventCManager.values()) {
                        if (hpchx.getConfig().getStringList("advent." + acm.name()).contains(p.getUniqueId().toString())) {
                            ItemStack is = hpchx.setTexture(acm.texture, HeadsPlus.getInstance().getNMS().getSkullMaterial(1));
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.name));
                            is.setItemMeta(im);
                            is = HeadsPlus.getInstance().getNMS().setCalendarValue(is, acm.name());
                            is = HeadsPlus.getInstance().getNMS().setOpen(is, true);
                            items.add(is);
                        } else {
                            ItemStack is = hpchx.setTexture(acm.wTexture, HeadsPlus.getInstance().getNMS().getSkullMaterial(1));
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.wName));
                            is.setItemMeta(im);
                            is = HeadsPlus.getInstance().getNMS().setCalendarValue(is, acm.name());
                            is = HeadsPlus.getInstance().getNMS().setOpen(is, false);
                            items.add(is);
                        }
                    }
                    paged = new PagedLists<>(items, charOccurance(hp.getItems().getConfig().getString("inventories.headsection.icons"), "H"));
                    pages = paged.getTotalPages();
                    inventory = new HeadSection();
                    return new HeadSection().build(paged, p);
                }

            }
        } else if (type.equalsIgnoreCase("chal")) {
            NMSManager nms = HeadsPlus.getInstance().getNMS();
            List<ItemStack> items = new ArrayList<>();
            if (cSection.equalsIgnoreCase("menu")) {
                return new ChallengesMenu().build(null, p);
            } else {
                List<Challenge> cs = new ArrayList<>();
                for (Challenge c : hp.getChallenges()) {
                    if (c.getDifficulty().name().equalsIgnoreCase(cSection)) {
                        cs.add(c);
                    }
                }
                int a = charOccurance(hp.getItems().getConfig().getString("inventories.challenge-section.icons"), "C");
                for (Challenge c : cs) {
                    ItemStack is;
                    if (c.isComplete(p)) {
                        is = new ItemStack(new io.github.thatsmusic99.headsplus.config.headsx.icons.Challenge().getCompleteMaterial(), 1, (byte) hp.getItems().getConfig().getInt("icons.challenge.complete-data-value"));
                    } else {
                        is = new ItemStack(new io.github.thatsmusic99.headsplus.config.headsx.icons.Challenge().getMaterial(), 1, (byte) hp.getItems().getConfig().getInt("icons.challenge.data-value"));
                    }
                    is = nms.setChallenge(is, c);
                    items.add(is);
                }

                paged = new PagedLists<>(items, a);
                pages = paged.getTotalPages();
                inventory = new ChallengeSection();
                return new ChallengeSection().build(paged, p);
            }
        } else {
            HeadsPlusConfigHeads hpch = hp.getHeadsConfig();
            List<String> s = new ArrayList<>();
            for (String o : hpch.mHeads) {
                if (!hpch.getConfig().getStringList(o + ".name").isEmpty()) {
                    s.add(o);
                }
            }
            for (String o : hpch.uHeads) {
                if (!hpch.getConfig().getStringList(o + ".name").isEmpty()) {
                    s.add(o);
                }
            }

            List<ItemStack> items = new ArrayList<>();
            NMSManager nms = hp.getNMS();
            for (String o : s) {

                ItemStack it = nms.getSkullMaterial(1);
                SkullMeta sm = (SkullMeta) it.getItemMeta();
                sm = nms.setSkullOwner(hpch.getConfig().getStringList(o + ".name").get(0), sm);
                sm.setDisplayName(hpch.getConfig().getString(o + ".display-name"));
                List<String> d = new ArrayList<>();
                for (String a : hpch.getConfig().getStringList(o + ".lore")) {
                    d.add(ChatColor.translateAlternateColorCodes('&', a).replaceAll("\\{price}", String.valueOf(hpch.getConfig().getDouble(o + ".price"))).replaceAll("\\{type}", o));
                }
                sm.setLore(d);
                it.setItemMeta(sm);
                it = nms.setType(o, it);
                items.add(it);
            }
            PagedLists<ItemStack> ps = new PagedLists<>(items, charOccurance(hp.getItems().getConfig().getString("inventories.sellheadmenu.icons"), "H"));
            pages = ps.getTotalPages();
            inventory = new SellheadMenu();
            return new SellheadMenu().build(ps, p);
        }
    }

    private ItemStack skull(String str, Player p) throws NoSuchFieldException, IllegalAccessException {
        NMSManager nms = HeadsPlus.getInstance().getNMS();
        ItemStack s = nms.getSkullMaterial(1);
        SkullMeta sm = (SkullMeta) s.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
        if (hpchx.getConfig().getBoolean("heads." + str + ".encode")) {
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", hpchx.getConfig().getString(str + ".texture")).getBytes());
            gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
        } else {
            gm.getProperties().put("textures", new Property("texture", hpchx.getConfig().getString("heads." + str + ".texture")));
        }

        Field profileField;
        profileField = sm.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(sm, gm);
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("heads." + str + ".displayname")));
        List<String> price = new ArrayList<>();
        double pr = 0.0;
        if (HeadsPlus.getInstance().econ()) {
            if (hpchx.getConfig().get("heads." + str + ".price") instanceof String) {
                if (!((String) hpchx.getConfig().get("heads." + str + ".price")).equalsIgnoreCase("free")) {
                    if (((String) hpchx.getConfig().get("heads." + str + ".price")).equalsIgnoreCase("default")) {
                        if (!hpchx.getConfig().get("options.default-price").equals("free")) {
                            pr = hpchx.getConfig().getDouble("options.default-price");
                        }
                    } else {
                        pr = hpchx.getConfig().getDouble("heads." + str + ".price");
                    }
                }
            } else {
                if (!(((Double) hpchx.getConfig().get("heads." + str + ".price")) == 0.0)) {
                    pr = hpchx.getConfig().getDouble("heads." + str + ".price");
                }
            }
        }
        price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + pr));

        HPPlayer hps = HPPlayer.getHPPlayer(p);
        if (hps.hasHeadFavourited(str) && !(cSection.equalsIgnoreCase("menu"))) {
            price.add(ChatColor.GOLD + "Favourite!");
        }
        sm.setLore(price);
        s.setItemMeta(sm);
        s = nms.addDatabaseHead(s, str, pr);
        return s;
    }

    public static InventoryManager getIM(Player p) {
        return pls.get(p);
    }

    private int charOccurance(String s, String c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (String.valueOf(s.charAt(i)).equalsIgnoreCase(c)) {
                count++;
            }
        }
        return count;
    }
}
