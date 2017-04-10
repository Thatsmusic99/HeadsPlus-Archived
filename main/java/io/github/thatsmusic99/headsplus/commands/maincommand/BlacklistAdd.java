package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistAdd {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	public static void blacklistAdd(CommandSender sender, String name) {
		String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
		if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
			   if (name.matches("^[A-Za-z0-9_]+$")) {
		           try {
		        	   
			           if  (!(configF.exists())) {
			               HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
			               config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        @SuppressWarnings("unused")
					        File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
			           }
			           List<String> blacklist = config.getStringList("blacklist");
			           String aHead = name.toLowerCase();
			           if (blacklist.contains(aHead)) {
				           sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-a-add"))));
			           } else {
			    	       blacklist.add(aHead);
				           config.set("blacklist", blacklist);
				           config.options().copyDefaults(true);
				           HeadsPlus.getInstance().saveConfig();
				       
				           sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-added").replaceAll("%p", name))));
			          }
		           } catch (Exception e) {
			          HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add head!");
			          e.printStackTrace();
			          sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("bl-fail"))));
			        	  
			          
		           }
			   } else {
				   sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
			   }
	} else {
		sender.sendMessage(HeadsPlusCommand.noPerms);
	}

	}
}
