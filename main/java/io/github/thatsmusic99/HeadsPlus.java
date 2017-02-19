package io.github.thatsmusic99;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {	
	
	public static Logger log = Logger.getLogger("Minecraft");
	public boolean blacklistOn = this.getConfig().getBoolean("blacklistOn");
	public static HeadsPlus instance() {
		HeadsPlus instance = null;
		return instance;
	}
	FileConfiguration config;
	File cfile;
	
	@Override
	public void onEnable() {
		
		getCommand("head").setExecutor(new Head());
		getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
		log.info("[HeadsPlus] HeadsPlus has been enabled.");
    }
	@Override
	public void onDisable() {
		log.info("[HeadsPlus] HeadsPlus has been disabled.");
	}
	


	
}
