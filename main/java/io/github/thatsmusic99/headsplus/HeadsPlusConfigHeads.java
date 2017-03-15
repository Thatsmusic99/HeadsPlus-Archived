package io.github.thatsmusic99.headsplus;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HeadsPlusConfigHeads {
	private static FileConfiguration heads;
	public static File headsF;
	
	public static FileConfiguration getHeads() {
		return heads;
	}
	public static void headsEnable() {
		reloadHeads();
		loadHeads();
	}
	
	private static void loadHeads() {
		getHeads().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
		getHeads().addDefault("zombieHeadN", "MHF_Zombie");
		getHeads().addDefault("zombieHeadC", 25);
		getHeads().addDefault("zombieHeadDN", "Zombie Head");
		getHeads().addDefault("zombieHeadP", 50.00);
		getHeads().addDefault("skeletonHeadN", "MHF_Skeleton");
		getHeads().addDefault("skeletonHeadC", 25);
		getHeads().addDefault("skeletonHeadDN", "Skeleton Head");
		getHeads().addDefault("blazeHeadN", "MHF_Blaze");
		getHeads().addDefault("blazeHeadC", 10);
		getHeads().addDefault("blazeHeadDN", "Blaze Head");
		getHeads().addDefault("cavespiderHeadN", "MHF_Cavespider");
		getHeads().addDefault("cavespiderHeadC", 25);
		getHeads().addDefault("cavespiderHeadDN", "Cave Spider Head");
		getHeads().addDefault("chickenHeadN", "MHF_Chicken");
		getHeads().addDefault("chickenHeadC", 25);
		getHeads().addDefault("chickenHeadDN", "Chicken Head");
		getHeads().options().copyDefaults(true);
		saveHeads();
	}
	public static void reloadHeads() {
		if (headsF == null) {
			headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");
		}
		heads = YamlConfiguration.loadConfiguration(headsF);
	}
    public static void saveHeads() {
    	if (heads == null || headsF == null) {
    		return;
    	}
    	try {
    		heads.save(headsF);
    	} catch (IOException e) {
    		HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save heads.yml!");
    		e.printStackTrace();
    	}
    }


}
