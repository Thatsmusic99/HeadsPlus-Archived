package io.github.thatsmusic99.headsplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thatsmusic99.headsplus.commands.Head;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.commands.SellHead;
import io.github.thatsmusic99.headsplus.crafting.RecipeListeners;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import io.github.thatsmusic99.headsplus.events.HeadInteractEvent;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HeadsPlus extends JavaPlugin {	
	public Logger log = Logger.getLogger("Minecraft");
	private static HeadsPlus instance;
	public PluginDescriptionFile pluginYml = getDescription();
	public String author = pluginYml.getAuthors().toString();
	public String version = pluginYml.getVersion();
	public boolean sellable;
	private static Economy econ;
	
    public static FileConfiguration config;
	public File configF;
	FileConfiguration messages;
	File messagesF;
	Plugin p;

		
	
	@Override
	public void onEnable() {
		try { 
			instance = this;
			setUpMConfig();
			HeadsPlusConfig.msgEnable();
			HeadsPlusConfigHeads.headsEnable();
			if (getConfig().getBoolean("craftHeads")) {
			    RecipeListeners.addRecipes();
			}
			if (!(econ()) && (getConfig().getBoolean("sellHeads"))) {
				log.warning("[HeadsPlus] Vault not found! Heads cannot be sold.");
				sellable = false;
			} else if ((econ()) && (getConfig().getBoolean("sellHeads"))) {
				if (this.getServer().getPluginManager().getPlugin("SimpleAPI") == null) {
					log.warning("[HeadsPlus] SimpleAPI not found! Heads cannot be sold.");
					sellable = false;
				} else {
					this.getCommand("sellhead").setExecutor(new SellHead());
					sellable = true;
				}
				
			}
			getServer().getPluginManager().registerEvents(new HeadInteractEvent(), this);
			//if (getConfig().getBoolean("dropHeads")) {
			getServer().getPluginManager().registerEvents(new DeathEvents(), this);
		    //}
		    this.getCommand("headsplus").setExecutor(new HeadsPlusCommand());
		    this.getCommand("hp").setExecutor(new HeadsPlusCommand());
		    this.getCommand("head").setExecutor(new Head());
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

/*	@SuppressWarnings("deprecation")
	public void addRecipes() {
		ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta zMeta = (SkullMeta) zHead.getItemMeta();
		zMeta.setOwner("MHF_Zombie");
		zMeta.setDisplayName("Zombie Head");
		zHead.setItemMeta(zMeta);
		
		
		ShapelessRecipe zombieRecipe = new ShapelessRecipe(zHead)
	             .addIngredient(Material.ROTTEN_FLESH)
	             .addIngredient(Material.SKULL_ITEM, 0);
		
		getServer().addRecipe(zombieRecipe);

		
		
	} */

	public void setUpMConfig() {
			configF = new File(getDataFolder(), "config.yml");
			config = getConfig();
			if(!getDataFolder().exists()) {
				
				getDataFolder().mkdirs();
				
			}
			if (!configF.exists()) {
				try {
					configF.createNewFile();
				} catch (IOException e) {
					log.severe("[HeadsPlus] Couldn't create config!");
					e.printStackTrace();
				}
			}
			config.options().copyDefaults(true);
			saveConfig();
	/*		headsF = new File(getDataFolder(), "heads.yml");
			if (!headsF.exists()) {
				try {
				    headsF.createNewFile();
				} catch (IOException e) {
					log.severe("[HeadsPlus] Couldn't create heads config!");
					e.printStackTrace();
				}
			*/}
		 
    public void reloadMConfig() {
    	config = YamlConfiguration.loadConfiguration(configF);
    }
    public String translateMessages(String s) {
    	s = s.replaceAll("''", "'");
		s = s.replaceAll("^'", "");
		s = s.replaceAll("'$", "");
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


/*	public FileConfiguration getHConfig() {
		return heads;
	}
*/



	// TODO need to grab those config things and shove them into a file of their own.
	// TODO Add crafting recipe? :)

	

	
}
