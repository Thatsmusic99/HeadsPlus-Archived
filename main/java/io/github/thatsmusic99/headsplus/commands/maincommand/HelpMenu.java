package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpMenu {
	
	public static void helpNoArgs(CommandSender sender) {
	if (sender.hasPermission("headsplus.maincommand")) {
		List<PermissionEnums> headPerms = new ArrayList<>();
	    for (PermissionEnums key : PermissionEnums.values()) {
	    	if (sender.hasPermission(key.str)) {
	    		headPerms.add(key);
	    	}
	    }
	    int pageNo = 1;
	    int hpp = headPerms.size();
	    while (hpp > 8) {
	    	pageNo++;
	    	hpp = hpp - 8;
	    }
		sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + " HeadsPlus " + ChatColor.GRAY + "1/" + String.valueOf(pageNo) + " " + ChatColor.DARK_BLUE + "===============");
		int TimesSent = 0;
		for (PermissionEnums key2 : headPerms) {
			if (TimesSent <= 7) {
				if (headPerms != null) {
					PermissionEnums pe = key2;
					sender.sendMessage(ChatColor.GRAY + pe.cmd + " - " + ChatColor.DARK_AQUA + pe.dsc);
					TimesSent++;
				}
			}
		}
		/*if (sender.hasPermission("headsplus.maincommand.reload")) {
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
	    */
	    } 
	}
}
