package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;

public class RecipeEnumUser {
	
	private static final FileConfiguration crafting = HeadsPlusCrafting.getCrafting();
	private static final FileConfiguration heads = HeadsPlusConfigHeads.getHeads();
	public static List<ShapelessRecipe> recipes = new ArrayList<>();

	public static void addEnumToConfig() {
	    try {
            for (RecipeEnums key : RecipeEnums.values()) {
                ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                SkullMeta im = (SkullMeta) i.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + "HeadDN")));
                im.setOwner(heads.getStringList(key.str + "HeadN").get(0));
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
                    recipes.add(recipe);
                    Bukkit.addRecipe(recipe);
                }
            }
            for (RecipeUndefinedEnums key : RecipeUndefinedEnums.values()) {
                ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                SkullMeta im = (SkullMeta) i.getItemMeta();
                if (!(heads.getString(key.str + "HeadDN").equals("")) && !(heads.getStringList(key.str + "HeadN").get(0).equals(""))) {
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(heads.getString(key.str + "HeadDN"))));
                    im.setOwner(heads.getStringList(key.str + "N").get(0));
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
                        recipes.add(recipe);
                        Bukkit.addRecipe(recipe);
                    }
                }
            }
        } catch (Exception e) {
	        HeadsPlus.getInstance().getLogger().log(Level.WARNING, "If you're updating from a version earlier than 3.6, recipes currently don't work. Don't worry; this is just one-off, and next restart they'll be fine.");
        }

	}

	private static ShapelessRecipe getRecipe(ItemStack i, String name) {
	    if (Bukkit.getVersion().contains("1.12") || Bukkit.getVersion().contains("1.13")) {
            return new ShapelessRecipe(new NamespacedKey(HeadsPlus.getInstance(), name), i);
        } else {
	        return new ShapelessRecipe(i);
        }
    }
}
