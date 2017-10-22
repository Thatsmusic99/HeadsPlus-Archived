package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;
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
	
	private static final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	@SuppressWarnings("unused")
	private static FileConfiguration messages;
	private static final File messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");
	
	@SuppressWarnings("unused")
	private static FileConfiguration heads;
	private static final File headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");
	
	@SuppressWarnings("unused")
	private static FileConfiguration crafting;
	private static final File craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");

	private static FileConfiguration headsX;
	private  static final File headsXF = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
	
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
                       if (HeadsPlus.getInstance().getConfig().getBoolean("dropHeads")) {
				           if (!HeadsPlus.getInstance().drops) {
				               HeadsPlus.getInstance().drops = true;
                           }
                       } else {
				       	   if (HeadsPlus.getInstance().drops) {
				       	       HeadsPlus.getInstance().drops = false;
                           }
					   }
					   if (HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnFirstJoin")) {
                           if (!HeadsPlus.getInstance().arofj) {
                               HeadsPlus.getInstance().arofj = true;
                           }
					   } else {
				           if (HeadsPlus.getInstance().arofj) {
				               HeadsPlus.getInstance().arofj = false;
                           }
                       }
                       if (HeadsPlus.getInstance().econ() && HeadsPlus.getInstance().getConfig().getBoolean("sellHeads")) {
                           if (!HeadsPlus.getInstance().sellable) {
                               HeadsPlus.getInstance().sellable = true;
                           }
                       } else if (HeadsPlus.getInstance().econ() && !HeadsPlus.getInstance().getConfig().getBoolean("sellHeads")) {
                           if (HeadsPlus.getInstance().sellable) {
                               HeadsPlus.getInstance().sellable = false;
                           }
                       }
                       HeadsPlus.getInstance().db = HeadsPlus.getInstance().getConfig().getBoolean("headsDatabase");

					   HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
				   }
			      if (!(messagesF.exists())) {
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages not found, creating!");
			    	  HeadsPlusConfig.reloadMessages(false);
			    	  messages = YamlConfiguration.loadConfiguration(messagesF);
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages created!");
			      } else {
			    	  HeadsPlusConfig.reloadMessages(false);
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
			    	      HeadsPlusCrafting.craftingEnable();
			    	      crafting = YamlConfiguration.loadConfiguration(craftingF);
			    	      HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting created!");
			    	      sender.sendMessage(reloadM);
			    	  }
			      } else {
                      HeadsPlusCrafting.reloadCrafting();
			    	  sender.sendMessage(reloadM);
			      }
				   if (!headsXF.exists()) {
					   if (HeadsPlus.getInstance().getConfig().getBoolean("headsDatabase")) {
						   HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX not found, creating!");
						   HeadsPlusConfigHeadsX.reloadHeadsX();
						   headsX = YamlConfiguration.loadConfiguration(headsXF);
						   HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX created!");
					   }
				   } else {
					   HeadsPlusConfigHeadsX.reloadHeadsX();
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


