package io.github.thatsmusic99;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
			configLoad();
		    getCommand("head").setExecutor(new Head());
		    getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		    getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
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
	public void configLoad() {
		try {
			if (!(getDataFolder().exists())) {
				getDataFolder().mkdirs();
				log.info("[HeadsPlus] Data folder wasn't found, created!");
			}
			File config = new File(getDataFolder(), "config.yml");
			String[] list = {};
            if (!(config.exists())) {
            	log.info("[HeadsPlus] Config wasn't found, created!");
            	this.getConfig().addDefault("blacklist", list);
            	this.getConfig().addDefault("blacklistOn", true);
            	this.getConfig().options().copyDefaults(true);
            	this.saveConfig();
            } else {
            	log.info("[HeadsPlus] Config found! Loading...");
            	this.getConfig().addDefault("blacklist", list);
            	this.getConfig().addDefault("blacklistOn", true);
            	this.getConfig().options().copyDefaults(true);
            	this.saveConfig();
            }
			
		} catch (Exception e) {
		    log.severe("[HeadsPlus] Error loading config!");
			e.printStackTrace();
		}
	   
	}
	public static HeadsPlus getInstance() {
		return instance;
	}
	

	
}
