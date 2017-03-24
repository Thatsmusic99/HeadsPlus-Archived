package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BlockEvent implements Listener { // BlockEvents, according to Eclipse, doesn't exist.
	
	List<SkullMeta> SkullM = new ArrayList<>();
	List<Location> SkullL = new ArrayList<>();
	Location skullLoc;
	SkullMeta skullMeta;
	
	@EventHandler 
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlockPlaced().getType() == Material.SKULL) {
			SkullMeta skullM = (SkullMeta) e.getItemInHand().getItemMeta();
			Location skullL = e.getBlockPlaced().getLocation();
			SkullL.add(skullL);
			SkullM.add(skullM);
		} 
	}
	@EventHandler 
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SKULL) {
			if (SkullL.contains(e.getBlock().getLocation())) {
				int index = SkullL.indexOf(e.getBlock().getLocation());
				SkullMeta skullM = SkullM.get(index);
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				skull.setItemMeta(skullM);
				e.getBlock().getDrops().clear();
				e.getBlock().getDrops().add(skull);
			}
		} 
	}
}
