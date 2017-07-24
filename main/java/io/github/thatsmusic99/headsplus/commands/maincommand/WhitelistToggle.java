package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class WhitelistToggle {
private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	
	public static void toggleNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.whitelist.toggle")) {
			  try {
				  if (config.getBoolean("whitelistOn")) {
					  config.set("whitelistOn", false);
					  config.options().copyDefaults(true);
			          HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-on"))));
				  } else if (!config.getBoolean("whitelistOn")) {
					  config.set("whitelistOn", true);
					  config.options().copyDefaults(true);
					  HeadsPlus.getInstance().saveConfig();
					  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-off"))));
				  }
			  } catch (Exception e) {
				  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
				  e.printStackTrace();
				  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
			  }
		  } else {
		      sender.sendMessage(HeadsPlusCommand.noPerms);
		  }
	}
	public static void toggle(CommandSender sender, String str) {
		if (sender.hasPermission("headsplus.maincommand.whitelist.toggle")) {
			try {
			   	if (str.equalsIgnoreCase("on")) {
			 		if (!config.getBoolean("whitelistOn")) {
					    config.set("whitelistOn", true);
					    config.options().copyDefaults(true);
					    HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-on"))));
				    } else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-a-on"))));
					}
					       
			    } else if (str.equalsIgnoreCase("off")) {
					if (config.getBoolean("whitelistOn")) {
						config.set("whitelistOn", false);
					    config.options().copyDefaults(true);
			            HeadsPlus.getInstance().saveConfig();
					    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-off"))));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-a-off"))));
					}
				} else if (!(str.equalsIgnoreCase("on") && !(str.equalsIgnoreCase("off")))) {
				 	sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelist [On|Off]");
				}
		    } catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
				e.printStackTrace();
			    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
		    }
		} else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}
}
