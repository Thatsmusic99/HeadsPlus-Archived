package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.ArrayList;
import java.util.List;

import io.github.thatsmusic99.headsplus.util.PagedLists;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HelpMenu {

	private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

	public void helpNoArgs(CommandSender sender) {
	    if (sender.hasPermission("headsplus.maincommand")) {
		    List<PermissionEnums> headPerms = new ArrayList<>();
	        for (PermissionEnums key : PermissionEnums.values()) {
	        	if (sender.hasPermission(key.str)) {
	        		headPerms.add(key);
	        	}
	        }
            PagedLists<PermissionEnums> pl = new PagedLists<>(headPerms, 8);
		    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
		    for (PermissionEnums key2 : pl.getContentsInPage(1)) {
                new FancyMessage()
                        .text(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key2.cmd + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key2.dsc)
                        .command("/hp help " + key2.scmd)
                        .send(sender);
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
				PagedLists<PermissionEnums> pl = new PagedLists<>(headPerms, 8);
				
				if ((page > pl.getTotalPages()) || (0 >= page)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-pg-no"))));
				} else {
					sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + String.valueOf(page) + "/" + String.valueOf(pl.getTotalPages()) + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
				    for (PermissionEnums key : pl.getContentsInPage(page)) {
				        new FancyMessage()
                                .text(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key.cmd + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key.dsc)
                                .command("/hp help " + key.scmd)
                                .send(sender);
				         }
				}
			}
		} else {
	    	sender.sendMessage(new HeadsPlusCommand().noPerms);
	    }
	}

	public void helpCmd(CommandSender cs, String cmdName) {
        if (cs.hasPermission("headsplus.maincommand")) {
            PermissionEnums pe = null;
            for (PermissionEnums key : PermissionEnums.values()) {
                if (key.scmd.equalsIgnoreCase(cmdName)) {
                    pe = key;
                    break;
                }
            }
            if (pe != null) {
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Usage: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.cmd);
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Description: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.dsc);
                if (cs.hasPermission("headsplus.help.viewperms")) {
                    cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))+ "Permission: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.str);
                }
            } else {
                helpNoArgs(cs);
            }
        }
    }
}
