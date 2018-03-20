package io.github.thatsmusic99.headsplus.config.levels;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.ConfigSettings;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class HeadsPlusLevels extends ConfigSettings {

    public HeadsPlusLevels() {
        this.conName = "levels";
        enable(false);
    }

    @Override
    public void reloadC(boolean a) {
        boolean n = false;
        if (configF == null) {
            n = true;
            configF = new File(HeadsPlus.getInstance().getDataFolder(), "levels.yml");
        }
        config = YamlConfiguration.loadConfiguration(configF);
        if (n) {
            load(false);
        }
        save();
    }




}
