package io.github.thatsmusic99.headsplus.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.config.HeadsPlusDataFile;

public class BlockEvents implements Listener {
	
	private static ConfigurationSection data = HeadsPlusDataFile.getHPData();
	
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SKULL) {
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			World world = e.getBlock().getWorld();
			int skullx = e.getBlock().getX();
			int skully = e.getBlock().getY();
			int skullz = e.getBlock().getZ();
			if (data.getConfigurationSection(world.getName()) !=  null) {
				ConfigurationSection worldCS = data.getConfigurationSection(world.getName());
				if (worldCS.getConfigurationSection(String.valueOf(skullx) + "," + String.valueOf(skully) + "," + String.valueOf(skullz)) != null) {
					ConfigurationSection loc = worldCS.getConfigurationSection(String.valueOf(skullx) + "," + String.valueOf(skully) + "," + String.valueOf(skullz));
					if (loc.getConfigurationSection("SkullMeta") != null) {
						skull.setItemMeta((SkullMeta) loc.getConfigurationSection("SkullMeta"));
						if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				            e.getBlock().getDrops().clear();
				            e.getBlock().getDrops().add(skull);
			            }
					}
				}
			} else {
				return;
			}
		} 
	}
	public void onBlockPlace(BlockPlaceEvent e) {
		try {
		if (e.getBlock().getType() == Material.SKULL) {
			ItemStack skull = e.getItemInHand();
			SkullMeta skullM = (SkullMeta) skull.getItemMeta();
			World world = e.getBlockPlaced().getWorld();
			int skullx = e.getBlockPlaced().getX();
			int skully = e.getBlockPlaced().getY();
			int skullz = e.getBlockPlaced().getZ();
			if (data.getConfigurationSection(world.getName()) == null) {
			    data.createSection(world.getName());
			} 
			ConfigurationSection worldCS = data.getConfigurationSection(world.getName());
            if (worldCS.getConfigurationSection(String.valueOf(skullx) + "," + String.valueOf(skully) + "," + String.valueOf(skullz)) != null) {
    	        ConfigurationSection loc = data.getConfigurationSection(String.valueOf(skullx) + String.valueOf(skully) + String.valueOf(skullz));
    	        if (loc.getString("SkullMeta") != null) {
    	        	loc.set("SkullMeta", skullM.toString());
    	        } else {
    	        	loc.addDefault("SkullMeta", skullM.toString());
    	        }
            } else {
            	worldCS.createSection(String.valueOf(skullx) + String.valueOf(skully) + String.valueOf(skullz));
            	ConfigurationSection loc = data.getConfigurationSection(String.valueOf(skullx) + String.valueOf(skully) + String.valueOf(skullz));
            	if (loc.getString("SkullMeta") != null) {
    	        	loc.set("SkullMeta", skullM.toString());
    	        } else {
    	        	loc.addDefault("SkullMeta", skullM.toString());
    	        } 
            }
            HeadsPlusDataFile.getHPData().options().copyDefaults(true);
            HeadsPlusDataFile.saveHPData();
		} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} 

}
