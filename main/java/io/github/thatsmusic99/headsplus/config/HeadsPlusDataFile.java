package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusDataFile {
	private static FileConfiguration data;
	private static File dataF;
	
	public static FileConfiguration getHPData() {
		return data;
	}
	public static void loadHPData() {
		reloadHPData();
		getHPData().options().copyDefaults(true);
		saveHPData();
	}
	public static void saveHPData() {
		if (dataF == null || data == null) {
			return;
		}
		try {
			data.save(dataF);
		} catch (IOException e) {
			HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save data file!");
			e.printStackTrace();
		}
	}
	public static void reloadHPData() {
		if (dataF == null) {
			dataF = new File(HeadsPlus.getInstance().getDataFolder(), "data.yml");
		}
		data = YamlConfiguration.loadConfiguration(dataF);
		saveHPData();
	}

}
