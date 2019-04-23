package io.github.thatsmusic99.headsplus.nms.v1_14_R1_NMS;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import io.github.thatsmusic99.headsplus.util.AdventCManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class v1_14_R1_NMS implements NewNMSManager {
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

    @Override
    public ItemStack addSection(ItemStack is, String sec) {
        return null;
    }

    @Override
    public String getSection(ItemStack is) {
        return null;
    }

    @Override
    public ItemStack setIcon(ItemStack i, Icon o) {
        return null;
    }

    @Override
    public Icon getIcon(ItemStack i) {
        return null;
    }

    @Override
    public ItemStack setCalendarValue(ItemStack i, String value) {
        return null;
    }

    @Override
    public AdventCManager getCalendarValue(ItemStack i) {
        return null;
    }

    @Override
    public ItemStack setChallenge(ItemStack i, Challenge a) {
        return null;
    }

    @Override
    public Challenge getChallenge(ItemStack is) {
        return null;
    }

    @Override
    public ItemStack removeIcon(ItemStack i) {
        return null;
    }

    @Override
    public String getNMSVersion() {
        return null;
    }

    @Override
    public ItemStack setOpen(ItemStack i, boolean value) {
        return null;
    }

    @Override
    public boolean isOpen(ItemStack is) {
        return false;
    }

    @Override
    public HashMap<String, String> getNBTTags(ItemStack item) {
        return null;
    }

    @Override
    public ItemStack setPrice(ItemStack i, double price) {
        return null;
    }
}
