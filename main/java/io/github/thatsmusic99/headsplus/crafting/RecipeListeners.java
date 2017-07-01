package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import io.github.thatsmusic99.headsplus.HeadsPlus;

public class RecipeListeners {
	
	public static void makeSell(ItemMeta m) {
		if ((HeadsPlus.getInstance().sellable)) {
			List<String> ls = new ArrayList<>();
			for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
				ls.add(ChatColor.translateAlternateColorCodes('&', str));
			}
			m.setLore(ls);
		}
	}
}
