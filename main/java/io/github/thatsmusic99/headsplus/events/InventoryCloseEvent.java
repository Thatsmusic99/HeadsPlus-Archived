package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void onInvClose(org.bukkit.event.inventory.InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        HeadsPlus.getInstance().getLogger().info("Piing!");
        if (InventoryManager.pls.containsKey(p)) {
            HeadsPlus.getInstance().getLogger().info("Poong!");
            InventoryManager im = InventoryManager.getIM(p);
            if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: page " + im.getPage() + "/" + im.getPages()) || e.getInventory().getName().equalsIgnoreCase("HeadsPlus ")) {
                HeadsPlus.getInstance().getLogger().info("Paang!");
                InventoryManager.pls.remove(p);
            }
        }
    }
}
