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
import io.github.thatsmusic99.headsplus.crafting.RecipeEnumUser;
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
import io.github.thatsmusic99.headsplus.util.MySQLAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
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

	public final Logger log = Logger.getLogger("Minecraft");
	private static HeadsPlus instance;
	private final PluginDescriptionFile pluginYml = getDescription();
	public final String author = pluginYml.getAuthors().toString();
	public final String version = pluginYml.getVersion();
	public boolean sellable;
	public Economy econ;
	public Permission perms;
	public boolean drops;
	public boolean arofj;
	public boolean db;
	public boolean lb;
	public boolean stopP;
	public static Object[] update = null;
    public Connection connection;
    public boolean con = false;
    public boolean dm;
    public boolean chal;
    public HeadsPlusConfig hpc;
    public HeadsPlusConfigHeads hpch;
    public HeadsPlusConfigHeadsX hpchx;
    public DeathEvents de;
    public HeadsPlusLeaderboards hplb;
    public HeadsPlusCrafting hpcr;
    public MySQLAPI mySQLAPI;
    public HeadsPlusChallenges hpchl;
    public HeadsPlusAPI hapi;
    public HeadsPlusLevels hpl;
    public final List<Challenge> challenges = new ArrayList<>();
    public NMSManager nms;
    public final List<IHeadsPlusCommand> commands = new ArrayList<>();
    public HashMap<Integer, Level> levels = new HashMap<>();
    private final List<String> nms1_8 = new ArrayList<>(Arrays.asList("1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8"));
    private FileConfiguration config;

    @SuppressWarnings("unused")
	private File messagesF;

	@Override
	public void onEnable() {
		try { 
			instance = this;
			setupNMS();
			setUpMConfig();
			try {
                hpc.getConfig().getString("locale");
                LocaleManager.class.newInstance().setupLocale();
            } catch (NullPointerException ex) {
                hpc = new HeadsPlusConfig(true);
                LocaleManager.class.newInstance().setupLocale();
            }
           // addLevels();
            createInstances();
			checkTheme();
			if (config.getBoolean("mysql-usage")) {
                openConnection();
            }
			if (!getConfig().getBoolean("disableCrafting")) {
			    hpcr = new HeadsPlusCrafting();
                new RecipeEnumUser();
                getServer().getPluginManager().registerEvents(new RecipePerms(), this);
			}
			if (!(econ()) && (getConfig().getBoolean("sellHeads"))) {
				log.warning("[HeadsPlus] Vault not found! Heads cannot be sold and challenge rewards can not add/remove groups.");
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
                            log.info( ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getUpdateFound())));
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
			log.severe("[HeadsPlus] Error enabling HeadsPlus!");
			e.printStackTrace();
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
        File configF = new File(instance.getDataFolder(), "config.yml");
			config = instance.getConfig();
			if(!instance.getDataFolder().exists()) {
				instance.getDataFolder().mkdirs();
			}
			if (!configF.exists()) {
				try {
					configF.createNewFile();
				} catch (IOException e) {
					instance.log.severe("[HeadsPlus] Couldn't create config!");
					e.printStackTrace();
				}
			}
			config.options().copyDefaults(true);
			instance.saveConfig();
			}
		 
    
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
            connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("mysql-host")+ ":" + config.getString("mysql-port") + "/" + config.getString("mysql-database") + "?useSSL=false", config.getString("mysql-username"), config.getString("mysql-password"));
            Statement st = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            for (String str : Arrays.asList("headspluslb", "headsplussh", "headspluscraft" )) {

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

    public void checkTheme() {
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
        getServer().getPluginManager().registerEvents(new InventoryCloseEvent(), this);
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
        hpch = new HeadsPlusConfigHeads();
        hpchx = new HeadsPlusConfigHeadsX();
        de = new DeathEvents();
        hplb = new HeadsPlusLeaderboards();
        hpchl = new HeadsPlusChallenges();
        hapi = new HeadsPlusAPI();
        mySQLAPI = new MySQLAPI();
        hpl = new HeadsPlusLevels();
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
        }

        else if (getServer().getVersion().contains("1.9.4")) {
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
    }

    private void addLevels() {
	    levels.put(1, new StarterLevels.Grass());
        levels.put(2, new StarterLevels.Dirt());
        levels.put(3, new StarterLevels.Stone());
        levels.put(4, new LowerLevels.Coal());
        levels.put(5, new LowerLevels.CoalII());
        levels.put(6, new LowerLevels.Iron());
        levels.put(7, new LowerLevels.IronII());
        levels.put(8, new LowerLevels.Redstone());
        levels.put(9, new LowerLevels.RedstoneII());
        levels.put(10, new MidLevels.Lapis());
        levels.put(11, new MidLevels.LapisII());
        levels.put(12, new MidLevels.LapisIII());
        levels.put(13, new MidLevels.Gold());
        levels.put(14, new MidLevels.GoldII());
        levels.put(15, new MidLevels.GoldIII());
        levels.put(16, new MidLevels.Diamond());
        levels.put(17, new MidLevels.DiamondII());
        levels.put(18, new MidLevels.DiamondIII());
        levels.put(19, new HigherLevels.Obsidian());
        levels.put(20, new HigherLevels.ObsidianII());
        levels.put(21, new HigherLevels.ObsidianIII());
        levels.put(22, new HigherLevels.ObsidianIV());
        levels.put(23, new HigherLevels.Emerald());
        levels.put(24, new HigherLevels.EmeraldII());
        levels.put(25, new HigherLevels.EmeraldIII());
        levels.put(26, new HigherLevels.EmeraldIV());
        levels.put(27, new HigherLevels.Bedrock());
        levels.put(28, new HigherLevels.BedrockII());
        levels.put(29, new HigherLevels.BedrockIII());
        levels.put(30, new HigherLevels.BedrockIV());
        levels.put(31, new HigherLevels.BedrockV());
    }

}
