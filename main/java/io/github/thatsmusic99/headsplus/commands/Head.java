package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class Head implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {   
	    if (cmd.getName().equalsIgnoreCase("head")) {
		    if (sender instanceof Player){
		    	if (sender.hasPermission("headsplus.head")) {
			        if (args.length == 0) {
				        sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head [IGN]");
				        return false;
			        }
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
			        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("too-short-head"))));
				        return false;
			        }
			
			        if (args[0].matches("^[A-Za-z0-9_]+$") && (3 < args[0].length() << 16)) {
			            HeadsPlus.getInstance().saveConfig();
                        List<String> bl = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("blacklist")) {
                    	    bl.add(str.toLowerCase());
                        }
                        List<String> wl = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("whitelist")) {
                            wl.add(str.toLowerCase());
                        }

					    boolean blacklistOn = HeadsPlus.getInstance().getConfig().getBoolean("blacklistOn");
                        boolean wlOn = HeadsPlus.getInstance().getConfig().getBoolean("whitelistOn");
				        String head = args[0].toLowerCase();
				        if (((Player) sender).getInventory().firstEmpty() == -1) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("full-inv")));
                            return true;
                        }
				        if (wlOn) {
                           if (blacklistOn) {
                               if (wl.contains(head)) {
                                   if (!bl.contains(head)) {
                                       giveHead((Player) sender, args[0]);
                                       return true;
                                   } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                                       giveHead((Player) sender, args[0]);
                                       return true;
                                   } else {
                                       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blacklist-head"))));
                                       return true;
                                   }
                               } else if (sender.hasPermission("headsplus.bypass.whitelist")) {
                                   if (!bl.contains(head)) {
                                       giveHead((Player) sender, args[0]);
                                       return true;
                                   } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                                       giveHead((Player) sender, args[0]);
                                       return true;
                                   } else {
                                       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blacklist-head"))));
                                       return true;
                                   }
                               } else {
                                   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("whitelist-head"))));
                                   return true;
                               }
                           } else {
                               if (wl.contains(head)) {
                                   giveHead((Player) sender, args[0]);
                                   return true;
                               } else if (sender.hasPermission("headsplus.bypass.whitelist")){
                                   giveHead((Player) sender, args[0]);
                                   return true;
                               } else {
                                   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("whitelist-head"))));
                                   return true;
                               }
                           }
                        } else {
                            if (blacklistOn) {
                                if (!bl.contains(head)) {
                                    giveHead((Player) sender, args[0]);
                                    return true;
                                } else if (sender.hasPermission("headsplus.bypass.blacklist")){
                                    giveHead((Player) sender, args[0]);
                                    return true;
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blacklist-head"))));
                                    return true;
                                }
                            } else {
                                giveHead((Player) sender, args[0]);
                                return true;
                            }
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
        return false;
    }
	private static void giveHead(Player p, String n) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(n);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("playerHeadDN").replaceAll("%d", n)));
        skull.setItemMeta(meta);
        Location playerLoc = (p).getLocation();
        double playerLocY = playerLoc.getY() + 1;
        playerLoc.setY(playerLocY);
        World world = (p).getWorld();
        world.dropItem(playerLoc, skull).setPickupDelay(0);
	}
}
	
