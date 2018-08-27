package io.github.thatsmusic99.headsplus.nms.v1_13_NMS;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class v1_13_NMS implements NewNMSManager {
    @Override
    public org.bukkit.inventory.ItemStack addNBTTag(Object i) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public boolean isSellable(Object i) {
        if (CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag()).getBoolean("headsplus-sell");
        }
        return false;
    }

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return new SearchGUI1_13(p, a);
    }

    @Override
    public String getSkullOwnerName(SkullMeta m) {
        return m.getOwner();
    }

    @Override
    public ShapelessRecipe getRecipe(org.bukkit.inventory.ItemStack i, String name) {
        return new ShapelessRecipe(new NamespacedKey(HeadsPlus.getInstance(), "HeadsPlus_" + name), i);
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    @Override
    public Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    @Override
    public GameProfile getGameProfile(ItemStack s) {
        EntityPlayer e = ((CraftPlayer) ((SkullMeta) s.getItemMeta()).getOwningPlayer().getPlayer()).getHandle();
        return e.getProfile();
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack setType(String s, ItemStack i) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("headsplus-type", s);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getType(ItemStack i) {
        if (CraftItemStack.asNMSCopy(i).getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(i).getTag()).getString("headsplus-type");
        }
        return "";
    }

    @Override
    public ItemStack addDatabaseHead(ItemStack i, String id, double price) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("head-id", id);
        is.getTag().setDouble("head-price", price);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public double getPrice(ItemStack is) {
        net.minecraft.server.v1_13_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(is).getTag()).getDouble("head-price");
        }
        return -1;
    }

    @Override
    public String getId(ItemStack id) {
        net.minecraft.server.v1_13_R1.ItemStack i = CraftItemStack.asNMSCopy(id);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(id).getTag()).getString("head-id");
        }
        return "";
    }

    @Override
    public ItemStack addSection(ItemStack i, String sec) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("head-section", sec);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getSection(ItemStack is) {
        net.minecraft.server.v1_13_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(i.getTag()).getString("head-section");
        }
        return "";
    }

    @Override
    public ItemStack setIcon(ItemStack i, Icon o) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("icon", o.getIconName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Icon getIcon(ItemStack is) {
        net.minecraft.server.v1_13_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Icon.getIconFromName(Objects.requireNonNull(i.getTag()).getString("icon"));
        }
        return null;
    }

    @Override
    public ItemStack setChallenge(ItemStack i, Challenge a) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("challenge", a.getConfigName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Challenge getChallenge(ItemStack is) {
        net.minecraft.server.v1_13_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return HeadsPlus.getInstance().getChallengeByName(Objects.requireNonNull(i.getTag()).getString("challenge"));
        }
        return null;
    }

    @Override
    public ItemStack removeIcon(ItemStack i) {
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().remove("icon");
        return CraftItemStack.asBukkitCopy(is);
    }

}
