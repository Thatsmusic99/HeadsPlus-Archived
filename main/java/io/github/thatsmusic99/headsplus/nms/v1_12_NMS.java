package io.github.thatsmusic99.headsplus.nms;


import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class v1_12_NMS implements NMSManager {

    @Override
    public ItemStack addNBTTag(Object i) {
        ItemStack is = (ItemStack) i;
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return is;
    }

    @Override
    public boolean isSellable(Object i) {
        
        return false;
    }
}
