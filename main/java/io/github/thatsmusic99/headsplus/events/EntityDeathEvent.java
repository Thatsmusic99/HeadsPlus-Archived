/* package io.github.thatsmusic99.headsplus.events;


import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class EntityDeathEvent implements Listener {

	int eTimesSent = 0;
	Random headSpawn = new Random();
	private Object getDrops;

	
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
    	int pChance = headSpawn.nextInt(99) + 1;
    	int pHead = HeadsPlus.getInstance().getConfig().getInt("playerDeathHead");
    	if (pChance <= pHead) {
    		Player player = (Player) e.getEntity();
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(player.toString());
            meta.setDisplayName(player.toString() + "'s head");
            skull.setItemMeta(meta);
    	    e.getDrops().add(skull);
    	}
    	
    }
   @SuppressWarnings("unchecked")
@EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
	   
       if (e.getEntity() instanceof Zombie) {
    	   int zHead = HeadsPlus.getInstance().getConfig().getInt("zombieHeadC");
	       int zChance = headSpawn.nextInt(99) + 1;
	       if (zChance <= zHead) {
            	ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            	SkullMeta meta = (SkullMeta) skull.getItemMeta();
            	meta.setOwner(HeadsPlus.getInstance().getHeadsConfig().getString("zombieHead"));
            	meta.setDisplayName("Zombie Head");
            	skull.setItemMeta(meta);
            	((List<ItemStack>) e.getDrops).add(skull);
           }
	   }
       if (e.getEntity() instanceof Blaze) {
    	   
       }
    }

private LivingEntity getEntity() {
	
	LivingEntity entity = getEntity();
	return entity;
}

	
}
*/