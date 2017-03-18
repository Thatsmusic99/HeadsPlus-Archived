package io.github.thatsmusic99.headsplus.crafting;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipePerms implements Listener {
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		List<ShapelessRecipe> recipes = RecipeListeners.recipes;
		if (recipes.contains(e.getRecipe())) {
			if (e.getWhoClicked().hasPermission("headsplus.craft")) {
				return;
			} else {
				e.setCancelled(true);
			}
		}
	}

}
