package io.github.thatsmusic99.headsplus.nms.v1_8_R1_NMS;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import io.github.thatsmusic99.headsplus.util.AdventCManager;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("deprecation")
public class v1_8_R1_NMS implements NMSManager {

    @Override
    public org.bukkit.inventory.ItemStack addNBTTag(Object i) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy((ItemStack) i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("headsplus-sell", true);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public boolean isSellable(Object i) {
        if (CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) i).getTag() != null) {
            return CraftItemStack.asNMSCopy((ItemStack) i).getTag().getBoolean("headsplus-sell");
        }
        return false;
    }

    @Override
    public SearchGUI getSearchGUI(Player p, SearchGUI.AnvilClickEventHandler a) {
        return new SearchGUI1_8_R1(p, a);
    }

    @Override
    public String getSkullOwnerName(SkullMeta m) {
        return m.getOwner();
    }

    @Override
    public ShapelessRecipe getRecipe(ItemStack i, String name) {
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
    public GameProfile getGameProfile(ItemStack s) {
        EntityPlayer e = ((CraftPlayer) ((SkullMeta) s.getItemMeta()).getOwningPlayer().getPlayer()).getHandle();
        return e.getProfile();
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getItemInHand();
    }

    @Override
    public org.bukkit.inventory.ItemStack setType(String s, ItemStack i) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("headsplus-type", s);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getType(ItemStack i) {
        if (CraftItemStack.asNMSCopy(i).getTag() != null) {
            return CraftItemStack.asNMSCopy(i).getTag().getString("headsplus-type");
        }
        return "";
    }

    @Override
    public ItemStack addDatabaseHead(ItemStack is, String id, double price) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() == null) {
            i.setTag(new NBTTagCompound());
        }
        i.getTag().setString("head-id", id);
        i.getTag().setDouble("head-price", price);
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public double getPrice(org.bukkit.inventory.ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(is).getTag()).getDouble("head-price");
        }
        return -1;
    }

    @Override
    public String getId(org.bukkit.inventory.ItemStack id) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(id);
        if (i.getTag() != null) {
            return Objects.requireNonNull(CraftItemStack.asNMSCopy(id).getTag()).getString("head-id");
        }
        return "";
    }

    public ItemStack getOffHand(Player p) {
        return new ItemStack(Material.AIR);
    }

    @Override
    public org.bukkit.inventory.ItemStack addSection(org.bukkit.inventory.ItemStack i, String sec) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("head-section", sec);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getSection(org.bukkit.inventory.ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(i.getTag()).getString("head-section");
        }
        return "";
    }

    @Override
    public ItemStack setIcon(ItemStack i, Icon o) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is == null) return i;
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("icon", o.getIconName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Icon getIcon(ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i == null) return null;
        if (i.getTag() != null) {
            return Icon.getIconFromName(Objects.requireNonNull(i.getTag()).getString("icon"));
        }
        return null;
    }

    @Override
    public ItemStack setCalendarValue(ItemStack i, String value) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is == null) return i;
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("advent-value", value);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public AdventCManager getCalendarValue(ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i == null) return null;
        if (i.getTag() != null) {
            return AdventCManager.valueOf(Objects.requireNonNull(i.getTag()).getString("advent-value"));
        }
        return null;
    }

    @Override
    public ItemStack setChallenge(ItemStack i, Challenge a) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setString("challenge", a.getConfigName());
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public Challenge getChallenge(ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return HeadsPlus.getInstance().getChallengeByName(Objects.requireNonNull(i.getTag()).getString("challenge"));
        }
        return null;
    }

    @Override
    public ItemStack removeIcon(ItemStack i) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().remove("icon");
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public ItemStack setOpen(ItemStack i, boolean value) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setBoolean("advent-open", value);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public boolean isOpen(ItemStack is) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(is);
        if (i.getTag() != null) {
            return Objects.requireNonNull(i.getTag()).getBoolean("advent-open");
        }
        return false;
    }

    @Override
    public HashMap<String, String> getNBTTags(ItemStack item) {
        net.minecraft.server.v1_8_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        HashMap<String, String> keys = new HashMap<>();
        if (i.getTag() != null) {
            for (String str : (Set<String>) i.getTag().c()) {
                keys.put(str, i.getTag().get(str).toString());
            }
        }
        return keys;
    }

    @Override
    public ItemStack setPrice(ItemStack i, double price) {
        net.minecraft.server.v1_8_R1.ItemStack is = CraftItemStack.asNMSCopy(i);
        if (is.getTag() == null) {
            is.setTag(new NBTTagCompound());
        }
        is.getTag().setDouble("head-price", price);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getNMSVersion() {
        return "v1_8_R1";
    }

    @Override
    public Sound getEXPSound() {
        return Sound.valueOf("ORB_PICKUP");
    }
}
