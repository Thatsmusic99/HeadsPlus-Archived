package io.github.thatsmusic99.headsplus.nms.v1_8_R2_NMS;

import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_8_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class v1_8_R2_NMS implements NMSManager {

    @Override
    public org.bukkit.inventory.ItemStack addNBTTag(Object i) {
        net.minecraft.server.v1_8_R2.ItemStack is = CraftItemStack.asNMSCopy((ItemStack) i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return CraftItemStack.asBukkitCopy(is);
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

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return new SearchGUI1_8_R2(p, a);
    }
}
