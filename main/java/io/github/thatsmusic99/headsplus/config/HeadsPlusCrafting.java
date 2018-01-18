package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnums;
import io.github.thatsmusic99.headsplus.crafting.RecipeUndefinedEnums;

public class HeadsPlusCrafting {
	
	public FileConfiguration crafting;
	private File craftingF;
	
	public FileConfiguration getCrafting() {
		return crafting;
	}
	public void craftingEnable() {
		reloadCrafting();
	}

	public HeadsPlusCrafting() {
	    craftingEnable();
    }
	
	private void loadCrafting() {
		getCrafting().options().header("HeadsPlus by Thatsmusic99 - due to the way Bukkit works, this config can only be reloaded on restart.\nInstructions for setting up can be found at: https://github.com/Thatsmusic99/HeadsPlus/wiki");
		getCrafting().options().copyDefaults(true);
		saveCrafting();
	}
	public void reloadCrafting() {
		if (craftingF == null) {
			craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");
		}
		crafting = YamlConfiguration.loadConfiguration(craftingF);
		loadCrafting();
		checkCrafting();
		saveCrafting();
	}
	private void saveCrafting() {
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
	private void checkCrafting() {
		for (RecipeEnums key : RecipeEnums.values()) {
			getCrafting().addDefault(key.str + "I", new ArrayList<>(Collections.singletonList(key.mat.toString())));
			List<String> keyl = getCrafting().getStringList(key.str + "I");
			if (keyl.size() > 9) {
				getCrafting().getStringList(key.str + "I").clear();
			}
		}
		for (RecipeUndefinedEnums key : RecipeUndefinedEnums.values()) {
			getCrafting().addDefault(key.str + "I", new ArrayList<String>());
			List<String> keyl = getCrafting().getStringList(key.str + "I");
			if (keyl.size() > 9) {
				getCrafting().getStringList(key.str + "I").clear();
			}
		}
	}
}
