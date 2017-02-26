package io.github.thatsmusic99.headsplus.config;


import java.io.File;
import java.util.Arrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class mainConfig {
	
	private static mainConfig instance;
	private static FileConfiguration mConfig;
	private static File dataFolder = HeadsPlus.getInstance().getDataFolder();
	private static File mConfigF;
	
	private static FileConfiguration mConfig() {
		return mConfig;
	}
	
	public void setupMConfig() {
		reloadMainConfig();
		loadMainConfig();
		saveMainConfig();
		reloadMainConfig();
	}
	
	private static void loadMainConfig() {
		
		String header = "HeadsPlus by Thatsmusic99, source code to not be duplicated in any form./n";
		header = header.concat("Disclaimer: I am not responsible for any bugs that are introduced if you do not follow the config instructions.\n");
		header = header.concat("Available options:\n");
		header = header.concat(" - blacklist: [] - The main blacklist function for /head, NOT CUSTOM RECIPES./n");
		header = header.concat(" - blacklistOn: true - Turns the blacklist for /head on and off.");
		String[] blacklist = {};
		mConfig().addDefault("blacklist", Arrays.asList(blacklist));
		mConfig().addDefault("blacklistOn", true);
		mConfig().options().copyDefaults(true);
		saveMainConfig();		
		
	}
	public static void reloadMainConfig() {
		if (!(dataFolder.exists())) {
			dataFolder.mkdirs();
		}
		if (mConfigF == null) {
			@SuppressWarnings("unused")
			File mConfigF = new File(dataFolder, "config.yml");
		}
		mConfig = YamlConfiguration.loadConfiguration(mConfigF);
	}
	public static void saveMainConfig() {
	    if (mConfig == null || mConfigF == null) {
		    return;
	    }
	    try {
	    	mConfig.save(mConfigF);
	    } catch (Exception e) {
	    	HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to save config.");
	    	e.printStackTrace();
	    }
	}
	public static mainConfig getInstance() {
		return instance;
	}

}
