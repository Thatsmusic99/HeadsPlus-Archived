package io.github.thatsmusic99.headsplus.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;

public class HeadsPlusMainConfig extends ConfigSettings {

    public HeadsPlusMainConfig() {
        this.conName = "config";
        enable(false);
    }

    @Override
    protected void load(boolean nullp) {

        if (getConfig().get("blacklistOn") instanceof Boolean) {
            configF.delete();
            reloadC(false);
        }
        getConfig().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
        getConfig().addDefault("blacklist.default.enabled", true);
        getConfig().addDefault("blacklist.world.enabled", true);
        getConfig().addDefault("whitelist.default.enabled", false);
        getConfig().addDefault("whitelist.world.enabled", false);
        getConfig().addDefault("blacklist.default.list", new ArrayList<>());
        getConfig().addDefault("blacklist.world.list", new ArrayList<>());
        getConfig().addDefault("whitelist.default.list", new ArrayList<>());
        getConfig().addDefault("whitelist.world.list", new ArrayList<>());
        getConfig().addDefault("mysql.host", "localhost");
        getConfig().addDefault("mysql.port", "3306");
        getConfig().addDefault("mysql.database", "db");
        getConfig().addDefault("mysql.username", "username");
        getConfig().addDefault("mysql.passworld", "password");
        getConfig().addDefault("mysql.enabled", false);
        getConfig().addDefault("theme-colours.1", "DARK_BLUE");
        getConfig().addDefault("theme-colours.2", "GOLD");
        getConfig().addDefault("theme-colours.3", "GRAY");
        getConfig().addDefault("theme-colours.4", "DARK_AQUA");
        getConfig().addDefault("plugin.perks.sell-heads", true);
        getConfig().addDefault("plugin.perks.drop-heads", true);
        getConfig().addDefault("plugin.perks.craft-heads", false);
        getConfig().addDefault("plugin.perks.disable-crafting", false);
        getConfig().addDefault("plugin.perks.heads-selector", true);
        getConfig().addDefault("plugin.perks.challenges", true);
        getConfig().addDefault("plugin.perks.leaderboards", true);
        getConfig().addDefault("plugin.perks.levels", true);
        getConfig().addDefault("plugin.perks.player-death-messages", false);
        getConfig().addDefault("plugin.perks.death-messagees",
                new ArrayList<>(Arrays.asList("&b{player} &3was killed by &b{killer} &3and had their head removed!",
                        "&b{killer} &3finished the job and removed the worst part of &b{player}&3: The head.",
                        "&3The server owner screamed at &b{player} &3\"OFF WITH HIS HEAD!\"&3. &b{killer} &3finished the job.")));
        getConfig().addDefault("plugin.perks.smite-player-if-they-get-a-head", false);
        getConfig().addDefault("plugin.perks.mask-powerups", true);
        getConfig().addDefault("plugin.mechanics.theme", "classic");
        getConfig().addDefault("plugin.mechanics.plugin-theme-dont-change", "classic");
        getConfig().addDefault("plugin.mechanics.gui-glass-color", "SILVER");
        getConfig().addDefault("plugin.mechanics.update.check", true);
        getConfig().addDefault("plugin.mechanics.update.notify", true);
        getConfig().addDefault("plugin.mechanics.allow-looting-enchantment", true);
        getConfig().addDefault("plugin.mechanics.stop-placement-of-sellable-heads", false);
        getConfig().addDefault("plugin.mechanics.sellhead-gui", true);
        getConfig().addDefault("plugin.mechanics.debug.create-debug-files", true);
        getConfig().addDefault("plugin.mechanics.debug.print-stacktraces-in-console", true);
        getConfig().options().copyDefaults(true);
        save();
    }

    public ConfigurationSection getMechanics() {
        return getConfig().getConfigurationSection("plugin.mechanics");
    }

    public ConfigurationSection getPerks() {
        return getConfig().getConfigurationSection("plugin.perks");
    }

    public ConfigurationSection getBlacklist(String type) {
        return getConfig().getConfigurationSection("blacklist." + type);
    }

    public ConfigurationSection getWhitelist(String type) {
        return getConfig().getConfigurationSection("whitelist." + type);
    }

    public ConfigurationSection getMySQL() {
        return getConfig().getConfigurationSection("mysql");
    }
}
