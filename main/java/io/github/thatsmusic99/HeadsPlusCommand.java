package io.github.thatsmusic99;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HeadsPlusCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("headsplus")) {
			if (sender.hasPermission("headsplus.maincommand")) {
			    if (args.length == 0) {
				    sender.sendMessage(ChatColor.DARK_BLUE + "==========" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "==========");
				    if (sender.hasPermission("headsplus.maincommand.reload")) {
					    sender.sendMessage(ChatColor.GRAY + "/headsplus reload - " + ChatColor.DARK_AQUA + "Reloads the config.");
				    }
					if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistadd <IGN> - " + ChatColor.DARK_AQUA + "Adds a head to the blacklist.");
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistdel <IGN> - " + ChatColor.DARK_AQUA + "Removes head from the blacklist.");
						
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistl [Page #] - " + ChatColor.DARK_AQUA + "Lists all blacklisted heads.");
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklist <On|Off> - " + ChatColor.DARK_AQUA + "Turns the blacklist on/off.");
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "==============================");
			    
			   } 
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
				       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Reloading config...");
				       try {
					       File config = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					       if  (!(config.exists())) {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
						       HeadsPlus.getInstance().saveDefaultConfig();
						   
					       } else {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
						       HeadsPlus.getInstance().reloadConfig();
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Reloaded config!");
					      }  
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config!");
					       e.printStackTrace();
				   }
			   }
			   
			    
			    
			
		    
		return false;
		}

			}
		return false;
	}
	

	
}