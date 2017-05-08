package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwToggle {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	
	public static void toggleWorldNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.toggle")) {
			  try {
				  Boolean blacklistToggle = config.getBoolean("blacklistwOn");
				  if (blacklistToggle) {
					  config.set("blacklistwOn", false);
					  config.options().copyDefaults(true);
			          HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-off"))));
				  } else if (!(blacklistToggle)) {
					  config.set("blacklistwOn", true);
					  config.options().copyDefaults(true);
					  HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-on"))));
				  }
			  } catch (Exception e) {
				  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
				  e.printStackTrace();
				  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-fail"))));
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
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-on"))));
				    } else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-a-on"))));
					}
					       
			    } else if (str.equalsIgnoreCase("off")) {
					if (config.getBoolean("blacklistwOn")) {
						config.set("blacklistwOn", false);
					    config.options().copyDefaults(true);
			            HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-off"))));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-a-off"))));
					}
				} else if (!(str.equalsIgnoreCase("on") && !(str.equalsIgnoreCase("off")))) {
				 	sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistw [On|Off]");
				}
		    } catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
				e.printStackTrace();
			    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blw-fail"))));
		    }
		} else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}

}
