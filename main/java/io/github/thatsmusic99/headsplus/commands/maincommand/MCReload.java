package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;

public class MCReload {
	
	private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
	
	@SuppressWarnings("unused")
	private static FileConfiguration messages;
	private static File messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");;
	
	@SuppressWarnings("unused")
	private static FileConfiguration heads;
	private static File headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");;
	
	@SuppressWarnings("unused")
	private static FileConfiguration crafting;
	private static File craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");;
	
	public static void reload(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.reload")) {
			   String reloadM = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reload-message"));
			   String reloadingM = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reloading-message"));
			   String reloadF = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("reload-fail"));
			   reloadM = HeadsPlus.getInstance().translateMessages(reloadM);
			   reloadingM = HeadsPlus.getInstance().translateMessages(reloadingM);
			   reloadF = HeadsPlus.getInstance().translateMessages(reloadF);
		       sender.sendMessage(reloadingM);
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
			      if (!(craftingF.exists())) {
			    	  if (HeadsPlus.getInstance().getConfig().getBoolean("craftHeads")) {
			    		  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting not found, creating!");
			    	      Bukkit.resetRecipes();
			    	      HeadsPlusCrafting.reloadCrafting();
			    	      crafting = YamlConfiguration.loadConfiguration(craftingF);
			    	      HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting created!");
			    	      sender.sendMessage(reloadM);
			    	  }
			      } else {
			    	  Bukkit.resetRecipes();
			    	  HeadsPlusCrafting.reloadCrafting();
			    	  sender.sendMessage(reloadM);
			      }
			      
		       } catch (Exception e) {
			       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config!");
			       e.printStackTrace();
			       sender.sendMessage(reloadF);
		       }
		   } else {
		       sender.sendMessage(HeadsPlusCommand.noPerms);
		   }
	   }
	
}


