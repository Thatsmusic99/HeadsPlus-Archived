package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
                    e.setCancelled(true);
                    return;
                }
                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                e.setCancelled(true);
            } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(true));
                } else {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(false));
                }
            }
        }
    }
}
