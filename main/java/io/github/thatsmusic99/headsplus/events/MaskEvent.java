package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class MaskEvent implements Listener {

    private static HashMap<Player, BukkitRunnable> maskMonitors = new HashMap<>();

    @EventHandler
    public void onMaskPutOn(InventoryClickEvent e) {
        HeadsPlus hp = HeadsPlus.getInstance();
        if (hp.getConfiguration().getPerks().getBoolean("mask-powerups")) {
            if (e.getSlot() == 39) {
                ItemStack ist = e.getCursor();
                if (ist != null) {
                    NMSManager nms = hp.getNMS();
                    if (ist.getType().equals(nms.getSkullMaterial(1).getType())) {
                        HeadsPlusConfigHeads hpch = hp.getHeadsConfig();
                        String s = nms.getType(ist).toLowerCase();
                        if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
                            HPPlayer pl = HPPlayer.getHPPlayer((OfflinePlayer) e.getWhoClicked());
                            pl.addMask(s);
                            maskMonitors.put((Player) e.getWhoClicked(), new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (e.getWhoClicked().getInventory().getItem(39) == null) {
                                        HPPlayer pl = HPPlayer.getHPPlayer((OfflinePlayer) e.getWhoClicked());
                                        pl.clearMask();
                                        maskMonitors.remove(e.getWhoClicked());
                                        cancel();
                                    }
                                }
                            });
                            maskMonitors.get(e.getWhoClicked()).runTaskTimer(HeadsPlus.getInstance(), 20, 40);
                        }
                    }
                }
            }
        }
    }
}
