package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistToggle {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	static String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
	public static void toggleNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
			  try {
				  Boolean blacklistToggle = config.getBoolean("blacklistOn");
				  if (blacklistToggle) {
					  config.set("blacklistOn", false);
					  config.options().copyDefaults(true);
			          HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist disabled, use /headsplus blacklist to re-enable!");
				  } else if (!(blacklistToggle)) {
					  config.set("blacklistOn", true);
					  config.options().copyDefaults(true);
					  HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist enabled, use /headsplus blacklist to disable!");
				  }
			  } catch (Exception e) {
				  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
				  e.printStackTrace();
				  sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle blacklist.");
			  }
		  }
	}
	public static void toggle(CommandSender sender, String str) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
			try {
			   	if (str.equalsIgnoreCase("off")) {
			 		if (config.getBoolean("blacklistOn")) {
					    config.set("blacklistOn", true);
					    config.options().copyDefaults(true);
					    HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist enabled!");
				    } else {
						sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist is already disabled!");
					}
					       
			    } else if (str.equalsIgnoreCase("on")) {
					if (!(config.getBoolean("blacklistOn"))) {
						config.set("blacklistOn", false);
					    config.options().copyDefaults(true);
			            HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist disabled!");
					} else {
						sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist is already enabled!");
					}
				} else if (!(str.equalsIgnoreCase("on") || !(str.equalsIgnoreCase("off")))) {
				 	sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
				}
		    } catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
				e.printStackTrace();
			    sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle blacklist.");
		    }
		}
	}
}
