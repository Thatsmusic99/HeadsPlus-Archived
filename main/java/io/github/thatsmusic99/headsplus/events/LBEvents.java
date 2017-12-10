package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.api.EntityHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LBEvents implements Listener {

    @EventHandler
    public void onHeadDrop(EntityHeadDropEvent e) {
        if (HeadsPlusLeaderboards.addPlayerOnFileIfNotFound(e.getPlayer(), e.getEntityType().name())) {
            HeadsPlusLeaderboards.addOntoValue(e.getPlayer(), e.getEntityType().name());
            return;
        }
        if (HeadsPlusLeaderboards.addSectionOnFileIfNotFound(e.getPlayer(), e.getEntityType().name())) {
            HeadsPlusLeaderboards.addOntoValue(e.getPlayer(), e.getEntityType().name());
        }
    }

    @EventHandler
    public void onPHeadDrop(PlayerHeadDropEvent e) {
        if (HeadsPlusLeaderboards.addPlayerOnFileIfNotFound(e.getKiller(), "player")) {
            HeadsPlusLeaderboards.addOntoValue(e.getKiller(), "player");
            return;
        }
        if (HeadsPlusLeaderboards.addSectionOnFileIfNotFound(e.getKiller(), "player")) {
            HeadsPlusLeaderboards.addOntoValue(e.getKiller(), "player");
        }
    }
}
