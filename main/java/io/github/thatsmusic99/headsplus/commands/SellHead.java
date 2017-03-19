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
import io.github.thatsmusic99.headsplus.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.HeadsPlusConfigHeads;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SellHead implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			PlayerInventory inv = ((Player) sender).getInventory();
		    ItemStack invi = inv.getItemInMainHand();
		    
		    if (((Player) sender).getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM) {
		    	
		    	ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		        SkullMeta skullM = (SkullMeta) invi.getItemMeta();
		        String owner = skullM.getOwner();
		    	if ((skullM.getLore() != null) && (skullM.getLore().get(0) == " ")) {
		    		Economy econ = null;
		    		List<String> mHeads = HeadsPlusConfigHeads.mHeads;
		    		List<String> uHeads = HeadsPlusConfigHeads.uHeads; 
		    		for (String key : mHeads) {
		    			
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				@SuppressWarnings({ "deprecation", "null" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().remove(skull);
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    				
		    			} else if ((owner.matches("MHF_Golem")) && (key.equalsIgnoreCase("irongolem"))) {
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP");
		    				String senderName = sender.getName();
		    				@SuppressWarnings({ "deprecation", "null" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().remove(skull);
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
								sender.sendMessage(key);
								
							}
		    			}
		    		}
		    		for (String key : uHeads) {
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				@SuppressWarnings({ "deprecation", "null" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success"));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								((Player) sender).getInventory().remove(skull);
								sender.sendMessage(success);
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    				
		    			} else {
		    				sender.sendMessage("You failure.");
		    			}
		    		}
		    	} else {
		    		String falseHead = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("false-head"));
		    		falseHead = ChatColor.translateAlternateColorCodes('&', falseHead);
		    		sender.sendMessage(falseHead);
		    	} 
		    	
		    } /*else {
		    	sender.sendMessage("Pong"); // TODO Add incorrect item message
		    	sender.sendMessage(invi.toString());
		    	sender.sendMessage(skull.toString());
		    } */
		
		} else {
			sender.sendMessage("[HeadsPlus] You must be a player to send this command!");
		}
		return false;
	}

}
