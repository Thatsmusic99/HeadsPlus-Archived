package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: page " + InventoryManager.getPage() + "/" + InventoryManager.getPages())) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("full-inv")));
                    return;
                }
                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                e.setCancelled(true);
            }
        }
    }
}
