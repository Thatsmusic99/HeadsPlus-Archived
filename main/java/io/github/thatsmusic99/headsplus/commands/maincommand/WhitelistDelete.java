package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class WhitelistDelete {
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	@SuppressWarnings("unused")
	private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	public static void whitelistDel(CommandSender sender, String name) {
		if (sender.hasPermission("headsplus.maincommand.whitelist.delete")) {
			if (name.matches("^[A-Za-z0-9_]+$")) {
		        try {
		            config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
			          if (!(cfile.exists())) {
			              HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
			          }
			          try {
			              List<String> whitelist = config.getStringList("whitelist");
			              String rHead = name.toLowerCase();
			              if (whitelist.contains(rHead)) {
				              whitelist.remove(rHead);
				              config.set("whitelist", whitelist);
				              config.options().copyDefaults(true);
				              HeadsPlus.getInstance().saveConfig();
				              sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-removed-wl").replaceAll("%p", name))));
			              } else {
			    	          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-a-removed-wl"))));
			          
                    }} catch (Exception e) {
			    	      HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
			    	      e.printStackTrace();
			    	      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
			          }
		       } catch (Exception e) {
			       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
			       e.printStackTrace();
			       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
		       }
		  } else {
			  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
		  }
	} else {
		sender.sendMessage(HeadsPlusCommand.noPerms);
	}
	}
}
