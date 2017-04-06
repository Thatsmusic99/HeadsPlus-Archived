package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwAdd {
	
	private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	public static void blacklistAdd(CommandSender sender, String world) {
		String prefix = HeadsPlus.getInstance().translateMessages(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
	
		if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
			   if (world.matches("^[A-Za-z0-9_]+$")) {
		           try {
		        	   
			           if (!(configF.exists())) {
			              HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
			              config.options().copyDefaults(true);
                          HeadsPlus.getInstance().saveConfig();
                          @SuppressWarnings("unused")
					      File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
			              sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
			           }
			           List<String> blacklist = config.getStringList("blacklistw");
			           String aWorld = world.toLowerCase();
			           if (blacklist.contains(aWorld)) {
				           sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + "This world is already added!");
			           } else {
			    	       blacklist.add(aWorld);
				           config.set("blacklistw", blacklist);
				           config.options().copyDefaults(true);
				           HeadsPlus.getInstance().saveConfig();
				       
				           sender.sendMessage(prefix + " " + ChatColor.DARK_AQUA + world + " has been added to the world blacklist!");
			          }
		           } catch (Exception e) {
			          HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add world!");
			          e.printStackTrace();
		           }
			   } else {
				   sender.sendMessage(prefix + " " + ChatColor.RED + "Use alphanumeric names only!");
			   }
	} else {
		sender.sendMessage(HeadsPlusCommand.noPerms);
	}

	}

}