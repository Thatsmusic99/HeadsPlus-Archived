package io.github.thatsmusic99.headsplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thatsmusic99.headsplus.crafting.ZombieRecipe;
import io.github.thatsmusic99.headsplus.events.HeadInteractEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
			setupMConfig();
			getServer().addRecipe(ZombieRecipe.getInstance().zombieRecipe);
			getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
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
	
	
	// Main config
	
	private static FileConfiguration mConfig;
	private static File mConfigF;
	
	public static FileConfiguration mConfig() {
		return mConfig;
	}
	
	public void setupMConfig() {
		try {
		    reloadMainConfig();
		    loadMainConfig();
		    saveMainConfig();
		    reloadMainConfig();
		} catch (Exception e) {
			HeadsPlus.getInstance().log.severe("[HeadsPlus] Something went wrong enabling the config!");
			e.printStackTrace();
		}
	}
	
	private void loadMainConfig() {
		
		String[] blacklist = {};
		mConfig().addDefault("blacklist", Arrays.asList(blacklist));
		mConfig().addDefault("blacklistOn", true);
		mConfig().options().copyDefaults(true);
		saveMainConfig();		
		
	}
	public void reloadMainConfig() {
		if (!(getDataFolder().exists())) {
			getDataFolder().mkdirs();
		}
		mConfigF = new File(getDataFolder(), "config.yml");
		if (!(mConfigF.exists())) {
			try {
			    mConfigF.createNewFile();
			} catch (IOException ex) {
				log.severe("[HeadsPlus] Failed to create main config!");
				ex.printStackTrace();
			}
	    mConfig = YamlConfiguration.loadConfiguration(mConfigF);
		}
	}
	public void saveMainConfig() {
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
	
	
	




	// TODO need to grab those config things and shove them into a file of their own.
    // TODO fix config loading.
	// TODO Add crafting recipe? :)

	

	
}
