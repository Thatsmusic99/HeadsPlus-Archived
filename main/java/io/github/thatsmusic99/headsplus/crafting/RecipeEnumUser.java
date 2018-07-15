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

	public RecipeEnumUser() {
	    addEnumToConfig();
    }

	private void addEnumToConfig() {
	    NMSManager nms = HeadsPlus.getInstance().getNMS();
	    for (String key : HeadsPlus.getInstance().getHeadsConfig().mHeads) {
	  //  for (RecipeEnums key : RecipeEnums.values()) {
	        ItemStack i = nms.getSkullMaterial(1);
	        SkullMeta im = (SkullMeta) i.getItemMeta();


	        try {
	            if (key.equalsIgnoreCase("sheep")) {
                    im = nms.setSkullOwner(heads.getStringList(key + ".name.default").get(0), im);
	            } else {
                    im = nms.setSkullOwner(heads.getStringList(key + ".name").get(0), im);
	            }
	        } catch (IndexOutOfBoundsException ex) {
	            HeadsPlus.getInstance().getLogger().warning("There was an issue setting the crafting skull for " + key + "! Setting it to default...");
	            HeadsPlus.getInstance().getLogger().warning("If your heads.yml was out of date, just let it update and then the config will automatically reload as soon as a player joins (if enabled).");
	            HeadsPlus.getInstance().getLogger().warning("Otherwise, use /hp reload.");
	            im = nms.setSkullOwner("MHF_" + WordUtils.capitalize(key), im);
	        }
            i.setItemMeta(im);
            SkullMeta ims = (SkullMeta) i.getItemMeta();
			if (key.equalsIgnoreCase("sheep")) {
                ims = nms.setSkullOwner(heads.getStringList(key + ".name.default").get(0), ims);

			} else {
                ims = nms.setSkullOwner(heads.getStringList(key + ".name").get(0), ims);
			}
            i.setItemMeta(ims);
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key + ".display-name")));
            List<String> strs = new ArrayList<>();
            for (String str : heads.getStringList(key + ".lore")) {
                strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{type}", key).replaceAll("\\{price}", String.valueOf(heads.getDouble(key + ".price")))));
            }
            im.setLore(strs);
            i.setItemMeta(im);
            i = makeSell(i, key);
	        ShapelessRecipe recipe = nms.getRecipe(i, "hp" + key);
	        List<String> ingrs = new ArrayList<>();
	        for (String key2 : crafting.getStringList(key + "I")) {
	            try {
                    recipe.addIngredient(Material.getMaterial(key2));
                    ingrs.add(key2);
                } catch (IllegalArgumentException ex) {
	                HeadsPlus.getInstance().getLogger().warning("Received an error trying to add " + key2 + " as in ingredient for " + key + "'s recipe. If you're on 1.13, please update your material names, or restart the configuration.");
                }

	        }
	        recipe.addIngredient(nms.getSkullMaterial(1).getType());
	        if (ingrs.size() > 0) {
	            try {
	                Bukkit.addRecipe(recipe);
	            } catch (IllegalStateException ignored) {

	            }
	        }
	    }
	    for (String key : HeadsPlus.getInstance().getHeadsConfig().uHeads) {
	        ItemStack i = HeadsPlus.getInstance().getNMS().getSkullMaterial(1);
	        SkullMeta im = (SkullMeta) i.getItemMeta();
	        if (!(heads.getString(key + ".display-name").equals("")) && !(heads.getStringList(key + ".name").isEmpty())) {
	            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', heads.getString(key + ".display-name")));

	            im = nms.setSkullOwner(heads.getStringList(key + ".name").get(0), im);

                i.setItemMeta(im);
                SkullMeta ims;
                ims = nms.setSkullOwner(heads.getStringList(key + ".name").get(0), im);

                i.setItemMeta(ims);
                i = makeSell(i, key);
	            ShapelessRecipe recipe = nms.getRecipe(i, "hp" + key);
	            List<String> ingrs = new ArrayList<>();
	            if (crafting.getStringList(key + "I") != null) {
	                ingrs = crafting.getStringList(key + "I");
	                for (String key2 : ingrs) {
	                    recipe.addIngredient(Material.getMaterial(key2));
	                }
	                if (ingrs.size() > 0) {
	                    recipe.addIngredient(nms.getSkullMaterial(1).getType());
	                }
	            } else {
	                crafting.addDefault(key + "I", ingrs);
	            }
	            if (ingrs.size() > 0) {
	                try {
	                    Bukkit.addRecipe(recipe);
	                } catch (IllegalStateException ignored) {

	                }
	            }
	        }
	    }
	}

	private ItemStack makeSell(ItemStack item, String type) {
	    HeadsPlus hp = HeadsPlus.getInstance();
        if (hp.canSellHeads()) {
            item = hp.getNMS().addNBTTag(item);
            item = hp.getNMS().setType(type, item);
        }
        return item;
    }
}
