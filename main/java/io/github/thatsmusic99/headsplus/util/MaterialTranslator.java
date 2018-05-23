package io.github.thatsmusic99.headsplus.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class MaterialTranslator {

    // By brainsynder for 1.13

    public static Material toMaterial (BlockType type, int data) {
        if (type == null) throw new NullPointerException("BlockType can not be null");
        if ((data < 0) || (data > 15)) throw new IndexOutOfBoundsException("data value has to be between 0 -> 15");

        DyeColor dye = DyeColor.values()[data];
        return Material.valueOf(dye.name()+"_"+type.name());
    }

    public enum BlockType {
        CONCRETE,
        CONCRETE_POWDER,
        STAINED_GLASS,
        STAINED_GLASS_PANE,
        SHULKER_BOX,
        TERRACOTTA,
        GLAZED_TERRACOTTA,
        CARPET,
        DYE,
        WOOL
    }
}
