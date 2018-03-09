package io.github.thatsmusic99.headsplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnumUser;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusCrafting;

public class JoinEvent implements Listener { 
	
	public static boolean reloaded = false;
    private static final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	private static final File messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");

	private static final File headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");

	private static final File craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");

	private static final File headsXF = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");

    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("headsplus.notify")) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("update-notify")) {
                if (HeadsPlus.update != null) {
                    new FancyMessage().text(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("update-found"))))
                    .tooltip(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion() + HeadsPlus.getInstance().getDescription().getVersion())
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getNewVersion() + HeadsPlus.update[2])
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getDescription() + HeadsPlus.update[1])).link("https://www.spigotmc.org/resources/headsplus-1-8-x-1-12-x.40265/updates/").send(e.getPlayer());
                }
            }
        }
		if (!HeadsPlus.getInstance().arofj) return;
		if (!reloaded) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnFirstJoin")) {
			    try {
			    	javax.swing.Timer timer = new javax.swing.Timer(3000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if  (!(configF.exists())) {
							       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
							       HeadsPlus.getInstance().saveConfig();
							       
						       } else {
							       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
							       HeadsPlus.getInstance().reloadConfig();
							       HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
						      }

						      if (!(messagesF.exists())) {
						    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages not found, creating!");
						    	  hpc.reloadC(false);
						    	  hpc.config = YamlConfiguration.loadConfiguration(messagesF);
						    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages created!");
						      } else {
						    	  hpc.reloadC(false);
						      }
						      HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().hpch;
						      if (!(headsF.exists())) {
						    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads not found, creating!");
						    	  hpch.reloadC(false);
						    	  hpch.config = YamlConfiguration.loadConfiguration(headsF);
						    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads created!");
						      } else {
						    	  hpch.reloadC(false);
						      }
						      if (HeadsPlus.getInstance().hpcr != null) {
								  HeadsPlusCrafting hpcr = HeadsPlus.getInstance().hpcr;
								  if (!(craftingF.exists())) {
									  if (HeadsPlus.getInstance().getConfig().getBoolean("craftHeads")) {
										  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting not found, creating!");
										  Bukkit.clearRecipes();
										  hpcr.reloadC(false);
										  hpcr.config = YamlConfiguration.loadConfiguration(craftingF);
										  new RecipeEnumUser();
										  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting created!");
									  }
								  } else {
									  Bukkit.resetRecipes();
									  hpcr.reloadC(false);
									  new RecipeEnumUser();
								  }
							  }
						      HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().hpchx;
						      if (!headsXF.exists()) {
                                  if (HeadsPlus.getInstance().getConfig().getBoolean("headsDatabase")) {
                                  	  HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX not found, creating!");
                                      hpchx.reloadC(false);
                                      hpchx.config = YamlConfiguration.loadConfiguration(headsXF);
                                      HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX created!");
								  }
                              } else {
							      hpchx.reloadC(false);
                              }
					}});
			    	timer.setRepeats(false); // Make it so it does not repeat every 3 seconds
			    	timer.start(); // Run the Task
				       
			       } catch (Exception ex) {
				       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload configs.");
				       ex.printStackTrace();
			       }
		    }
		}
	}
}
