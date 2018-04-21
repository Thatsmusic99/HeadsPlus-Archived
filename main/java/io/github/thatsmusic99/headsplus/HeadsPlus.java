package io.github.thatsmusic99.headsplus;

import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.api.Level;
import io.github.thatsmusic99.headsplus.commands.*;
import io.github.thatsmusic99.headsplus.commands.maincommand.*;
import io.github.thatsmusic99.headsplus.config.*;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.levels.*;
import io.github.thatsmusic99.headsplus.crafting.RecipePerms;
import io.github.thatsmusic99.headsplus.events.*;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;

import io.github.thatsmusic99.headsplus.nms.*;
import io.github.thatsmusic99.headsplus.nms.v1_10_NMS.v1_10_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_11_NMS.v1_11_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_12_NMS.v1_12_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R1_NMS.v1_8_R1_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R2_NMS.v1_8_R2_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_8_R3_NMS.v1_8_R3NMS;
import io.github.thatsmusic99.headsplus.nms.v1_9_NMS.v1_9_NMS;
import io.github.thatsmusic99.headsplus.nms.v1_9_R2_NMS.V1_9_NMS2;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import io.github.thatsmusic99.headsplus.util.MySQLAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {

    private final Logger log = Logger.getLogger("Minecraft");
    private static HeadsPlus instance;
    private final PluginDescriptionFile pluginYml = getDescription();
    private final String author = pluginYml.getAuthors().toString();
    private final String version = pluginYml.getVersion();
    private boolean sellable;
    private Economy econ;
    private Permission perms;
    private boolean drops;
    private boolean arofj;
    private boolean db;
    private boolean lb;
    private boolean stopP;
    private static Object[] update = null;
    private Connection connection;
    private boolean con = false;
    private boolean dm;
    private boolean chal;
    private HeadsPlusConfig hpc;
    private HeadsPlusConfigHeads hpch;
    private HeadsPlusConfigHeadsX hpchx;
    private DeathEvents de;
    private HeadsPlusLeaderboards hplb;
    private HeadsPlusCrafting hpcr;
    private MySQLAPI mySQLAPI;
    private HeadsPlusChallenges hpchl;
    private HeadsPlusAPI hapi;
    private HeadsPlusLevels hpl;
    private final List<Challenge> challenges = new ArrayList<>();
    private NMSManager nms;
    private final List<IHeadsPlusCommand> commands = new ArrayList<>();
    private HashMap<Integer, Level> levels = new HashMap<>();
    private final List<String> nms1_8 = new ArrayList<>(Arrays.asList("1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8"));
    private List<ConfigSettings> cs = new ArrayList<>();

    @SuppressWarnings("unused")
    private File messagesF;

    @Override
    public void onEnable() {
        try {
            instance = this;
            setupNMS();
            setUpMConfig();
            try {
                hpc.getString("locale");
                LocaleManager.class.newInstance().setupLocale();
            } catch (NullPointerException ex) {
                hpc = new HeadsPlusConfig(true);
                LocaleManager.class.newInstance().setupLocale();
            }
            createInstances();
            checkTheme();
            if (getConfig().getBoolean("mysql-usage")) {
                openConnection();
            }
            if (!getConfig().getBoolean("disableCrafting")) {
                hpcr = new HeadsPlusCrafting();
                getServer().getPluginManager().registerEvents(new RecipePerms(), this);
            }
            if (!(econ()) && (getConfig().getBoolean("sellHeads"))) {
                log.warning(hpc.getString(""));
            }
            if (econ()) {
                setupPermissions();
            }
            setPluginValues();

            registerEvents();
            registerCommands();
            registerSubCommands();
            JoinEvent.reloaded = false;
            Metrics metrics = new Metrics(this);
            metrics.addCustomChart(new Metrics.SimplePie("languages", () -> LocaleManager.getLocale().getLanguage()));
            metrics.addCustomChart(new Metrics.SimplePie("theme", () -> WordUtils.capitalize(getConfig().getString("pTheme").toLowerCase())));
            if (getConfig().getBoolean("update-checker")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        update = UpdateChecker.getUpdate();
                        if (update != null) {
                            log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getUpdateFound())));
                            log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion()))
                                    + getDescription().getVersion());
                            log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion() + update[2])));
                            if (update[1].toString().length() > 50) {
                                update[1] = update[1].toString().subSequence(0, 50) + "... (Check Spigot for more information)";
                            }
                            log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getDescription())) + update[1]);
                            log.info("Download link: https://www.spigotmc.org/resources/headsplus-1-8-x-1-12-x.40265/");
                        } else {
                            log.info("[HeadsPlus] Plugin is up to date!");
                        }
                    }
                }.runTaskAsynchronously(this);
            }
            log.info("[HeadsPlus] HeadsPlus has been enabled.");
        } catch (Exception e) {
            try {
                if (getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                    e.printStackTrace();
                }
                if (getConfig().getBoolean("debug.create-debug-files")) {
                    log.severe("HeadsPlus has failed to start up correctly. An error report has been made in /plugins/HeadsPlus/debug");
                    try {
                        String s = new DebugFileCreator().createReport(e, "Startup");
                        getLogger().severe("Report name: " + s);
                        getLogger().severe("Please submit this report to the developer at one of the following links:");
                        getLogger().severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                        getLogger().severe("https://discord.gg/nbT7wC2");
                        getLogger().severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                    } catch (IOException e1) {
                        if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                            e1.printStackTrace();
                        }
                    }
                }
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
        log.info("[HeadsPlus] HeadsPlus has been disabled.");
    }

    public static HeadsPlus getInstance() {
        return instance;

    }

    private void setUpMConfig() {
        File configF = new File(getDataFolder(), "config.yml");
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        if (!configF.exists()) {
            try {
                configF.createNewFile();
            } catch (IOException e) {
                if (getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                    e.printStackTrace();
                }
            }
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Deprecated
    public String translateMessages(String s) {
        if (s == null) return "";
        if (s.contains("''")) {
            s = s.replaceAll("''", "'");
        }
        if (s.contains("^'")) {
            s = s.replaceAll("^'", "");
        }
        if (s.contains("'$")) {
            s = s.replaceAll("'$", "");
        }
        if (s.contains("%h")) {
            s = s.replaceAll("%h", ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("prefix")));
        }
        return s;
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
            connection = DriverManager.getConnection("jdbc:mysql://" + getConfig().getString("mysql-host") + ":" + getConfig().getString("mysql-port") + "/" + getConfig().getString("mysql-database") + "?useSSL=false", getConfig().getString("mysql-username"), getConfig().getString("mysql-password"));
            Statement st = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            for (String str : Arrays.asList("headspluslb", "headsplussh", "headspluscraft")) {

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
                st.executeUpdate(sb2.toString());

            }
            con = true;
        }
    }

    private void checkTheme() {
        if (!getInstance().getConfig().getString("theme").equalsIgnoreCase(getInstance().getConfig().getString("pTheme"))) {
            try {
                MenuThemes mt = MenuThemes.valueOf(getInstance().getConfig().getString("theme").toUpperCase());
                getInstance().getConfig().set("themeColor1", mt.c1);
                getInstance().getConfig().set("themeColor2", mt.c2);
                getInstance().getConfig().set("themeColor3", mt.c3);
                getInstance().getConfig().set("themeColor4", mt.c4);
                getInstance().getConfig().set("pTheme", mt.name());
                getInstance().getConfig().options().copyDefaults(true);
                getInstance().saveConfig();
            } catch (Exception ex) {
                getInstance().log.warning("[HeadsPlus] Faulty theme was put in! No theme changes will be made.");
            }
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvents(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new LBEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
    }

    private void setPluginValues() {
        drops = getConfig().getBoolean("dropHeads");
        arofj = getConfig().getBoolean("autoReloadOnFirstJoin");
        stopP = getConfig().getBoolean("stop-placement-of-sellable-heads");
        lb = getConfig().getBoolean("leaderboards");
        db = getConfig().getBoolean("headsDatabase");
        dm = getConfig().getBoolean("player-death-messages");
        sellable = (econ()) && (getConfig().getBoolean("sellHeads"));
        chal = getConfig().getBoolean("challenges");
    }

    private void registerCommands() {
        this.getCommand("headsplus").setExecutor(new HeadsPlusCommand());
        this.getCommand("hp").setExecutor(new HeadsPlusCommand());
        this.getCommand("hp").setTabCompleter(new TabComplete());
        this.getCommand("head").setExecutor(new Head());
        this.getCommand("heads").setExecutor(new Heads());
        this.getCommand("myhead").setExecutor(new MyHead());
        this.getCommand("hplb").setExecutor(new LeaderboardsCommand());
        this.getCommand("hplb").setTabCompleter(new TabCompleteLB());
        this.getCommand("sellhead").setExecutor(new SellHead());
        this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
        this.getCommand("hpc").setExecutor(new ChallengeCommand());
    }

    private void createInstances() {
        hpc = new HeadsPlusConfig(false);
        cs.add(hpc);
        hpch = new HeadsPlusConfigHeads();
        cs.add(hpch);
        hpchx = new HeadsPlusConfigHeadsX();
        cs.add(hpchx);
        de = new DeathEvents();
        hplb = new HeadsPlusLeaderboards();
        cs.add(hplb);
        hpchl = new HeadsPlusChallenges();
        cs.add(hpchl);
        hapi = new HeadsPlusAPI();
        mySQLAPI = new MySQLAPI();
        hpl = new HeadsPlusLevels();
        cs.add(hpl);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


    private void setupNMS() {
        for (String s : nms1_8) {
            if (getServer().getVersion().contains(s)) {
                nms = new v1_8_R3NMS();
                return;
            }
        }
        if (getServer().getVersion().contains("1.8.3")) {
            nms = new v1_8_R2_NMS();
        } else if (getServer().getVersion().contains("1.8")) {
            nms = new v1_8_R1_NMS();
        } else if (getServer().getVersion().contains("1.9.4")) {
            nms = new V1_9_NMS2();
        } else if (getServer().getVersion().contains("1.9")) {
            nms = new v1_9_NMS();
        } else if (getServer().getVersion().contains("1.10")) {
            nms = new v1_10_NMS();
        } else if (getServer().getVersion().contains("1.11")) {
            nms = new v1_11_NMS();
        } else if (getServer().getVersion().contains("1.12")) {
            nms = new v1_12_NMS();
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
    }

    // GETTERS


    public String getVersion() {
        return version;
    }

    public boolean isAutoReloadingOnFirstJoin() {
        return arofj;
    }

    public boolean isUsingHeadDatabase() {
        return db;
    }

    public boolean hasChallengesEnabled() {
        return chal;
    }

    public boolean isConnectedToMySQLDatabase() {
        return con;
    }

    public boolean isDeathMessagesEnabled() {
        return dm;
    }

    public boolean isDropsEnabled() {
        return drops;
    }

    public boolean canSellHeads() {
        return sellable;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isStoppingPlaceableHeads() {
        return stopP;
    }

    public HashMap<Integer, Level> getLevels() {
        return levels;
    }

    public boolean isUsingLeaderboards() {
        return lb;
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

    public HeadsPlusConfig getMessagesConfig() {
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

    public static Object[] getUpdate() {
        return update;
    }

    public DeathEvents getDeathEvents() {
        return de;
    }
}
