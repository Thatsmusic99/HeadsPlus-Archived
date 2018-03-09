package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HPChallengeRewardTypes;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeDifficulty;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeTypes;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Challenge {

    private String configName;
    private String mainName;
    private String header;
    private List<String> description;
    private int requiredHeadAmount;
    private HeadsPlusChallengeTypes challengeType;
    private HPChallengeRewardTypes rewardType;
    private Object rewardValue;
    private int rewardItemAmount;
    private String headType;
    private int gainedXP;
    private HeadsPlusChallengeDifficulty difficulty;

    public Challenge(String configName, String mainName, String header, List<String> description, int requiredHeadAmount, HeadsPlusChallengeTypes challengeType, HPChallengeRewardTypes rewardType, Object rewardValue, int rewardItemAmount, String headType, int gainedXP, HeadsPlusChallengeDifficulty difficulty) {
        this.configName = configName;
        this.mainName = mainName;
        this.header = header;
        this.description = description;
        this.requiredHeadAmount = requiredHeadAmount;
        this.challengeType = challengeType;
        this.rewardType = rewardType;
        this.rewardValue = rewardValue;
        this.rewardItemAmount = rewardItemAmount;
        this.headType = headType;
        this.gainedXP = gainedXP;
        this.difficulty = difficulty;
    }

    public String getConfigName() {
        return configName;
    }

    public HeadsPlusChallengeTypes getChallengeType() {
        return challengeType;
    }

    public int getRequiredHeadAmount() {
        return requiredHeadAmount;
    }

    public HPChallengeRewardTypes getRewardType() {
        return rewardType;
    }

    public int getRewardItemAmount() {
        return rewardItemAmount;
    }

    public List<String> getDescription() {
        return description;
    }

    public Object getRewardValue() {
        return rewardValue;
    }

    public String getChallengeHeader() {
        return header;
    }

    public String getMainName() {
        return mainName;
    }

    public String getHeadType() {
        return headType;
    }

    public int getGainedXP() {
        return gainedXP;
    }

    public HeadsPlusChallengeDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean canComplete(Player p) throws SQLException {
        if (getChallengeType() == HeadsPlusChallengeTypes.MISC) {
            return true;
        } else if (getChallengeType() == HeadsPlusChallengeTypes.CRAFTING) {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, getHeadType(), "headspluscraft") >= getRequiredHeadAmount()) {
                return true;
            }
        } else if (getChallengeType() == HeadsPlusChallengeTypes.LEADERBOARD) {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, getHeadType(), "headspluslb") >= getRequiredHeadAmount()) {
                return true;
            }
        } else {
            if (HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, getHeadType(), "headsplussh") >= getRequiredHeadAmount()) {
                return true;
            }
        }
        return false;
    }

    public boolean isComplete(Player p) {
        FileConfiguration c = HeadsPlus.getInstance().hpchl.getConfig();
        if (c.getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").size() <= 0) {
            HeadsPlus.getInstance().hpchl.getConfig().addDefault("player-data." + p.getUniqueId().toString() + ".completed-challenges", new ArrayList<>());
            return false;
        } else {
            return c.getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").contains(getConfigName());
        }
    }

    public void complete(Player p, Inventory i, int slot) {
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().hpchl;
        List<String> str = hpc.getConfig().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges");
        str.add(getConfigName());
        hpc.getConfig().set("player-data." + p.getUniqueId().toString() + ".completed-challenges", str);
        ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getChallengeHeader()));
        List<String> lore = new ArrayList<>();
        for (String st : getDescription()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', st));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD).append("Reward: ");
        HPChallengeRewardTypes re = rewardType;
        StringBuilder sb2 = new StringBuilder();
        if (re == HPChallengeRewardTypes.ECO) {
            sb.append(ChatColor.GREEN).append("$").append(getRewardValue());
            sb2.append("$").append(getRewardValue());
        } else if (re == HPChallengeRewardTypes.GIVE_ITEM) {
            try {
                Material.valueOf(getRewardValue().toString().toUpperCase());
                sb
                        .append(ChatColor.GREEN)
                        .append(getRewardItemAmount())
                        .append(" ")
                        .append(WordUtils.capitalize(getRewardValue().toString().replaceAll("_", " ")))
                        .append(getRewardItemAmount() > 1 ? "(s)" : "");
                sb2
                        .append(getRewardItemAmount())
                        .append(" ")
                        .append(getRewardValue().toString().replaceAll("_", " "))
                        .append(getRewardItemAmount() > 1 ? "(s)" : "");
            } catch (IllegalArgumentException e) {
                //
            }
        }
        lore.add(sb.toString());
        lore.add(ChatColor.GOLD + "XP: " + ChatColor.GREEN + getGainedXP());
        lore.add(ChatColor.GREEN + "Completed!");
        im.setLore(lore);
        is.setItemMeta(im);
        i.setItem(slot, is);
        addXp(p);
        reward(p);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlus.getInstance().hpc.getConfig().getString("challenge-complete")
                    .replaceAll("%c", getMainName())
                    .replaceAll("%p", p.getName()))));
        }
        p.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().getReward() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + sb2.toString());
        p.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "XP: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + getGainedXP());
    }

    private void reward(Player p) {
        Economy econ = HeadsPlus.getInstance().econ;
        Permission perms = HeadsPlus.getInstance().perms;
        HPChallengeRewardTypes re = getRewardType();
        if (re == HPChallengeRewardTypes.ECO) {
            econ.depositPlayer(p, Double.valueOf(String.valueOf(getRewardValue())));
        } else if (re == HPChallengeRewardTypes.ADD_GROUP) {
            if (!perms.playerInGroup(p, (String) getRewardValue())) {
                perms.playerAddGroup(p, (String) getRewardValue());
            }
        } else if (re == HPChallengeRewardTypes.REMOVE_GROUP) {
            if (perms.playerInGroup(p, (String) getRewardValue())) {
                perms.playerRemoveGroup(p, (String) getRewardValue());
            }
        } else if (re == HPChallengeRewardTypes.GIVE_ITEM) {
            try {
                ItemStack is = new ItemStack(Material.valueOf(((String) getRewardValue()).toUpperCase()), getRewardItemAmount());
                if (p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(is);
                }
            } catch (IllegalArgumentException ex) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("Couldn't give reward to " + p.getName() + "! Details:");
                log.warning("Challenge name: " + getConfigName());
                log.warning("Difficulty: " + getChallengeType().name());
                log.warning("Item name: " + getRewardValue());
                log.warning("Item amount: " + getRewardItemAmount());
            }
        }
    }

    private void addXp(Player p) {
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().hpchl;
        if (hpc.getConfig().getInt("player-data." + p.getUniqueId().toString() + ".profile.xp") <= 0) {
            hpc.getConfig().addDefault("player-data." + p.getUniqueId().toString() + ".profile.xp", getGainedXP());
        } else {
            int a = hpc.getConfig().getInt("player-data." + p.getUniqueId().toString() + ".profile.xp");
            a += getGainedXP();
            hpc.getConfig().set("player-data." + p.getUniqueId().toString() + ".profile.xp", a);
        }
        hpc.getConfig().options().copyDefaults(true);
        hpc.save();
    }
}
