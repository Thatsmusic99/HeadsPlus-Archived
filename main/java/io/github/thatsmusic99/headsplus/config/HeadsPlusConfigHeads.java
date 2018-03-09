package io.github.thatsmusic99.headsplus.config;

import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HeadsPlusConfigHeads extends ConfigSettings {
	public final List<String> mHeads = new ArrayList<>(Arrays.asList("blaze", "cavespider", "chicken", "cow", "creeper", "enderman", "ghast", "guardian", "irongolem", "mushroomcow", "rabbit", "pig", "sheep", "skeleton", "slime", "spider", "squid", "villager", "witch", "zombie"));
	public final List<String> uHeads = new ArrayList<>(Arrays.asList("bat", "donkey", "enderdragon", "elderguardian", "endermite", "evoker", "horse", "llama", "magmacube", "mule", "parrot", "polarbear", "shulker", "silverfish", "skeletonhorse", "snowman", "stray", "vex", "vindicator", "wither", "witherskeleton"));
	private final List<String> eHeads = new ArrayList<>(Arrays.asList("apple", "cake", "chest", "cactus", "melon", "pumpkin"));
	private final List<String> ieHeads = new ArrayList<>(Arrays.asList("coconutB", "coconutG", "oaklog", "present1", "present2", "tnt", "tnt2", "arrowUp", "arrowDown", "arrowQuestion", "arrowLeft", "arrowRight", "arrowExclamation"));

	public HeadsPlusConfigHeads() {
	    this.conName = "heads";
	    enable(false);
    }

	@Override
	public void load(boolean ehhLolIDontNeedThisButJavaIsMakingMeAnywaysSoHiHowAreYou) {
		try {
			getConfig().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
		    addMHFHeads();
		    addUndefinedHeads();
		    addPlayerHeads();
		    addENHeads();
		    addieHeads();
		    updateHeads();
		    checkForOldFormat();
		    getConfig().options().copyDefaults(true);
		    save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    private void addUndefinedHeads() {
    	for (String key : uHeads) {
    		getConfig().addDefault(key + ".name", new ArrayList<>());
    		getConfig().addDefault(key + ".chance", 0);
    		getConfig().addDefault(key + ".display-name", "");
    		getConfig().addDefault(key + ".price", 0.00);
    		getConfig().addDefault(key + ".interact-name", WordUtils.capitalize(key));
    	}
    }
    private void addMHFHeads() {
    	
    	for (String key : mHeads) {
    		if (!key.equals("irongolem") && !key.equals("sheep")) {
    		    getConfig().addDefault(key + ".name", Collections.singleton("MHF_" + WordUtils.capitalize(key)));
    		    getConfig().addDefault(key + ".chance", 25);
    		    getConfig().addDefault(key + ".display-name", WordUtils.capitalize(key) + " Head");
    		    getConfig().addDefault(key + ".price", 10.00);
    		    getConfig().addDefault(key + ".interact-name", WordUtils.capitalize(key));
    		    
    		} else if (key.equals("irongolem")) {
    			getConfig().addDefault("irongolem.name", new ArrayList<>(Collections.singleton("MHF_Golem")));
    			getConfig().addDefault("irongolem.chance", 25);
    			getConfig().addDefault("irongolem.display-name", "Iron Golem Head");
    			getConfig().addDefault("irongolem.price", 10.00);
    		    getConfig().addDefault("irongolem.interact-name", "Iron Golem");
    		} else {
    			getConfig().addDefault("sheep.name.default", new ArrayList<>(Collections.singleton("MHF_Sheep")));
    			for (DyeColor dc : DyeColor.values()) {
    			    getConfig().addDefault("sheep.name." + dc.name(), new ArrayList<>(Collections.singleton("HP#" + dc.name().toLowerCase() + "_sheep")));
                }
                getConfig().addDefault(key + ".chance", 25);
                getConfig().addDefault(key + ".display-name", WordUtils.capitalize(key) + " Head");
                getConfig().addDefault(key + ".price", 10.00);
                getConfig().addDefault(key + ".interact-name", WordUtils.capitalize(key));
			}
    	}
    }
    private void addPlayerHeads() {
    	getConfig().addDefault("player.chance", 100);
    	getConfig().addDefault("player.display-name", "%d's head");
    	getConfig().addDefault("player.price", 10.00);
    }
    private void addENHeads() {
    	for (String key : eHeads) {
    		getConfig().addDefault(key + "HeadN", WordUtils.capitalize(key));
    		getConfig().addDefault(key + "N", "MHF_" + key);
    	}
    }
    private void addieHeads() {
    	for (String key : ieHeads) {
    		if (key.equals("coconutB")) {
    			getConfig().addDefault("brownCoconutHeadEN", "Brown Coconut");
    			getConfig().addDefault("brownCoconutHeadN", "MHF_CoconutB");
    		}
    		if (key.equals("coconutG")) {
    			getConfig().addDefault("greenCoconutHeadEN", "Green Coconut");
    			getConfig().addDefault("greenCoconutHeadN", "MHF_CoconutG");
    		}
    		if (key.equals("oaklog")) {
    			getConfig().addDefault("oakLogHeadEN", "Oak Log");
    			getConfig().addDefault("oakLogHeadN", "MHF_OakLog");
    		}
    		if (key.equals("present1")) {
    			getConfig().addDefault("present1HeadEN", "Present");
    			getConfig().addDefault("present1HeadN", "MHF_Present1");
    		}
    		if (key.equals("present2")) {
    			getConfig().addDefault("present2HeadEN", "Present");
    			getConfig().addDefault("present2HeadN", "MHF_Present2");
    		}
    		if (key.equals("tnt")) {
    			getConfig().addDefault("tntHeadEN", "TNT");
    			getConfig().addDefault("tntHeadN", "MHF_TNT");
    		}
    		if (key.equalsIgnoreCase("tnt2")) {
    			getConfig().addDefault("tnt2HeadEN", "TNT");
    			getConfig().addDefault("tnt2HeadN", "MHF_TNT");
    		}
    		if (key.equalsIgnoreCase("arrowUp")) {
    			getConfig().addDefault("arrowUpHeadEN", "Arrow pointing up");
    			getConfig().addDefault("arrowUpHeadN", "MHF_ArrowUp");
    		}
    		if (key.equalsIgnoreCase("arrowDown")) {
    			getConfig().addDefault("arrowDownHeadEN", "Arrow pointing down");
    			getConfig().addDefault("arrowDownHeadN", "MHF_ArrowDown");
    		}
    		if (key.equalsIgnoreCase("arrowRight")) {
    			getConfig().addDefault("arrowRightHeadEN", "Arrow pointing right");
    			getConfig().addDefault("arrowRightHeadN", "MHF_ArrowRight");
    		}
    		if (key.equalsIgnoreCase("arrowLeft")) {
    			getConfig().addDefault("arrowLeftHeadEN", "Arrow pointing left");
    			getConfig().addDefault("arrowLeftHeadN", "MHF_ArrowLeft");
    		}
    		if (key.equalsIgnoreCase("arrowExclamation")) {
    			getConfig().addDefault("exclamationHeadEN", "Exclamation");
    			getConfig().addDefault("exclamationHeadN", "MHF_Exclamation");
    		}
    		if (key.equalsIgnoreCase("arrowQuestion")) {
    			getConfig().addDefault("questionHeadEN", "Question");
    			getConfig().addDefault("questionHeadN", "MHF_Question");
    		}
    	}
    }

    private void updateHeads() {
	    if (getConfig().get("blazeHeadN") instanceof String) {
	        for (String key : mHeads) {
	            if (!key.equalsIgnoreCase("sheep") && !key.equalsIgnoreCase("irongolem")) {
	                getConfig().set(key + "HeadN", null);
	                getConfig().addDefault(key + "HeadN", new ArrayList<>(Collections.singleton("MHF_" + WordUtils.capitalize(key))));
                } else if (key.equalsIgnoreCase("irongolem")) {
                    getConfig().set(key + "HeadN", null);
	                getConfig().addDefault(key + "HeadN", new ArrayList<>(Collections.singleton("MHF_Golem")));
                } else {
	                getConfig().set("sheepHeadN", null);
                    getConfig().addDefault("sheep.name.default", new ArrayList<>(Collections.singleton("MHF_Sheep")));
                    for (DyeColor dc : DyeColor.values()) {
                        getConfig().addDefault("sheepHeadN." + dc.name(), new ArrayList<>(Collections.singleton("HP#" + dc.name().toLowerCase() + "_sheep")));
                    }
                }
            }
            for (String key : uHeads) {
                getConfig().set(key + "HeadN", null);
                getConfig().set(key + "HeadN", new ArrayList<>());
            }
        }
    }

    private void checkForOldFormat() {
		if (getConfig().get("blazeHeadDN") instanceof String) {
            for (String key : mHeads) {
                if (!key.equalsIgnoreCase("sheep")) {
                	List<String> ls = getConfig().getStringList(key + "HeadN");
                	int c = getConfig().getInt(key + "HeadC");
                	String dn = getConfig().getString(key + "HeadDN");
                	double p = getConfig().getDouble(key + "HeadP");
                	String e = getConfig().getString(key + "HeadEN");
                	getConfig().set(key + "HeadN", null);
                    getConfig().set(key + "HeadC", null);
                    getConfig().set(key + "HeadDN", null);
                    getConfig().set(key + "HeadP", null);
                    getConfig().set(key + "HeadEN", null);
                    getConfig().set(key + ".name", ls);
                    getConfig().set(key + ".chance", c);
                    getConfig().set(key + ".display-name", dn);
                    getConfig().set(key + ".price", p);
                    getConfig().set(key + ".interact-name", e);

                } else {

                    getConfig().set("sheep.name.default", getConfig().getStringList("sheepHeadN.default"));
                    for (DyeColor dc : DyeColor.values()) {
                        getConfig().set("sheep.name." + dc.name(), getConfig().getStringList("sheepHeadN." + dc.name()));
                    }
                    int c = getConfig().getInt(key + "HeadC");
                    String dn = getConfig().getString(key + "HeadDN");
                    double p = getConfig().getDouble(key + "HeadP");
                    String e = getConfig().getString(key + "HeadEN");
                    getConfig().set("sheepHeadN", null);
                    getConfig().set("sheepHeadC", null);
                    getConfig().set("sheepHeadDN", null);
                    getConfig().set("sheepHeadP", null);
                    getConfig().set("sheepHeadEN", null);
                    getConfig().set("sheep.chance", c);
                    getConfig().set("sheep.display-name", dn);
                    getConfig().set("sheep.price", p);
                    getConfig().set("sheep.interact-name", e);

                }

            }
            for (String key : uHeads) {
                List<String> ls = getConfig().getStringList(key + "HeadN");
                int c = getConfig().getInt(key + "HeadC");
                String dn = getConfig().getString(key + "HeadDN");
                double p = getConfig().getDouble(key + "HeadP");
                String e = getConfig().getString(key + "HeadEN");
                getConfig().set(key + "HeadN", null);
                getConfig().set(key + "HeadC", null);
                getConfig().set(key + "HeadDN", null);
                getConfig().set(key + "HeadP", null);
                getConfig().set(key + "HeadEN", null);
                getConfig().set(key + ".name", ls);
                getConfig().set(key + ".chance", c);
                getConfig().set(key + ".display-name", dn);
                getConfig().set(key + ".price", p);
                getConfig().set(key + ".interact-name", e);

            }
            String dn = getConfig().getString("playerHeadDN");
            int c = getConfig().getInt("playerHeadC");
            double p = getConfig().getDouble("playerHeadP");
            getConfig().set("playerHeadDN", null);
            getConfig().set("playerHeadC", null);
            getConfig().set("playerHeadP", null);
            getConfig().set("player.chance", c);
            getConfig().set("player.display-name", dn);
            getConfig().set("player.price", p);
		}
    }
}
