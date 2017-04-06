package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwToggle {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	static String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
	public static void toggleWorldNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.toggle")) {
			  try {
				  Boolean blacklistToggle = config.getBoolean("blacklistwOn");
				  if (blacklistToggle) {
					  config.set("blacklistwOn", false);
					  config.options().copyDefaults(true);
			          HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "World blacklist disabled, use /headsplus blacklistw to re-enable!");
				  } else if (!(blacklistToggle)) {
					  config.set("blacklistwOn", true);
					  config.options().copyDefaults(true);
					  HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "World blacklist enabled, use /headsplus blacklistw to disable!");
				  }
			  } catch (Exception e) {
				  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
				  e.printStackTrace();
				  sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle world blacklist.");
			  }
		  } else {
		      sender.sendMessage(HeadsPlusCommand.noPerms);
		  }
	}
	public static void toggleWorld(CommandSender sender, String str) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.toggle")) {
			try {
			   	if (str.equalsIgnoreCase("on")) {
			 		if (!config.getBoolean("blacklistwOn")) {
					    config.set("blacklistwOn", true);
					    config.options().copyDefaults(true);
					    HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "World blacklist enabled!");
				    } else {
						sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "World blacklist is already enabled!");
					}
					       
			    } else if (str.equalsIgnoreCase("off")) {
					if (config.getBoolean("blacklistwOn")) {
						config.set("blacklistwOn", false);
					    config.options().copyDefaults(true);
			            HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "World blacklist disabled!");
					} else {
						sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "World blacklist is already disabled!");
					}
				} else if (!(str.equalsIgnoreCase("on") && !(str.equalsIgnoreCase("off")))) {
				 	sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistw [On|Off]");
				}
		    } catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
				e.printStackTrace();
			    sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle world blacklist.");
		    }
		} else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}

}
