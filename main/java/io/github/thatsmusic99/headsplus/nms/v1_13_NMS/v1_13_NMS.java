package io.github.thatsmusic99.headsplus.nms.v1_13_NMS;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

public class v1_13_NMS implements NewNMSManager {
    @Override
    public ItemStack addNBTTag(Object item) {
        return null;
    }

    @Override
    public boolean isSellable(Object item) {
        return false;
    }

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return null;
    }

    @Override
    public String getSkullOwnerName(SkullMeta m) {
        return null;
    }

    @Override
    public ShapelessRecipe getRecipe(ItemStack i, String name) {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return null;
    }

    @Override
    public Player getPlayer(String name) {
        return null;
    }

    @Override
    public GameProfile getGameProfile(ItemStack s) {
        return null;
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return null;
    }

    @Override
    public ItemStack setType(String s, ItemStack i) {
        return null;
    }

    @Override
    public String getType(ItemStack i) {
        return null;
    }

    @Override
    public ItemStack addDatabaseHead(ItemStack is, String id, double price) {
        return null;
    }

    @Override
    public double getPrice(ItemStack is) {
        return 0;
    }

    @Override
    public String getId(ItemStack id) {
        return null;
    }

 /*   @Override
    public ItemStack getSkullMaterial(int amount) {
        return new ItemStack(Material.PLAYER_HEAD, amount);
    } */
}
