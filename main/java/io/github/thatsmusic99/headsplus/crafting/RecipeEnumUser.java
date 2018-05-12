package io.github.thatsmusic99.headsplus.crafting;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class RecipeEnumUser {
	
	private final FileConfiguration crafting = HeadsPlus.getInstance().getCraftingConfig().getConfig();
	private final FileConfiguration heads = HeadsPlus.getInstance().getHeadsConfig().getConfig();
	private static List<ShapelessRecipe> recipes = new ArrayList<>();

	public RecipeEnumUser() {
	    addEnumToConfig();
    }

	private void addEnumToConfig() {
	    NMSManager nms = HeadsPlus.getInstance().getNMS();
	    for (RecipeEnums key : RecipeEnums.values()) {
	        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
	        SkullMeta im = (SkullMeta) i.getItemMeta();


	        try {
	            if (key.str.equalsIgnoreCase("sheep")) {
	            	if (heads.getStringList("sheep.name.default").get(0).equalsIgnoreCase("MHF_Sheep")) {
	            	 //   im = (SkullMeta) HeadsPlus.getInstance().getAPI().createSkull(key.tex, "").getItemMeta();
	            	    im = nms.setSkullOwner(heads.getStringList(key.str + ".name.default").get(0), im);
                    } else {
                        im = nms.setSkullOwner(heads.getStringList(key.str + ".name.default").get(0), im);
                    }
	            } else {
	                if (key.equals(RecipeEnums.IRONGOLEM)) {
	                    if (heads.getStringList("irongolem.name").get(0).equalsIgnoreCase("MHF_Golem")) {
                        //    im = (SkullMeta) HeadsPlus.getInstance().getAPI().createSkull(key.tex, "").getItemMeta();
                            im = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);
                        }  else {
                            im = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);
	                    }
	                } else {
                        if (heads.getStringList(key.str + ".name").get(0).equalsIgnoreCase("MHF_" + key.str)) {
                        //    im = (SkullMeta) HeadsPlus.getInstance().getAPI().createSkull(key.tex, "").getItemMeta();
                            im = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);
                        }  else {
                            im = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);
                        }
                    }
	            }
	        } catch (IndexOutOfBoundsException ex) {
	            HeadsPlus.getInstance().getLogger().warning("There was an issue setting the crafting skull for " + key.str + "! Setting it to default...");
	            HeadsPlus.getInstance().getLogger().warning("If your heads.yml was out of date, just let it update and then the config will automatically reload as soon as a player joins (if enabled).");
	            HeadsPlus.getInstance().getLogger().warning("Otherwise, use /hp reload.");
	            im = nms.setSkullOwner("MHF_" + WordUtils.capitalize(key.str), im);
	        }
            i.setItemMeta(im);
            SkullMeta ims = (SkullMeta) i.getItemMeta();
			if (key.str.equalsIgnoreCase("sheep")) {
				if (heads.getStringList("sheep.name.default").get(0).equalsIgnoreCase("MHF_Sheep")) {
					ims = nms.setSkullOwner(heads.getStringList(key.str + ".name.default").get(0), ims);
				} else {
					ims = nms.setSkullOwner(heads.getStringList(key.str + ".name.default").get(0), ims);
				}
			} else {
				if (key.equals(RecipeEnums.IRONGOLEM)) {
					if (heads.getStringList("irongolem.name").get(0).equalsIgnoreCase("MHF_Golem")) {
						ims = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), ims);
					}  else {
						ims = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), ims);
					}
				} else {
					if (heads.getStringList(key.str + ".name").get(0).equalsIgnoreCase("MHF_" + key.str)) {
						ims = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), ims);
					}  else {
						ims = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), ims);
					}
				}
			}
            i.setItemMeta(ims);
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + ".display-name")));
            List<String> strs = new ArrayList<>();
            for (String str : heads.getStringList(key.str + ".lore")) {
                strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{type}", key.str).replaceAll("\\{price}", String.valueOf(heads.getDouble(key.str + ".price")))));
            }
            im.setLore(strs);
            i.setItemMeta(im);
            i = RecipeListeners.makeSell(i, key.str);
	        ShapelessRecipe recipe = nms.getRecipe(i, "hp" + key.name());
	        List<String> ingrs = new ArrayList<>();
	        for (String key2 : crafting.getStringList(key.str + "I")) {
	            recipe.addIngredient(Material.getMaterial(key2));
	            ingrs.add(key2);
	        }
	        recipe.addIngredient(Material.SKULL_ITEM);
	        if (ingrs.size() > 0) {
	            try {
	                recipes.add(recipe);
	                Bukkit.addRecipe(recipe);
	            } catch (IllegalStateException ignored) {

	            }
	        }
	    }
	    for (RecipeUndefinedEnums key : RecipeUndefinedEnums.values()) {
	        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
	        SkullMeta im = (SkullMeta) i.getItemMeta();
	        if (!(heads.getString(key.str + ".display-name").equals("")) && !(heads.getStringList(key.str + ".name").isEmpty())) {
	            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + ".display-name")));

	            im = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);

                i.setItemMeta(im);
                SkullMeta ims;
                ims = nms.setSkullOwner(heads.getStringList(key.str + ".name").get(0), im);

                i.setItemMeta(ims);
                i = RecipeListeners.makeSell(i, key.str);
	            ShapelessRecipe recipe = nms.getRecipe(i, "hp" + key.name());
	            List<String> ingrs = new ArrayList<>();
	            if (crafting.getStringList(key.str + "I") != null) {
	                ingrs = crafting.getStringList(key.str + "I");
	                for (String key2 : ingrs) {
	                    recipe.addIngredient(Material.getMaterial(key2));
	                }
	                if (ingrs.size() > 0) {
	                    recipe.addIngredient(Material.SKULL_ITEM);
	                }
	            } else {
	                crafting.addDefault(key.str + "I", ingrs);
	            }
	            if (ingrs.size() > 0) {
	                try {
	                    recipes.add(recipe);
	                    Bukkit.addRecipe(recipe);
	                } catch (IllegalStateException ignored) {

	                }
	            }
	        }
	    }
	}
}
