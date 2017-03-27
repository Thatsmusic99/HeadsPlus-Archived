package io.github.thatsmusic99.headsplus.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
					        if (data.getConfigurationSection(e.getPlayer().getUniqueId().toString()) == null) {
				                data.createSection(e.getPlayer().getUniqueId().toString());
					        }
				            ConfigurationSection uuid = data.getConfigurationSection(e.getPlayer().getUniqueId().toString());
				            if (uuid.get("SkullMeta" + num) != null) {
				    	        data.getConfigurationSection(e.getPlayer().getUniqueId().toString()).set("SkullMeta" + num, skull.getItemMeta().toString()); 
				            } else {
				        	    data.getConfigurationSection(e.getPlayer().getUniqueId().toString()).addDefault("SkullMeta" + num, skull.getItemMeta().toString()); 
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
					ConfigurationSection uuid = data.getConfigurationSection(e.getPlayer().getUniqueId().toString());
					SkullMeta skullM = (SkullMeta) uuid.get("SkullMeta" + num2);
					skull.setItemMeta(skullM);
			    }   
		    }
		}
	}
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SKULL) {
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			World world = e.getBlock().getWorld();
			int skullx = e.getBlock().getX();
			int skully = e.getBlock().getY();
			int skullz = e.getBlock().getZ();
			if (data.getConfigurationSection(world.toString()) !=  null) {
				ConfigurationSection worldCS = data.getConfigurationSection(world.toString());
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
		if (e.getBlock().getType() == Material.SKULL) {
			ItemStack skull = e.getItemInHand();
			SkullMeta skullM = (SkullMeta) skull.getItemMeta();
			World world = e.getBlockPlaced().getWorld();
			int skullx = e.getBlockPlaced().getX();
			int skully = e.getBlockPlaced().getY();
			int skullz = e.getBlockPlaced().getZ();
			if (data.getConfigurationSection(world.toString()) == null) {
			    data.createSection(world.toString());
			}
			ConfigurationSection worldCS = data.getConfigurationSection(world.toString());
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
		}else {
		e.getPlayer().sendMessage(e.getBlock().getType().toString());
	    }
	} 
}
