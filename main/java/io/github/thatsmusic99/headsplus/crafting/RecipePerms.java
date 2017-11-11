package io.github.thatsmusic99.headsplus.crafting;

import java.util.List;

import io.github.thatsmusic99.headsplus.api.HeadCraftEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class RecipePerms implements Listener {
	
	@EventHandler
	public void onCraft(InventoryClickEvent e) {
		if (HeadsPlus.getInstance().getConfig().getBoolean("craftHeads")) {
			Player player = (Player) e.getWhoClicked();
		    List<String> worlds = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
		    if ((!worlds.contains(player.getWorld().getName())) || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn") || player.hasPermission("headsplus.bypass.blacklistw")) {
				if (HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(player.getWorld().getName())) {
					if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            HeadCraftEvent event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation());
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
						return;
					}
				} else if (player.hasPermission("headsplus.bypass.whitelistw")){
                    if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            HeadCraftEvent event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation());
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                        return;
                    }
                } else if (!HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
                    if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            HeadCraftEvent event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation());
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                        return;
                    }
                }

		    }
	        if(e.getSlot() == 0){
                if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                	if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                		player.sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                	}
                }   
	        }
		} else {
			if(e.getSlot() == 0){
                if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                	if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                		e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                	}
                }   
	        }
		}
	}
}
