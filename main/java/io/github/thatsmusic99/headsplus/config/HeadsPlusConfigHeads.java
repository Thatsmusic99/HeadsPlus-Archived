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
	public static List<String> uHeads = new ArrayList<>(Arrays.asList("bat", "donkey", "enderdragon", "elderguardian", "endermite", "evoker", "horse", "llama", "magmacube", "mule", "parrot", "polarbear", "rabbit", "shulker", "silverfish", "skeletonhorse", "snowman", "stray", "vex", "vindicator", "wither", "witherskeleton"));
	private static List<String> eHeads = new ArrayList<>(Arrays.asList("apple", "cake", "chest", "cactus", "melon", "pumpkin"));
	private static List<String> ieHeads = new ArrayList<>(Arrays.asList("coconutB", "coconutG", "oaklog", "present1", "present2", "tnt", "tnt2", "arrowUp", "arrowDown", "arrowQuestion", "arrowLeft", "arrowRight", "arrowExclamation"));
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
		try {
			getHeads().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
		    addMHFHeads();
		    addUndefinedHeads();
		    addPlayerHeads();
		    addENHeads();
		    addieHeads();
		    getHeads().options().copyDefaults(true);
		    saveHeads();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
    private static void addENHeads() {
    	for (String key : eHeads) {
    		String str = key.substring(0, 1).toUpperCase();
		    String str2 = key.substring(1, key.length());
    		getHeads().addDefault(key + "HeadEN", str + str2);
    		getHeads().addDefault(key + "HeadN", "MHF_" + key);
    	}
    }
    private static void addieHeads() {
    	for (String key : ieHeads) {
    		if (key.equals("coconutB")) {
    			getHeads().addDefault("brownCoconutHeadEN", "Brown Coconut");
    			getHeads().addDefault("brownCoconutHeadN", "MHF_CoconutB");
    		}
    		if (key.equals("coconutG")) {
    			getHeads().addDefault("greenCoconutHeadEN", "Green Coconut");
    			getHeads().addDefault("greenCoconutHeadN", "MHF_CoconutG");
    		}
    		if (key.equals("oaklog")) {
    			getHeads().addDefault("oakLogHeadEN", "Oak Log");
    			getHeads().addDefault("oakLogHeadN", "MHF_OakLog");
    		}
    		if (key.equals("present1")) {
    			getHeads().addDefault("present1HeadEN", "Present");
    			getHeads().addDefault("present1HeadN", "MHF_Present1");
    		}
    		if (key.equals("present2")) {
    			getHeads().addDefault("present2HeadEN", "Present");
    			getHeads().addDefault("present2HeadN", "MHF_Present2");
    		}
    		if (key.equals("tnt")) {
    			getHeads().addDefault("tntHeadEN", "TNT");
    			getHeads().addDefault("tntHeadN", "MHF_TNT");
    		}
    		if (key.equalsIgnoreCase("tnt2")) {
    			getHeads().addDefault("tnt2HeadEN", "TNT");
    			getHeads().addDefault("tnt2HeadN", "MHF_TNT");
    		}
    		if (key.equalsIgnoreCase("arrowUp")) {
    			getHeads().addDefault("arrowUpHeadEN", "Arrow pointing up");
    			getHeads().addDefault("arrowUpHeadN", "MHF_ArrowUp");
    		}
    		if (key.equalsIgnoreCase("arrowDown")) {
    			getHeads().addDefault("arrowDownHeadEN", "Arrow pointing down");
    			getHeads().addDefault("arrowDownHeadN", "MHF_ArrowDown");
    		}
    		if (key.equalsIgnoreCase("arrowRight")) {
    			getHeads().addDefault("arrowRightHeadEN", "Arrow pointing right");
    			getHeads().addDefault("arrowRightHeadN", "MHF_ArrowRight");
    		}
    		if (key.equalsIgnoreCase("arrowLeft")) {
    			getHeads().addDefault("arrowLeftHeadEN", "Arrow pointing left");
    			getHeads().addDefault("arrowLeftHeadN", "MHF_ArrowLeft");
    		}
    		if (key.equalsIgnoreCase("arrowExclamation")) {
    			getHeads().addDefault("exclamationHeadEN", "Exclamation");
    			getHeads().addDefault("exclamationHeadN", "MHF_Exclamation");
    		}
    		if (key.equalsIgnoreCase("arrowQuestion")) {
    			getHeads().addDefault("questionHeadEN", "Question");
    			getHeads().addDefault("questionHeadN", "MHF_Question");
    		}
    	}
    }


}
