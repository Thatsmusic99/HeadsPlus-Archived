package io.github.thatsmusic99;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {	
	
	public static Logger log = Logger.getLogger("Minecraft");
	public boolean blacklistOn = this.getConfig().getBoolean("blacklistOn");
	public static HeadsPlus instance() {
		HeadsPlus instance = null;
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		getCommand("head").setExecutor(new Head());
		getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
		this.createConfig();
		getConfig().addDefault("blacklist", "[]");
		getConfig().set("blacklist", Arrays.asList());
		getConfig().addDefault("blacklistOn", true);
		log.info("[HeadsPlus] HeadsPlus has been enabled.");
    }
	@Override
	public void onDisable() {
		log.info("[HeadsPlus] HeadsPlus has been disabled.");
	}
	private void createConfig() {
	    try {
	        if (!getDataFolder().exists()) {
	            getDataFolder().mkdirs();
	        }
	        File file = new File(getDataFolder(), "config.yml");
	        if (!file.exists()) {
	            log.info("[HeadsPlus] Config.yml not found, creating!");
	            saveDefaultConfig();
	        } else {
	            log.info("[HeadsPlus] Config.yml found, loading!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();

	    }

	}
}