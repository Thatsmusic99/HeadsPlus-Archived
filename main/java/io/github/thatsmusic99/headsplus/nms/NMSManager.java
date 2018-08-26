package io.github.thatsmusic99.headsplus.nms;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.challenges.HPChallengeRewardTypes;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.MaterialTranslator;
import org.bukkit.Material;
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

    GameProfile getGameProfile(ItemStack s);

    ItemStack getItemInHand(Player p);

    ItemStack setType(String s, ItemStack i);

    String getType(ItemStack i);

    default ItemStack getSkullMaterial(int amount) {
        return new ItemStack(Material.getMaterial("SKULL_ITEM"), amount, (byte) 3);
    }

    default ItemStack getColouredBlock(MaterialTranslator.BlockType b, int data) {
        if (b.equals(MaterialTranslator.BlockType.TERRACOTTA)) {
            return new ItemStack(Material.getMaterial("STAINED_CLAY"), 1, (byte) data);
        } else if (b.equals(MaterialTranslator.BlockType.STAINED_GLASS_PANE)) {
            return new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (byte) data);
        } else if (b.equals(MaterialTranslator.BlockType.WOOL)) {
            return new ItemStack(Material.getMaterial("WOOL"), 1, (byte) data);
        }
        return null;
    }

    ItemStack addDatabaseHead(ItemStack is, String id, double price);

    double getPrice(ItemStack is);

    String getId(ItemStack id);

    default ItemStack getOffHand(Player p) {
        return p.getInventory().getItemInOffHand();
    }

    ItemStack addSection(ItemStack is, String sec);

    String getSection(ItemStack is);

    default Material getNewItems(MaterialTranslator.ChangedMaterials b) {
        if (b == MaterialTranslator.ChangedMaterials.FIREWORK_CHARGE) {
            return Material.getMaterial("FIREWORK_CHARGE");
        } else if (b == MaterialTranslator.ChangedMaterials.PORK) {
            return Material.getMaterial("PORK");
        } else if (b == MaterialTranslator.ChangedMaterials.SULPHUR){
            return Material.getMaterial("SULPHUR");
        } else if (b == MaterialTranslator.ChangedMaterials.GRILLED_PORK){
            return Material.getMaterial("GRILLED_PORK");
        } else {
            return Material.getMaterial("INK_SACK");
        }
    }

    ItemStack setIcon(ItemStack i, Icon o);

    Icon getIcon(ItemStack i);

    ItemStack setChallenge(ItemStack i, Challenge a);

    Challenge getChallenge(ItemStack is);

    ItemStack removeIcon(ItemStack i);

}
