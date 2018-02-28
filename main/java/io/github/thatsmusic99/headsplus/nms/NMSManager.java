package io.github.thatsmusic99.headsplus.nms;

import org.bukkit.inventory.ItemStack;

public interface NMSManager {

    ItemStack addNBTTag(Object item);

    boolean isSellable(Object item);

}
