package io.github.thatsmusic99.headsplus.nms;

import io.github.thatsmusic99.headsplus.util.MaterialTranslator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface NewNMSManager extends NMSManager {

    default ItemStack getSkullMaterial(int amount) {
        return new ItemStack(Material.PLAYER_HEAD, amount);
    }

    default ItemStack getColouredBlock(MaterialTranslator.BlockType b, int data) {
        return new ItemStack(MaterialTranslator.toMaterial(b, data));
    }

    default Material getNewItems(MaterialTranslator.ChangedMaterials b) {
        return MaterialTranslator.getItem(b);
    }

    default Material getWallSkull() {
        return Material.PLAYER_WALL_HEAD;
    }

    default Material getSkull0() {
        return Material.SKELETON_SKULL;
    }
}
