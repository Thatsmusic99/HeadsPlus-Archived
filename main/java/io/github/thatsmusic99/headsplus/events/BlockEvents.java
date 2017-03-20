package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BlockEvents implements Listener {
	
	ArrayList<String[]> skullList;
	HashMap<Location, SkullMeta> skullmap = new HashMap<Location, SkullMeta>();
	Location skullLoc;
	SkullMeta skullMeta;
	
	@EventHandler 
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getItemInHand().getType() == Material.SKULL_ITEM) {
		    ItemStack skull = e.getItemInHand();
		    SkullMeta skullM = (SkullMeta) skull.getItemMeta();
		    Location skullL = e.getBlock().getLocation();
		    skullMeta = skullM;
		    skullmap.put(skullL, skullM);
		}
	}
	@EventHandler 
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SKULL) {
			Location skullL = e.getBlock().getLocation();
			if (skullmap.containsKey(skullL)) {
				skullLoc = skullL;
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().getType() == Material.SKULL_ITEM) {
			if ((skullmap.containsKey(skullLoc)) && (skullmap.containsKey(skullMeta))) {
				ItemStack skull = e.getItem().getItemStack();
				skull.setItemMeta(skullMeta);
			}
		}
	}

}
