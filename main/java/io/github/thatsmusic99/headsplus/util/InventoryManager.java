package io.github.thatsmusic99.headsplus.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

    public static Inventory create(int slots, String name) {
        return Bukkit.createInventory(null, slots, name);
    }
    public static Inventory setupInvHeadsX() {
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
    }

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
    public static Inventory changePage(boolean next) {
        heads = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        int h = heads;
        pages = 1;
        while (h > 45) {
            h -= 45;
            pages++;
        }
        if (next) {
            cPage++;
        } else {
            cPage--;
        }
        int si = (cPage - 1) * 45;
        int ei = 45 + si;
        if (ei > HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size()) {
            ei = HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false).size();
        }
        Inventory i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + pages);
        List<String> l = new ArrayList<>(HeadsPlusConfigHeadsX.getHeadsX().getConfigurationSection("heads").getKeys(false)).subList(si, ei);
        for (String str : l) {
            skull(str, i);
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
        s.setItemMeta(sm);
        i.setItem(timesSent, s);
        timesSent++;
    }
}
