package io.github.thatsmusic99.headsplus.config;

import org.bukkit.configuration.ConfigurationSection;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        getConfig().addDefault("mysql.password", "password");
        getConfig().addDefault("mysql.enabled", false);
        getConfig().addDefault("theme-colours.1", "DARK_BLUE");
        getConfig().addDefault("theme-colours.2", "GOLD");
        getConfig().addDefault("theme-colours.3", "GRAY");
        getConfig().addDefault("theme-colours.4", "DARK_AQUA");
        getConfig().addDefault("plugin.perks.sell-heads", true);
        getConfig().addDefault("plugin.perks.drop-heads", true);
        getConfig().addDefault("plugin.perks.drops.ignore-players", new ArrayList<>());
        getConfig().addDefault("plugin.perks.drops.needs-killer", false);
        getConfig().addDefault("plugin.perks.drops.entities-requiring-killer", new ArrayList<>(Collections.singleton("player")));
        getConfig().addDefault("plugin.perks.craft-heads", false);
        getConfig().addDefault("plugin.perks.disable-crafting", false);
        getConfig().addDefault("plugin.perks.heads-selector", true);
        getConfig().addDefault("plugin.perks.challenges", true);
        getConfig().addDefault("plugin.perks.leaderboards", true);
        getConfig().addDefault("plugin.perks.levels", true);
        getConfig().addDefault("plugin.perks.player-death-messages", false);
        getConfig().addDefault("plugin.perks.death-messages",
                new ArrayList<>(Arrays.asList("&b{player} &3was killed by &b{killer} &3and had their head removed!",
                        "&b{killer} &3finished the job and removed the worst part of &b{player}&3: The head.",
                        "&3The server owner screamed at &b{player} &3\"OFF WITH HIS HEAD!\"&3. &b{killer} &3finished the job.")));
        getConfig().addDefault("plugin.perks.smite-player-if-they-get-a-head", false);
        getConfig().addDefault("plugin.perks.mask-powerups", true);
        getConfig().addDefault("plugin.perks.pvp.player-balance-competition", false);
        getConfig().addDefault("plugin.perks.pvp.percentage-lost", 0.05);
        getConfig().addDefault("plugin.perks.pvp.percentage-balance-for-head", 0.05);
        getConfig().addDefault("plugin.perks.houses.enabled", true);
        getConfig().addDefault("plugin.mechanics.theme", "easter");
        getConfig().addDefault("plugin.mechanics.plugin-theme-dont-change", "classic");
        getConfig().addDefault("plugin.mechanics.update.check", true);
        getConfig().addDefault("plugin.mechanics.update.notify", true);
        getConfig().addDefault("plugin.mechanics.allow-looting-enchantment", true);
        getConfig().addDefault("plugin.mechanics.looting.ignored-entities", new ArrayList<>());
        getConfig().addDefault("plugin.mechanics.looting.use-old-system", false);
        getConfig().addDefault("plugin.mechanics.stop-placement-of-sellable-heads", false);
        getConfig().addDefault("plugin.mechanics.sellhead-gui", true);
        getConfig().addDefault("plugin.mechanics.debug.create-debug-files", true);
        getConfig().addDefault("plugin.mechanics.debug.print-stacktraces-in-console", true);
        getConfig().addDefault("plugin.mechanics.debug.console.enabled", false);
        getConfig().addDefault("plugin.mechanics.debug.console.level", 1);
        getConfig().addDefault("plugin.mechanics.anvil-menu-search", false);
        getConfig().addDefault("plugin.mechanics.mythicmobs.no-hp-drops", true);
        getConfig().addDefault("plugin.mechanics.round-balance-to-2-d-p", true);
        getConfig().addDefault("plugin.mechanics.boss-bar.enabled", true);
        getConfig().addDefault("plugin.mechanics.boss-bar.color", "RED");
        getConfig().addDefault("plugin.mechanics.boss-bar.title", "&c&lXP to next HP level");
        getConfig().addDefault("plugin.mechanics.boss-bar.lifetime", 5);
    //    getConfig().addDefault("plugin.mechanics.ignored-players-head-drops", new ArrayList<>());
        getConfig().set("mysql.passworld", null); // I still love this
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

    public String fixBalanceStr(double balance) {
        if (getMechanics().getBoolean("round-balance-to-2-d-p")) {
            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.CEILING);
            return format.format(balance);
        } else {
            return String.valueOf(balance);
        }

    }
}
