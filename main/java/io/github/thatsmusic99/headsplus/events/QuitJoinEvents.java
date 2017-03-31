package io.github.thatsmusic99.headsplus.events;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class QuitJoinEvents implements Listener {
	
	public static int num;
	public static int num2;
	private static ConfigurationSection data = HeadsPlusDataFile.getHPData();
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (e.getPlayer().getInventory().contains(Material.SKULL_ITEM)) {
			Inventory skullCheck = e.getPlayer().getInventory();
			ItemStack[] skulls = skullCheck.getContents();
			for (ItemStack skull : skulls) {
				try {
					if (skull != null) {
				        if (skull.getType() == Material.SKULL_ITEM) {
					        num++;
				            String uuid = e.getPlayer().getUniqueId().toString();
				            SkullMeta skullM = (SkullMeta) skull.getItemMeta();
				            if (data.getString(uuid + ".SkullMeta" + num) == null) {
				            	data.addDefault(uuid + ".SkullMeta" + num, skullM.toString());
				            } else {
				            	data.set(uuid + ".SkullMeta" + num, skullM.toString());
				            }
				            HeadsPlusDataFile.getHPData().options().copyDefaults(true);
				            HeadsPlusDataFile.saveHPData();
			            }   
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		    }
		}
	}
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().getInventory().contains(Material.SKULL_ITEM)) {
			PlayerInventory skullCheck = e.getPlayer().getInventory();
			ItemStack[] skulls = skullCheck.getContents();
			for (ItemStack skull : skulls) {
				if (skull.equals(Material.SKULL_ITEM)) {
					num2++;
					String uuid = e.getPlayer().getUniqueId().toString();
					SkullMeta skullM = (SkullMeta) data.get(uuid + ".SkullMeta" + num);
					skull.setItemMeta(skullM);
			    }   
		    }
		}
	}
	
}
