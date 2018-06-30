package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        try {
            if (HeadsPlus.getInstance().isStoppingPlaceableHeads()) {
                if (e.getItemInHand().getType() == Material.SKULL || e.getItemInHand().getType() == Material.SKULL_ITEM) {
                    if (!e.getPlayer().hasPermission("headsplus.bypass.preventplacement")) {
                        if (HeadsPlus.getInstance().getNMS().isSellable(e.getItemInHand())) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("block-place-denied"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new DebugPrint(ex, "Event (PlaceEvent)", false, null);
        }
    }
}
