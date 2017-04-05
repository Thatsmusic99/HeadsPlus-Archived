package io.github.thatsmusic99.headsplus.commands;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistAdd;
import io.github.thatsmusic99.headsplus.commands.maincommand.MCReload;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class HeadsPlusCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = HeadsPlus.getInstance().getConfig();
		HeadsPlus.getInstance();
		HeadsPlusConfig.getMessages();
		HeadsPlusConfigHeads.getHeads();
		HeadsPlusDataFile.getHPData();
		File dataF = HeadsPlusDataFile.dataF;
		
		HeadsPlusCrafting.getCrafting();
		if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
			if (sender.hasPermission("headsplus.maincommand")) {
				String prefix = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix"));
			    if (args.length == 0) {
			    	if (sender.hasPermission("headsplus.maincommand.reload")) {
			    	}
			    	if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
			    	}
				    sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_GRAY + ChatColor.DARK_BLUE + "===============");
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
					if (sender.hasPermission("headsplus.head")) {
						sender.sendMessage(ChatColor.GRAY + "/head <IGN> - " + ChatColor.DARK_AQUA + "Spawns in a head.");
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistl [Page no.] - " + ChatColor.DARK_AQUA + "Lists blacklisted heads.");
					}
					if (sender.hasPermission("headsplus.maincommand.purgedata")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus purgedata - " + ChatColor.DARK_AQUA + "Purges data.yml.");
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "========================================");
			    
			   } 
			    
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) { 
				   MCReload.reload(sender);
			   }   
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   BlacklistAdd.blacklistAdd(sender, args[1]);
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
		      
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
			 	  if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
				  	  if (args[1].matches("^[A-Za-z0-9_]+$")) {
				          try {
				              config.options().copyDefaults(true);
                              HeadsPlus.getInstance().saveConfig();
                              File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					          if (!(cfile.exists())) {
					              HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					           
					              sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
					          }
					          try {
					              List<String> blacklist = (List<String>)config.getStringList("blacklist");
					              String rHead = args[1].toLowerCase();
					              if (blacklist.contains(rHead)) {
						              blacklist.remove(rHead);
						              config.set("blacklist", blacklist);
						              config.options().copyDefaults(true);
						              HeadsPlus.getInstance().saveConfig();
						              sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + args[1] + " has been removed from the blacklist!");
					              } else {
					    	          sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "This head is not on the blacklist!");
					          
	                          }} catch (Exception e) {
					    	      HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					    	      e.printStackTrace();
					          }
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					       e.printStackTrace();
				       }
				  } else {
					  sender.sendMessage(prefix + " " + ChatColor.RED + "Use alphanumberic names only!");
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
						  sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle blacklist.");
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
								  sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist is already disabled!");
							  }
							      sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist enabled!");
						  } else if (args[1].equalsIgnoreCase("off")) {
							  if (!(config.getBoolean("blacklistOn"))) {
								  sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist is already enabled.");
							  } else {
							      config.set("blacklistOn", false);
							      config.options().copyDefaults(true);
					              HeadsPlus.getInstance().saveConfig();
							      sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Blacklist disabled!");
							  }
						  } else {
							  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
						  }
					  } catch (Exception e) {
						  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
						  e.printStackTrace();
						  sender.sendMessage(prefix + " " + ChatColor.RED + "Failed to toggle blacklist.");
					  }
				  }
			  }
			  if (((args.length > 2) || (args.length == 2 && (!(args[1].equalsIgnoreCase("off")) || !(args[1].equalsIgnoreCase("on"))))) && (args[0].equalsIgnoreCase("blacklist"))) {
				  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
			  }
			  
			  if ((args.length >= 1) && (args[0].equalsIgnoreCase("info"))) {
				  if (sender.hasPermission("headsplus.maincommand.info")) {
				      String version = HeadsPlus.getInstance().version;
				      String author = HeadsPlus.getInstance().author;
				      sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
				      sender.sendMessage(ChatColor.DARK_AQUA + "Version: " + ChatColor.GRAY + version);
				      sender.sendMessage(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + author);
				  }
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistl"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
				      int headsN = 1;
				      List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
				      int bls = bl.size();
				      while (bls > 8) {
					      headsN++;
					      bls = bls - 8;
				      }
				      sender.sendMessage(ChatColor.DARK_BLUE + "============" + ChatColor.GOLD + "Blacklist: " + ChatColor.GRAY + "1/" + headsN + ChatColor.DARK_BLUE + "==========" );
				      int TimesSent = 0;
				      for (String key : bl) {
					      if (TimesSent <= 7) {
					    	  if (bl != null) {
					              sender.sendMessage(ChatColor.GRAY + key);
					              TimesSent++;
						      }
					      } else {
						      TimesSent = 0;
						      return true;
					      }
				     }
				  }
			  }
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistl"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
					  if (args[1].matches("^[0-9]+$")) {
						  List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
						  int entries = 8;
						  int page = Integer.parseInt(args[1]);
						  int sIndex = (page - 1) * entries;
						  int eIndex = entries + sIndex;
						  if (eIndex > bl.size()) {
							  eIndex = bl.size();
						  }
						  int pages = 1;
						  int bls = bl.size();
						  while (bls > 8) {
							  pages++;
							  bls = bls - 8;
						  }
						  
						  if ((page > pages) || (0 >= page)) {
							  sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-pg-no"))));
						  } else {
							  sender.sendMessage(ChatColor.DARK_BLUE + "==========" + ChatColor.GOLD + "Blacklist: " + ChatColor.GRAY + page + "/" + pages + ChatColor.DARK_BLUE + "===========");
					          List<String> blsl = bl.subList(sIndex, eIndex);
						      for (String key : blsl) {
						          sender.sendMessage(ChatColor.GRAY + key);
						      }
						  }
			      } else {
				    	  sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-input-int"))));	  
				  }
			  }
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("purgedata"))) {
				  if (sender.hasPermission("headsplus.maincommand.purgedata")) {
				      try {
				          dataF.delete();
				          HeadsPlusDataFile.reloadHPData();
				          sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("purged-data"))));
				      } catch (Exception e) {
					      sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages("purge-fail")));
				      }
				  }
			  }
		}
			}
	return false;
	}	
}