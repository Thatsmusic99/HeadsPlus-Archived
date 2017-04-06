package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class PurgeData {
	
	private static File dataF = new File(HeadsPlus.getInstance().getDataFolder(), "data.yml");
	static String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
	public static void purgeData(CommandSender sender) {
	    if (sender.hasPermission("headsplus.maincommand.purgedata")) {
	        try {
	            dataF.delete();
	            HeadsPlusDataFile.reloadHPData();
	            sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("purged-data"))));
	        } catch (Exception e) {
		        sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages("purge-fail")));
	        }
	    } else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}

}
