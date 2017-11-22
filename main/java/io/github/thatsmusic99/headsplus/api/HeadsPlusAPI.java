package io.github.thatsmusic99.headsplus.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HeadsPlusAPI {

    public static ItemStack getHead(String option) {
        return HeadsPlusConfigHeadsX.getSkull(option);
    }

    public static boolean isSellable(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        if (is.getType() == Material.SKULL_ITEM) {
            for (String key : HeadsPlusConfigHeads.mHeads) {
                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                    Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                            return true;
                        }
                    }
                }
            }
            for (String key : HeadsPlusConfigHeads.uHeads) {
                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                    Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                            return true;
                        }
                    }
                }
            }
            SkullMeta skullM = (SkullMeta) is.getItemMeta();
            String owner = skullM.getOwner();
            List<String> ls = new ArrayList<>();
            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
            }

            if ((skullM.getLore() != null) &&  (skullM.getLore().equals(ls))) {

                List<String> mHeads = HeadsPlusConfigHeads.mHeads;
                List<String> uHeads = HeadsPlusConfigHeads.uHeads;
                for (String key : mHeads) {

                    if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                        return true;
                    } else if ((owner.matches(HeadsPlusConfigHeads.getHeads().getString("irongolemHeadN"))) && (key.equalsIgnoreCase("irongolem"))) {
                        return true;
                    }
                }
                for (String key : uHeads) {
                    if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                        return true;
                    }
                }

                return true;

            }
        }
        return false;
    }

    public static ItemStack createSkull(String texture, String displayname) {
        ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) s.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");

        gm.getProperties().put("textures", new Property("texture", texture));

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
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        return s;
    }

    public static String getSkullType(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        for (String key : HeadsPlusConfigHeads.mHeads) {
            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                pro.setAccessible(true);
                GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                for (Property p : gm.getProperties().get("textures")) {
                    if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                        return key;
                    }
                }
            }
        }
        for (String key : HeadsPlusConfigHeads.uHeads) {
            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                pro.setAccessible(true);
                GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                for (Property p : gm.getProperties().get("textures")) {
                    if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                        return key;
                    }
                }
            }
        }
        SkullMeta skullM = (SkullMeta) is.getItemMeta();
        String owner = skullM.getOwner();
        List<String> ls = new ArrayList<>();
        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
        }

        if ((skullM.getLore() != null) &&  (skullM.getLore().equals(ls))) {

            List<String> mHeads = HeadsPlusConfigHeads.mHeads;
            List<String> uHeads = HeadsPlusConfigHeads.uHeads;
            for (String key : mHeads) {

                if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                    return key;
                } else if ((owner.matches(HeadsPlusConfigHeads.getHeads().getString("irongolemHeadN"))) && (key.equalsIgnoreCase("irongolem"))) {
                    return key;
                }
            }
            for (String key : uHeads) {
                if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                    return key;
                }
            }

            return "player";

        }
        return "invalid";
    }



}
