package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnumUser;

public class HeadsPlusCrafting {
	
	private static List<String> mHeads = HeadsPlusConfigHeads.mHeads;
	private static List<String> uHeads = HeadsPlusConfigHeads.uHeads;
	
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
		getCrafting().options().header("HeadsPlus by Thatsmusic99 - due to the way Bukkit works, this config can only be reloaded on restart.");
		addIngredients();
	}
	public static void addIngredients() {
		Bukkit.resetRecipes();
		RecipeEnumUser.addEnumToConfig();
		getCrafting().options().copyDefaults(true);
		saveCrafting();
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
