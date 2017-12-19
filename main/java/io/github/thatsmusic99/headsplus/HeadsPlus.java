package io.github.thatsmusic99.headsplus;

import io.github.thatsmusic99.headsplus.commands.*;
import io.github.thatsmusic99.headsplus.config.*;
import io.github.thatsmusic99.headsplus.events.*;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thatsmusic99.headsplus.crafting.RecipePerms;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {

	public final Logger log = Logger.getLogger("Minecraft");
	private static HeadsPlus instance;
	private final PluginDescriptionFile pluginYml = getDescription();
	public final String author = pluginYml.getAuthors().toString();
	public final String version = pluginYml.getVersion();
	public boolean sellable;
	public Economy econ;
	public boolean drops;
	public boolean arofj;
	public boolean db;
	public boolean lb;
	public boolean stopP;
	public static Object[] update = null;
    public Connection connection;
    public static boolean con = false;

    public static FileConfiguration config;

    @SuppressWarnings("unused")
	private File messagesF;

	@Override
	public void onEnable() {
		try { 
			instance = this;
			setUpMConfig();
			try {
                HeadsPlusConfig.getMessages().getString("locale");
            } catch (NullPointerException ex) {
                HeadsPlusConfig.msgEnable(true);
            }
			LocaleManager.class.newInstance().setupLocale();
			HeadsPlusConfig.msgEnable(false);
			HeadsPlusConfigHeads.headsEnable();
			HeadsPlusConfigHeadsX.headsxEnable();
			DeathEvents.createList();

			if (config.getBoolean("leaderboards-mysql")) {
                openConnection();
            }

			if (!getConfig().getBoolean("disableCrafting")) {
			    HeadsPlusCrafting.craftingEnable();
			    getServer().getPluginManager().registerEvents(new RecipePerms(), this);
			}
			if (!(econ()) && (getConfig().getBoolean("sellHeads"))) {
                this.getCommand("sellhead").setExecutor(new SellHead());
				this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				log.warning("[HeadsPlus] Vault not found! Heads cannot be sold.");
				sellable = false;
			} else if ((econ()) && !(getConfig().getBoolean("sellHeads"))) {
				this.getCommand("sellhead").setExecutor(new SellHead());
				this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				sellable = false;
			} else if ((econ()) && (getConfig().getBoolean("sellHeads"))){
				this.getCommand("sellhead").setExecutor(new SellHead());
                this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				sellable = true;
			} else if (!(econ() && !(getConfig().getBoolean("sellHeads")))) {
                this.getCommand("sellhead").setExecutor(new SellHead());
                this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				sellable = false;
			}
			getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
			getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
			if (getConfig().getBoolean("dropHeads")) {
			    drops = true;
			    getServer().getPluginManager().registerEvents(new DeathEvents(), this);
		    } else {
			    drops = false;
                getServer().getPluginManager().registerEvents(new DeathEvents(), this);
            }
			if (getConfig().getBoolean("autoReloadOnFirstJoin")) {
			    arofj = true;
				getServer().getPluginManager().registerEvents(new JoinEvent(), this);
			} else {
			    arofj = false;
                getServer().getPluginManager().registerEvents(new JoinEvent(), this);
            }
            if(getConfig().getBoolean("stop-placement-of-sellable-heads")) {
			    stopP = true;
			    getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
            } else {
			    stopP = false;
			    getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
            }
            if (getConfig().getBoolean("leaderboards")) {
			    lb = true;
			    getServer().getPluginManager().registerEvents(new LBEvents(), this);

			    HeadsPlusLeaderboards.lbFileEnable();
            }
            db = getConfig().getBoolean("headsDatabase");
		    this.getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		    this.getCommand("hp").setExecutor(new HeadsPlusCommand());
		    this.getCommand("hp").setTabCompleter(new TabComplete());
		    this.getCommand("head").setExecutor(new Head());
		    this.getCommand("heads").setExecutor(new Heads());
		    this.getCommand("myhead").setExecutor(new MyHead());
			this.getCommand("hplb").setExecutor(new LeaderboardsCommand());
		    JoinEvent.reloaded = false;
			Metrics metrics = new Metrics(this);
			metrics.addCustomChart(new Metrics.SimplePie("languages", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return LocaleManager.getLocale().getLanguage();
                }
            }));
			if (getConfig().getBoolean("update-checker")) {
			    new BukkitRunnable() {
                    @Override
                    public void run() {
                        update = UpdateChecker.getUpdate();
                        if (update != null) {
                            log.info("[HeadsPlus] An update has been found!");
                            log.info("Current version: " + getDescription().getVersion());
                            log.info("New version: " + update[2]);
                            if (update[1].toString().length() > 50) {
                                update[1] = update[1].toString().subSequence(0, 50) + "... (Check Spigot for more information)";
                            }
                            log.info("Description: " + update[1]);
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
	private static void setUpMConfig() {
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
    		s = s.replaceAll("%h", ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("prefix")));
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

	public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("mysql-host")+ ":" + config.getString("mysql-port") + "/" + config.getString("mysql-port"), config.getString("mysql-username"), config.getString("mysql-password"));
            Statement st = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            try {
				st.executeQuery("SELECT * from headspluslb");
			} catch (SQLException ex) {
				sb.append("CREATE TABLE `headspluslb` (" +
						"`id` INT NOT NULL AUTO_INCREMENT," +
						"`uuid` VARCHAR(45)," +
						"`total` VARCHAR(45)");
				for (EntityType e : DeathEvents.ableEntities) {
					sb.append(", `").append(e.name()).append("` VARCHAR(45)");
				}
				st.executeUpdate(sb.toString());
				StringBuilder sb2 = new StringBuilder();
				sb2.append("INSERT INTO `headspluslb` (uuid, total");
				for (EntityType e : DeathEvents.ableEntities) {
					sb2.append(", ").append(e.name());
				}
				sb2.append("VALUES('server-total', '0'");
				for (EntityType ignored : DeathEvents.ableEntities) {
					sb2.append(", 0");
				}
				sb2.append(")");
				st.executeUpdate(sb2.toString());
				con = true;
			}


        }
    }

}
