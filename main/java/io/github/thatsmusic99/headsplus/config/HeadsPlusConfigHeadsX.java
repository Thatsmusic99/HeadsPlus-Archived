package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HeadsPlusConfigHeadsX {

    public static boolean s = false;
    private static FileConfiguration headsx;
    private static File headsxf;

    private static void saveHeadsX() {
        if (headsx == null || headsxf == null) {
            return;
        }
        try {
            headsx.save(headsxf);
        } catch (IOException e) {
            HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save headsx.yml!");
            e.printStackTrace();
        }
    }
    private static void loadHeadsX() {
        getHeadsX().options().header("HeadsPlus by Thatsmusic99 " +
                "\n WARNING: This is an advanced section of the plugin. If you do not know what you a doing with it, please do not use it due to risk of crashing your own and other's games. " +
                "\n For more information visit the GitHub wiki for HeadsX.yml: https://github.com/Thatsmusic99/HeadsPlus/wiki/headsx.yml");

        for (HeadsXEnums e : HeadsXEnums.values()) {
            getHeadsX().addDefault("heads." + e.name + ".database", true);
            getHeadsX().addDefault("heads." + e.name + ".encode", false);
            getHeadsX().addDefault("heads." + e.name + ".displayname", e.dn);
            getHeadsX().addDefault("heads." + e.name + ".texture", e.tex);
            getHeadsX().addDefault("heads." + e.name + ".price", "default");
        }

        getHeadsX().options().copyDefaults(true);
        saveHeadsX();
    }
    public static FileConfiguration getHeadsX() {
        return headsx;
    }
    public static void reloadHeadsX() {
        if (headsxf == null) {
            headsxf = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
        }
        headsx = YamlConfiguration.loadConfiguration(headsxf);
        getHeadsX().addDefault("options.version", 0.3);
        getHeadsX().addDefault("options.default-price", 10.00);
        if (headsxf.length() <= 2000) {
            loadHeadsX();
        }
        if (getHeadsX().getDouble("options.version") < 0.3) {
            for (String str : getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                getHeadsX().addDefault("heads." + str + ".price", "default");
            }
        }
        saveHeadsX();
        s = false;
    }
    public static void headsxEnable() {
        reloadHeadsX();
       // if (s) {
      //      loadHeadsX();
      //  }
        s = false;
    }
   /* public static boolean isHPXSkull(String str) {
        return str.startsWith("HP#");
    }
    public static ItemStack getSkull(String s) {
        String st = s.split("#")[1];
        ItemStack i = new ItemStack(Material.SKULL_ITEM);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
        if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("heads." + st + ".encode")) {
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", HeadsPlusConfigHeadsX.getHeadsX().getString(st + ".texture")).getBytes());
            gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
        } else {
            gm.getProperties().put("textures", new Property("texture", HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + st + ".texture")));
        }

        Field profileField = null;
        try {
            profileField = sm.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(sm, gm);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeadsX.getHeadsX().getString("heads." + st + ".displayname")));
        i.setItemMeta(sm);
        return i;
    } */
}
