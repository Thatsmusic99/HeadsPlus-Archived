package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HeadsPlusConfigHeadsX {

    private static boolean s = false;
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
        for (HeadsXEnums e : HeadsXEnums.values()) {
            getHeadsX().addDefault("heads." + e.name + ".database", true);
            getHeadsX().addDefault("heads." + e.name + ".encode", false);
            getHeadsX().addDefault("heads." + e.name + ".displayname", e.dn);
            getHeadsX().addDefault("heads." + e.name + ".texture", e.tex);
        }
        getHeadsX().options().copyDefaults(true);
        saveHeadsX();
    }
    public static FileConfiguration getHeadsX() {
        return headsx;
    }
    public static void reloadHeadsX() {
        if (headsxf == null) {
            headsxf = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
            s = true;
        }
        headsx = YamlConfiguration.loadConfiguration(headsxf);
        if (s) {
            loadHeadsX();
        }
        saveHeadsX();
    }
    public static void headsxEnable() {
        reloadHeadsX();
        if (s) {
            loadHeadsX();
        }
    }
}
