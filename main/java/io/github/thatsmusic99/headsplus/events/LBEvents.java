package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.EntityHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LBEvents implements Listener {

    @EventHandler
    public void onHeadDrop(EntityHeadDropEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().lb) {
                HeadsPlusLeaderboards.addOntoValue(e.getPlayer(), e.getEntityType().name());
            }
        }
    }

    @EventHandler
    public void onPHeadDrop(PlayerHeadDropEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().lb) {
                HeadsPlusLeaderboards.addOntoValue(e.getKiller(), "player");
            }
        }
    }
}
