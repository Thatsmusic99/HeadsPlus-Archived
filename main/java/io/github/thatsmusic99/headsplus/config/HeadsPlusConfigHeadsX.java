package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HeadsPlusConfigHeadsX {
    private static FileConfiguration headsx;
    private static File headsxf;

    private static void saveHeadsX() {
        if (headsx == null || headsxf == null) {
            return;
        }
        try {
            headsx.save(headsxf);
        } catch (IOException e) {
            HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save headsx.yml!");
            e.printStackTrace();
        }
    }
    public static void loadHeadsX() {
        getHeadsX().options().header("HeadsPlus by Thatsmusic99 " +
                "\n WARNING: This is an advanced section of the plugin. If you do not know what you a doing with it, please do not use it due to risk of crashing your own and other's games. " +
                "\n For more information visit the GitHub wiki for HeadsX.yml: ");
        saveHeadsX();
    }
    public static FileConfiguration getHeadsX() {
        return headsx;
    }
    public static void reloadHeadsX() {
        if (headsxf == null) {
            headsxf = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
        }
        headsx = YamlConfiguration.loadConfiguration(headsxf);
        loadHeadsX();
        saveHeadsX();
    }
    public static void headsxEnable() {
        reloadHeadsX();
        loadHeadsX();
    }
}
