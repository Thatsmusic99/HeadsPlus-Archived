package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlaceEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        try {
            if (HeadsPlus.getInstance().isStoppingPlaceableHeads()) {
                if (e.getItemInHand().getType() == Material.SKULL || e.getItemInHand().getType() == Material.SKULL_ITEM) {
                    if (!e.getPlayer().hasPermission("headsplus.bypass.preventplacement")) {
                        if (e.getItemInHand().getItemMeta().getLore() != null) {
                            List<String> ls = new ArrayList<>();
                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                            }
                            if (e.getItemInHand().getItemMeta().getLore().equals(ls)) {
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("block-place-denied"));
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                ex.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to fire this event. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(ex, "Event (PlaceEvent)");
                    log.severe("Report name: " + s);
                    log.severe("Please submit this report to the developer at one of the following links:");
                    log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    log.severe("https://discord.gg/nbT7wC2");
                    log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
