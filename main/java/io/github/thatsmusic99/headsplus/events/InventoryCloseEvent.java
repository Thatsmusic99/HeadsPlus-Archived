package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryCloseEvent implements Listener {

  /*  @EventHandler
    public void onInvClose(org.bukkit.event.inventory.InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        HeadsPlus.getInstance().getLogger().info("Piing!");
        if (InventoryManager.pls.containsKey(p)) {
            HeadsPlus.getInstance().getLogger().info("Poong!");
            InventoryManager.pls.remove(p);

        }
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (!InventoryManager.pls.containsKey(p)) {
            if (e.getInventory().getName().startsWith("HeadsPlus")) {
                InventoryManager.pls.put(p)
            }
        }
    } */
}
