package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

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
            new DebugPrint(ex, "Event (PlayerDeathEvent, PlayerHeadDropEvent)", false, null);
        }
    }
}
