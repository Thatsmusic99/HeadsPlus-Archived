package io.github.thatsmusic99.headsplus.listeners;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;

import io.github.thatsmusic99.headsplus.util.SellheadInventory;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {

    private InventoryManager im;

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        try {
            if (!(e.getWhoClicked() instanceof Player)) return;
            Player p = (Player) e.getWhoClicked();
            if (e.getRawSlot() > 53) return;
            if (InventoryManager.getIM(p) == null) return;
            im = InventoryManager.getIM(p);
            NMSManager nms = HeadsPlus.getInstance().getNMS();
            // int month = Calendar.getInstance().get(Calendar.MONTH);
            if (im.getType().equalsIgnoreCase("heads")) {
                Icon i = nms.getIcon(e.getCurrentItem());
                if (i == null) return;
                if (e.getRawSlot() < 54) {
                    e.setCancelled(true);
                }
                i.onClick(p, im, e);
            } else if (im.getType().equalsIgnoreCase("chal")) {
                Icon i = nms.getIcon(e.getCurrentItem());
                if (i == null) return;
                i.onClick(p, im, e);
            } else if (im.getType().equalsIgnoreCase("sellhead")) {
                Icon i = nms.getIcon(e.getCurrentItem());
                if (i == null) return;
                i.onClick(p, im, e);
            }
        } catch (Exception ex) {
            new DebugPrint(ex, "Event (InventoryInteractEvent)", false, null);
        }
    }
}
