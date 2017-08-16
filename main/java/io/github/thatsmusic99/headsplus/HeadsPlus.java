package io.github.thatsmusic99.headsplus;

import io.github.thatsmusic99.headsplus.events.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thatsmusic99.headsplus.commands.Head;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.commands.SellHead;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;
import io.github.thatsmusic99.headsplus.crafting.RecipePerms;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {	
	public final Logger log = Logger.getLogger("Minecraft");
	private static HeadsPlus instance;
	private final PluginDescriptionFile pluginYml = getDescription();
	public final String author = pluginYml.getAuthors().toString();
	public final String version = pluginYml.getVersion();
	public boolean sellable;
	public Economy econ;
	
    public static FileConfiguration config;
	private static File configF;

	@SuppressWarnings("unused")
	private File messagesF;

	@Override
	public void onEnable() {
		try { 
			instance = this;
			setUpMConfig();
			HeadsPlusConfig.msgEnable();
			HeadsPlusConfigHeads.headsEnable();
			DeathEvents.createList();
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
				sellable = true;
			} else if ((econ()) && (getConfig().getBoolean("sellHeads"))){
				this.getCommand("sellhead").setExecutor(new SellHead());
                this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				sellable = true;
			} else if (!(econ() && !(getConfig().getBoolean("sellHeads")))) {
				this.getCommand("sellhead").setExecutor(new SellHead());
                this.getCommand("sellhead").setTabCompleter(new TabCompleteSellhead());
				sellable = false;
			}
			getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
			if (getConfig().getBoolean("dropHeads")) {
			    getServer().getPluginManager().registerEvents(new DeathEvents(), this);
		    }
			if (getConfig().getBoolean("autoReloadOnFirstJoin")) {
				getServer().getPluginManager().registerEvents(new JoinEvent(), this);
			}
		    this.getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		    this.getCommand("hp").setExecutor(new HeadsPlusCommand());
		    this.getCommand("hp").setTabCompleter(new TabComplete());
		    this.getCommand("head").setExecutor(new Head());
		    JoinEvent.reloaded = false;
			Metrics metrics = new Metrics(this);
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
	public static void setUpMConfig() {
			configF = new File(instance.getDataFolder(), "config.yml");
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
	private boolean econ() {
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
}
