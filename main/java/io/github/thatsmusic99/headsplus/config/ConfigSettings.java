package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigSettings {

    public FileConfiguration config;
    public File configF;
    public String conName = "";

    public void enable(boolean nullp) {
        reloadC(nullp);
        load(nullp);
    }

    public void load(boolean nullp) {
          getConfig().options().copyDefaults(true);
          save();
    }

    public void reloadC(boolean nullp) {
        if (configF == null) {
            configF = new File(HeadsPlus.getInstance().getDataFolder(), conName + ".yml");
        }
        config = YamlConfiguration.loadConfiguration(configF);
        load(nullp);
        save();
    }

    public void save() {
        if (configF == null || config == null) {
            return;
        }
        try {
            config.save(configF);
        } catch (IOException e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
