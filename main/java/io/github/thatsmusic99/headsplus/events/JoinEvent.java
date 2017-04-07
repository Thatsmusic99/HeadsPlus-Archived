package io.github.thatsmusic99.headsplus.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class JoinEvent implements Listener { 
	
	public static boolean reloaded = false;
    private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
	
	@SuppressWarnings("unused")
	private static FileConfiguration messages;
	private static File messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");;
	
	@SuppressWarnings("unused")
	private static FileConfiguration heads;
	private static File headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");;
	
	@SuppressWarnings("unused")
	private static FileConfiguration data;
	private static File dataF = new File(HeadsPlus.getInstance().getDataFolder(), "data.yml");;
	
	@SuppressWarnings("unused")
	private static FileConfiguration crafting;
	private static File craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");;
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!reloaded) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnFirstJoin")) {
		    	String reloadM = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reload-message"));
				   String reloadingM = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reloading-message"));
				   String reloadF = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reload-fail"));
				   reloadM = HeadsPlus.getInstance().translateMessages(reloadM);
				   reloadingM = HeadsPlus.getInstance().translateMessages(reloadingM);
				   reloadF = HeadsPlus.getInstance().translateMessages(reloadF);
			       try {

				       if  (!(configF.exists())) {
					       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					       HeadsPlus.getInstance().saveConfig();
					       
				       } else {
					       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
					       HeadsPlus.getInstance().reloadConfig();
					       HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
				      }  
				      if (!(messagesF.exists())) {
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages not found, creating!");
				    	  HeadsPlusConfig.reloadMessages();
				    	  messages = YamlConfiguration.loadConfiguration(messagesF);
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages created!");
				      } else {
				    	  HeadsPlusConfig.reloadMessages();
				      }
				      if (!(headsF.exists())) {
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads not found, creating!");
				    	  HeadsPlusConfigHeads.reloadHeads();
				    	  heads = YamlConfiguration.loadConfiguration(headsF);
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads created!");
				      } else {
				    	  HeadsPlusConfigHeads.reloadHeads();
				      }
				      if (!(dataF.exists())) {
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Data not found, creating!");
				    	  Bukkit.resetRecipes();
				    	  HeadsPlusDataFile.reloadHPData();
				    	  data = YamlConfiguration.loadConfiguration(dataF);
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Data created!");
				      } else {
				    	  Bukkit.resetRecipes();
				    	  HeadsPlusDataFile.reloadHPData();
				      }
				      if (!(craftingF.exists())) {
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting not found, creating!");
				    	  HeadsPlusCrafting.reloadCrafting();
				    	  crafting = YamlConfiguration.loadConfiguration(craftingF);
				    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting created!");
				      } else {
				    	  HeadsPlusCrafting.reloadCrafting();
				      }
				      reloaded = true;
				      
			       } catch (Exception ex) {
				       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config.");
				       ex.printStackTrace();
			       }
	        	
	        }
		}
	}
}
