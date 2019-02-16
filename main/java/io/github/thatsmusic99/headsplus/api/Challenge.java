package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.config.challenges.HPChallengeRewardTypes;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeDifficulty;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeTypes;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.MaterialTranslator;
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

    // I
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
        HeadsPlusAPI hapi = HeadsPlus.getInstance().getAPI();
        if (getChallengeType() == HeadsPlusChallengeTypes.MISC) {
            return true;
        } else if (getChallengeType() == HeadsPlusChallengeTypes.CRAFTING) {
            return hapi.getPlayerInLeaderboards(p, getHeadType(), "headspluscraft") >= getRequiredHeadAmount();
        } else if (getChallengeType() == HeadsPlusChallengeTypes.LEADERBOARD) {
            return hapi.getPlayerInLeaderboards(p, getHeadType(), "headspluslb") >= getRequiredHeadAmount();
        } else {
            return hapi.getPlayerInLeaderboards(p, getHeadType(), "headsplussh") >= getRequiredHeadAmount();
        }
    }

    public boolean isComplete(Player p) {
        return HeadsPlus.getInstance().getScores().getCompletedChallenges(p.getUniqueId().toString()).contains(getConfigName());
    }

    public void complete(Player p, Inventory i, int slot) {
        HeadsPlus hp = HeadsPlus.getInstance();
        HPPlayer player = HPPlayer.getHPPlayer(p);
        player.addCompleteChallenge(this);
        ItemStack is = hp.getNMS().getColouredBlock(MaterialTranslator.BlockType.TERRACOTTA, 13);
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
        player.addXp(getGainedXP());
        reward(p);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(hp.getMessagesConfig().getString("challenge-complete")
                    .replaceAll("\\{challenge}", getMainName())
                    .replaceAll("\\{name}", p.getName()));
        }
        p.sendMessage(hp.getThemeColour(4) + LocaleManager.getLocale().getReward() + hp.getThemeColour(2) + sb2.toString());
        p.sendMessage(hp.getThemeColour(4) + "XP: " + hp.getThemeColour(2) + getGainedXP());
    }

    private void reward(Player p) {
        HPChallengeRewardTypes re = getRewardType();
        HeadsPlus hp = HeadsPlus.getInstance();
        HeadsPlusMessagesConfig hpc = hp.getMessagesConfig();
        Permission perms = hp.getPermissions();

        if (re == HPChallengeRewardTypes.ECO) {
            if (hp.econ()) {
                hp.getEconomy().depositPlayer(p, Double.valueOf(String.valueOf(getRewardValue())));
            } else {
                hp.log.warning(hpc.getString("no-vault-2"));
            }

        } else if (re == HPChallengeRewardTypes.ADD_GROUP) {
            if (hp.econ()) {
                if (!perms.playerInGroup(p, (String) getRewardValue())) {
                    perms.playerAddGroup(p, (String) getRewardValue());
                }
            } else {
                hp.log.warning(hpc.getString("no-vault-2"));
            }

        } else if (re == HPChallengeRewardTypes.REMOVE_GROUP) {
            if (hp.econ()) {
                if (perms.playerInGroup(p, (String) getRewardValue())) {
                    perms.playerRemoveGroup(p, (String) getRewardValue());
                }
            } else {
                hp.log.warning(hpc.getString("no-vault-2"));
            }
        } else if (re == HPChallengeRewardTypes.GIVE_ITEM) {
            try {
                ItemStack is = new ItemStack(Material.valueOf(((String) getRewardValue()).toUpperCase()), getRewardItemAmount());
                if (p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(is);
                }
            } catch (IllegalArgumentException ex) {
                Logger log = hp.getLogger();
                log.severe("Couldn't give reward to " + p.getName() + "! Details:");
                log.warning("Challenge name: " + getConfigName());
                log.warning("Difficulty: " + getChallengeType().name());
                log.warning("Item name: " + getRewardValue());
                log.warning("Item amount: " + getRewardItemAmount());
            }
        }
    }

}
