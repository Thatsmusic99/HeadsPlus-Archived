package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.List;

import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwList {

	private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	
	public void blacklistwListNoArgs(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
            List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
            if (bl.size() < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("empty-blw"))));
                return;
            }
            PagedLists<String> pl = new PagedLists<>(bl, 8);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Blacklist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
		       for (String key : pl.getContentsInPage(1)) {
		           sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
		      }
	    } else {
	    	sender.sendMessage(new HeadsPlusCommand().noPerms);
	    }
	}
	public void blacklistwList(CommandSender sender, String i) {
		if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
			   if (i.matches("^[0-9]+$")) {
				   List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
				   int page = Integer.parseInt(i);
                   PagedLists<String> pl = new PagedLists<>(bl, 8);
				  
				   if ((page > pl.getTotalPages()) || (0 >= page)) {
					   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-pg-no"))));
				   } else {
					   sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Blacklist: "
				   + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages()
				   + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");
				       for (String key : pl.getContentsInPage(page)) {
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
