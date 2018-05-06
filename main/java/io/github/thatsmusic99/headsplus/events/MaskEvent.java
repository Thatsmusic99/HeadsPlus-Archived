package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaskEvent implements Listener {

    @EventHandler
    public void onMaskPutOn(InventoryClickEvent e) {
        if (HeadsPlus.getInstance().getConfig().getBoolean("mask-powerups")) {
            if (e.getSlot() == 39) {
                ItemStack ist = e.getCursor();
                if (ist != null) {
                    if (ist.getType().equals(Material.SKULL_ITEM)) {
                        NMSManager nms = HeadsPlus.getInstance().getNMS();
                        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
                        String s = nms.getType(ist).toLowerCase();
                        if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
                            List<PotionEffect> po = new ArrayList<>();
                            for (int i = 0; i < hpch.getConfig().getStringList(s + ".mask-effects").size(); i++) {
                                String is = hpch.getConfig().getStringList(s + ".mask-effects").get(i).toUpperCase();
                                int amp = hpch.getConfig().getIntegerList(s + ".mask-amplifiers").get(i);
                                try {
                                    PotionEffect p = new PotionEffect(PotionEffectType.getByName(is), 1000000, amp);
                                    p.apply(e.getWhoClicked());
                                    po.add(p);
                                } catch (IllegalArgumentException ex) {
                                    HeadsPlus.getInstance().getLogger().severe("Invalid potion type detected. Please check your masks configuration in heads.yml!");
                                }
                            }
                            HPPlayer pl = HPPlayer.getHPPlayer((OfflinePlayer) e.getWhoClicked());
                            PotionEffect[] pa = new PotionEffect[po.size()];
                            for (int i = 0; i < po.size(); i++) {
                                pa[i] = po.get(i);
                            }
                            pl.addMask(pa);
                        }
                    } else {
                        if (e.getWhoClicked().getActivePotionEffects().size() > 0) {
                            HPPlayer pl = HPPlayer.getHPPlayer((OfflinePlayer) e.getWhoClicked());
                            for (PotionEffect p : pl.getActiveMasks()) {
                                e.getWhoClicked().removePotionEffect(p.getType());
                            }
                        }
                    }
                }
            }
        }
    }
}
