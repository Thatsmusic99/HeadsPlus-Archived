package io.github.thatsmusic99.headsplus.crafting;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class RecipeEnumUser {

    private final HeadsPlus hp = HeadsPlus.getInstance();
	private final FileConfiguration crafting = hp.getCraftingConfig().getConfig();
	private final HeadsPlusConfigHeads heads = hp.getHeadsConfig();

	public RecipeEnumUser() {
	    addEnumToConfig();
    }

	private void addEnumToConfig() {
	    NMSManager nms = hp.getNMS();
	    for (String key : hp.getHeadsConfig().mHeads) {
            hp.debug("Checking head for " + key + "...", 3);
	    	try {
	    	    if (key.equalsIgnoreCase("sheep")) {
                    hp.debug("Crafting head for " + key + "...", 3);
                    if (!(heads.getDisplayName(key).equals(""))) {
                        for (DyeColor d : DyeColor.values()) {
                            ItemStack i = new ItemStack(nms.getSkullMaterial(1));
                            SkullMeta im = (SkullMeta) i.getItemMeta();


                            try {
                                if (heads.getConfig().getStringList(key + ".name." + d.name()).get(0).startsWith("HP#")) {
                                    i = HeadsPlus.getInstance().getHeadsXConfig().getSkull(heads.getConfig().getStringList(key + ".name." + d.name()).get(0));
                                    im = (SkullMeta) i.getItemMeta();
                                } else {
                                    im = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name." + d.name()).get(0), im);
                                }

                            } catch (IndexOutOfBoundsException ex) {
                                if (heads.getConfig().getStringList(key + ".name.default").get(0).startsWith("HP#")) {
                                    i = HeadsPlus.getInstance().getHeadsXConfig().getSkull(heads.getConfig().getStringList(key + ".name.default").get(0));
                                    im = (SkullMeta) i.getItemMeta();
                                } else {
                                    im = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name.default").get(0), im);
                                }
                            }
                            i.setItemMeta(im);
                            SkullMeta ims = (SkullMeta) i.getItemMeta();
                            try {
                                ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name." + d.name()).get(0), ims);
                            } catch (IndexOutOfBoundsException ex) {
                                ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name.default").get(0), ims);
                            }
                            i.setItemMeta(ims);

                            i.setItemMeta(setupItemMeta(im, key));
                            i = makeSell(i, key);

                            ShapelessRecipe recipe = nms.getRecipe(i, "hp_" + d.name().toLowerCase() + key);
                            List<String> ingrs = new ArrayList<>();
                            for (String key2 : crafting.getStringList(key + "." + d.name() + ".ingredients")) {
                                try {

                                    recipe.addIngredient(Material.getMaterial(key2));
                                    ingrs.add(key2);
                                } catch (IllegalArgumentException e) {
                                }
                            }
                            recipe.addIngredient(new ItemStack(Material.getMaterial(crafting.getString("base-item.material")), 1, (short) crafting.getInt("base-item.data")).getType());
                            if (ingrs.size() > 0) {
                                try {
                                    Bukkit.addRecipe(recipe);
                                } catch (IllegalStateException e) {
                                }
                            }
                        }
                    }
                } else if (!(heads.getDisplayName(key).equals("")) && !(heads.getConfig().getStringList(key + ".name").isEmpty())) {
                    hp.debug("Crafting head for " + key + "...", 3);
                    ItemStack i = new ItemStack(nms.getSkullMaterial(1));

                    i.setItemMeta(setupItemMeta(i.getItemMeta(), key));
                    if (heads.getConfig().getStringList(key + ".name").get(0).startsWith("HP#")) {
                        i = HeadsPlus.getInstance().getHeadsXConfig().getSkull(heads.getConfig().getStringList(key + ".name").get(0));
                    } else {
                        SkullMeta im = (SkullMeta) i.getItemMeta();
                        im = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name").get(0), im);
                        i.setItemMeta(im);
                        SkullMeta ims = (SkullMeta) i.getItemMeta();
                        if (key.equalsIgnoreCase("sheep")) {
                            ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name.default").get(0), ims);

                        } else {
                            ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name").get(0), ims);
                        }
                        i.setItemMeta(ims);
                        i.setItemMeta(im);
                    }
                    i = makeSell(i, key);
                    if (heads.getConfig().getStringList(key + ".name").get(0).equalsIgnoreCase("{mob-default}")) {
                        if (key.equalsIgnoreCase("skeleton")) {
                            i.setType(nms.getSkull(0).getType());
                        } else if (key.equalsIgnoreCase("zombie")) {
                            i.setType(nms.getSkull(2).getType());
                        } else if (key.equalsIgnoreCase("creeper")) {
                            i.setType(nms.getSkull(4).getType());
                        } else {
                            i.setType(nms.getSkull(3).getType());
                        }
                    }
                    ShapelessRecipe recipe = nms.getRecipe(i, "hp_" + key);
                    List<String> ingrs = new ArrayList<>();
                    for (String key2 : crafting.getStringList(key + ".ingredients")) {
                        try {
                            recipe.addIngredient(Material.getMaterial(key2));
                            ingrs.add(key2);
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                    recipe.addIngredient(new ItemStack(Material.getMaterial(crafting.getString("base-item.material")), 1, (short) crafting.getInt("base-item.data")).getType());
                    if (ingrs.size() > 0) {
                        try {
                            Bukkit.addRecipe(recipe);
                        } catch (IllegalStateException ignored) {

                        }
                    }
                }

            } catch (NullPointerException ex) {
                HeadsPlus.getInstance().getLogger().warning("Error thrown creating head for " + key + ". If this mob isn't in your server version, ignore this message.");
            } catch (IndexOutOfBoundsException ignored) {
            } catch (Exception e) {
	    	    hp.getLogger().severe("Error thrown creating head for " + key + ". Please check the report for details.");
                new DebugPrint(e, "Startup (Crafting)", false, null);
            }
	  //  for (RecipeEnums key : RecipeEnums.values()) {

	    }
	    for (String key : hp.getHeadsConfig().uHeads) {
	        hp.debug("Checking head for " + key + "...", 3);
	        try {
                ItemStack i = hp.getNMS().getSkullMaterial(1);
                SkullMeta im = (SkullMeta) i.getItemMeta();
                try {
                    if (!(heads.getDisplayName(key).equals("")) && !(heads.getConfig().getStringList(key + ".name").isEmpty())) {
                        hp.debug("Crafting head for " + key + "...", 3);
                        i.setItemMeta(setupItemMeta(im, key));
                        if (heads.getConfig().getStringList(key + ".name").get(0).startsWith("HP#")) {
                            i = HeadsPlus.getInstance().getHeadsXConfig().getSkull(heads.getConfig().getStringList(key + ".name").get(0));
                            i.setItemMeta(setupItemMeta(i.getItemMeta(), key));
                        } else {
                            im = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name").get(0), im);
                            i.setItemMeta(im);
                            SkullMeta ims = (SkullMeta) i.getItemMeta();
                            if (key.equalsIgnoreCase("sheep")) {
                                ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name.default").get(0), ims);

                            } else {
                                ims = nms.setSkullOwner(heads.getConfig().getStringList(key + ".name").get(0), ims);
                            }
                            i.setItemMeta(ims);
                            i.setItemMeta(im);
                        }
                        i = makeSell(i, key);
                        if (heads.getConfig().getStringList(key + ".name").get(0).equalsIgnoreCase("{mob-default}")) {
                            if (key.equalsIgnoreCase("witherskeleton")) {
                                i.setType(nms.getSkull(1).getType());
                            } else if (key.equalsIgnoreCase("enderdragon")) {
                                i.setType(nms.getSkull(5).getType());
                            } else {
                                i.setType(nms.getSkull(3).getType());
                            }
                        }
                        ShapelessRecipe recipe = nms.getRecipe(i, "hp_" + key);
                        List<String> ingrs = new ArrayList<>();
                        if (crafting.getStringList(key + ".ingredients") != null) {
                            ingrs = crafting.getStringList(key + ".ingredients");
                            for (String key2 : ingrs) {
                                try {
                                    recipe.addIngredient(Material.getMaterial(key2));
                                } catch (IllegalArgumentException ignored) {

                                }

                            }
                            if (ingrs.size() > 0) {
                                recipe.addIngredient(new ItemStack(Material.getMaterial(crafting.getString("base-item.material")), 1, (short) crafting.getInt("base-item.data")).getType());
                            }
                        } else {
                            crafting.addDefault(key + ".ingredients", ingrs);
                        }
                        if (ingrs.size() > 0) {
                            try {
                                Bukkit.addRecipe(recipe);
                            } catch (IllegalStateException ignored) {

                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    HeadsPlus.getInstance().getLogger().warning("Error thrown creating head for " + key + ". If this mob isn't in your server version, ignore this message.");
                }

            } catch (Exception e) {
                HeadsPlus.getInstance().getLogger().severe("Error thrown creating head for " + key + ". Please check the report for details.");
                new DebugPrint(e, "Startup (Crafting)", false, null);
            }

	    }
	}

	private ItemStack makeSell(ItemStack item, String type) {
        if (hp.canSellHeads()) {
            item = hp.getNMS().addNBTTag(item);
            item = hp.getNMS().setType(type, item);
            item = hp.getNMS().setPrice(item, heads.getPrice(type));
        }
        return item;
    }

    private ItemMeta setupItemMeta(ItemMeta im, String key) {
        im.setDisplayName(heads.getDisplayName(key));
        List<String> strs = new ArrayList<>();
        for (String str : heads.getLore(key)) {
            strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{type}", key).replaceAll("\\{price}", String.valueOf(heads.getPrice(key)))));
        }
        im.setLore(strs);
        return im;
    }
}
