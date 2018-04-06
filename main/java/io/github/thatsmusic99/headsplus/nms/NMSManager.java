package io.github.thatsmusic99.headsplus.nms;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

public interface NMSManager {

    ItemStack addNBTTag(Object item);

    boolean isSellable(Object item);

    SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a);

    void setSkullOwner(OfflinePlayer p, SkullMeta m);

    String getSkullOwnerName(SkullMeta m);

    ShapelessRecipe getRecipe(ItemStack i, String name);

    OfflinePlayer getOfflinePlayer(String name);

    Player getPlayer(String name);

}
