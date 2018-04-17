package io.github.thatsmusic99.headsplus.crafting;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.inventory.ItemStack;

class RecipeListeners {
	
	public static ItemStack makeSell(ItemStack item) {
		if ((HeadsPlus.getInstance().canSellHeads())) {
			item = HeadsPlus.getInstance().getNMS().addNBTTag(item);
		}
		return item;
	}
}
