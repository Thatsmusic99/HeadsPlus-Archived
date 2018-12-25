package io.github.thatsmusic99.headsplus;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.api.events.CommunicateEvent;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.api.Level;
import io.github.thatsmusic99.headsplus.commands.*;
import io.github.thatsmusic99.headsplus.commands.maincommand.*;
import io.github.thatsmusic99.headsplus.config.*;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.levels.*;
import io.github.thatsmusic99.headsplus.crafting.RecipePerms;
import io.github.thatsmusic99.headsplus.listeners.*;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;

import io.github.thatsmusic99.headsplus.nms.*;
import io.github.thatsmusic99.headsplus.nms.v1_10_NMS.v1_10_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_11_NMS.v1_11_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_12_NMS.v1_12_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_13_NMS.v1_13_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_13_R2_NMS.v1_13_R2_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R1_NMS.v1_8_R1_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R2_NMS.v1_8_R2_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R3_NMS.v1_8_R3NMS;
import io.github.thatsmusic99.headsplus.nms.v1_9_NMS.v1_9_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_9_R2_NMS.V1_9_NMS2;
import io.github.thatsmusic99.headsplus.storage.Favourites;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import io.github.thatsmusic99.headsplus.util.MySQLAPI;
import io.github.thatsmusic99.og.OreGenerator;
import io.github.thatsmusic99.pg.Core;
import io.github.thatsmusic99.specprotect.CoreClass;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {

    public final Logger log = Logger.getLogger("Minecraft");
    private static HeadsPlus instance;
    private final PluginDescriptionFile pluginYml = getDescription();
    private final String author = pluginYml.getAuthors().toString();
    private final String version = pluginYml.getVersion();
    private Economy econ;
    private Permission perms;
    private static Object[] update = null;
    private Connection connection;
    private boolean con = false;
    private HeadsPlusMessagesConfig hpc;
    private HeadsPlusConfigHeads hpch;
    private HeadsPlusConfigHeadsX hpchx;
    private DeathEvents de;
    private HeadsPlusLeaderboards hplb;
    private HeadsPlusCrafting hpcr;
    private MySQLAPI mySQLAPI;
    private HeadsPlusChallenges hpchl;
    private HeadsPlusAPI hapi;
    private HeadsPlusLevels hpl;
    private HeadsPlusMainConfig config;
    private HeadsPlusConfigItems items;
    private HeadsPlusConfigSounds sounds;
    private final List<Challenge> challenges = new ArrayList<>();
    private NMSManager nms;
    private final List<IHeadsPlusCommand> commands = new ArrayList<>();
    private HashMap<Integer, Level> levels = new HashMap<>();
    private List<ConfigSettings> cs = new ArrayList<>();
    private Favourites favourites;

    @Override
    public void onEnable() {
        try {
            instance = this;
            setupNMS();
            try {
                hpc.getString("locale");
                LocaleManager.class.newInstance().setupLocale();
            } catch (NullPointerException ex) {
                hpc = new HeadsPlusMessagesConfig(true);
                LocaleManager.class.newInstance().setupLocale();
            }
            createInstances();

            debug("- Checking plugin theme.", 1);
            checkTheme();
            debug("- Setting up favourites.json.", 1);
            setupJSON();
            if (getConfiguration().getMySQL().getBoolean("enabled")) {
                debug("- MySQL is to be enabled. Opening connection...", 1);
                openConnection();
            }
            if (!getConfiguration().getPerks().getBoolean("disable-crafting")) {
                debug("- Recipes may be added. Creating...", 1);
                getServer().getPluginManager().registerEvents(new RecipePerms(), this);
            }
            if (!(econ()) && (getConfiguration().getPerks().getBoolean("sellHeads"))) {
                log.warning(hpc.getString("no-vault"));
            }
            if (econ()) {
                setupPermissions();
            }


            debug("- Registering listeners!", 1);
            registerEvents();
            debug("- Registering commands!", 1);
            registerCommands();
            debug("- Registering subcommands!", 1);
            registerSubCommands();
            JoinEvent.reloaded = false;
            debug("- Creating Metrics!", 1);
            Metrics metrics = new Metrics(this);
            metrics.addCustomChart(new Metrics.SimplePie("languages", () -> LocaleManager.getLocale().getLanguage()));
            metrics.addCustomChart(new Metrics.SimplePie("theme", () -> WordUtils.capitalize(getConfiguration().getMechanics().getString("plugin-theme-dont-change").toLowerCase())));
            metrics.addCustomChart(new Metrics.SingleLineChart("christmas_hype", () -> getHeadsXConfig().getConfig().getInt("options.christmas-hype")));
            debug("- Metrics complete, can be found at https://bstats.org/plugin/bukkit/HeadsPlus", 2);
            if (getConfiguration().getMechanics().getBoolean("update.check")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            debug("- Checking for update...", 1);
                            update = UpdateChecker.getUpdate();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (update != null) {
                            if (!((String) update[2]).equalsIgnoreCase("5.1.10")) {
                                log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getUpdateFound())));
                                log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion()))
                                        + getDescription().getVersion());
                                log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getNewVersion() + update[2])));
                                if (update[1].toString().length() > 50) {
                                    update[1] = update[1].toString().subSequence(0, 50) + "... (Check Spigot for more information)";
                                }
                                log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getDescription())) + update[1]);
                                log.info("Download link: https://www.spigotmc.org/resources/headsplus-1-8-x-1-12-x.40265/");

                            }
                        } else {
                            log.info(hpc.getString("plugin-up-to-date"));
                        }
                        checkForMutuals();
                    }
                }.runTaskAsynchronously(this);
            }


            log.info(hpc.getString("plugin-enabled"));
        } catch (Exception e) {
            try {
                new DebugPrint(e, "Startup", false, null);
            } catch (Exception ex) {
                getLogger().severe("HeadsPlus has failed to start up correctly and can not read the config. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Startup");
                    getLogger().severe("Report name: " + s);
                    getLogger().severe("Please submit this report to the developer at one of the following links:");
                    getLogger().severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    getLogger().severe("https://discord.gg/nbT7wC2");
                    getLogger().severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    

    @Override
    public void onDisable() {
        try {
            favourites.save();
        } catch (IOException e) {
            new DebugPrint(e, "Disabling (saving favourites)", false, null);
        }
        log.info(hpc.getString("plugin-disabled"));
    }

    public static HeadsPlus getInstance() {
        return instance;

    }

    public boolean econ() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            ConfigurationSection mysql = getConfiguration().getMySQL();
            connection = DriverManager.getConnection("jdbc:mysql://" + mysql.getString("host") + ":" + mysql.getString("port") + "/" + mysql.getString("database") + "?useSSL=false", mysql.getString("username"), mysql.getString("password"));
            Statement st = connection.createStatement();
            for (String str : Arrays.asList("headspluslb", "headsplussh", "headspluscraft")) {
                debug("- Creating database for " + str + "...", 2);
                StringBuilder sb = new StringBuilder();
                sb.append("CREATE TABLE IF NOT EXISTS `").append(str).append("` (").append("`id` INT NOT NULL AUTO_INCREMENT,").append("`uuid` VARCHAR(45),").append("`total` VARCHAR(45)");
                for (EntityType e : de.ableEntities) {
                    sb.append(", `").append(e.name()).append("` VARCHAR(45)");
                }
                sb.append(", `PLAYER` VARCHAR(45)");
                sb.append(", PRIMARY KEY (`id`))");
                st.executeUpdate(sb.toString());


                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `").append(str).append("` (uuid, total");
                for (EntityType e : de.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(", PLAYER) VALUES('server-total', '0'");
                for (EntityType ignored : de.ableEntities) {
                    sb2.append(", '0'");
                }
                sb2.append(", '0'");
                sb2.append(")");
                try {
                    st.executeUpdate(sb2.toString());
                } catch (MySQLSyntaxErrorException ex) {
                    debug("- MySQL error thrown. Must be old database. No worries - I'm on it.", 1);
                    for (EntityType e : de.ableEntities) {
                        ResultSet rs2 = st.executeQuery("SELECT * FROM `" + str + "` WHERE `uuid`='server-total'");
                        rs2.next();
                        try {
                            rs2.getString(e.name());
                        } catch (SQLException owowhatsthis) {
                            try {
                                st.executeUpdate("ALTER TABLE `" + str + "` ADD COLUMN `" + e.name() + "` VARCHAR(45)");
                                st.executeUpdate("INSERT INTO `" + str + "` (" + e.name() + ") VALUES('0')");
                            } catch (MySQLSyntaxErrorException ignored) { // huh

                            }

                        }
                    }
                  /*  getLogger().severe("MYSQL ERROR: If you're migrating from an old HeadsPlus version, please reset all of the HeadsPlus tables. If this error persists after you've reset the tables, a report has been made for you to send.");
                    try {
                        String s = new DebugFileCreator().createReport(ex, "Startup");
                        log.severe("Report name: " + s);
                        log.severe("If the issue persists, please send to one of the following links (I WILL ASK IF YOU'VE RESET YOUR MYSQL TABLES):");
                        log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                        log.severe("https://discord.gg/nbT7wC2");
                        log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                        break;
                    } catch (IOException ignored) {

                    } */
                }


            }
            con = true;
            debug("- Connected to MySQL!", 2);
        }
    }

    private void checkTheme() {
        HeadsPlusMainConfig fc = getInstance().getConfiguration();
        if (!fc.getMechanics().getString("theme").equalsIgnoreCase(fc.getMechanics().getString("plugin-theme-dont-change"))) {
            try {
                MenuThemes mt = MenuThemes.valueOf(fc.getMechanics().getString("theme").toUpperCase());
                fc.getConfig().set("theme-colours.1", mt.c1);
                fc.getConfig().set("theme-colours.2", mt.c2);
                fc.getConfig().set("theme-colours.3", mt.c3);
                fc.getConfig().set("theme-colours.4", mt.c4);
                fc.getMechanics().set("plugin-theme-dont-change", mt.name());
                fc.getConfig().options().copyDefaults(true);
                fc.save();
                debug("- Theme set to " + mt.name() + "!", 1);
            } catch (Exception ex) {
                log.warning("[HeadsPlus] Faulty theme was put in! No theme changes will be made.");
            }
        }
    }

    private void registerEvents() {
        debug("- Registering InventoryEvent...", 3);
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        debug("- Registering HeadInteractEvent...", 3);
        getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
        debug("- Registering DeathEvents...", 3);
        getServer().getPluginManager().registerEvents(de, this);
        debug("- Registering JoinEvent...", 3);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        debug("- Registering PlaceEvent...", 3);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        debug("- Registering LBEvents...", 3);
        getServer().getPluginManager().registerEvents(new LBEvents(), this);
        debug("- Registering PlayerDeathEvent...", 3);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        debug("- Registering MaskEvent...", 3);
        getServer().getPluginManager().registerEvents(new MaskEvent(), this);
        debug("- Registering SoundEvent...", 3);
        getServer().getPluginManager().registerEvents(new SoundEvent(), this);
        debug("- Finished registering listeners!", 2);
    }

    private void registerCommands() {
        debug("- Registering /headsplus...", 3);
        getCommand("headsplus").setExecutor(new HeadsPlusCommand());
        debug("- Registering /hp...", 3);
        getCommand("hp").setExecutor(new HeadsPlusCommand());
        debug("- Registering /hp's tab completer..", 3);
        getCommand("hp").setTabCompleter(new TabComplete());
        debug("- Registering /head...", 3);
        getCommand("head").setExecutor(new Head());
        debug("- Registering /heads...", 3);
        getCommand("heads").setExecutor(new Heads());
        debug("- Registering /myhead...", 3);
        getCommand("myhead").setExecutor(new MyHead());
        debug("- Registering /hplb...", 3);
        getCommand("hplb").setExecutor(new LeaderboardsCommand());
        debug("- Registering /hplb's tab completer..", 3);
        getCommand("hplb").setTabCompleter(new TabCompleteLB());
        debug("- Registering /sellhead...", 3);
        getCommand("sellhead").setExecutor(new SellHead());
        debug("- Registering /sellhead's tab completer...", 3);
        getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
        debug("- Registering /hpc...", 3);
        getCommand("hpc").setExecutor(new ChallengeCommand());
        debug("- Finished registering commands!", 2);
    }

    private void createInstances() {
        config = new HeadsPlusMainConfig();
        cs.add(config);
        debug("- Instance for HeadsPlusMainConfig created!", 3);
        hpc = new HeadsPlusMessagesConfig(false);
        cs.add(hpc);
        debug("- Instance for HeadsPlusMessagesConfig created!", 3);
        hpch = new HeadsPlusConfigHeads();
        cs.add(hpch);
        debug("- Instance for HeadsPlusConfigHeads created!", 3);
        hpchx = new HeadsPlusConfigHeadsX();
        cs.add(hpchx);
        debug("- Instance for HeadsPlusConfigHeadsX created!", 3);
        hpcr = new HeadsPlusCrafting();
        cs.add(hpcr);
        debug("- Instance for HeadsPlusCrafting created!", 3);
        de = new DeathEvents();
        debug("- Instance for DeathEvents created!", 3);
        hplb = new HeadsPlusLeaderboards();
        cs.add(hplb);
        debug("- Instance for HeadsPlusLeaderboards created!", 3);
        hpchl = new HeadsPlusChallenges();
        cs.add(hpchl);
        debug("- Instance for HeadsPlusChallenges created!", 3);
        hapi = new HeadsPlusAPI();
        debug("- Instance for HeadsPlus's API created!", 3);
        mySQLAPI = new MySQLAPI();
        debug("- Instance for MySQL created!", 3);
        hpl = new HeadsPlusLevels();
        cs.add(hpl);
        debug("- Instance for HeadsPlusLevels created!", 3);
        items = new HeadsPlusConfigItems();
        cs.add(items);
        debug("- Instance for HeadsPlusConfigItems created!", 3);
        sounds = new HeadsPlusConfigSounds();
        cs.add(sounds);
        debug("- Instance for HeadsPlusConfigSounds created!", 3);
        debug("Instances created.", 1);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private void setupJSON() throws IOException, ParseException {
        favourites = new Favourites();
        favourites.create();
        favourites.read();
    }


    private void setupNMS() {
        List<NMSManager> managers = new ArrayList<>();
        managers.add(new v1_8_R1_NMS());
        managers.add(new v1_8_R2_NMS());
        managers.add(new v1_8_R3NMS());
        managers.add(new v1_9_NMS());
        managers.add(new V1_9_NMS2());
        managers.add(new v1_10_NMS());
        managers.add(new v1_11_NMS());
        managers.add(new v1_12_NMS());
        managers.add(new v1_13_NMS());
        managers.add(new v1_13_R2_NMS());
        String a = getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);
        for (NMSManager nms : managers) {
            if (nms.getNMSVersion().equalsIgnoreCase(version)) {
                this.nms = nms;
            }
        }
        if (this.nms == null) {
            getLogger().severe("ERROR: HeadsPlus does not support this version (" + version + ")!");
            getLogger().severe("If this is not known of, let the developer know in one of these places:");
            getLogger().severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
            getLogger().severe("https://discord.gg/nbT7wC2");
            getLogger().severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-13-x.237088/");
            getLogger().severe("To prevent any further damage, the plugin is being disabled...");
            setEnabled(false);
        }
    }

    private void registerSubCommands() {
        commands.add(new BlacklistAdd());
        commands.add(new BlacklistDelete());
        commands.add(new BlacklistList());
        commands.add(new BlacklistToggle());
        commands.add(new BlacklistwAdd());
        commands.add(new BlacklistwDelete());
        commands.add(new BlacklistwList());
        commands.add(new BlacklistwToggle());
        commands.add(new HelpMenu());
        commands.add(new Info());
        commands.add(new MCReload());
        commands.add(new ProfileCommand());
        commands.add(new WhitelistAdd());
        commands.add(new WhitelistDel());
        commands.add(new WhitelistList());
        commands.add(new WhitelistToggle());
        commands.add(new WhitelistwAdd());
        commands.add(new WhitelistwDelete());
        commands.add(new WhitelistwList());
        commands.add(new WhitelistwToggle());
        commands.add(new ChallengeCommand());
        commands.add(new Head());
        commands.add(new Heads());
        commands.add(new LeaderboardsCommand());
        commands.add(new MyHead());
        commands.add(new SellHead());
        commands.add(new DebugPrint());
        commands.add(new HeadInfoCommand());
    }

    // GETTERS


    public Favourites getFavourites() {
        return favourites;
    }

    public String getVersion() {
        return version;
    }

    public boolean isUsingHeadDatabase() {
        return getConfiguration().getPerks().getBoolean("heads-selector");
    }

    public boolean hasChallengesEnabled() {
        return getConfiguration().getPerks().getBoolean("challenges");
    }

    public boolean isConnectedToMySQLDatabase() {
        return con;
    }

    public boolean isDeathMessagesEnabled() {
        return getConfiguration().getPerks().getBoolean("player-death-messages");
    }

    public boolean isDropsEnabled() {
        return getConfiguration().getPerks().getBoolean("drop-heads");
    }

    public boolean canSellHeads() {
        return (econ()) && (getConfiguration().getPerks().getBoolean("sell-heads"));
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isStoppingPlaceableHeads() {
        return getConfiguration().getMechanics().getBoolean("stop-placement-of-sellable-heads");
    }

    public HashMap<Integer, Level> getLevels() {
        return levels;
    }

    public boolean isUsingLeaderboards() {
        return getConfiguration().getPerks().getBoolean("leaderboards");
    }

    public Economy getEconomy() {
        return econ;
    }

    public List<IHeadsPlusCommand> getCommands() {
        return commands;
    }

    public Permission getPermissions() {
        return perms;
    }

    public HeadsPlusAPI getAPI() {
        return hapi;
    }

    public String getAuthor() {
        return author;
    }

    public HeadsPlusChallenges getChallengeConfig() {
        return hpchl;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public MySQLAPI getMySQLAPI() {
        return mySQLAPI;
    }

    public HeadsPlusLeaderboards getLeaderboardsConfig() {
        return hplb;
    }

    public NMSManager getNMS() {
        return nms;
    }

    public HeadsPlusConfigHeads getHeadsConfig() {
        return hpch;
    }

    public HeadsPlusMessagesConfig getMessagesConfig() {
        return hpc;
    }

    public HeadsPlusConfigHeadsX getHeadsXConfig() {
        return hpchx;
    }

    protected HeadsPlusLevels getLevelsConfig() {
        return hpl;
    }

    public HeadsPlusCrafting getCraftingConfig() {
        return hpcr;
    }

    public List<ConfigSettings> getConfigs() {
        return cs;
    }

    public boolean usingLevels() {
        return getConfiguration().getPerks().getBoolean("levels");
    }

    public static Object[] getUpdate() {
        return update;
    }

    public DeathEvents getDeathEvents() {
        return de;
    }

    public HeadsPlusMainConfig getConfiguration() {
        return config;
    }

    public HeadsPlusConfigItems getItems() {
        return items;
    }

    public HeadsPlusConfigSounds getSounds() {
        return sounds;
    }

    public ChatColor getThemeColour(int i) {
        return ChatColor.valueOf(getConfiguration().getConfig().getString("theme-colours." + i));
    }

    public void debug(String message, int l) {
        if (getConfiguration().getMechanics().getBoolean("debug.console.enabled")) {
            int level = getConfiguration().getMechanics().getInt("debug.console.level");
            if (l <= level) {
                getLogger().info("Debug: " + message);
            }
        }

    }

    public Challenge getChallengeByName(String name) {
        for (Challenge c : getChallenges()) {
            if (c.getConfigName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public void checkForMutuals() {
        try {
            if (Bukkit.getServer().getPluginManager().getPlugin("ProjectPG-PRO") instanceof Core) {
                getLogger().info("I think I see ProjectPG here... are you ready, Aaron?");
                // Although I have my suspicions about those lot.
                // Here's my reason.
                // Error is dead, right?
                // Or at least... TM's alive. But that AI isn't running...
                // The only ones running are the SE1s.
                // Although I think in 2019... something is going to happen.
                // I'm not looking forward to it.
                // Let's see though, let's see.
                //
                // ProjectPG - SE1, where it all begins.
                Bukkit.getPluginManager().callEvent(new CommunicateEvent("ProjectPG"));
            }
            if (Bukkit.getServer().getPluginManager().getPlugin("AdvancedOreGenerator") instanceof OreGenerator) {
                getLogger().info("What're we gonna do, AOG?");
                Bukkit.getPluginManager().callEvent(new CommunicateEvent("AOG"));
            }
            if (Bukkit.getPluginManager().getPlugin("SpectateProtection") instanceof CoreClass) {
                getLogger().info("'Ello SpectateProtection! What's up??");
                Bukkit.getPluginManager().callEvent(new CommunicateEvent("SpectateProtection"));
            }
        } catch (NoClassDefFoundError ignored) {

        }



    }
}
