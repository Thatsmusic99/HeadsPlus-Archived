package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class HeadsPlusCrafting {
	
	private static List<ItemStack> zombieI = new ArrayList<>();
	private static List<ItemStack> skeleI = new ArrayList<>();
	
	private static FileConfiguration crafting;
	public static File craftingF;
	
	public static FileConfiguration getCrafting() {
		return crafting;
	}
	
	private static void loadCrafting() {
		getCrafting().options().header("HeadsPlus by Thatsmusic99");
		getCrafting().addDefault("zombieI", zombieI);
		
	}
	public static void addIngredients() {
		ItemStack zombieIt = new ItemStack(Material.ROTTEN_FLESH);
		zombieI.add(zombieIt);
		ItemStack skeleIt = new ItemStack(Material.BONE);
		
	}
}
