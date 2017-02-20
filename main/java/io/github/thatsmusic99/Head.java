package io.github.thatsmusic99;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class Head implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {    
    // Does the command equal "/head"?
	if (cmd.getName().equalsIgnoreCase("head")) {
		// Is a player sending it?
		if (sender instanceof Player){
			if (args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head [IGN]");
				return false;
			}
			// Checks if the arguments used include A-Z, a-z and 0-9 letters, as well as more than one of them.
			if ((args.length == 1) && !(args[0].matches("^[A-Za-z0-9_]+$"))) {
				sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "This command only handles alphanumeric usernames!");
				return false;
			}
			if (args.length > 1) {
				sender.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/head [IGN]");
				return false;
			}
			if (args[0].length() > 16) {
				sender.sendMessage(ChatColor.DARK_RED + "IGN is too long to be valid! Please use an IGN between 3 and 16 characters.");
				return false;
			}
			if (args[0].length() < 3) {
				sender.sendMessage(ChatColor.DARK_RED + "IGN is too short to be valid! Please use an IGN between 3 and 16 characters.");
				return false;
			}
			
			if ((args.length == 1) && (args[0].matches("^[A-Za-z0-9_]+$")) && (3 < args[0].length() << 16)) {
				List<String> blacklist = (List<String>)HeadsPlus.getInstance().getConfig().getStringList("blacklist");
				Boolean blacklistOn = HeadsPlus.getInstance().getConfig().getBoolean("blacklistOn");
				String head = args[0].toLowerCase();
				if (!(blacklist.contains(head))) {
					if (((Player) sender).getInventory().firstEmpty() == -1) {
						sender.sendMessage(ChatColor.RED + "Your inventory is full!");
					} else {
						Player player = (Player) sender;
		                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			            SkullMeta meta = (SkullMeta) skull.getItemMeta();
		                meta.setOwner(args[0]);
				        meta.setDisplayName(args[0] + "'s head");
				        skull.setItemMeta(meta);
				        player.getInventory().addItem(skull);
				        return true;
					}
				    
				} else if ((blacklist.contains(head)) && (blacklistOn)) {
					sender.sendMessage(ChatColor.RED + "That head is blacklisted and cannot be used!");
					return false;
				} else if ((blacklist.contains(head)) && !(blacklistOn)) {
					if (((Player) sender).getInventory().firstEmpty() == -1) {
						sender.sendMessage(ChatColor.RED + "Your inventory is full!");
					} else {
						Player player = (Player) sender;
		                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			            SkullMeta meta = (SkullMeta) skull.getItemMeta();
		                meta.setOwner(args[0]);
				        meta.setDisplayName(args[0] + "'s head");
				        skull.setItemMeta(meta);
				        player.getInventory().addItem(skull);
				        return true;
					}
				}
		    }
				
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
			return false;
		}
	} else {
		return false;
	}
	
	return false;
	}

	
}
	
