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
	        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key.str + ".display-name")));

	        try {
	            if (key.str.equalsIgnoreCase("sheep")) {
	                nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList("sheep.name.default").get(0)), im);
	            } else {
                        nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList(key.str + ".name").get(0)), im);
	            }
	        } catch (IndexOutOfBoundsException ex) {
	            HeadsPlus.getInstance().getLogger().warning("There was an issue setting the crafting skull for " + key.str + "! Setting it to default...");
	            HeadsPlus.getInstance().getLogger().warning("If your heads.yml was out of date, just let it update and then the config will automatically reload as soon as a player joins (if enabled).");
	            HeadsPlus.getInstance().getLogger().warning("Otherwise, use /hp reload.");
	            nms.setSkullOwner(nms.getOfflinePlayer("MHF_" + WordUtils.capitalize(key.str)), im);
	        }
            i.setItemMeta(im);
            SkullMeta ims = (SkullMeta) i.getItemMeta();
            try {
                if (key.str.equalsIgnoreCase("sheep")) {
                    nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList("sheep.name.default").get(0)), im);
                } else {
                    nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList(key.str + ".name").get(0)), im);
                }
            } catch (IndexOutOfBoundsException ex) {
                HeadsPlus.getInstance().getLogger().warning("There was an issue setting the crafting skull for " + key.str + "! Setting it to default...");
                HeadsPlus.getInstance().getLogger().warning("If your heads.yml was out of date, just let it update and then the config will automatically reload as soon as a player joins (if enabled).");
                HeadsPlus.getInstance().getLogger().warning("Otherwise, use /hp reload.");
                nms.setSkullOwner(nms.getOfflinePlayer("MHF_" + WordUtils.capitalize(key.str)), im);
            }
            i.setItemMeta(ims);
            i.setItemMeta(im); // Beating the hell out of it twice because otherwise it doesn't set textures
            i = RecipeListeners.makeSell(i);
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

	            nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList(key.str + ".name").get(0)), im);
	            i.setItemMeta(im);
                SkullMeta ims = (SkullMeta) i.getItemMeta();
                nms.setSkullOwner(nms.getOfflinePlayer(heads.getStringList(key.str + ".name").get(0)), ims);
                i.setItemMeta(ims);
                i.setItemMeta(im);
                i = RecipeListeners.makeSell(i);
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
