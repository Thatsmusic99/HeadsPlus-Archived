package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;

public class Info {
	
	private static final String noPerms = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusCommand.noPerms));
	
	public static void info(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.info")) {
		       String version = HeadsPlus.getInstance().version;
		       String author = HeadsPlus.getInstance().author;
		       sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "HeadsPlus" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
		       sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Version: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + version);
		       sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Author: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + author);
		       sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Language: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + LocaleManager.getLocale().getLanguage());
		} else {
	    	sender.sendMessage(noPerms);
	    }
	}
}
