package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;

public class RecipeEnumUser {
	
	private static final FileConfiguration crafting = HeadsPlusCrafting.getCrafting();
	private static final FileConfiguration heads = HeadsPlusConfigHeads.getHeads();

	public static void addEnumToConfig() {
		 for (RecipeEnums key : RecipeEnums.values()) {
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta im = (SkullMeta) i.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + "HeadDN")));
			im.setOwner(heads.getString(key.str + "HeadN"));
			RecipeListeners.makeSell(im);
			i.setItemMeta(im);
			ShapelessRecipe recipe = new ShapelessRecipe(i);
			List<String> ingrs = new ArrayList<>();
			for (String key2 : crafting.getStringList(key.str + "I")) {
				recipe.addIngredient(Material.getMaterial(key2));
				ingrs.add(key2);
			}
			recipe.addIngredient(Material.SKULL_ITEM/*, (byte) 0*/);
			if (ingrs.size() > 0) {
				Bukkit.addRecipe(recipe);
			}
		}
		for (RecipeUndefinedEnums key : RecipeUndefinedEnums.values()) {
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta im = (SkullMeta) i.getItemMeta();
		    if (!(heads.getString(key.str + "HeadDN").equals("")) && !(heads.getString(key.str + "HeadN").equals(""))) {
		    	im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(heads.getString(key.str + "HeadDN"))));
		    	im.setOwner(heads.getString(key.str + "N"));
		    	RecipeListeners.makeSell(im);
		    	i.setItemMeta(im);
		    	ShapelessRecipe recipe = new ShapelessRecipe(i);
		    	List<String> ingrs = new ArrayList<>();
		    	if (crafting.getStringList(key.str + "I") != null) {
		    		ingrs = crafting.getStringList(key.str + "I");
		    		for (String key2 : ingrs) {
		    			recipe.addIngredient(Material.getMaterial(key2));
		    		}
		    		if (ingrs.size() > 0) {
		    			recipe.addIngredient(Material.SKULL_ITEM/*, (byte) 0*/);
		    		}
		    	} else {
		    		crafting.addDefault(key.str + "I", ingrs);
		    	}
		    	if (ingrs.size() > 0) {
		    		Bukkit.addRecipe(recipe);
		    	}
		    }
    	}
	}
}
