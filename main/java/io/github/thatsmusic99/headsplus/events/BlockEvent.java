package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BlockEvent implements Listener { // BlockEvents, according to Eclipse, doesn't exist.
	
	ArrayList<String[]> skullList;
	TreeMap<Location, SkullMeta> skullmap = new TreeMap<Location, SkullMeta>();
	Location skullLoc;
	SkullMeta skullMeta;
	
	@EventHandler 
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getItemInHand().getType() == Material.SKULL_ITEM) {
		    ItemStack skull = e.getItemInHand();
		    SkullMeta skullM = (SkullMeta) skull.getItemMeta();
		    Location skullL = e.getBlock().getLocation();
		    skullmap.put(skullL, skullM);
		    skullMeta = skullM;
		}
	}
	@EventHandler 
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SKULL) {
			Location skullL = e.getBlock().getLocation();
			if (skullmap.containsValue(skullL)) {
				
				ItemStack skull = new ItemStack(e.getBlock().getType());
				skull.setItemMeta(skullMeta);
				
				
			}
		}
	}

}
