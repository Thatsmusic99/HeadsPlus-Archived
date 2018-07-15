package io.github.thatsmusic99.headsplus.crafting;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.MaterialTranslator;
import org.bukkit.Material;

public enum RecipeEnums {
	
	BLAZE(Material.BLAZE_POWDER, "blaze"), 
	CAVESPIDER(Material.FERMENTED_SPIDER_EYE, "cavespider"), 
	CHICKEN(Material.FEATHER, "chicken"), 
	COW(Material.LEATHER, "cow"), 
	CREEPER(HeadsPlus.getInstance().getNMS().getNewItems(MaterialTranslator.ChangedMaterials.SULPHUR), "creeper"),
	ENDERMAN(Material.ENDER_PEARL, "enderman"), 
	GHAST(HeadsPlus.getInstance().getNMS().getNewItems(MaterialTranslator.ChangedMaterials.FIREWORK_CHARGE), "ghast"),
	GUARDIAN(Material.PRISMARINE_SHARD, "guardian"), 
    IRONGOLEM(Material.IRON_BLOCK, "irongolem"), 
    MUSHROOMCOW(Material.RED_MUSHROOM, "mushroomcow"), 
    PIG(HeadsPlus.getInstance().getNMS().getNewItems(MaterialTranslator.ChangedMaterials.PORK), "pig"),
    SHEEP(HeadsPlus.getInstance().getNMS().getColouredBlock(MaterialTranslator.BlockType.WOOL, 0).getType(), "sheep"),
    SKELETON(Material.BONE, "skeleton"), 
    SLIME(Material.SLIME_BALL, "slime"), 
    SPIDER(Material.SPIDER_EYE, "spider"), 
    SQUID(HeadsPlus.getInstance().getNMS().getNewItems(MaterialTranslator.ChangedMaterials.INK_SAC), "squid"),
    VILLAGER(Material.EMERALD, "villager"), 
    WITCH(Material.POTION, "witch"), 
    ZOMBIE(Material.ROTTEN_FLESH, "zombie");
	
    public final Material mat;
    public final String str;
	
	RecipeEnums(Material mat, String str) {
		this.mat = mat;
		this.str = str;
	}
}

