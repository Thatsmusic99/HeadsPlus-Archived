package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class BlockEvent implements Listener { // BlockEvents, according to Eclipse, doesn't exist.
	
	List<SkullMeta> SkullM = new ArrayList<>();
	List<Location> SkullL = new ArrayList<>();
	Location skullLoc;
	SkullMeta skullMeta;
	
	@EventHandler 
	public void onPlayerHoldEvent(PlayerItemHeldEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM) {
		    UUID id = e.getPlayer().getUniqueId();
		    ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		    SkullMeta skull = (SkullMeta) item.getItemMeta();
		    HeadsPlusDataFile.writeToData(id, item, skull);
	    }
	}
}
