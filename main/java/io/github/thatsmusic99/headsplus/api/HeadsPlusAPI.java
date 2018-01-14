package io.github.thatsmusic99.headsplus.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class HeadsPlusAPI {

    private HeadsPlusConfigHeadsX hpcHeadsX = new HeadsPlusConfigHeadsX();
    private HeadsPlusConfigHeads hpcHeads = new HeadsPlusConfigHeads();

    public ItemStack getHead(String option) {
        return hpcHeadsX.getSkull(option);
    }


    public boolean isSellable(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        if (is.getType() == Material.SKULL_ITEM) {
            for (String key : hpcHeads.mHeads) {
                if (key.equalsIgnoreCase("sheep")) {
                    for (String s : hpcHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                        for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN." + s).size(); i++) {
                            if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i))) {
                                Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                pro.setAccessible(true);
                                GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                                for (Property p : gm.getProperties().get("textures")) {
                                    if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i)))) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                        if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                            Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                            pro.setAccessible(true);
                            GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                            for (Property p : gm.getProperties().get("textures")) {
                                if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN").get(i)))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            for (String key : hpcHeads.uHeads) {
                for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                    if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                        Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                        pro.setAccessible(true);
                        GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                        for (Property p : gm.getProperties().get("textures")) {
                            if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN").get(i)))) {
                                return true;
                            }
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

                List<String> mHeads = hpcHeads.mHeads;
                List<String> uHeads = hpcHeads.uHeads;
                for (String key : mHeads) {
                    if (key.equalsIgnoreCase("sheep")) {
                        for (String s : hpcHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                            for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN." + s).size(); i++) {
                                if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i))) {
                                    return true;
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                            if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                                return true;
                            } else if ((owner.matches(hpcHeads.getHeads().getStringList("irongolemHeadN").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                                return true;
                            }
                        }
                    }

                }
                for (String key : uHeads) {
                    for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                        if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                            return true;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public ItemStack createSkull(String texture, String displayname) {
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

    public String getSkullType(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        for (String key : hpcHeads.mHeads) {
            if (key.equalsIgnoreCase("sheep")) {
                for (String s : hpcHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                    for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN." + s).size(); i++) {
                        if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i))) {
                            Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                            pro.setAccessible(true);
                            GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                            for (Property p : gm.getProperties().get("textures")) {
                                if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i)))) {
                                    return key;
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                    if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                        Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                        pro.setAccessible(true);
                        GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                        for (Property p : gm.getProperties().get("textures")) {
                            if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN").get(i)))) {
                                return key;
                            }
                        }
                    }
                }
            }


        }
        for (String key : hpcHeads.uHeads) {
            for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                if (hpcHeadsX.isHPXSkull(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                    Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getHeads().getStringList(key + "HeadN").get(i)))) {
                            return key;
                        }
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

            List<String> mHeads = hpcHeads.mHeads;
            List<String> uHeads = hpcHeads.uHeads;
            for (String key : mHeads) {
                if (key.equalsIgnoreCase("sheep")) {
                    for (String s : hpcHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                        for (int i = 0; i < hpcHeads.getHeads().getStringList("sheepHeadN." + s).size(); i++) {
                            if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN." + s).get(i))) {
                                return key;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                        if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                            return key;
                        } else if ((owner.matches(hpcHeads.getHeads().getStringList("irongolemHeadN").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                            return key;
                        }
                    }
                }
            }
            for (String key : uHeads) {
                for (int i = 0; i < hpcHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                    if (owner.matches(hpcHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                        return key;
                    }
                }
            }
            return "player";

        }
        return "invalid";
    }

    public int getPlayerInLeaderboards(OfflinePlayer p, String section) throws SQLException {
        return new HeadsPlusLeaderboards().getScores(section).get(p);
    }

    public LinkedHashMap<OfflinePlayer, Integer> getScores(String section) throws SQLException {
        return new HeadsPlusLeaderboards().getScores(section);
    }

}
