package io.github.thatsmusic99.headsplus.config.challenges;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
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
        addChallenges();
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
        if (!HeadsPlus.getInstance().con) {
            getChallenges().addDefault("server-total.sellhead.total", 0);
            for (EntityType e : HeadsPlus.getInstance().de.ableEntities) {
                getChallenges().addDefault("server-total.sellhead." + e.name(), 0);
            }
            getChallenges().addDefault("server-total.crafting.total", 0);
            for (EntityType e : HeadsPlus.getInstance().de.ableEntities) {
                getChallenges().addDefault("server-total.crafting." + e.name(), 0);
            }
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

    private void addChallenges() {
        for (String st : getChallenges().getConfigurationSection("challenges").getKeys(false)) {
            for (String s : getChallenges().getConfigurationSection("challenges." + st).getKeys(false)) {
                String name = getChallenges().getString("challenges." + st + "." + s + ".name");
                String header = getChallenges().getString("challenges." + st + "." + s + ".header");
                List<String> desc = getChallenges().getStringList("challenges." + st + "." + ".description");
                HeadsPlusChallengeTypes type;
                try {
                    type = HeadsPlusChallengeTypes.valueOf(getChallenges().getString("challenges." + st + "." + s + ".type").toUpperCase());
                } catch (Exception ex) {
                    continue;
                }
                int min = getChallenges().getInt("challenges." + st + "." + s + ".min");
                HPChallengeRewardTypes reward;
                try {
                    reward = HPChallengeRewardTypes.valueOf(getChallenges().getString("challenges." + st + "." + s + ".reward-type").toUpperCase());
                } catch (Exception e) {
                    continue;
                }
                Object rewardVal = getChallenges().get("challenges." + st + "." + s + ".reward-value");
                int items = getChallenges().getInt("challenges." + st + "." + s + ".item-amount");
                String headType = getChallenges().getString("challenges." + st + "." + s + ".head-type");
                int xp = getChallenges().getInt("challenges." + st + "." + s + ".xp");

                Challenge c = new Challenge(s, name, header, desc, min, type, reward, rewardVal, items, headType, xp);
                HeadsPlus.getInstance().challenges.add(c);

            }
        }

    }
}
