package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.ArrayList;
import java.util.List;

import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HelpMenu {

	public void helpNoArgs(CommandSender sender) {
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
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + String.valueOf(pageNo) + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
		int TimesSent = 0;
		for (PermissionEnums key2 : headPerms) {
			if (TimesSent <= 7) {
				sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key2.cmd + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key2.dsc);
				TimesSent++;
			}
		}
	    } else {
	    	sender.sendMessage(new HeadsPlusCommand().noPerms);
	    } 
	}
	public void helpNo(CommandSender sender, String str) {
		if (sender.hasPermission("headsplus.maincommand")) {
			if (str.matches("^[0-9]+$")) {
				List<PermissionEnums> headPerms = new ArrayList<>();
				for (PermissionEnums key : PermissionEnums.values()) {
					if (sender.hasPermission(key.str)) {
						headPerms.add(key);
					}
				}
				int page = Integer.parseInt(str);
				PagedLists pl = new PagedLists(headPerms, 8);
				
				if ((page > pl.getTotalPages()) || (0 >= page)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-pg-no"))));
				} else {
					sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + String.valueOf(page) + "/" + String.valueOf(pl.getTotalPages()) + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
					List<?> hppsl = pl.getContentsInPage(page);
				    for (Object keyz : hppsl) {
				        PermissionEnums key = (PermissionEnums) keyz;
				        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key.cmd + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key.dsc);
				    }
				}
			}
		} else {
	    	sender.sendMessage(new HeadsPlusCommand().noPerms);
	    }
	}
}
