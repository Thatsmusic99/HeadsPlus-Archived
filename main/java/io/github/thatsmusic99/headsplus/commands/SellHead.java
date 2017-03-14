package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlusConfigHeads;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SellHead implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
		    Inventory inv = ((Player) sender).getInventory();
		    if (inv.contains(Material.SKULL_ITEM)) {
		    	ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		    	SkullMeta skullM = (SkullMeta) skull.getItemMeta();
		    	String owner = skullM.getOwner();
		    	if (skull.containsEnchantment(Enchantment.DURABILITY)) {
		    		Economy econ = null;
		    		if (owner == HeadsPlusConfigHeads.getHeads().getString("zombieHeadN")) {
		    			Double price = HeadsPlusConfigHeads.getHeads().getDouble("zombieHeadP");
		    			String senderName = sender.getName();
						@SuppressWarnings({ "deprecation", "null" })
						EconomyResponse zr = econ.depositPlayer(senderName, price);
						if (zr.transactionSuccess()) {
							sender.sendMessage(""); // TODO add success message.
						} else {
							sender.sendMessage("" + zr.errorMessage); // TODO add error message.
						}
		    		}
		    	} else {
		    		sender.sendMessage(""); // TODO add config option for false heads.
		    	}
		    	
		    }
		
		} else {
			sender.sendMessage("[HeadsPlus] You must be a player to send this command!");
		}
		return false;
	}

}
