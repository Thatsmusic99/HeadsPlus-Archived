package io.github.thatsmusic99.headsplus.reflection;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NBTManager {

    // Reflection utils
    public Object getNMSCopy(ItemStack i) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String version = HeadsPlus.getInstance().getNMS().getNMSVersion();
        Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
        Method method = clazz.getMethod("asNMSCopy", Class.forName("org.bukkit.inventory.ItemStack"));
        return method.invoke(method, i);
    }

    public ItemStack asBukkitCopy(Object o) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String version = HeadsPlus.getInstance().getNMS().getNMSVersion();
        Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
        Class<?> itemClass = Class.forName("net.minecraft.server." + version + ".ItemStack");
        Method method = clazz.getMethod("asBukkitCopy", itemClass);
        return (ItemStack) method.invoke(method, o);
    }

    public Object getNBTTag(Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = o.getClass().getMethod("getTag");
        return method.invoke(o);
    }

    public Object setNBTTag(Object o, Object nbt) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String version = HeadsPlus.getInstance().getNMS().getNMSVersion();
        Class<?> clazz = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
        Method method = o.getClass().getMethod("setTag", clazz);
        method.invoke(o, nbt);
        return o;
    }


    public Object newNBTTag() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String version = HeadsPlus.getInstance().getNMS().getNMSVersion();
        Constructor<?> con = Class.forName("net.minecraft.server." + version + ".NBTTagCompound").getConstructor();
        return con.newInstance();
    }

    public ItemStack makeSellable(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(nbtTag, "headsplus-sell", true);
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public boolean isSellable(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getBoolean", String.class);
            return (boolean) method.invoke(nbtTag, "headsplus-sell");
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ItemStack setType(ItemStack i, String type) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, "headsplus-type", type);
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ItemStack addDatabaseHead(ItemStack i, String id, double price) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, "head-id", id);
            Method method1 = nbtTag.getClass().getMethod("setDouble", String.class, double.class);
            method1.invoke(nbtTag, "head-price", price);
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public String getType(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return String.valueOf(method.invoke(nbtTag, "headsplus-type"));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getID(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return String.valueOf(method.invoke(nbtTag, "head-id"));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return "";
    }

    public double getPrice(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getDouble", String.class);
            return (double) method.invoke(nbtTag, "head-price");
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ItemStack addSection(ItemStack i, String section) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, "head-section", section);
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public String getSection(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return String.valueOf(method.invoke(nbtTag, "head-section"));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Icon getIcon(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return Icon.getIconFromName(String.valueOf(method.invoke(nbtTag, "head-icon")));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack setIcon(ItemStack i, Icon icon) {
        try {
            Object nmsItem = getNMSCopy(i);
            if (nmsItem == null) return i;
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, "head-icon", icon.getIconName());
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ItemStack setChallenge(ItemStack i, Challenge c) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, "head-challenge", c.getConfigName());
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public Challenge getChallenge(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return HeadsPlus.getInstance().getChallengeByName(String.valueOf(method.invoke(nbtTag, "head-challenge")));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack removeIcon(ItemStack i) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method = nbtTag.getClass().getMethod("remove", String.class);
            method.invoke(nbtTag, "head-icon");
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ItemStack setPrice(ItemStack i, double price) {
        try {
            Object nmsItem = getNMSCopy(i);
            Object nbtTag = getNBTTag(nmsItem);
            if (nbtTag == null) {
                nbtTag = newNBTTag();
            }
            Method method1 = nbtTag.getClass().getMethod("setDouble", String.class, double.class);
            method1.invoke(nbtTag, "head-price", price);
            nmsItem = setNBTTag(nmsItem, nbtTag);
            return asBukkitCopy(nmsItem);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return i;
    }
}
