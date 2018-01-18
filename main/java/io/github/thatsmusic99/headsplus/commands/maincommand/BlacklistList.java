package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.List;

import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistList {

	private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	
	public void blacklistListNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
			int headsN = 1;
			List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
			int bls = bl.size();
			if (bls < 1) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("empty-bl"))));
				return;
			}
			while (bls > 8) {
				headsN++;
				bls = bls - 8;
			}
			sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Blacklist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + headsN + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
			int TimesSent = 0;
			for (String key : bl) {
				if (TimesSent <= 7) {
					sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
					TimesSent++;
				}
			}
	    } else {
	    	sender.sendMessage(new HeadsPlusCommand().noPerms);
	    }
	}
	public void blacklistList(CommandSender sender, String i) {
		if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
			   if (i.matches("^[0-9]+$")) {
				   List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
				   int page = Integer.parseInt(i);

                   PagedLists pl = new PagedLists(bl, 8);
				  
				   if ((page > pl.getTotalPages()) || (0 >= page)) {
					   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-pg-no"))));
				   } else {
					   sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Blacklist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");

				       for (Object keyz : pl.getContentsInPage(page)) {
				           String key = (String) keyz;
				           sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
				       }
				   }
	       } else {
		       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-input-int"))));
		   }
	} else {
    	sender.sendMessage(new HeadsPlusCommand().noPerms);
    }
	}

}
