package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

public class DeathEvents implements Listener {
	
	private List<EntityType> ableEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER, EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HORSE, EntityType.HUSK, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER, EntityType.WITHER_SKELETON, EntityType.ZOMBIE ));

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (ableEntities.contains(e.getEntityType())) {
			if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(e.getEntity().getWorld().getName())) {
		        String entity = e.getEntityType().toString().toLowerCase().replaceAll("_", "");
		        Random rand = new Random();
		        int chance1 = HeadsPlusConfigHeads.getHeads().getInt(entity + "HeadC");
		        int chance2 = rand.nextInt(100) + 1;
		        if (chance2 <= chance1) {
			        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			        SkullMeta headM = (SkullMeta) head.getItemMeta();
			        headM.setOwner(HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN"));
			        headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString(entity + "HeadDN")));
			        if (e.getEntity().getKiller() != null) {
			            if ((HeadsPlus.getInstance().sellable) && (e.getEntity().getKiller().hasPermission("headsplus.sellhead"))) {
			        	    List<String> lore = new ArrayList<>();
				    	    lore.add(ChatColor.translateAlternateColorCodes('&', "&6&lThis head can be sold!"));
					        lore.add(ChatColor.translateAlternateColorCodes('&', "&6Do /sellhead to sell!"));
					        headM.setLore(lore);
			            }
			        }
			       head.setItemMeta(headM);
			       Location entityLoc = e.getEntity().getLocation();
                   double entityLocY = entityLoc.getY() + 1;
                   entityLoc.setY(entityLocY);
                   World world = e.getEntity().getWorld();
                   world.dropItem(entityLoc, head);
               }
		    }
		}
	} 
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ep) {
		if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(ep.getEntity().getWorld().getName())) { 
			
		
		    Random rand = new Random();
		    int chance1 = HeadsPlusConfigHeads.getHeads().getInt("playerHeadC");
		    int chance2 = rand.nextInt(100) + 1;
		    if (chance2 <= chance1) {
		    	ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			    SkullMeta headM = (SkullMeta) head.getItemMeta();
			    headM.setOwner(ep.getEntity().getName());
			    headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("playerHeadDN").replaceAll("%d", ep.getEntity().getName())));
			    if (ep.getEntity().getKiller() != null) {
		    	    if ((HeadsPlus.getInstance().sellable)  && (ep.getEntity().getKiller().hasPermission("headsplus.sellhead"))) {
				        List<String> lore = new ArrayList<>();
				        lore.add(ChatColor.translateAlternateColorCodes('&', "&6&lThis head can be sold!"));
				        lore.add(ChatColor.translateAlternateColorCodes('&', "&6Do /sellhead to sell!"));
				        headM.setLore(lore);
			        }
			    }
			    head.setItemMeta(headM);
			    Location entityLoc = ep.getEntity().getLocation();
                double entityLocY = entityLoc.getY() + 1;
               entityLoc.setY(entityLocY);
                World world = ep.getEntity().getWorld();
                world.dropItem(entityLoc, head);
            }
		}
	}
}