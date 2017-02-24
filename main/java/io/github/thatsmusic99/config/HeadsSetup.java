package io.github.thatsmusic99.config;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsSetup {
	File dataFolder = HeadsPlus.getInstance().getDataFolder();
	Logger log = HeadsPlus.getInstance().getLogger();
	private FileConfiguration mConfig, hConfig;
	public FileConfiguration getMainConfig() {
		return this.mConfig;
	}
	public void mainConfigLoad() {
		try {
			if (!(dataFolder.exists())) {
				dataFolder.mkdirs();
				log.info("[HeadsPlus] Data folder wasn't found, created!");
			}
			File mConfig = new File(dataFolder, "config.yml");
			String[] list = {};
            if (!(mConfig.exists())) {
            	log.info("[HeadsPlus] Config wasn't found, created!");
            	this.getMainConfig().addDefault("blacklist", list);
            	this.getMainConfig().addDefault("blacklistOn", true);
            	this.getMainConfig().options().copyDefaults(true);
            	this.saveResource("config.yml", false);
            } else {
            	log.info("[HeadsPlus] Config found! Loading...");
            	this.getConfig().addDefault("blacklist", list);
            	this.getConfig().addDefault("blacklistOn", true);
            	this.getConfig().addDefault("playerDeathHead", 100);
            	this.getConfig().addDefault("zombieHeadC", 75);
            	this.getConfig().addDefault("zombieHead", "MHF_Zombie");
            	this.getConfig().options().copyDefaults(true);
            	this.saveConfig();
            }
			
		} catch (Exception e) {
		    log.severe("[HeadsPlus] Error loading config!");
			e.printStackTrace();
		}
	   
	}
}
