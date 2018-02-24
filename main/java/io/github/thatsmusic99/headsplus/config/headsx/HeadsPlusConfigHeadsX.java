package io.github.thatsmusic99.headsplus.config.headsx;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class HeadsPlusConfigHeadsX {

    public boolean s = false;
    public FileConfiguration headsx;
    private File headsxf;
    private double cVersion = 1.3;

    public HeadsPlusConfigHeadsX() {
        headsxEnable();
    }

    private void saveHeadsX() {
        headsxf = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
        if (headsx == null) {
            return;
        }
        try {
            headsx.save(headsxf);
        } catch (IOException e) {
            HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save headsx.yml!");
            e.printStackTrace();
        }
    }
    private void loadHeadsX() {
        getHeadsX().options().header("HeadsPlus by Thatsmusic99 " +
                "\n WARNING: This is an advanced section of the plugin. If you do not know what you a doing with it, please do not use it due to risk of crashing your own and other's games. " +
                "\n For more information visit the GitHub wiki for HeadsX.yml: https://github.com/Thatsmusic99/HeadsPlus/wiki/headsx.yml");

        for (HeadsXSections h : HeadsXSections.values()) {
            getHeadsX().addDefault("sections." + h.let + ".display-name", h.dn);
            getHeadsX().addDefault("sections." + h.let + ".texture", h.tx);
        }
        for (HeadsXEnums e : HeadsXEnums.values()) {
            getHeadsX().addDefault("heads." + e.name + ".database", true);
            getHeadsX().addDefault("heads." + e.name + ".encode", false);
            getHeadsX().addDefault("heads." + e.name + ".displayname", e.dn);
            getHeadsX().addDefault("heads." + e.name + ".texture", e.tex);
            getHeadsX().addDefault("heads." + e.name + ".price", "default");
            getHeadsX().addDefault("heads." + e.name + ".section", e.sec);
        }


        getHeadsX().options().copyDefaults(true);
        saveHeadsX();
    }

    public FileConfiguration getHeadsX() {
        return headsx;
    }

    public void reloadHeadsX() {
        if (headsxf == null) {
            headsxf = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
        }
        headsx = YamlConfiguration.loadConfiguration(headsxf);
        getHeadsX().addDefault("options.version", cVersion);
        getHeadsX().addDefault("options.default-price", 10.00);
       // getHeadsX().addDefault("options.advent-calender", true);
       // if (getHeadsX().getBoolean("options.advent-calender")) {
        //    for (AdventCManager acm : AdventCManager.values()) {
        //        getHeadsX().addDefault("advent." + acm.name(), new ArrayList<>());
        //    }
       // }
        if (headsxf.length() <= 500) {
            loadHeadsX();
        }
        if (getHeadsX().getDouble("options.version") < cVersion) {
            for (String str : getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                getHeadsX().addDefault("heads." + str + ".price", "default");
            }
            getHeadsX().set("options.version", cVersion);
            for (String str : getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                for (HeadsXEnums e : HeadsXEnums.values()) {
                    if (e.name.equalsIgnoreCase(str)) {
                        getHeadsX().addDefault("heads." + e.name + ".section", e.sec);
                    }
                }
            }
            for (HeadsXEnums e : HeadsXEnums.values()) {
                if (e.v == cVersion) {
                    getHeadsX().addDefault("heads." + e.name + ".database", true);
                    getHeadsX().addDefault("heads." + e.name + ".encode", false);
                    getHeadsX().addDefault("heads." + e.name + ".displayname", e.dn);
                    getHeadsX().addDefault("heads." + e.name + ".texture", e.tex);
                    getHeadsX().addDefault("heads." + e.name + ".price", "default");
                    getHeadsX().addDefault("heads." + e.name + ".section", e.sec);
                }
            }
            for (HeadsXSections h : HeadsXSections.values()) {
                if (h.d == cVersion) {
                    getHeadsX().addDefault("sections." + h.let + ".display-name", h.dn);
                    getHeadsX().addDefault("sections." + h.let + ".texture", h.tx);
                }
            }
            if (getHeadsX().get("sections.logos.texture").equals("HP#youtube")) {
                getHeadsX().addDefault("heads.youtube.database", true);
                getHeadsX().addDefault("heads.youtube.encode", false);
                getHeadsX().addDefault("heads.youtube.displayname", "&8[&cYouTube&8]");
                getHeadsX().addDefault("heads.youtube.texture", HeadsXEnums.YOUTUBE.tex);
                getHeadsX().addDefault("heads.youtube.price", "default");
            }
            getHeadsX().options().copyDefaults(true);
        }
        saveHeadsX();
        s = false;
    }
    public void headsxEnable() {
        reloadHeadsX();
       // if (s) {
      //      loadHeadsX();
      //  }
        s = false;
    }
    public boolean isHPXSkull(String str) {
        return str.startsWith("HP#");
    }

    public ItemStack getSkull(String s) {
        String st = s.split("#")[1];
        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
        if (getHeadsX().getBoolean("heads." + st + ".encode")) {
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", getHeadsX().getString(st + ".texture")).getBytes());
            gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
        } else {
            gm.getProperties().put("textures", new Property("texture", getHeadsX().getString("heads." + st + ".texture")));
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
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', getHeadsX().getString("heads." + st + ".displayname")));
        i.setItemMeta(sm);
        return i;
    }
    public String getTextures(String s) {
        String[] st = s.split("#");
        try {
            return getHeadsX().getString("heads." + st[1] + ".texture");
        } catch (Exception ex) {
            HeadsPlus.getInstance().getLogger().log(Level.SEVERE, "Texture returning error. Please report to the developer if this consists!");
            HeadsPlus.getInstance().getLogger().log(Level.SEVERE, "Link: https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
            ex.printStackTrace();
            return "";
        }
    }
}
