package io.github.thatsmusic99.headsplus.crafting;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class RecipePerms implements Listener {
	
	private static FileConfiguration data = HeadsPlusDataFile.getHPData();
	
	@EventHandler
	public void onCraft(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (data.get(player.getWorld().getName()) != null) {
		    if ((player.hasPermission("headsplus.craft")) && ((player.getGameMode().equals(GameMode.SURVIVAL)  || player.getGameMode().equals(GameMode.ADVENTURE)) && (GameMode.valueOf((String) data.get(player.getWorld().getName()))).equals(GameMode.SURVIVAL))) {
		    	return;
	    	}
	    	if(e.getSlot() == 0){
                if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                    player.sendMessage(ChatColor.RED + "You can not craft heads!");
                    e.setCancelled(true);
                }   
           }
	   }
	}
}
