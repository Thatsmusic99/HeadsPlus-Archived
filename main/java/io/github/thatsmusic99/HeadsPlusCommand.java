package io.github.thatsmusic99;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

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
			    if (args.length == 1) {
			    	if (args[0].equals("reload")) {
			    		if (sender.hasPermission("headsplus.maincommand.reload")) {
			    			try {
			    		        sender.sendMessage(ChatColor.BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.BLUE + "] " + ChatColor.GRAY + "Reloading config...");
			    		        FileConfiguration config = HeadsPlus.instance().getConfig();
			    		        if (config != null) {
			    		        	HeadsPlus.instance().reloadConfig();
			    		        	HeadsPlus.log.info("Config loaded successfully!");
			    		            sender.sendMessage(ChatColor.BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.BLUE + "] " + ChatColor.GRAY + "Reloaded config!");
			    		        } else {
			    		            try {
			    		    	        if (!HeadsPlus.instance().getDataFolder().exists()) {
			    		    	            HeadsPlus.instance().getDataFolder().mkdirs();
			    		    	        }
			    		    	        File file = new File(HeadsPlus.instance().getDataFolder(), "config.yml");
			    		    	        if (!file.exists()) {
			    		    	            HeadsPlus.instance();
											HeadsPlus.log.info("[HeadsPlus] Config.yml not found, creating!");
			    		    	            HeadsPlus.instance().saveDefaultConfig();
			    		    	            HeadsPlus.log.info("Config loaded successfully!");
			    		    	        } else {
			    		    	            HeadsPlus.instance();
											HeadsPlus.log.info("[HeadsPlus] Config.yml found, loading!");
			    		    	            HeadsPlus.instance().reloadConfig();
			    		    	            HeadsPlus.log.info("Config loaded successfully!");
			    		    	        }
			    		    	    } catch (Exception e) {
			    		    	        e.printStackTrace();

			    		    	    }
			    		        }
				//				@SuppressWarnings({ "deprecation", "unused" })
				//				FileConfiguration config = YamlConfiguration.loadConfiguration(HeadsPlus.instance().getResource("config.yml"));
			    	//	        @SuppressWarnings("unused")
			    		        
			    			} 
			    			catch (Exception e) {
			    				HeadsPlus.log.severe("Config failed to reload!");
			    				sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + ChatColor.RED + "Config has failed to reload. Try again, if problem consists contact Thatsmusic99.");
			    				e.printStackTrace();
			    			}
			    		    
			    		}
			    	}
			    	if (args[0].equals("blacklistadd")) {
			    		List<String> blacklist = HeadsPlus.instance().getConfig().getStringList("blacklist");
						if (!(blacklist.contains(args[1]))) {
							blacklist.add(args[1]);
							sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.GRAY + args[0] + " has been added to the blacklist!");
						}
			    	}
			    }
			}
		    }
		return false;
		}

		
	}

