package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryCloseEvent implements Listener {

    private InventoryManager im;

    @EventHandler
    public void onInvClose(org.bukkit.event.inventory.InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: page " + im.getPage() + "/" + im.getPages())) {
            if (InventoryManager.pls.containsKey(p)) {
                im = InventoryManager.getIM(p);
                InventoryManager.pls.remove(p);
            }
        }
    }
}
