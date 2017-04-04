package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;

public class RecipeEnumUser {
	
	private static FileConfiguration crafting = HeadsPlusCrafting.getCrafting();
	static RecipeEnums enums;
	private static List<String> uHeads = HeadsPlusConfigHeads.uHeads;
	
	public static void addEnumToConfig() {
		for (RecipeEnums key : RecipeEnums.values()) {
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta im = (SkullMeta) i.getItemMeta();
			im.setDisplayName(HeadsPlusConfigHeads.getHeads().getString(key.str + "HeadDN"));
			im.setOwner(HeadsPlusConfigHeads.getHeads().getString(key.str + "HeadN"));
			RecipeListeners.makeSell(im);
			i.setItemMeta(im);
			ShapelessRecipe recipe = new ShapelessRecipe(i)
					.addIngredient(key.mat);
			Bukkit.addRecipe(recipe);
			List<String> ingrs = new ArrayList<>();
			ingrs.add(key.mat.toString());
			crafting.addDefault(key.str + "I", ingrs);
		}
		for (String key : uHeads) {
			// TODO finish.
		}
	}

}
