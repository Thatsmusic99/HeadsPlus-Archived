package io.github.thatsmusic99.headsplus.commands;

import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = HeadsPlus.getInstance().getConfig();
		File configF = HeadsPlus.getInstance().configF;
		String prefix = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getMessages().getString("prefix"));
		if (cmd.getName().equalsIgnoreCase("headsplus")) {
			if (sender.hasPermission("headsplus.maincommand")) {
			    if (args.length == 0) {
				    sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
				    if (sender.hasPermission("headsplus.maincommand.reload")) {
					    sender.sendMessage(ChatColor.GRAY + "/headsplus reload - " + ChatColor.DARK_AQUA + "Reloads the config.");
				    }
					if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistadd <IGN> - " + ChatColor.DARK_AQUA + "Adds a head to the blacklist.");
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistdel <IGN> - " + ChatColor.DARK_AQUA + "Removes head from the blacklist.");
						
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklist <On|Off> - " + ChatColor.DARK_AQUA + "Turns the blacklist on/off.");
					}
					if (sender.hasPermission("headsplus.maincommand.info")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus info - " + ChatColor.DARK_AQUA + "Gets plugin info.");
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "========================================");
			    
			   } 
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) { // TODO Add messages.yml
				   if (sender.hasPermission("headsplus.maincommand.reload")) {
				       sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Reloading config...");
				       try {

					       if  (!(configF.exists())) {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
						       HeadsPlus.getInstance().saveConfig();
						       
					       } else {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
						       HeadsPlus.getInstance().reloadConfig();
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
						       sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Reloaded config!");
					      }  
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config - if this problem consists, contact Thatsmusic99!");
					       e.printStackTrace();
					       sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + "Failed to reload config.");
				       }
				   }
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
				       try {

					       if  (!(configF.exists())) {
					           HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					           config.options().copyDefaults(true);
                               HeadsPlus.getInstance().saveConfig();
                               @SuppressWarnings("unused")
							File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
					       }
					       List<String> blacklist = config.getStringList("blacklist");
					       String aHead = args[1].toLowerCase();
					       if (blacklist.contains(aHead)) {
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "This head is already added!");
					       } else {
					    	   blacklist.add(aHead);
						       config.set("blacklist", blacklist);
						       config.options().copyDefaults(true);
						       HeadsPlus.getInstance().saveConfig();
						       
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + args[1] + " has been added to the blacklist!");
					       }
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add head!");
					       e.printStackTrace();
				       }
			  } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				  }
			  } else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
					  sender.sendMessage(ChatColor.RED + "Too many arguments!");
				  }
			  }
		}
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					  try {
				           config.options().copyDefaults(true);
                           HeadsPlus.getInstance().saveConfig();
                           File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					       if  (!(cfile.exists())) {
					           HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					           
					           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
					       }
					       try {
					           List<String> blacklist = (List<String>)config.getStringList("blacklist");
					           String rHead = args[1].toLowerCase();
					           if (blacklist.contains(rHead)) {
						           blacklist.remove(rHead);
						           config.set("blacklist", blacklist);
						           config.options().copyDefaults(true);
						           HeadsPlus.getInstance().saveConfig();
						           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + args[1] + " has been removed from the blacklist!");
					           } else {
					    	       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "This head is not on the blacklist!");
					       }} catch (Exception e) {
					    	   HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					    	   e.printStackTrace();
					       }
					       
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					       e.printStackTrace();
				       }
				  }
			  } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/heads [IGN]");
				  }
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklist"))) {
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
						  sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + ChatColor.RED + "Failed to toggle blacklist.");
					  }
				  }
			  }
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklist"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
					  try {
						  if (args[1].equalsIgnoreCase("on")) {
							  if (config.getBoolean("blacklistOn")) {
							      config.set("blacklistOn", true);
							      config.options().copyDefaults(true);
							      HeadsPlus.getInstance().saveConfig();
							  } else {
								  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist is already disabled!");
							  }
							      sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist enabled!");
						  } else if (args[1].equalsIgnoreCase("off")) {
							  if (!(config.getBoolean("blacklistOn"))) {
								  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist is already enabled.");
							  } else {
							      config.set("blacklistOn", false);
							      config.options().copyDefaults(true);
					              HeadsPlus.getInstance().saveConfig();
							      sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist disabled!");
							  }
						  } else {
							  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
						  }
					  } catch (Exception e) {
						  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
						  e.printStackTrace();
						  sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + ChatColor.RED + "Failed to toggle blacklist.");
					  }
				  }
			  }
			  if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklist"))) {
				  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
			  }
			  
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("info"))) {
				  String version = HeadsPlus.getInstance().version;
				  String author = HeadsPlus.getInstance().author;
				  sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
				  sender.sendMessage(ChatColor.DARK_AQUA + "Version: " + ChatColor.GRAY + version);
				  sender.sendMessage(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + author);
			  }
			   
			    
			    
			
		    
	
		}

			}
	
	 
		return false;
	

	}	

}