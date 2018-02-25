package io.github.thatsmusic99.headsplus.nms;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class v1_8_R3NMS implements NMSManager {

    @Override
    public ItemStack addNBTTag(Object i) {
        ItemStack is = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return is;
    }

    @Override
    public boolean isSellable(Object i) {
        if (CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag() != null) {
            if (CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag().getBoolean("headsplus-sell")) {
                return true;
            }
        }
        return false;
    }
}
