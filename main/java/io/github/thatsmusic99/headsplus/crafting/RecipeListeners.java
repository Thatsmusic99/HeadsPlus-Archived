package io.github.thatsmusic99.headsplus.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class RecipeListeners {
	
	@SuppressWarnings("deprecation")
	public static void addRecipes() {
		ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta zMeta = (SkullMeta) zHead.getItemMeta();
		zMeta.setOwner("MHF_Zombie");
		zMeta.setDisplayName("Zombie Head");
		zMeta.addEnchant(Enchantment.LUCK, 1, true);
		zMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		zHead.setItemMeta(zMeta);
		
		
		ShapelessRecipe zombieRecipe = new ShapelessRecipe(zHead)
	             .addIngredient(Material.ROTTEN_FLESH)
	             .addIngredient(Material.SKULL_ITEM, 0);
		
		HeadsPlus.getInstance().getServer().addRecipe(zombieRecipe);
		ItemStack bHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta bMeta = (SkullMeta) bHead.getItemMeta();
		bMeta.setOwner("MHF_Blaze");
		bMeta.setDisplayName("Blaze Head");
		bMeta.addEnchant(Enchantment.LUCK, 1, true);
		bMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		bHead.setItemMeta(bMeta);

		
		
	}
	

}
