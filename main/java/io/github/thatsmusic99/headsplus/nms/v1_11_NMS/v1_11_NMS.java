package io.github.thatsmusic99.headsplus.nms.v1_11_NMS;


import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class v1_11_NMS implements NMSManager {

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
            return Objects.requireNonNull(CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag()).getBoolean("headsplus-sell");
        }
        return false;
    }

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return new SearchGUI1_11(p, a);
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

    @Override
    public GameProfile getGameProfile(org.bukkit.inventory.ItemStack s) {
        EntityPlayer e = ((CraftPlayer) ((SkullMeta) s.getItemMeta()).getOwningPlayer().getPlayer()).getHandle();
        return e.getProfile();
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemInHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }

    @Override
    public org.bukkit.inventory.ItemStack setType(String s, org.bukkit.inventory.ItemStack i) {
        ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("headsplus-type", s);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getType(org.bukkit.inventory.ItemStack i) {
        if (CraftItemStack.asNMSCopy(i).getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(i).getTag()).getString("headsplus-type");
        }
        return "";
    }

    @Override
    public org.bukkit.inventory.ItemStack addDatabaseHead(org.bukkit.inventory.ItemStack is, String id, double price) {
        ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() == null) {
            i.setTag(new NBTTagCompound());
        }
        i.getTag().setString("head-id", id);
        i.getTag().setDouble("head-price", price);
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public double getPrice(org.bukkit.inventory.ItemStack is) {
        ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(is).getTag()).getDouble("head-price");
        }
        return -1;
    }

    @Override
    public String getId(org.bukkit.inventory.ItemStack id) {
        ItemStack i = CraftItemStack.asNMSCopy(id);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(id).getTag()).getString("head-id");
        }
        return "";
    }

    @Override
    public org.bukkit.inventory.ItemStack addSection(org.bukkit.inventory.ItemStack i, String sec) {
        ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("head-section", sec);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getSection(org.bukkit.inventory.ItemStack is) {
        ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(i.getTag()).getString("head-section");
        }
        return "";
    }

    @Override
    public org.bukkit.inventory.ItemStack setIcon(org.bukkit.inventory.ItemStack i, Icon o) {
        ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("icon", o.getIconName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Icon getIcon(org.bukkit.inventory.ItemStack is) {
        ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Icon.getIconFromName(Objects.requireNonNull(i.getTag()).getString("icon"));
        }
        return null;
    }

    @Override
    public org.bukkit.inventory.ItemStack setChallenge(org.bukkit.inventory.ItemStack i, Challenge a) {
        ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("challenge", a.getConfigName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Challenge getChallenge(org.bukkit.inventory.ItemStack is) {
        ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return HeadsPlus.getInstance().getChallengeByName(Objects.requireNonNull(i.getTag()).getString("challenge"));
        }
        return null;
    }

    @Override
    public org.bukkit.inventory.ItemStack removeIcon(org.bukkit.inventory.ItemStack i) {
        ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("icon", "");
        return CraftItemStack.asBukkitCopy(is);
    }
}
