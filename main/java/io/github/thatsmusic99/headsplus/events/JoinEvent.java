package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.ConfigSettings;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnumUser;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.logging.Logger;

public class JoinEvent implements Listener { 
	
	public static boolean reloaded = false;
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("headsplus.notify")) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("update-notify")) {
                if (HeadsPlus.getUpdate() != null) {
                    new FancyMessage().text(hpc.getString("update-found"))
                    .tooltip(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion() + HeadsPlus.getInstance().getDescription().getVersion())
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getNewVersion() + HeadsPlus.getUpdate()[2])
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getDescription() + HeadsPlus.getUpdate()[1])).link("https://www.spigotmc.org/resources/headsplus-1-8-x-1-12-x.40265/updates/").send(e.getPlayer());
                }
            }
        }
        new RecipeEnumUser();
		if (!HeadsPlus.getInstance().isAutoReloadingOnFirstJoin()) return;
		if (!reloaded) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnFirstJoin")) {
			    try {
			    	new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (ConfigSettings cs : HeadsPlus.getInstance().getConfigs()) {
                                cs.reloadC(false);
                            }
                            HeadsPlus.getInstance().getConfig().options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            HPPlayer.players.clear();
                        }
                    }.runTaskAsynchronously(HeadsPlus.getInstance());

			    } catch (Exception ex) {
					if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
						ex.printStackTrace();
					}
					if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
						Logger log = HeadsPlus.getInstance().getLogger();
						log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
						try {
							String s = new DebugFileCreator().createReport(ex, "Event (JoinEvent)");
							log.severe("Report name: " + s);
							log.severe("Please submit this report to the developer at one of the following links:");
							log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
							log.severe("https://discord.gg/nbT7wC2");
							log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
						} catch (IOException e1) {
							if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                                e1.printStackTrace();
							}
						}
					}
			    }
		    }
		}
	}
}
