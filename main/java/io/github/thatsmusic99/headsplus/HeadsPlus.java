package io.github.thatsmusic99.headsplus;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {	
	public Logger log = Logger.getLogger("Minecraft");
	private static HeadsPlus instance;
	public PluginDescriptionFile pluginYml = getDescription();
	public String author = pluginYml.getAuthors().toString();
	public String version = pluginYml.getVersion();
		
	FileConfiguration config;
	File cfile;
	
	@Override
	public void onEnable() {
		try { 
			instance = this;
			this.mainConfigLoad();
			this.headsConfigLoad();
		    this.getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		    this.getCommand("head").setExecutor(new Head());
		    log.info("[HeadsPlus] HeadsPlus has been enabled.");
		} catch (Exception e) {
			log.severe("[HeadsPlus] Error enabling HeadsPlus!");
			e.printStackTrace();
		}
    }
	@Override
	public void onDisable() {
		log.info("[HeadsPlus] HeadsPlus has been disabled.");
	}
	
	public static HeadsPlus getInstance() {
		return instance;
		
	}
	File dataFolder = this.getDataFolder();
	private FileConfiguration mConfig, hConfig;
	
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
            if (!(mConfig.exists())) {
            	log.info("[HeadsPlus] Config wasn't found, created!");
            	mConfig.createNewFile();
            	Reader mConfigSetup = new InputStreamReader(this.getResource("config.yml"));
            	if (mConfigSetup != null) {
            		YamlConfiguration mConfigDef = YamlConfiguration.loadConfiguration(mConfigSetup);
            		((Configuration) mConfig).setDefaults(mConfigDef);
            	}
            	
            } else {
            	log.info("[HeadsPlus] Config found! Loading...");
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
				hConfig.createNewFile();
				Reader hConfigSetup = new InputStreamReader(this.getResource("heads.yml"));
            	if (hConfigSetup != null) {
            		YamlConfiguration hConfigDef = YamlConfiguration.loadConfiguration(hConfigSetup);
            		((Configuration) hConfig).setDefaults(hConfigDef);
            	}
				
			} else {
				log.info("[HeadsPlus] Heads config found! Loading...");
            	YamlConfiguration.loadConfiguration(hConfig);
			}
		} catch (Exception e) {
			log.severe("[HeadsPlus] Error loading config!");
			e.printStackTrace();
		}
	}
	// TODO need to grab those config things and shove them into a file of their own.
    // TODO fix config loading.

	

	
}
