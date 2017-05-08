package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistToggle {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	
	public static void toggleNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
			  try {
				  Boolean blacklistToggle = config.getBoolean("blacklistOn");
				  if (blacklistToggle) {
					  config.set("blacklistOn", false);
					  config.options().copyDefaults(true);
			          HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-on"))));
				  } else if (!(blacklistToggle)) {
					  config.set("blacklistOn", true);
					  config.options().copyDefaults(true);
					  HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-off"))));
				  }
			  } catch (Exception e) {
				  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
				  e.printStackTrace();
				  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-fail"))));
			  }
		  } else {
		      sender.sendMessage(HeadsPlusCommand.noPerms);
		  }
	}
	public static void toggle(CommandSender sender, String str) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
			try {
			   	if (str.equalsIgnoreCase("on")) {
			 		if (!config.getBoolean("blacklistOn")) {
					    config.set("blacklistOn", true);
					    config.options().copyDefaults(true);
					    HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-on"))));
				    } else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-a-on"))));
					}
					       
			    } else if (str.equalsIgnoreCase("off")) {
					if (config.getBoolean("blacklistOn")) {
						config.set("blacklistOn", false);
					    config.options().copyDefaults(true);
			            HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-off"))));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-a-off"))));
					}
				} else if (!(str.equalsIgnoreCase("on") && !(str.equalsIgnoreCase("off")))) {
				 	sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
				}
		    } catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
				e.printStackTrace();
			    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-fail"))));
		    }
		} else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}
}
