package io.github.thatsmusic99.headsplus.events;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class QuitJoinEvents implements Listener {
	
	int num;
	int num2;
	private static ConfigurationSection data = HeadsPlusDataFile.getHPData();
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (e.getPlayer().getInventory().contains(Material.SKULL_ITEM)) {
			PlayerInventory skullCheck = e.getPlayer().getInventory();
			ItemStack[] skulls = skullCheck.getContents();
			for (ItemStack skull : skulls) {
				try {
				    if (skull.equals(Material.SKULL_ITEM)) {
					    num++;
				        data.createSection(e.getPlayer().getUniqueId().toString());
				        ConfigurationSection uuid = data.getConfigurationSection(e.getPlayer().getUniqueId().toString());
				        if (uuid.get("SkullMeta" + num) != null) {
				    	    uuid.set("SkullMeta" + num, skull.getItemMeta().toString()); 
				        } else {
				            uuid.addDefault("SkullMeta" + num, skull.getItemMeta().toString()); 
				        }
				        
				        HeadsPlusDataFile.getHPData().options().copyDefaults(true);
				        HeadsPlusDataFile.saveHPData();
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
					ConfigurationSection uuid = data.getConfigurationSection(e.getPlayer().getUniqueId().toString());
					SkullMeta skullM = (SkullMeta) uuid.get("SkullMeta" + num2);
					skull.setItemMeta(skullM);
			    }   
		    }
		}
	}
}
