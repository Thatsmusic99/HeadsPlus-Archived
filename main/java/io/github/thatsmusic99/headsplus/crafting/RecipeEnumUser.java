package io.github.thatsmusic99.headsplus.crafting;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class RecipeEnumUser {
	
	private final FileConfiguration crafting = HeadsPlus.getInstance().hpcr.getCrafting();
	private final FileConfiguration heads = HeadsPlus.getInstance().hpch.getHeads();
	private List<ShapelessRecipe> recipes = new ArrayList<>();

	public RecipeEnumUser() {
	    addEnumToConfig();
    }

	private void addEnumToConfig() {
            for (RecipeEnums key : RecipeEnums.values()) {
                ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                SkullMeta im = (SkullMeta) i.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + ".display-name")));
                if (key.str.equalsIgnoreCase("sheep")) {
                    im.setOwner(heads.getStringList("sheep.name.default").get(0));
                } else {
                    im.setOwner(heads.getStringList(key.str + ".name").get(0));
                }

                RecipeListeners.makeSell(im);
                i.setItemMeta(im);
                ShapelessRecipe recipe = getRecipe(i, "hp" + key.name());
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
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(heads.getString(key.str + ".display-name"))));
                    im.setOwner(heads.getStringList(key.str + ".name").get(0));
                    RecipeListeners.makeSell(im);
                    i.setItemMeta(im);
                    ShapelessRecipe recipe = getRecipe(i, "hp" + key.name());
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

	private ShapelessRecipe getRecipe(ItemStack i, String name) {
	    if (Bukkit.getVersion().contains("1.12") || Bukkit.getVersion().contains("1.13")) {
            return new ShapelessRecipe(new NamespacedKey(HeadsPlus.getInstance(), "HeadsPlus_" + name), i);
        } else {
	        return new ShapelessRecipe(i);
       }
    }
}
