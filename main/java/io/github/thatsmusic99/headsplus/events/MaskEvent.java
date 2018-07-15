package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MaskEvent implements Listener {

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
                        }
                    } else {
                        if (e.getWhoClicked().getActivePotionEffects().size() > 0) {
                            HPPlayer pl = HPPlayer.getHPPlayer((OfflinePlayer) e.getWhoClicked());
                            pl.clearMask();
                        }
                    }
                }
            }
        }
    }
}
