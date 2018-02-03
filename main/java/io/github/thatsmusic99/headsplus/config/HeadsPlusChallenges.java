package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class HeadsPlusChallenges {

    public FileConfiguration challenges;
    private File challengesF;

    public HeadsPlusChallenges() {
        chlngeEnable();
    }

    private void chlngeEnable() {

    }

    public void reloadChlnges() {
        if (challengesF == null) {
            challengesF = new File(HeadsPlus.getInstance().getDataFolder(), "challenges.yml");
        }
        challenges = YamlConfiguration.loadConfiguration(challengesF);
    }

    public void loadChlnges() {
        challenges.options().header("HeadsPlus by Thatsmusic99 - Challenge configuration" +
                "\nKey for challenges:" +
                "\nHeader - what is displayed as the challenge title." +
                "\nDescription - Description for the challenge." +
                "\nType - what kind of challenge it is, valid values: SELLHEAD, CRAFTING AND LEADERBOARD" +
                "\nMin - The minimum amount of heads required to complete the challenge, if required." +
                "\nReward type - The type of reward at hand, valid values: ECO (to give money), ADD_GROUP (to give someone a new group), REMOVE_GROUP (to remove someone from a group because LOL), and GIVE_ITEM" +
                "\nReward value - What the reward is, for example: if Reward type is set to ECO, it would be for example 500, if ADD_GROUP, it would be the group name, etc." +
                "\nItem Amount - If using GIVE_ITEM as a reward type, you can set an it" +
                "\nHead type - The head type required to complete the challenge, use \"total\" for all types.");

        for (HeadsPlusChallengeEnums hpc : HeadsPlusChallengeEnums.values()) {
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".header", hpc.h);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".description", hpc.d);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".type", hpc.p.name());
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".min", hpc.m);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".reward-type", hpc.r.name());
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".reward-value", hpc.o);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".item-amount", hpc.a);
            challenges.addDefault("challenges." + hpc.cd.name() + "." + hpc.n + ".head-type", hpc.t);
        }
    }
}
