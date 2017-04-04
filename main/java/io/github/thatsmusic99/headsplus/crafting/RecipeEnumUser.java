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
	
	private static FileConfiguration crafting = HeadsPlusCrafting.getCrafting();
	static RecipeEnums enums;
	private static List<String> uHeads = HeadsPlusConfigHeads.uHeads;
	
	@SuppressWarnings("deprecation")
	public static void addEnumToConfig() {
		for (RecipeEnums key : RecipeEnums.values()) {
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta im = (SkullMeta) i.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfigHeads.getHeads().getString(key + "HeadDN"))));
			im.setOwner(HeadsPlusConfigHeads.getHeads().getString(key.str + "HeadN"));
			RecipeListeners.makeSell(im);
			i.setItemMeta(im);
			ShapelessRecipe recipe = new ShapelessRecipe(i);
			List<String> ingrs = new ArrayList<>();
			if (crafting.getStringList(key.str + "I") == null) {
				ingrs.add(key.mat.toString());
				crafting.addDefault(key.str + "I", ingrs);
				recipe.addIngredient(key.mat);
				recipe.addIngredient(Material.SKULL_ITEM, (byte) 0);
				crafting.options().copyDefaults(true);
				HeadsPlusCrafting.saveCrafting();
				
			} else {
				ingrs = crafting.getStringList(key.str + "I");
				for (String key2 : ingrs) {
					recipe.addIngredient(Material.getMaterial(key2));
					
				}
				recipe.addIngredient(Material.SKULL_ITEM, (byte) 0);
			}
			
			Bukkit.addRecipe(recipe);
			
		}
		for (String key : uHeads) {
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta im = (SkullMeta) i.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfigHeads.getHeads().getString(key + "HeadDN"))));
			if (!HeadsPlusConfigHeads.getHeads().getString(key + "HeadN").equals("")) {
			    im.setOwner(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"));
			}
			RecipeListeners.makeSell(im);
			i.setItemMeta(im);
			ShapelessRecipe recipe = new ShapelessRecipe(i);
			List<String> ingrs = new ArrayList<>();
			if (crafting.getStringList(key + "I") == null) {
				crafting.addDefault(key + "I", ingrs);
				recipe.addIngredient(Material.SKULL_ITEM, (byte) 0);
				crafting.options().copyDefaults(true);
				HeadsPlusCrafting.saveCrafting();
				
			} else {
				ingrs = crafting.getStringList(key + "I");
				for (String key2 : ingrs) {
					if ((!key2.equals("")) || !(key2 == null)) {
					    recipe.addIngredient(Material.getMaterial(key2));
					}
					recipe.addIngredient(Material.SKULL_ITEM, (byte) 0);
				}
			}
			if (recipe.getIngredientList().size() > 0) {
			    Bukkit.addRecipe(recipe);
			}
		}
	}

}
