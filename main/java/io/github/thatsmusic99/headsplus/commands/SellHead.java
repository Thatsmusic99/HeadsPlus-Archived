package io.github.thatsmusic99.headsplus.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SellHead implements CommandExecutor {
	
	static String lore;
	static String lore2;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			PlayerInventory inv = ((Player) sender).getInventory();
		    ItemStack invi = inv.getItemInMainHand();
		    
		    if ((((Player) sender).getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM) && (sender.hasPermission("headsplus.sellhead"))) {
		    	
		    	SkullMeta skullM = (SkullMeta) invi.getItemMeta();
		        String owner = skullM.getOwner();
		        if (skullM.getLore() != null) {
		            lore = skullM.getLore().get(0);
		            lore2 = skullM.getLore().get(1);
		        }
		    	if ((skullM.getLore() != null) && ((lore.equals("" + ChatColor.GOLD + ChatColor.BOLD + "This head can be sold!")) && (lore2.equals("" + ChatColor.GOLD + "Do /sellhead to sell!")))) {
		    		Economy econ = HeadsPlus.getInstance().econ;
		    		List<String> mHeads = HeadsPlusConfigHeads.mHeads;
		    		List<String> uHeads = HeadsPlusConfigHeads.uHeads; 
		    		for (String key : mHeads) {
		    			
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				if (invi.getAmount() > 0) {
								price = price * invi.getAmount();
							}
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    				
		    			} else if ((owner.matches("MHF_Golem")) && (key.equalsIgnoreCase("irongolem"))) {
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP");
		    				String senderName = sender.getName();
		    				if (invi.getAmount() > 0) {
								price = price * invi.getAmount();
							}
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    			} 
		    		}
		    		for (String key : uHeads) {
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
							if (invi.getAmount() > 0) {
								price = price * invi.getAmount();
							}
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    				
		    			} else if ((!uHeads.contains(key)) && (!mHeads.contains(key))) {
			    				Double price = HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP");
			    				String senderName = sender.getName();
			    				if (invi.getAmount() > 0) {
			    					price = price * invi.getAmount();
			    				}
			    				@SuppressWarnings("deprecation")
								EconomyResponse zr = econ.depositPlayer(senderName, price);
			    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
			    				success = ChatColor.translateAlternateColorCodes('&', success);
			    				String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
								fail = ChatColor.translateAlternateColorCodes('&', fail);
								if (zr.transactionSuccess()) {
									((Player) sender).getInventory().setItemInMainHand(new ItemStack(Material.AIR));
									sender.sendMessage(success);
								} else {
									sender.sendMessage(fail + ": " + zr.errorMessage);
								}
			    			
		    			}
		    		}
		    	} else {
		    		String falseHead = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("false-head"));
		    		falseHead = ChatColor.translateAlternateColorCodes('&', falseHead);
		    		sender.sendMessage(falseHead);
		    	} 
		    	
		    } else {
		    	if (!sender.hasPermission("headsplus.sellhead")) {
		    		sender.sendMessage(HeadsPlusCommand.noPerms);
		    	} else {
		    		String falseItem = HeadsPlusConfig.getMessages().getString("false-item");
		    	falseItem = HeadsPlus.getInstance().translateMessages(falseItem);
		    	falseItem = ChatColor.translateAlternateColorCodes('&', falseItem);
		    	sender.sendMessage(falseItem);
		    	}
		    	
		    	
		    } 
		
		} else {
			sender.sendMessage("[HeadsPlus] You must be a player to send this command!");
		}
		return false;
	}

}
