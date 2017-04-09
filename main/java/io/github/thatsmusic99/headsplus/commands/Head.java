package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class Head implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {   

    // Does the command equal "/head"?
	    if (cmd.getName().equalsIgnoreCase("head")) {
	    	
		// Is a player sending it?
		    if (sender instanceof Player){
		    	if (sender.hasPermission("headsplus.head")) {
			        if (args.length == 0) {
				        sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head [IGN]");
				        return false;
			        }
			// Checks if the arguments used include A-Z, a-z and 0-9 letters, as well as more than one of them.
		        	if ((args.length == 1) && !(args[0].matches("^[A-Za-z0-9_]+$"))) {
			         	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
				        return false;
			        }
			        if (args.length > 1) {
			        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("too-many-args"))));
				        sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head [IGN]");
				        return false;
			        }
		    	    if (args[0].length() > 16) {
		    	    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-too-long"))));
			    	    return false;
			        }
			        if (args[0].length() < 3) {
			        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-too-short"))));
				        return false;
			        }
			
			        if ((args.length == 1) && (args[0].matches("^[A-Za-z0-9_]+$")) && (3 < args[0].length() << 16)) {
				        HeadsPlus.getInstance();
					    // List<String> blacklist = (List<String>)HeadsPlus.config.getStringList("blacklist");
                        List<String> blacklist = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("blacklist")) {
                    	    blacklist.add(str.toLowerCase());
                        }
				        HeadsPlus.getInstance();
					    Boolean blacklistOn = HeadsPlus.config.getBoolean("blacklistOn");
				        String head = args[0].toLowerCase();
				        if (!(blacklist.contains(head))) {
					        if (((Player) sender).getInventory().firstEmpty() == -1) {
					        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("full-inv"))));
					        } else {
						        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
		                        meta.setOwner(args[0]);
				                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("playerHeadDN").replaceAll("%d", args[0])));
				                skull.setItemMeta(meta);
				                Location playerLoc = ((Player) sender).getLocation();
				                double playerLocY = playerLoc.getY() + 1;
				                playerLoc.setY(playerLocY);
				                World world = ((Player) sender).getWorld();
				                world.dropItem(playerLoc, skull).setPickupDelay(0);
				                // player.getInventory().addItem(skull);
				                return true;
				        	}
				    
				        } else if ((blacklist.contains(head)) && (blacklistOn)) {
				        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blacklist-head"))));
					        return false;
				        } else if ((blacklist.contains(head)) && !(blacklistOn)) {
					        if (((Player) sender).getInventory().firstEmpty() == -1) {
					        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("full-inv"))));;
					        } else {
						        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
		                        meta.setOwner(args[0]);
				                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("playerHeadDN").replaceAll("%d", args[0])));
				                skull.setItemMeta(meta);
				                Location playerLoc = ((Player) sender).getLocation();
				                double playerLocY = playerLoc.getY() + 1;
				                playerLoc.setY(playerLocY);
				                World world = ((Player) sender).getWorld();
				                world.dropItem(playerLoc, skull).setPickupDelay(0);
				                return true;
				    	    }
				       }
		          }
				
		        } else {
		    	    sender.sendMessage(HeadsPlusCommand.noPerms);
			        return false;
		       }
	        } else {
	            sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
		        return false;
	        }
	    }
	
	return false;
	}

	
}
	
