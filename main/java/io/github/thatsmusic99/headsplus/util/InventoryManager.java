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

import java.lang.reflect.Field;
import java.util.*;

public class InventoryManager {

    private static int pages;
    private static int heads;
    private static int timesSent = 0;
    private static int cPage = 0;
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

    public static int getPages() {
        return pages;
    }
    public static int getPage() {
        return cPage;
    }
    public static int getHeads() {
        return heads;
    }
    private static void loop(int in, Inventory i) {
        if (HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size() == 0) return;
        while (timesSent < in) {
            for (String str : HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                skull(str, i);
            }
        }
    }
    public static Inventory changePage(boolean next, boolean start, Player p) {
        heads = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        int h = heads;
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
        if (ei > HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size()) {
            ei = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        }
        Inventory i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + pages);
        List<String> l = new ArrayList<>(HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)).subList(si, ei);
        for (String str : l) {
            if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("heads." + str + ".database")) {
                skull(str, i);
            }
        }
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
        lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance(p));
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
}
