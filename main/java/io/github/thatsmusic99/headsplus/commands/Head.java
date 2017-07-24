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

					    boolean blacklistOn = HeadsPlus.getInstance().getConfig().getBoolean("blacklistOn");
				        String head = args[0].toLowerCase();
				        if (blacklistOn) {
				        	if (!bl.contains(head)) {
				        		HeadsPlus.getInstance().log.info(head);
				        		for (String s : bl) {
				        			HeadsPlus.getInstance().log.info(s);
				        		}
                                giveHead((Player) sender, args[0]);
                                return true;
                            } else if (sender.hasPermission("headsplus.bypass.blacklist")){
                            	HeadsPlus.getInstance().log.info("Ping");
                                giveHead((Player) sender, args[0]);
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("blacklist-head"))));
                                return true;
                            }
                        } else {
                        	HeadsPlus.getInstance().log.info("Pong");
                            giveHead((Player) sender, args[0]);
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
	
