package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusDataFile {
	public static FileConfiguration data;
	public static File dataF;
	
	public static FileConfiguration getHPData() {
		return data;
	}
	public static void loadHPData() {
		getHPData().getConfigurationSection("data");
		saveHPData();
	}
	public static void writeToData(UUID id, ItemStack item) {
		
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

}
