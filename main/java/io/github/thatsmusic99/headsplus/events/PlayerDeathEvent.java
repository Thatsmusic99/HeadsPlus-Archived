package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerHeadDropEvent e) {
        try {
            if (HeadsPlus.getInstance().isDeathMessagesEnabled()) {
                if (e.getKiller() != null) {
                    Random r = new Random();
                    int thing = r.nextInt(HeadsPlus.getInstance().getConfig().getStringList("death-messages").size());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!p.hasPermission("headplus.death.ignore")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getStringList("death-messages").get(thing).replaceAll("%h", HeadsPlus.getInstance().getMessagesConfig().getString("prefix")).replaceAll("%k", e.getKiller().getName()).replaceAll("%p", e.getDeadPlayer().getName())));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                ex.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to fire this event. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(ex, "Event (PlayerHeadDropEvent, PlayerDeathEvent)");
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
