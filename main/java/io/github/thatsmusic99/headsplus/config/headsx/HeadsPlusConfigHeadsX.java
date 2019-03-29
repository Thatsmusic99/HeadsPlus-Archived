package io.github.thatsmusic99.headsplus.config.headsx;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.ConfigSettings;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.lang.reflect.Field;
import java.util.UUID;

public class HeadsPlusConfigHeadsX extends ConfigSettings {

    public boolean s = false;
    private double cVersion = 2.2;

    public HeadsPlusConfigHeadsX() {
        this.conName = "headsx";
        headsxEnable();
    }


    private void loadHeadsX() {
        getConfig().options().header("HeadsPlus by Thatsmusic99 " +
                "\n WARNING: This is an advanced section of the plugin. If you do not know what you a doing with it, please do not use it due to risk of crashing your own and other's games. " +
                "\n For more information visit the GitHub wiki for HeadsX.yml: https://github.com/Thatsmusic99/HeadsPlus/wiki/headsx.yml");

        for (HeadsXSections h : HeadsXSections.values()) {
            getConfig().addDefault("sections." + h.let + ".display-name", h.dn);
            getConfig().addDefault("sections." + h.let + ".texture", h.tx);
        }
        for (HeadsXEnums e : HeadsXEnums.values()) {
            getConfig().addDefault("heads." + e.name + ".database", true);
            getConfig().addDefault("heads." + e.name + ".encode", false);
            getConfig().addDefault("heads." + e.name + ".displayname", e.dn);
            getConfig().addDefault("heads." + e.name + ".texture", e.tex);
            getConfig().addDefault("heads." + e.name + ".price", "default");
            getConfig().addDefault("heads." + e.name + ".section", e.sec);
        }



        getConfig().options().copyDefaults(true);
        save();
    }


    @Override
    public void reloadC(boolean a) {
        if (configF == null) {
            configF = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");
        }
        config = YamlConfiguration.loadConfiguration(configF);
        getConfig().addDefault("options.update-heads", true);
        getConfig().addDefault("options.version", cVersion);
        getConfig().addDefault("options.default-price", 10.00);
     //   getConfig().addDefault("options.price-per-world.example-one", 15.00);
     //   getConfig().addDefault("options.advent-calendar", true);
    //    getConfig().addDefault("options.advent-texture", "HP#snowman");
    //    getConfig().addDefault("options.advent-display-name", "&4[&a&lHeadsPlus &c&lAdvent Calendar!&2]");
    //    getConfig().addDefault("options.christmas-hype", 0);
    //    if (getConfig().getBoolean("options.advent-calendar")) {
    //        for (AdventCManager acm : AdventCManager.values()) {
    //            getConfig().addDefault("advent-18." + acm.name(), new ArrayList<>());
    //        }
    //    }
        if (configF.length() <= 500) {
            loadHeadsX();
        }
        boolean b = getConfig().getBoolean("options.update-heads");
        if (getConfig().getDouble("options.version") < cVersion && b) {
            for (String str : getConfig().getConfigurationSection("heads").getKeys(false)) {
                getConfig().addDefault("heads." + str + ".price", "default");
            }
            getConfig().set("options.version", cVersion);
            for (String str : getConfig().getConfigurationSection("heads").getKeys(false)) {
                for (HeadsXEnums e : HeadsXEnums.values()) {
                    if (e.name.equalsIgnoreCase(str)) {
                        getConfig().addDefault("heads." + e.name + ".section", e.sec);
                    }
                }
            }
            for (HeadsXSections h : HeadsXSections.values()) {
                if (h.d == cVersion) {
                    getConfig().addDefault("sections." + h.let + ".display-name", h.dn);
                    getConfig().addDefault("sections." + h.let + ".texture", h.tx);
                }
            }
            for (HeadsXEnums e : HeadsXEnums.values()) {
                if (e.v == cVersion) {
                    getConfig().addDefault("heads." + e.name + ".database", true);
                    getConfig().addDefault("heads." + e.name + ".encode", false);
                    getConfig().addDefault("heads." + e.name + ".displayname", e.dn);
                    getConfig().addDefault("heads." + e.name + ".texture", e.tex);
                    getConfig().addDefault("heads." + e.name + ".price", "default");
                    getConfig().addDefault("heads." + e.name + ".section", e.sec);
                }
            }

            getConfig().options().copyDefaults(true);
        }
        save();
        s = false;
    }
    private void headsxEnable() {
        reloadC(false);
       // if (s) {
      //      loadHeadsX();
      //  }
        s = false;
    }
    public boolean isHPXSkull(String str) {
        return str.startsWith("HP#");
    }

    public ItemStack getSkull(String s) throws NoSuchFieldException, IllegalAccessException {
        String st = s.split("#")[1];
        ItemStack i = HeadsPlus.getInstance().getNMS().getSkullMaterial(1);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("heads." + st + ".displayname")));
        i.setItemMeta(sm);
        i = setTexture(getTextures(s).replaceAll("=", ""), i);
        return i;
    }
    public String getTextures(String s) {
        String[] st = s.split("#");
        try {
            return getConfig().getString("heads." + st[1] + ".texture");
        } catch (Exception ex) {
            new DebugPrint(ex, "Startup (headsx.yml)", false, null);
            return "";
        }
    }

    public ItemStack setTexture(String tex, ItemStack is) throws IllegalAccessException, NoSuchFieldException {
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        GameProfile gm = new GameProfile(UUID.fromString("7091cdbc-ebdc-4eac-a6b2-25dd8acd3a0e"), "HPXHead");
        gm.getProperties().put("textures", new Property("texture", tex.replaceAll("=", "")));


        Field profileField;
        profileField = sm.getClass().getDeclaredField("profile");

        profileField.setAccessible(true);
        profileField.set(sm, gm);
        is.setItemMeta(sm);
        return is;
    }

    public void addChristmasHype() {
        int hype = getConfig().getInt("options.christmas-hype");
        hype++;
        getConfig().set("options.christmas-hype", hype);
        save();
    }
}
