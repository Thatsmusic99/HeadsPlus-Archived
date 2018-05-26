package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.crafting.RecipeEnumUser;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;

import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent implements Listener { 
	
	public static boolean reloaded = false;
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("headsplus.notify")) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("update-notify")) {
                if (HeadsPlus.getUpdate() != null) {
                    new FancyMessage().text(hpc.getString("update-found"))
                    .tooltip(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getCurrentVersion() + HeadsPlus.getInstance().getDescription().getVersion())
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getNewVersion() + HeadsPlus.getUpdate()[2])
							+ "\n" + ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getDescription() + HeadsPlus.getUpdate()[1])).link("https://www.spigotmc.org/resources/headsplus-1-8-x-1-12-x.40265/updates/").send(e.getPlayer());
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (e.getPlayer().getInventory().getArmorContents()[3] != null) {
                    if (e.getPlayer().getInventory().getArmorContents()[3].getType().equals(Material.SKULL_ITEM)) {
                        NMSManager nms = HeadsPlus.getInstance().getNMS();
                        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
                        String s = nms.getType(e.getPlayer().getInventory().getArmorContents()[3]).toLowerCase();
                        if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
                            HPPlayer pl = HPPlayer.getHPPlayer(e.getPlayer());
                            pl.addMask(s);
                        }
                    }
                }
            }
        }.runTaskLater(HeadsPlus.getInstance(), 20);
        if (!reloaded) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new RecipeEnumUser();
                }
            }.runTaskLaterAsynchronously(HeadsPlus.getInstance(), 20);
            reloaded = true;
        }

	}
}
