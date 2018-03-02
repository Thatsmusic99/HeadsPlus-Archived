package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusConfigHeads {
	public final List<String> mHeads = new ArrayList<>(Arrays.asList("blaze", "cavespider", "chicken", "cow", "creeper", "enderman", "ghast", "guardian", "irongolem", "mushroomcow", "rabbit", "pig", "sheep", "skeleton", "slime", "spider", "squid", "villager", "witch", "zombie"));
	public final List<String> uHeads = new ArrayList<>(Arrays.asList("bat", "donkey", "enderdragon", "elderguardian", "endermite", "evoker", "horse", "llama", "magmacube", "mule", "parrot", "polarbear", "shulker", "silverfish", "skeletonhorse", "snowman", "stray", "vex", "vindicator", "wither", "witherskeleton"));
	private final List<String> eHeads = new ArrayList<>(Arrays.asList("apple", "cake", "chest", "cactus", "melon", "pumpkin"));
	private final List<String> ieHeads = new ArrayList<>(Arrays.asList("coconutB", "coconutG", "oaklog", "present1", "present2", "tnt", "tnt2", "arrowUp", "arrowDown", "arrowQuestion", "arrowLeft", "arrowRight", "arrowExclamation"));
	public FileConfiguration heads;
	private File headsF;
	
	private FileConfiguration config;

	public HeadsPlusConfigHeads() {
	    headsEnable();
    }
	
	public FileConfiguration getHeads() {
		return heads;
	}

	private void headsEnable() {
		config = HeadsPlus.getInstance().getConfig();
		reloadHeads();
		loadHeads();
	}
	
	private void loadHeads() {
		try {
			getHeads().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
		    addMHFHeads();
		    addUndefinedHeads();
		    addPlayerHeads();
		    addENHeads();
		    addieHeads();
		    updateHeads();
		    checkForOldFormat();
		    getHeads().options().copyDefaults(true);
		    saveHeads();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void reloadHeads() {
		if (headsF == null) {
			headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");
		}
		heads = YamlConfiguration.loadConfiguration(headsF);
		loadHeads();
		saveHeads();
	}
    private void saveHeads() {
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
    private void addUndefinedHeads() {
    	for (String key : uHeads) {
    		getHeads().addDefault(key + ".name", new ArrayList<>());
    		getHeads().addDefault(key + ".chance", 0);
    		getHeads().addDefault(key + ".display-name", "");
    		getHeads().addDefault(key + ".price", 0.00);
    		getHeads().addDefault(key + ".interact-name", WordUtils.capitalize(key));
    	}
    }
    private void addMHFHeads() {
    	
    	for (String key : mHeads) {
    		if (!key.equals("irongolem") && !key.equals("sheep")) {
    		    getHeads().addDefault(key + ".name", Collections.singleton("MHF_" + WordUtils.capitalize(key)));
    		    getHeads().addDefault(key + ".chance", 25);
    		    getHeads().addDefault(key + ".display-name", WordUtils.capitalize(key) + " Head");
    		    getHeads().addDefault(key + ".price", 10.00);
    		    getHeads().addDefault(key + ".interact-name", WordUtils.capitalize(key));
    		    
    		} else if (key.equals("irongolem")) {
    			getHeads().addDefault("irongolem.name", new ArrayList<>(Collections.singleton("MHF_Golem")));
    			getHeads().addDefault("irongolem.chance", 25);
    			getHeads().addDefault("irongolem.display-name", "Iron Golem Head");
    			getHeads().addDefault("irongolem.price", 10.00);
    		    getHeads().addDefault("irongolem.interact-name", "Iron Golem");
    		} else {
    			getHeads().addDefault("sheep.name.default", new ArrayList<>(Collections.singleton("MHF_Sheep")));
    			for (DyeColor dc : DyeColor.values()) {
    			    getHeads().addDefault("sheep.name." + dc.name(), new ArrayList<>(Collections.singleton("HP#" + dc.name().toLowerCase() + "_sheep")));
                }
                getHeads().addDefault(key + ".chance", 25);
                getHeads().addDefault(key + ".display-name", WordUtils.capitalize(key) + " Head");
                getHeads().addDefault(key + ".price", 10.00);
                getHeads().addDefault(key + ".interact-name", WordUtils.capitalize(key));
			}
    	}
    }
    private void addPlayerHeads() {
    	getHeads().addDefault("player.chance", 100);
    	getHeads().addDefault("player.display-name", "%d's head");
    	getHeads().addDefault("player.price", 10.00);
    }
    private void addENHeads() {
    	for (String key : eHeads) {
    		getHeads().addDefault(key + "HeadN", WordUtils.capitalize(key));
    		getHeads().addDefault(key + "N", "MHF_" + key);
    	}
    }
    private void addieHeads() {
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

    private void updateHeads() {
	    if (getHeads().get("blazeHeadN") instanceof String) {
	        for (String key : mHeads) {
	            if (!key.equalsIgnoreCase("sheep") && !key.equalsIgnoreCase("irongolem")) {
	                getHeads().set(key + "HeadN", null);
	                getHeads().addDefault(key + "HeadN", new ArrayList<>(Collections.singleton("MHF_" + WordUtils.capitalize(key))));
                } else if (key.equalsIgnoreCase("irongolem")) {
                    getHeads().set(key + "HeadN", null);
	                getHeads().addDefault(key + "HeadN", new ArrayList<>(Collections.singleton("MHF_Golem")));
                } else {
	                getHeads().set("sheepHeadN", null);
                    getHeads().addDefault("sheep.name.default", new ArrayList<>(Collections.singleton("MHF_Sheep")));
                    for (DyeColor dc : DyeColor.values()) {
                        getHeads().addDefault("sheepHeadN." + dc.name(), new ArrayList<>(Collections.singleton("HP#" + dc.name().toLowerCase() + "_sheep")));
                    }
                }
            }
            for (String key : uHeads) {
                getHeads().set(key + "HeadN", null);
                getHeads().set(key + "HeadN", new ArrayList<>());
            }
        }
    }

    private void checkForOldFormat() {
		if (getHeads().get("blazeHeadDN") instanceof String) {
            for (String key : mHeads) {
                if (!key.equalsIgnoreCase("sheep")) {
                	List<String> ls = getHeads().getStringList(key + "HeadN");
                	int c = getHeads().getInt(key + "HeadC");
                	String dn = getHeads().getString(key + "HeadDN");
                	double p = getHeads().getDouble(key + "HeadP");
                	String e = getHeads().getString(key + "HeadEN");
                	getHeads().set(key + "HeadN", null);
                    getHeads().set(key + "HeadC", null);
                    getHeads().set(key + "HeadDN", null);
                    getHeads().set(key + "HeadP", null);
                    getHeads().set(key + "HeadEN", null);
                    getHeads().set(key + ".name", ls);
                    getHeads().set(key + ".chance", c);
                    getHeads().set(key + ".display-name", dn);
                    getHeads().set(key + ".price", p);
                    getHeads().set(key + ".interact-name", e);

                } else {

                    getHeads().set("sheep.name.default", getHeads().getStringList("sheepHeadN.default"));
                    for (DyeColor dc : DyeColor.values()) {
                        getHeads().set("sheep.name." + dc.name(), getHeads().getStringList("sheepHeadN." + dc.name()));
                    }
                    int c = getHeads().getInt(key + "HeadC");
                    String dn = getHeads().getString(key + "HeadDN");
                    double p = getHeads().getDouble(key + "HeadP");
                    String e = getHeads().getString(key + "HeadEN");
                    getHeads().set("sheepHeadN", null);
                    getHeads().set("sheepHeadC", null);
                    getHeads().set("sheepHeadDN", null);
                    getHeads().set("sheepHeadP", null);
                    getHeads().set("sheepHeadEN", null);
                    getHeads().set("sheep.chance", c);
                    getHeads().set("sheep.display-name", dn);
                    getHeads().set("sheep.price", p);
                    getHeads().set("sheep.interact-name", e);

                }

            }
            for (String key : uHeads) {
                List<String> ls = getHeads().getStringList(key + "HeadN");
                int c = getHeads().getInt(key + "HeadC");
                String dn = getHeads().getString(key + "HeadDN");
                double p = getHeads().getDouble(key + "HeadP");
                String e = getHeads().getString(key + "HeadEN");
                getHeads().set(key + "HeadN", null);
                getHeads().set(key + "HeadC", null);
                getHeads().set(key + "HeadDN", null);
                getHeads().set(key + "HeadP", null);
                getHeads().set(key + "HeadEN", null);
                getHeads().set(key + ".name", ls);
                getHeads().set(key + ".chance", c);
                getHeads().set(key + ".display-name", dn);
                getHeads().set(key + ".price", p);
                getHeads().set(key + ".interact-name", e);

            }
            String dn = getHeads().getString("playerHeadDN");
            int c = getHeads().getInt("playerHeadC");
            double p = getHeads().getDouble("playerHeadP");
            getHeads().set("playerHeadDN", null);
            getHeads().set("playerHeadC", null);
            getHeads().set("playerHeadP", null);
            getHeads().set("player.chance", c);
            getHeads().set("player.display-name", dn);
            getHeads().set("player.price", p);
		}
    }
}
