package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusConfigHeads {
	public static List<String> mHeads = new ArrayList<>(Arrays.asList("blaze", "cavespider", "chicken", "cow", "creeper", "enderman", "ghast", "guardian", "irongolem", "mushroomcow", "pig", "sheep", "skeleton", "slime", "spider", "squid", "villager", "witch", "zombie"));
	public static List<String> uHeads = new ArrayList<>(Arrays.asList("bat", "donkey", "enderdragon", "elderguardian", "endermite", "evoker", "horse", "llama", "magmacube", "mule", "polarbear", "rabbit", "shulker", "silverfish", "skeletonhorse", "snowman", "stray", "vex", "vindicator", "wither", "witherskeleton"));
	public static List<String> eHeads = new ArrayList<>(Arrays.asList("apple", "cake", "chest", "cactus", "melon", "pumpkin"));
	public static List<String> ieHeads = new ArrayList<>(Arrays.asList("coconutB", "coconutG", "oaklog", "present1", "present2", "tnt", "tnt2", "arrowUp", "arrowDown"));
	private static FileConfiguration heads;
	private static File headsF;
	
	public static FileConfiguration config = HeadsPlus.getInstance().getConfig();
	
	public static FileConfiguration getHeads() {
		return heads;
	}
	public static void headsEnable() {
		reloadHeads();
		loadHeads();
	}
	
	private static void loadHeads() {
		getHeads().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
		addMHFHeads();
		addUndefinedHeads();
		addPlayerHeads();
		getHeads().options().copyDefaults(true);
		saveHeads();
	}
	public static void reloadHeads() {
		if (headsF == null) {
			headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");
		}
		heads = YamlConfiguration.loadConfiguration(headsF);
		loadHeads();
		saveHeads();
	}
    private static void saveHeads() {
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
    private static void addUndefinedHeads() {
    	for (String key : uHeads) {
    		String str = key.substring(0, 1).toUpperCase();
		    String str2 = key.substring(1, key.length());
    		getHeads().addDefault(key + "HeadN", "");
    		getHeads().addDefault(key + "HeadC", 0);
    		getHeads().addDefault(key + "HeadDN", "");
    		getHeads().addDefault(key + "HeadP", 0.00);
    		getHeads().addDefault(key + "HeadEN", str + str2);
    	}
    }
    private static void addMHFHeads() {
    	
    	for (String key : mHeads) {
    		if (!key.equals("irongolem")) {
    			String str = key.substring(0, 1).toUpperCase();
    		    String str2 = key.substring(1, key.length());
    		    getHeads().addDefault(key + "HeadN", "MHF_" + str + str2);
    		    getHeads().addDefault(key + "HeadC", 25);
    		    getHeads().addDefault(key + "HeadDN", str + str2 + " Head");
    		    getHeads().addDefault(key + "HeadP", 10.00);
    		    getHeads().addDefault(key + "HeadEN", str + str2);
    		    
    		} else {
    			getHeads().addDefault("irongolemHeadN", "MHF_Golem");
    			getHeads().addDefault("irongolemHeadC", 25);
    			getHeads().addDefault("irongolemHeadDN", "Iron Golem Head");
    			getHeads().addDefault("irongolemHeadP", 10.00);
    		    getHeads().addDefault("irongolemHeadEN", "Iron Golem");
    		}
    	}
    }
    private static void addPlayerHeads() {
    	getHeads().addDefault("playerHeadC", 100);
    	getHeads().addDefault("playerHeadDN", "%d's head");
    	getHeads().addDefault("playerHeadP", 10.00);
    }


}
