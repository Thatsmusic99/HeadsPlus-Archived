package io.github.thatsmusic99.headsplus.crafting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RecipePerms implements Listener {
	
	@EventHandler
	public void onCraft(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (player.hasPermission("headsplus.craft")) {
			return;
		}
		if(e.getSlot() == 0){
            if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                player.sendMessage(ChatColor.RED + "You do not have permission to craft these heads!");
                e.setCancelled(true);
            }   
        }
	}

}
