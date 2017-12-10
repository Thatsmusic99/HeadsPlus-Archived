package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaceEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (HeadsPlus.getInstance().stopP) {
            if (e.getBlock().getType() == Material.SKULL || e.getBlock().getType() == Material.SKULL_ITEM) {
                if (e.getPlayer().hasPermission("headsplus.bypass.preventplacement")) {
                    if (e.getItemInHand().getItemMeta().getLore() != null) {
                        List<String> ls = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                        }
                        if (e.getItemInHand().getItemMeta().getLore().equals(ls)) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("block-place-denied")));
                        }
                    }
                }
            }
        }
    }
}
