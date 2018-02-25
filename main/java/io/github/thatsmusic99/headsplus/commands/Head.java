package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class Head implements CommandExecutor {

    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {   
	    if (cmd.getName().equalsIgnoreCase("head")) {
		    if (sender instanceof Player){
		    	if (sender.hasPermission("headsplus.head")) {
			        if (args.length == 0) {
				        sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head <IGN|Player giving head to> [IGN]");
				        return true;
			        }
		        	if ((args.length == 1) && !(args[0].matches("^[A-Za-z0-9_]+$"))) {
			         	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("alpha-names"))));
				        return true;
			        }
			        if (args.length > 2) {
			        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("too-many-args"))));
				        sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head <IGN|Player giving head to> [IGN]");
				        return true;
			        }
		    	    if (args[0].length() > 16) {
		    	    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("head-too-long"))));
			    	    return true;
			        }
			        if (args[0].length() < 3) {
			        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("too-short-head"))));
				        return true;
			        }
                    if (args.length == 2) {
                        if (sender.hasPermission("headsplus.head.others")) {
                            if (Bukkit.getPlayer(args[0]) != null) {
                                if (args[1].matches("^[A-Za-z0-9_]+$") && (3 < args[1].length()) && (args[1].length() < 16)) {
                                    String[] s = new String[2];
                                    s[0] = args[1];
                                    s[1] = args[0];
                                    giveH(s, (Player) sender, Bukkit.getPlayer(args[0]));
                                    return true;
                                } else if (!args[1].matches("^[A-Za-z0-9_]+$")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("alpha-names"))));
                                    return true;
                                } else if (args[1].length() < 3) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("too-short-head"))));
                                    return true;
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("head-too-long"))));
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("player-offline"))));
                                return true;
                            }
                        } else {
                            sender.sendMessage(new HeadsPlusCommand().noPerms);
                            return false;
                        }
                    }
			        if (args[0].matches("^[A-Za-z0-9_]+$") && (3 < args[0].length()) && (args[0].length() < 16)) {
                        giveH(args, (Player) sender, (Player) sender);
                        return true;
                    }


                }
            } else {
                sender.sendMessage(new HeadsPlusCommand().noPerms);
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
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().hpch.getHeads().getString("player.display-name").replaceAll("%d", n)));
        skull.setItemMeta(meta);
        Location playerLoc = (p).getLocation();
        double playerLocY = playerLoc.getY() + 1;
        playerLoc.setY(playerLocY);
        World world = (p).getWorld();
        world.dropItem(playerLoc, skull).setPickupDelay(0);
	}

	private void giveH(String[] args, Player sender, Player p) {
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
        if (p.getInventory().firstEmpty() == -1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getMessages().getString("full-inv")));
            return;
        }
        if (wlOn) {
            if (blacklistOn) {
                if (wl.contains(head)) {
                    if (!bl.contains(head)) {
                        giveHead(p, args[0]);
                    } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                        giveHead(p, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("blacklist-head"))));
                    }
                } else if (sender.hasPermission("headsplus.bypass.whitelist")) {
                    if (!bl.contains(head)) {
                        giveHead(p, args[0]);
                    } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                        giveHead(p, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("blacklist-head"))));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("whitelist-head"))));
                }
            } else {
                if (wl.contains(head)) {
                    giveHead(p, args[0]);
                } else if (sender.hasPermission("headsplus.bypass.whitelist")){
                    giveHead(p, args[0]);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("whitelist-head"))));
                }
            }
        } else {
            if (blacklistOn) {
                if (!bl.contains(head)) {
                    giveHead(p, args[0]);
                } else if (sender.hasPermission("headsplus.bypass.blacklist")){
                    giveHead(p, args[0]);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("blacklist-head"))));
                }
            } else {
                giveHead(p, args[0]);
            }
        }
    }
}
	
