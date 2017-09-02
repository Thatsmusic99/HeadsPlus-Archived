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
import java.util.Arrays;
import java.util.UUID;

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
        heads = HeadsPlusConfigHeadsX.getHeadsX().getKeys(false).size();
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

        while (timesSent < in) {
            if (HeadsPlusConfigHeadsX.getHeadsX().getKeys(false).size() == 0) return;
            for (String str : HeadsPlusConfigHeadsX.getHeadsX().getKeys(false)) {

            ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta sm = (SkullMeta) s.getItemMeta();
            GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
            if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean(str + ".encode")) {
                byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", HeadsPlusConfigHeadsX.getHeadsX().getString(str + ".texture")).getBytes());
                gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
            } else {
                gm.getProperties().put("textures", new Property("texture", HeadsPlusConfigHeadsX.getHeadsX().getString(str + ".texture")));
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
    }
}
