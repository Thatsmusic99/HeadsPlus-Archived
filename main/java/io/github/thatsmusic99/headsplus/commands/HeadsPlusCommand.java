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
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistwAdd;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistwDelete;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistwList;
import io.github.thatsmusic99.headsplus.commands.maincommand.BlacklistwToggle;
import io.github.thatsmusic99.headsplus.commands.maincommand.HelpMenu;
import io.github.thatsmusic99.headsplus.commands.maincommand.Info;
import io.github.thatsmusic99.headsplus.commands.maincommand.MCReload;
import io.github.thatsmusic99.headsplus.commands.maincommand.PurgeData;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HeadsPlusCommand implements CommandExecutor {
	
	public static String noPerms = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("no-perm")));
	private static String tooManyArgs = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("too-many-args")));
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> validCmds = new ArrayList<>(Arrays.asList("reload", "blacklistadd", "blacklistdel", "blacklist", "info", "blacklistl", "purgedata", "help", "blacklistw", "blacklistwadd", "blacklistwdel", "blacklistwl", "blacklistwt"));

		if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
			if (sender.hasPermission("headsplus.maincommand")) {
			   if ((args.length == 0)) {
				   HelpMenu.helpNoArgs(sender);
			   }
			    
			   if ((args.length >= 1) && (args[0].equalsIgnoreCase("reload"))) { 
				   MCReload.reload(sender);
			   }   
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   BlacklistAdd.blacklistAdd(sender, args[1]);
			   } 
			   else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   } 
			   else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
		      
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
			 	  BlacklistDelete.blacklistDel(sender, args[1]);
			   } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistdel [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   } else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistdel [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklist"))) {
				   BlacklistToggle.toggleNoArgs(sender);
			   }
			   if ((args.length >= 2) && (args[0].equalsIgnoreCase("blacklist"))) {
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
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistl"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
				       sender.sendMessage(tooManyArgs);
				       sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistl [Page no.]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length >= 1) && (args[0].equalsIgnoreCase("purgedata"))) {
				   PurgeData.purgeData(sender);
			   }
			   if ((args.length >= 1) && (args[0].matches("^[0-9]+$"))) {
				   HelpMenu.helpNo(sender, args[0]);
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
				   HelpMenu.helpNoArgs(sender);
			   }
			   if ((args.length >= 2) && (args[0].equalsIgnoreCase("help"))) {
			 	   HelpMenu.helpNo(sender, args[1]);
			   }
			   if ((args.length > 0) && !validCmds.contains(args[0]) && !args[0].matches("^[0-9]+$")) {
				   HelpMenu.helpNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwadd"))) {
				   BlacklistwAdd.blacklistAdd(sender, args[1]);
			   }
			   if ((args.length == 1) && (args[0].equals("blacklistwadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwadd <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.add")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwadd <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwdel"))) {
				   BlacklistwDelete.blacklistDel(sender, args[1]);
			   }
			   if ((args.length == 1) && (args[0].equals("blacklistwdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.delete")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwdel <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.delete")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwdel <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistw"))) {
				   BlacklistwToggle.toggleWorldNoArgs(sender);
			   }
			   if ((args.length >= 2) && (args[0].equalsIgnoreCase("blacklistw"))) {
				   BlacklistwToggle.toggleWorld(sender, args[1]);
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   BlacklistwList.blacklistwListNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   BlacklistwList.blacklistwList(sender, args[1]);
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
				       sender.sendMessage(tooManyArgs);
				       sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwl [Page no.]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   }
		}
			
	return false;
	}	
}