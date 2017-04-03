package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

public class RecipeListeners {
	
	public static List<ShapelessRecipe> recipes = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void addRecipes() {
		
		ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta zMeta = (SkullMeta) zHead.getItemMeta();
		zMeta.setOwner(HeadsPlusConfigHeads.getHeads().getString("zombieHeadN"));
		zMeta.setDisplayName(HeadsPlusConfigHeads.getHeads().getString("zombieHeadDN"));
		makeSell(zMeta);
		zHead.setItemMeta(zMeta);
		
		ShapelessRecipe zombieRecipe = new ShapelessRecipe(zHead)
	             .addIngredient(Material.ROTTEN_FLESH)
	             .addIngredient(Material.SKULL_ITEM, 0);
		
		HeadsPlus.getInstance().getServer().addRecipe(zombieRecipe);
		recipes.add(zombieRecipe);
		
		ItemStack bHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta bMeta = (SkullMeta) bHead.getItemMeta();
		bMeta.setOwner(HeadsPlusConfigHeads.getHeads().getString("blazeHeadN"));
		bMeta.setDisplayName(HeadsPlusConfigHeads.getHeads().getString("blazeHeadN"));
		makeSell(bMeta);
		bHead.setItemMeta(bMeta);
		
		
		ShapelessRecipe blazeRecipe = new ShapelessRecipe(bHead)
				.addIngredient(Material.BLAZE_POWDER)
				.addIngredient(Material.SKULL_ITEM, 0);

		HeadsPlus.getInstance().getServer().addRecipe(blazeRecipe);
		recipes.add(blazeRecipe);
		
		ItemStack csHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta csMeta = (SkullMeta) csHead.getItemMeta();
		csMeta.setOwner("MHF_CaveSpider");
		csMeta.setDisplayName("Cave Spider");
		makeSell(csMeta);
		csHead.setItemMeta(csMeta);
		
		ShapelessRecipe cavespiderRecipe = new ShapelessRecipe(csHead)
				.addIngredient(Material.FERMENTED_SPIDER_EYE)
				.addIngredient(Material.SKULL_ITEM, 0);
		
		HeadsPlus.getInstance().getServer().addRecipe(cavespiderRecipe);
		recipes.add(cavespiderRecipe);
	}
	public static void makeSell(ItemMeta m) {
		if ((HeadsPlus.getInstance().sellable)) {
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.GOLD + ChatColor.BOLD + "This head can be sold!");
			lore.add("" + ChatColor.GOLD + "Do /sellhead to sell!");
			m.setLore(lore);
		}
	}
}
