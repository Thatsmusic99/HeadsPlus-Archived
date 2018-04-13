package io.github.thatsmusic99.headsplus.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class HeadsPlusAPI {

    private final HeadsPlusConfigHeadsX hpcHeadsX = HeadsPlus.getInstance().hpchx;
    private final HeadsPlusConfigHeads hpcHeads = HeadsPlus.getInstance().hpch;

    public ItemStack getHead(String option) throws NoSuchFieldException, IllegalAccessException {
        return hpcHeadsX.getSkull(option);
    }

    public boolean isSellable(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        if (is.getType() == Material.SKULL_ITEM) {
            if (HeadsPlus.getInstance().nms.isSellable(is)) {
                for (String key : hpcHeads.mHeads) {
                    if (key.equalsIgnoreCase("sheep")) {
                        for (String s : hpcHeads.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                            for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name." + s).size(); i++) {
                                if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i))) {
                                    Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                    pro.setAccessible(true);
                                    GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                                    for (Property p : gm.getProperties().get("textures")) {
                                        if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i)))) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                            if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                                Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                pro.setAccessible(true);
                                GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                                for (Property p : gm.getProperties().get("textures")) {
                                    if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name").get(i)))) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                for (String key : hpcHeads.uHeads) {
                    for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                        if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                            Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                            pro.setAccessible(true);
                            GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                            for (Property p : gm.getProperties().get("textures")) {
                                if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name").get(i)))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            SkullMeta skullM = (SkullMeta) is.getItemMeta();
            String owner = HeadsPlus.getInstance().nms.getSkullOwnerName(skullM);

           // if ((skullM.getLore() != null) &&  (skullM.getLore().equals(ls))) {
            if (HeadsPlus.getInstance().nms.isSellable(is)) {
                List<String> mHeads = hpcHeads.mHeads;
                List<String> uHeads = hpcHeads.uHeads;
                for (String key : mHeads) {
                    if (key.equalsIgnoreCase("sheep")) {
                        for (String s : hpcHeads.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                            for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name." + s).size(); i++) {
                                if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i))) {
                                    return true;
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                            if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                                return true;
                            } else if ((owner.matches(hpcHeads.getConfig().getStringList("irongolem.name").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                                return true;
                            }
                        }
                    }

                }
                for (String key : uHeads) {
                    for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                        if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
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
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
        }
        profileField.setAccessible(true);
        try {
            profileField.set(sm, gm);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
        }
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        return s;
    }

    public String getSkullType(ItemStack is) throws NoSuchFieldException, IllegalAccessException {
        for (String key : hpcHeads.mHeads) {
            if (key.equalsIgnoreCase("sheep")) {
                for (String s : hpcHeads.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                    for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name." + s).size(); i++) {
                        if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i))) {
                            Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                            pro.setAccessible(true);
                            GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                            if (gm != null) {
                                if (gm.getProperties() != null) {
                                    for (Property p : gm.getProperties().get("textures")) {
                                        if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i)))) {
                                            return key;
                                        }
                                    }
                                } else {
                                    return "invalid";
                                }
                            } else {
                                return "invalid";
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                    if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                        Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                        pro.setAccessible(true);
                        GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                        for (Property p : gm.getProperties().get("textures")) {
                            if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name").get(i)))) {
                                return key;
                            }
                        }
                    }
                }
            }


        }
        for (String key : hpcHeads.uHeads) {
            for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                if (hpcHeadsX.isHPXSkull(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                    Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(hpcHeadsX.getTextures(hpcHeads.getConfig().getStringList(key + ".name").get(i)))) {
                            return key;
                        }
                    }
                }
            }

        }
        SkullMeta skullM = (SkullMeta) is.getItemMeta();
        String owner = HeadsPlus.getInstance().nms.getSkullOwnerName(skullM);
        List<String> ls = new ArrayList<>();
        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
        }

        if ((skullM.getLore() != null) &&  (skullM.getLore().equals(ls))) {

            List<String> mHeads = hpcHeads.mHeads;
            List<String> uHeads = hpcHeads.uHeads;
            for (String key : mHeads) {
                if (key.equalsIgnoreCase("sheep")) {
                    for (String s : hpcHeads.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                        for (int i = 0; i < hpcHeads.getConfig().getStringList("sheep.name." + s).size(); i++) {
                            if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name." + s).get(i))) {
                                return key;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                        if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                            return key;
                        } else if ((owner.matches(hpcHeads.getConfig().getStringList("irongolem.name").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                            return key;
                        }
                    }
                }
            }
            for (String key : uHeads) {
                for (int i = 0; i < hpcHeads.getConfig().getStringList(key + ".name").size(); i++) {
                    if (owner.matches(hpcHeads.getConfig().getStringList(key + ".name").get(i))) {
                        return key;
                    }
                }
            }
            return "player";

        }
        return "invalid";
    }

    @Deprecated
    public int getPlayerInLeaderboards(OfflinePlayer p, String section) throws SQLException {
        return HeadsPlus.getInstance().hplb.getScores(section).get(p);
    }

    @Deprecated
    public LinkedHashMap<OfflinePlayer, Integer> getScores(String section) throws SQLException {
        return HeadsPlus.getInstance().hplb.getScores(section);
    }

    public int getPlayerInLeaderboards(OfflinePlayer p, String section, String database) throws SQLException {
        try {
            return HeadsPlus.getInstance().mySQLAPI.getScores(section, database).get(p);
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public LinkedHashMap<OfflinePlayer, Integer> getScores(String section, String database) throws SQLException {
        return HeadsPlus.getInstance().mySQLAPI.getScores(section, database);
    }

    public List<Challenge> getChallenges() {
        return HeadsPlus.getInstance().challenges;
    }

    public Challenge getChallenge(String challengeName) {
        for (Challenge c : getChallenges()) {
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', c.getChallengeHeader())).equals(challengeName)) {
                return c;
            }
        }
        return null;
    }

    public Challenge getChallengeByConfigName(String name) {
        for (Challenge c : getChallenges()) {
            if (c.getConfigName().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
