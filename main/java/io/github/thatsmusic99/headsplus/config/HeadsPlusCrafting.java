package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusCrafting {
	
	private static List<String> mHeads = HeadsPlusConfigHeads.mHeads;
	private static List<String> uHeads = HeadsPlusConfigHeads.uHeads;
	
	private static List<ItemStack> blazeI = new ArrayList<>();
	private static List<ItemStack> zombieI = new ArrayList<>();
	private static List<ItemStack> skeleI = new ArrayList<>();
	
	private static FileConfiguration crafting;
	public static File craftingF;
	
	public static FileConfiguration getCrafting() {
		return crafting;
	}
	public static void craftingEnable() {
		reloadCrafting();
		loadCrafting();
		checkCrafting();
		reloadCrafting();
	}
	
	private static void loadCrafting() {
		getCrafting().options().header("HeadsPlus by Thatsmusic99");
		getCrafting().addDefault("blazeI", blazeI);
		getCrafting().addDefault("zombieI", zombieI);
		getCrafting().addDefault("skeleI", skeleI);
	}
	public static void addIngredients() {
		ItemStack blazeIt = new ItemStack(Material.BLAZE_POWDER);
		blazeI.add(blazeIt);
		ItemStack zombieIt = new ItemStack(Material.ROTTEN_FLESH);
		zombieI.add(zombieIt);
		ItemStack skeleIt = new ItemStack(Material.BONE);
		skeleI.add(skeleIt);
	}
	public static void reloadCrafting() {
		if (craftingF == null) {
			craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");
		}
		crafting = YamlConfiguration.loadConfiguration(craftingF);
		loadCrafting();
		saveCrafting();
	}
	public static void saveCrafting() {
		if (crafting == null || craftingF == null) {
			return;
		}
		try {
			crafting.save(craftingF);
		} catch (IOException e) {
			HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save crafting.yml!");
			e.printStackTrace();
		}
	}
	public static void checkCrafting() {
		for (String key : mHeads) {
			List<String> keyl = getCrafting().getStringList(key + "I");
			if (keyl.size() > 9) {
				getCrafting().getStringList(key + "I").clear();
			}
		}
		for (String key : uHeads) {
			List<String> keyl = getCrafting().getStringList(key + "I");
			if (keyl.size() > 9) {
				getCrafting().getStringList(key + "I").clear();
			}
		}
	}
}
