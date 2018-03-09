package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnums;
import io.github.thatsmusic99.headsplus.crafting.RecipeUndefinedEnums;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeadsPlusCrafting extends ConfigSettings {

	public HeadsPlusCrafting() {
        this.conName = "crafting";
	    enable(false);
    }
	
	private void loadCrafting() {
		getConfig().options().header("HeadsPlus by Thatsmusic99 - due to the way Bukkit works, this config can only be reloaded on restart.\nInstructions for setting up can be found at: https://github.com/Thatsmusic99/HeadsPlus/wiki");
		getConfig().options().copyDefaults(true);
		save();
	}

	@Override
	public void reloadC(boolean nnn) {
		if (configF == null) {
			configF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");
		}
		config = YamlConfiguration.loadConfiguration(configF);
		loadCrafting();
		checkCrafting();
		save();
	}

	private void checkCrafting() {
		for (RecipeEnums key : RecipeEnums.values()) {
			getConfig().addDefault(key.str + "I", new ArrayList<>(Collections.singletonList(key.mat.toString())));
			List<String> keyl = getConfig().getStringList(key.str + "I");
			if (keyl.size() > 9) {
				getConfig().getStringList(key.str + "I").clear();
			}
		}
		for (RecipeUndefinedEnums key : RecipeUndefinedEnums.values()) {
			getConfig().addDefault(key.str + "I", new ArrayList<String>());
			List<String> keyl = getConfig().getStringList(key.str + "I");
			if (keyl.size() > 9) {
				getConfig().getStringList(key.str + "I").clear();
			}
		}
	}
}
