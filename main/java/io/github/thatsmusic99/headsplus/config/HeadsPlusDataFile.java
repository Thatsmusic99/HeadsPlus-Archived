package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusDataFile {
	public static FileConfiguration data;
	public static File dataF;
	
	public static FileConfiguration getHPData() {
		return data;
	}
	public static void loadHPData() {
		reloadHPData();
		getHPData().options().copyDefaults(true);
		saveHPData();
	}
	// Unused
	public static void writeToData(UUID id, ItemStack item, SkullMeta skull) {
		if (data == null || dataF == null) {
			saveHPData();
		}
		ConfigurationSection uuid = getHPData().createSection(id.toString());
		uuid.addDefault("ItemStack", item);
		uuid.addDefault("SkullMeta", skull);
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
	public static ItemStack getItemFromData(UUID id, ItemStack item) {
		ConfigurationSection uuid = getHPData().getConfigurationSection(id.toString());
		return uuid.getItemStack("ItemStack");
	}
	public static SkullMeta getMetaFromData(UUID id, SkullMeta skull) {
		ConfigurationSection uuid = getHPData().getConfigurationSection(id.toString());
		return (SkullMeta) uuid.get("SkullMeta");
	}
	public static void reloadHPData() {
		if (dataF == null) {
			dataF = new File(HeadsPlus.getInstance().getDataFolder(), "data.yml");
		}
		data = YamlConfiguration.loadConfiguration(dataF);
		saveHPData();
	}

}
