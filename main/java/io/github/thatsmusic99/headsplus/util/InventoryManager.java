package io.github.thatsmusic99.headsplus.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;

import org.apache.commons.codec.binary.Base64;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

public class InventoryManager {

    private static int pages;
    private static int heads;
    private static int timesSent = 0;
    private static int cPage = 0;
    private static int sections = 0;
    private static String cSection = "menu";
    private static int[] pos() {
        int[] a = new int[28];
        a[0] = 10;
        a[1] = 11;
        a[2] = 12;
        a[3] = 13;
        a[4] = 14;
        a[5] = 15;
        a[6] = 16;
        a[7] = 19;
        a[8] = 20;
        a[9] = 21;
        a[10] = 22;
        a[11] = 23;
        a[12] = 24;
        a[13] = 25;
        a[14] = 28;
        a[15] = 29;
        a[16] = 30;
        a[17] = 31;
        a[18] = 32;
        a[19] = 33;
        a[20] = 34;
        a[21] = 37;
        a[22] = 38;
        a[23] = 39;
        a[24] = 40;
        a[25] = 41;
        a[26] = 42;
        a[27] = 43;
        return a;
    }
    private static int[] glass() {
        int[] a = new int[24];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        a[4] = 5;
        a[5] = 6;
        a[6] = 7;
        a[7] = 9;
        a[8] = 17;
        a[9] = 18;
        a[10] = 26;
        a[11] = 27;
        a[12] = 35;
        a[13] = 36;
        a[14] = 44;
        a[15] = 45;
        a[16] = 46;
        a[17] = 47;
        a[18] = 48;
        a[19] = 49;
        a[20] = 50;
        a[21] = 51;
        a[22] = 52;
        a[23] = 53;
        return a;
    }

    public static Inventory create(int slots, String name) {
        return Bukkit.createInventory(null, slots, name);
    }
  /*  public static Inventory setupInvHeadsX() {
        cPage = 1;
        heads = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        int h = heads;
        pages = 1;
        while (h > 45) {
            h -= 45;
            pages++;
        }
        Inventory i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + pages);
        if (heads > 45) {
            loop(45, i);
        } else {
            loop(heads, i);
        }
        ItemStack it = new ItemStack(Material.BARRIER);
        ItemMeta is = it.getItemMeta();
        is.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Menu");
        it.setItemMeta(is);
        i.setItem(49, it);
        if (pages > 1) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Next Page");
            item.setItemMeta(im);
            i.setItem(50, item);
        }
        timesSent = 0;
        return i;
    } */

    public static int getPages() { return pages; }
    public static int getPage() { return cPage; }
    public static int getHeads() { return heads; }
    public static String getSection() { return cSection; }
    public static int getSections() { return sections; }
    public static void setSection(String s) { cSection = s; }
    private static void loop(int in, Inventory i) {
        if (HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size() == 0) return;
        while (timesSent < in) {
            for (String str : HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                skull(str, i);
            }
        }
    }
    public static Inventory changePage(boolean next, boolean start, Player p, String section) {

        Inventory i;
        PagedLists ls;
        sections = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("sections").getKeys(false).size();
        heads = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        if (next) {
            cPage++;
        } else {
            cPage--;
        }
        if (start) {
            cPage = 1;
        }

        if (section.equalsIgnoreCase("menu")) {
           // if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("options.advent-calender")) {
           //     sections++;
           // }

            ls = new PagedLists(new ArrayList<>(HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("sections").getKeys(false)), 28);
            i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
            for (Object str : ls.getContentsInPage(cPage)) {
                String st = (String) str;
                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeadsX.getHeadsX().getString("sections." + st + ".texture"))) {
                    ItemStack is = HeadsPlusConfigHeadsX.getSkull(HeadsPlusConfigHeadsX.getHeadsX().getString("sections." + st + ".texture"));
                    SkullMeta im = (SkullMeta) is.getItemMeta();
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeadsX.getHeadsX().getString("sections." + st + ".display-name")));
                    is.setItemMeta(im);
                    i.setItem(pos()[timesSent], is);
                    timesSent++;
                } else {
                    ItemStack is = new ItemStack(Material.SKULL_ITEM);
                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    sm.setOwner(st);
                    is.setItemMeta(sm);
                    i.setItem(pos()[timesSent], is);
                    timesSent++;
                }
            }
          //  if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("options.advent-calender")) {
             //   ItemStack is = HeadsPlusConfigHeadsX.getSkull("HP#snowman_head");
             //   SkullMeta sm = (SkullMeta) is.getItemMeta();
             //   sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4[&a&lHeadsPlus &c&lAdvent Calender!&2]"));
             //   is.setItemMeta(sm);
             //   i.setItem(pos()[timesSent], is);
           // }
        } else if (section.startsWith("search:")) {
            String term = section.split(":")[1];
            List<String> f = new ArrayList<>();
            List<String> c = new ArrayList<>();
            HashMap<String, String> s = new HashMap<>();
            for (String str : HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                c.add(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + str + ".displayname"))).replace("[", "").replace("]", ""));
                s.put(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + str + ".displayname"))).replace("[", "").replace("]", ""), str);
            }
            StringUtil.copyPartialMatches(term, c, f);
            Collections.sort(f);
            Iterator<Map.Entry<String, String>> m = s.entrySet().iterator();
            while (m.hasNext()) {
                if (!f.contains(m.next().getKey())) {
                    m.remove();
                }
            }



            heads = s.size();
           /* int h = heads;
            pages = 1;
            while (h > 28) {
                h -= 28;
                pages++;
            }
            if (next) {
                cPage++;
            } else {
                cPage--;
            }
            if (start) {
                cPage = 1;
            }

            int si = (cPage - 1) * 28;
            int ei = 28 + si;

            if (ei > s.size()) {
                ei = s.size();
            }

            List<String> l = new ArrayList<>(s.values()).subList(si, ei); */

            ls = new PagedLists(new ArrayList<>(s.values()), 28);
            i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
            for (Object str : ls.getContentsInPage(cPage)) {
                if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("heads." + str + ".database")) {
                    skull(String.valueOf(str), i);
                }
            }


        } else {
            List<String> l = new ArrayList<>();
           // if (!section.equalsIgnoreCase("advent_calender")) {
                for (String str : HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                    if (HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + str + ".section").equalsIgnoreCase(section)) {
                        l.add(str);
                    }
                }
                heads = l.size();
                ls = new PagedLists(l, 28);
                i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
                for (Object str : ls.getContentsInPage(cPage)) {
                    if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("heads." + str + ".database")) {
                        skull(String.valueOf(str), i);
                    }
                }
           // } else {
              //  i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + pages);
              //  skullChristmas(i, p.getPlayer());
            //}

        }
        ItemStack isi = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemMeta ims = isi.getItemMeta();
        ims.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6"));
        isi.setItemMeta(ims);
        for (int in : glass()) {
            i.setItem(in, isi);
        }
        ItemStack name = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta nams = name.getItemMeta();
        nams.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6[&e&lSearch Heads&6]"));
        name.setItemMeta(nams);
        i.setItem(8, name);
        pages = ls.getTotalPages();
        if (pages > cPage) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Next Page");
            item.setItemMeta(im);
            i.setItem(50, item);
        }
        if (cPage != 1) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Back");
            item.setItemMeta(im);
            i.setItem(48, item);
        }
        if (!cSection.equalsIgnoreCase("menu")) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Main Menu");
            item.setItemMeta(im);
            i.setItem(45, item);
        }
        ItemStack it = new ItemStack(Material.BARRIER);
        ItemMeta is = it.getItemMeta();
        is.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Menu");
        it.setItemMeta(is);
        i.setItem(49, it);
        ItemStack is2 = new ItemStack(Material.PAPER);
        ItemMeta im = is2.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Total heads: " + heads);
        lore.add(ChatColor.GREEN + "Total pages: " + pages);
        lore.add(ChatColor.GREEN + "Total sections: " + sections) ;
        lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance(p));
        lore.add(ChatColor.GREEN + "Current section: " + section);
        im.setLore(lore);
        is2.setItemMeta(im);
        i.setItem(4, is2);
        timesSent = 0;
        return i;
    }

    private static void skull(String str, Inventory i) {
        ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) s.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
        if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("heads." + str + ".encode")) {
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", HeadsPlusConfigHeadsX.getHeadsX().getString(str + ".texture")).getBytes());
            gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
        } else {
            gm.getProperties().put("textures", new Property("texture", HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + str + ".texture")));
        }

        Field profileField = null;
        try {
            profileField = sm.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(sm, gm);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + str + ".displayname")));
        if (HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price") instanceof String) {
            if (!((String) HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price")).equalsIgnoreCase("free")) {
                if (((String) HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price")).equalsIgnoreCase("default")) {
                    if (!HeadsPlusConfigHeadsX.getHeadsX().get("options.default-price").equals("free")) {
                        List<String> price = new ArrayList<>();
                        price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + HeadsPlusConfigHeadsX.getHeadsX().get("options.default-price")));
                        sm.setLore(price);
                    }
                } else {
                    List<String> price = new ArrayList<>();
                    price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price")));
                    sm.setLore(price);
                }
            }
        } else {
            if (!(((Double) HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price")) == 0.0)) {
                List<String> price = new ArrayList<>();
                price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + HeadsPlusConfigHeadsX.getHeadsX().get("heads." + str + ".price")));
                sm.setLore(price);
            }

        }
        s.setItemMeta(sm);
        i.setItem(pos()[timesSent], s);
        timesSent++;
    }

  /*  public static void skullChristmas(Inventory i, Player p) {
        for (AdventCManager acm : AdventCManager.values()) {
            if (HeadsPlusConfigHeadsX.getHeadsX().getStringList("advent." + acm.name()).contains(p.getUniqueId().toString())) {
                ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta sm = (SkullMeta) s.getItemMeta();
                GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
                gm.getProperties().put("textures", new Property("texture", acm.texture));


                Field profileField = null;
                try {
                    profileField = sm.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(sm, gm);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.name));

                s.setItemMeta(sm);
                i.setItem(acm.place, s);
                timesSent++;
            } else {
                ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta sm = (SkullMeta) s.getItemMeta();
                GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
                gm.getProperties().put("textures", new Property("texture", acm.wTexture));


                Field profileField = null;
                try {
                    profileField = sm.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(sm, gm);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.wName));

                s.setItemMeta(sm);
                i.setItem(acm.place, s);
                timesSent++;
            }
        }
    } */
}
