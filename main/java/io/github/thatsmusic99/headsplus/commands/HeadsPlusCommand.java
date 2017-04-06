package io.github.thatsmusic99.headsplus.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistAdd;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistDelete;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistList;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistToggle;
import io.github.thatsmusic99.headsplus.commands.maincommand.HelpMenu;
import io.github.thatsmusic99.headsplus.commands.maincommand.Info;
import io.github.thatsmusic99.headsplus.commands.maincommand.MCReload;
import io.github.thatsmusic99.headsplus.commands.maincommand.PurgeData;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class HeadsPlusCommand implements CommandExecutor {
	
	public static String noPerms = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("no-perms")));
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> validCmds = new ArrayList<>(Arrays.asList("reload", "blacklistadd", "blacklistdel", "blacklist", "info", "blacklistl", "purgedata", "help", "blacklistw", "blacklistwadd", "blacklistwdel", "blacklistwl", "blacklistwt"));
		
		HeadsPlus.getInstance();
		HeadsPlusConfig.getMessages();
		HeadsPlusConfigHeads.getHeads();
		HeadsPlusDataFile.getHPData();
		HeadsPlusCrafting.getCrafting();
		if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
			if (sender.hasPermission("headsplus.maincommand")) {
			   if ((args.length == 0)) {
				   HelpMenu.helpNoArgs(sender);
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
			  if ((args.length == 1) && (args[0].matches("^[0-9]+$"))) {
				  HelpMenu.helpNo(sender, args[0]);
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
				  HelpMenu.helpNoArgs(sender);
			  }
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("help"))) {
				  HelpMenu.helpNo(sender, args[1]);
			  }
			  if ((args.length > 0) && !validCmds.contains(args[0]) && !args[0].matches("^[0-9]+$")) {
				  HelpMenu.helpNoArgs(sender);
			  }
		}
			
	return false;
	}	
}