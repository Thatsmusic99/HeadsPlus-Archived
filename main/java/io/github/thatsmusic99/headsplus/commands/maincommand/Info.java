package io.github.thatsmusic99.headsplus.commands.maincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class Info {
	
	public static void info(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.info")) {
		       String version = HeadsPlus.getInstance().version;
		       String author = HeadsPlus.getInstance().author;
		       sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
		       sender.sendMessage(ChatColor.DARK_AQUA + "Version: " + ChatColor.GRAY + version);
		       sender.sendMessage(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + author);
		   }
	}

}
