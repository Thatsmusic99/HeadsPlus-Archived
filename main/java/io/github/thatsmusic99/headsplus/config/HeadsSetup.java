package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsSetup {
	File dataFolder = HeadsPlus.getInstance().getDataFolder();
	Logger log = HeadsPlus.getInstance().getLogger();
	private FileConfiguration mConfig, hConfig;
	private static HeadsSetup instance;
	
	public FileConfiguration getMainConfig() {
		return this.mConfig;
	}
	public FileConfiguration getHeadsConfig() {
		return this.hConfig;
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
            	new File(dataFolder, "config.yml");
            } else {
            	log.info("[HeadsPlus] Config found! Loading...");
            	this.getMainConfig().addDefault("blacklist", list);
            	this.getMainConfig().addDefault("blacklistOn", true);
            	this.getMainConfig().addDefault("playerDeathHead", 100);
            	this.getMainConfig().addDefault("zombieHeadC", 75);
            	this.getMainConfig().addDefault("zombieHead", "MHF_Zombie");
            	this.getMainConfig().options().copyDefaults(true);
            	YamlConfiguration.loadConfiguration(mConfig);
            }
			
		} catch (Exception e) {
		    log.severe("[HeadsPlus] Error loading config!");
			e.printStackTrace();
		}
	   
	}
	public void headsConfigLoad() {
		try {
			if (!(dataFolder.exists())) {
				dataFolder.mkdirs();
				log.info("[HeadsPlus] Data folder wasn't found, created!");
			}
			File hConfig = new File(dataFolder, "heads.yml");
			if (!(hConfig.exists())) {
				log.info("[HeadsPlus] Head options config wasn't found, creating!");
				this.getHeadsConfig().addDefault("playerDeathHead", 100);
            	this.getHeadsConfig().addDefault("zombieHeadC", 75);
            	this.getHeadsConfig().addDefault("zombieHead", "MHF_Zombie");
            	this.getHeadsConfig().options().copyDefaults(true);
            	new File(dataFolder, "heads.yml");
			} else {
				log.info("[HeadsPlus] Heads config found! Loading...");
				this.getHeadsConfig().addDefault("playerDeathHead", 100);
            	this.getHeadsConfig().addDefault("zombieHeadC", 75);
            	this.getHeadsConfig().addDefault("zombieHead", "MHF_Zombie");
            	this.getHeadsConfig().options().copyDefaults(true);
            	YamlConfiguration.loadConfiguration(hConfig);
			}
		} catch (Exception e) {
			log.severe("[HeadsPlus] Error loading config!");
			e.printStackTrace();
		}
	}
	public static HeadsSetup getInstance() {
		
		return instance;
	}
	public void onEnable() {
		instance = this;
		this.mainConfigLoad();
		this.headsConfigLoad();
	}
}
