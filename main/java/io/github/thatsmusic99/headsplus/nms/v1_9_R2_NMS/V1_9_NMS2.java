package io.github.thatsmusic99.headsplus.nms.v1_9_R2_NMS;

import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("deprecation")
public class V1_9_NMS2 implements NMSManager {

    @Override
    public org.bukkit.inventory.ItemStack addNBTTag(Object i) {
        ItemStack is = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public boolean isSellable(Object i) {
        if (CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag() != null) {
            return CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag().getBoolean("headsplus-sell");
        }
        return false;
    }

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return new SearchGUI1_9_R2(p, a);
    }

    @Override
    public void setSkullOwner(OfflinePlayer p, SkullMeta m) {
        m.setOwner(p.getName());
    }

    @Override
    public String getSkullOwnerName(SkullMeta m) {
        return m.getOwner();
    }

    @Override
    public ShapelessRecipe getRecipe(org.bukkit.inventory.ItemStack i, String name) {
        return new ShapelessRecipe(i);
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    @Override
    public Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }
}
