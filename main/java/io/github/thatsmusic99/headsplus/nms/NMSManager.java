package io.github.thatsmusic99.headsplus.nms;

import com.mojang.authlib.GameProfile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

public interface NMSManager {

    ItemStack addNBTTag(Object item);

    boolean isSellable(Object item);

    SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a);

    default SkullMeta setSkullOwner(String s, SkullMeta m) {
        m.setOwner(s);
        return m;
    }

    String getSkullOwnerName(SkullMeta m);

    ShapelessRecipe getRecipe(ItemStack i, String name);

    OfflinePlayer getOfflinePlayer(String name);

    Player getPlayer(String name);

    RecipeManager getRecipeManager();

    GameProfile getGameProfile(ItemStack s);

    ItemStack getItemInHand(Player p);
}
