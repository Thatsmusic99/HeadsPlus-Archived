package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HeadsPlusChallenges {

    public FileConfiguration challenges;
    private File challengesF;

    public HeadsPlusChallenges() {
        chlngeEnable();
    }

    private void chlngeEnable() {
        reloadChlnges();
    }

    public void reloadChlnges() {
        boolean n = false;
        if (challengesF == null) {
            n = true;
            challengesF = new File(HeadsPlus.getInstance().getDataFolder(), "challenges.yml");
        }
        challenges = YamlConfiguration.loadConfiguration(challengesF);
        if (n) {
            loadChlnges();
        }
        saveChallenges();
    }

    private void loadChlnges() {
        challenges.options().header("HeadsPlus by Thatsmusic99 - Challenge configuration" +
                "\nKey for challenges:" +
                "\nHeader - what is displayed as the challenge title." +
                "\nDescription - Description for the challenge." +
                "\nType - what kind of challenge it is, valid values: SELLHEAD, CRAFTING AND LEADERBOARD" +
                "\nMin - The minimum amount of heads required to complete the challenge, if required." +
                "\nReward type - The type of reward at hand, valid values: ECO (to give money), ADD_GROUP (to give someone a new group), REMOVE_GROUP (to remove someone from a group because LOL), and GIVE_ITEM" +
                "\nReward value - What the reward is, for example: if Reward type is set to ECO, it would be for example 500, if ADD_GROUP, it would be the group name, etc." +
                "\nItem Amount - If using GIVE_ITEM as a reward type, you can set an it" +
                "\nHead type - The head type required to complete the challenge, use \"total\" for all types." +
                "\nXP - Amount of XP (HeadsPlus levels) that will be received.");

        for (HeadsPlusChallengeEnums hpc : HeadsPlusChallengeEnums.values()) {
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".name", hpc.dName);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".header", hpc.h);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".description", hpc.d);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".type", hpc.p.name());
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".min", hpc.m);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".reward-type", hpc.r.name());
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".reward-value", hpc.o instanceof Material ? ((Material) hpc.o).name() : hpc.o);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".item-amount", hpc.a);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".head-type", hpc.t);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".xp", hpc.exp);
        }
        getChallenges().addDefault("server-total.sellhead.total", 0);
        for (EntityType e : HeadsPlus.getInstance().de.ableEntities) {
            getChallenges().addDefault("server-total.sellhead." + e.name(), 0);
        }
        getChallenges().addDefault("server-total.crafting.total", 0);
        for (EntityType e : HeadsPlus.getInstance().de.ableEntities) {
            getChallenges().addDefault("server-total.crafting." + e.name(), 0);
        }
        getChallenges().options().copyDefaults(true);
        saveChallenges();
    }

    public FileConfiguration getChallenges() {
        return challenges;
    }

    public void saveChallenges() {
        if (challenges == null || challengesF == null) {
            return;
        }
        try {
            challenges.save(challengesF);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isChallengeCompleted(Player p, String s) {
        if (getChallenges().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").size() <= 0) {
            getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".completed-challenges", new ArrayList<>());
            return false;
        } else {
            return getChallenges().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").contains(s);
        }
    }

    private void addXp(Player p, int exp) {
        if (getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".profile.xp") <= 0) {
            getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".profile.xp", exp);
        } else {
            int a = getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".profile.xp");
            a += exp;
            getChallenges().set("player-data." + p.getUniqueId().toString() + ".profile.xp", a);
        }
        getChallenges().options().copyDefaults(true);
        saveChallenges();
    }

    private void reward(Player p, String s, String dif) {
        String re = getChallenges().getString("challenges." + dif + "." + s + ".reward-type");
        if (re.equalsIgnoreCase("ECO")) {
            HeadsPlus.getInstance().econ.depositPlayer(p, getChallenges().getDouble("challenges." + dif + "." + s + ".reward-value"));
        } else if (re.equalsIgnoreCase("ADD_GROUP")) {
            if (!HeadsPlus.getInstance().perms.playerInGroup(p, getChallenges().getString("challenges." + dif + "." + s + ".reward-value"))) {
                HeadsPlus.getInstance().perms.playerAddGroup(p, getChallenges().getString("challenges." + dif + "." + s + ".reward-value"));
            }
        } else if (re.equalsIgnoreCase("REMOVE_GROUP")) {
            if (HeadsPlus.getInstance().perms.playerInGroup(p, getChallenges().getString("challenges." + dif + "." + s + ".reward-value"))) {
                HeadsPlus.getInstance().perms.playerRemoveGroup(p, getChallenges().getString("challenges." + dif + "." + s + ".reward-value"));
            }
        } else if (re.equalsIgnoreCase("GIVE_ITEM")) {
            try {
                ItemStack is = new ItemStack(Material.valueOf(getChallenges().getString("challenges." + dif + "." + s + ".reward-value").toUpperCase()), getChallenges().getInt("challenges." + dif + "." + s +".item-amount"));
                if (p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(is);
                }
            } catch (IllegalArgumentException ex) {
                HeadsPlus.getInstance().getLogger().severe("Couldn't give reward to " + p.getName() + "! Details:");
                HeadsPlus.getInstance().getLogger().warning("Challenge name: " + s);
                HeadsPlus.getInstance().getLogger().warning("Difficulty: " + dif);
                HeadsPlus.getInstance().getLogger().warning("Item name: " + getChallenges().getString("challenges." + dif + "." + s + ".reward-value").toUpperCase());
                HeadsPlus.getInstance().getLogger().warning("Item amount: " + getChallenges().getInt("challenges." + dif + "." + s +".item-amount"));
            }
        }
    }
    public boolean canComplete(Player p, String s, String dif) throws SQLException {
        if (challenges.getString("challenges." + dif + "." + s + ".type").equalsIgnoreCase("MISC")) {
            return true;
        } else if (challenges.getString("challenges." + dif + "." + s + ".type").equalsIgnoreCase("SELLHEAD")) {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, challenges.getString("challenges." + dif + "." + s + ".head-type"), "headsplussh") >= challenges.getInt("challenges." + dif + "." + s + ".min")) {
                return true;
            }
        } else if (challenges.getString("challenges." + dif + "." + s + ".type").equalsIgnoreCase("CRAFTING")) {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, challenges.getString("challenges." + dif + "." + s + ".head-type"), "headspluscraft") >= challenges.getInt("challenges." + dif + "." + s + ".min")) {
                return true;
            }
        } else if (challenges.getString("challenges." + dif + "." + s + ".type").equalsIgnoreCase("LEADERBOARD")) {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, challenges.getString("challenges." + dif + "." + s + ".head-type"), "headspluslb") >= challenges.getInt("challenges." + dif + "." + s + ".min")) {
                return true;
            }
        }
        return false;
    }

    public void completeChallenge(Player p, String s, Inventory i, String dif, int in) {
        List<String> str = getChallenges().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges");
        str.add(s);
        getChallenges().set("player-data." + p.getUniqueId().toString() + ".completed-challenges", str);
        ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getChallenges().getString("challenges." + dif + "." + s + ".header")));
        List<String> lore = new ArrayList<>();
        for (String st : getChallenges().getStringList("challenges." + dif + "." + s + ".description")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', st));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD).append("Reward: ");
        String re = getChallenges().getString("challenges." + dif + "." + s + ".reward-type");
        StringBuilder sb2 = new StringBuilder();
        if (re.equalsIgnoreCase("ECO")) {
            sb.append(ChatColor.GREEN).append("$").append(getChallenges().getDouble("challenges." + dif + "." + s + ".reward-value"));
            sb2.append("$").append(getChallenges().getDouble("challenges." + dif + "." + s + ".reward-value"));
        } else if (re.equalsIgnoreCase("GIVE_ITEM")) {
            try {
                Material.valueOf(getChallenges().getString("challenges." + dif + "." + s + ".reward-value").toUpperCase());
                sb
                        .append(ChatColor.GREEN)
                        .append(getChallenges().getInt("challenges." + dif + "." + s +".item-amount"))
                        .append(" ")
                        .append(WordUtils.capitalize(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + dif + "." + s + ".reward-value").replaceAll("_", " ")))
                        .append(getChallenges().getInt("challenges." + dif + "." + s +".item-amount") > 1 ? "(s)" : "");
                sb2
                        .append(getChallenges().getInt("challenges." + dif + "." + s +".item-amount"))
                        .append(" ")
                        .append(WordUtils.capitalize(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + dif + "." + s + ".reward-value").replaceAll("_", " ")))
                        .append(getChallenges().getInt("challenges." + dif + "." + s +".item-amount") > 1 ? "(s)" : "");
            } catch (IllegalArgumentException e) {
                //
            }
        }
        lore.add(sb.toString());
        lore.add(ChatColor.GOLD + "XP: " + ChatColor.GREEN + getChallenges().getInt("challenges." + dif + "." + s +".xp"));
        lore.add(ChatColor.GREEN + "Completed!");
        im.setLore(lore);
        is.setItemMeta(im);
        i.setItem(in, is);
        addXp(p, getChallenges().getInt("challenges." + dif + "." + s +".xp"));
        reward(p, s, dif);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlus.getInstance().hpc.getMessages().getString("challenge-complete")
            .replaceAll("%c", getChallenges().getString("challenges." + dif + "." + s + ".name"))
            .replaceAll("%p", p.getName()))));
        }
        p.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().getReward() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + sb2.toString());
        p.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "XP: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + getChallenges().getInt("challenges." + dif + "." + s +".xp"));
    }
}
