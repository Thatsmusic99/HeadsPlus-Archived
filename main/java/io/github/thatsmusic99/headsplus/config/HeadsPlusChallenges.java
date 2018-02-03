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
        for (HeadsPlusChallengeEnums hpc : HeadsPlusChallengeEnums.values()) {
            
        }
    }
}
