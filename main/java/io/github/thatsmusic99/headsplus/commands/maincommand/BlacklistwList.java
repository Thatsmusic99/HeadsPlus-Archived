package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwList {
	
static String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
	public static void blacklistwListNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
		       int worldsN = 1;
		       List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
		       int bls = bl.size();
		       while (bls > 8) {
			       worldsN++;
			       bls = bls - 8;
		       }
		       sender.sendMessage(ChatColor.DARK_BLUE + "============ " + ChatColor.GOLD + "World Blacklist: " + ChatColor.GRAY + "1/" + worldsN + ChatColor.DARK_BLUE + " ==========" );
		       int TimesSent = 0;
		       for (String key : bl) {
			       if (TimesSent <= 7) {
			               sender.sendMessage(ChatColor.GRAY + key);
			               TimesSent++;
			       }
		      }
	    } else {
	    	sender.sendMessage(HeadsPlusCommand.noPerms);
	    }
	}
	public static void blacklistwList(CommandSender sender, String i) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
			   if (i.matches("^[0-9]+$")) {
				   List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
				   int entries = 8;
				   int page = Integer.parseInt(i);
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
					   sender.sendMessage(ChatColor.DARK_BLUE + "========== " + ChatColor.GOLD + "World Blacklist: " + ChatColor.GRAY + page + "/" + pages + ChatColor.DARK_BLUE + " ===========");
			           List<String> blsl = bl.subList(sIndex, eIndex);
				       for (String key : blsl) {
				           sender.sendMessage(ChatColor.GRAY + key);
				       }
				   }
	       } else {
		       sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-input-int"))));	  
		   }
	} else {
    	sender.sendMessage(HeadsPlusCommand.noPerms);
    }
	}

}
