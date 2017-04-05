package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistAdd;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistDelete;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistList;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistToggle;
import io.github.thatsmusic99.headsplus.commands.maincommand.Info;
import io.github.thatsmusic99.headsplus.commands.maincommand.MCReload;
import io.github.thatsmusic99.headsplus.commands.maincommand.PurgeData;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class HeadsPlusCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		HeadsPlus.getInstance();
		HeadsPlusConfig.getMessages();
		HeadsPlusConfigHeads.getHeads();
		HeadsPlusDataFile.getHPData();
		HeadsPlusCrafting.getCrafting();
		if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
			if (sender.hasPermission("headsplus.maincommand")) {
				ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix"));
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
			   } 
			   else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				   }
			   } 
			   else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
					   sender.sendMessage(ChatColor.RED + "Too many arguments!");
				   }
			   }
		      
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
			 	  BlacklistDelete.blacklistDel(sender, args[1]);
			   } 
			   else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/heads [IGN]");
				   }
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklist"))) {
				   BlacklistToggle.toggleNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklist"))) {
				   BlacklistToggle.toggle(sender, args[1]);
			   }
			   if (((args.length > 2) || (args.length == 2 && (!(args[1].equalsIgnoreCase("off")) || !(args[1].equalsIgnoreCase("on"))))) && (args[0].equalsIgnoreCase("blacklist"))) {
				   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
			   }
			   
			   if ((args.length >= 1) && (args[0].equalsIgnoreCase("info"))) {
				   Info.info(sender);
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistl"))) {
				   BlacklistList.blacklistListNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistl"))) {
				   BlacklistList.blacklistList(sender, args[1]);
			  }
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("purgedata"))) {
				  PurgeData.purgeData(sender);
			  }
		}
			
	return false;
	}	
}