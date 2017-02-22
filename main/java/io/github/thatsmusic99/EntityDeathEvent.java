package io.github.thatsmusic99;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public abstract class EntityDeathEvent implements Listener {

	int eTimesSent = 0;
	Random headSpawn = new Random();
    
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
 //  @EventHandler
 //   public void onEntityDeath(EntityDeathEvent e) {
 //           if (e.getEntity() instanceof Zombie) {
            	// ... for now, Eclipse doesn't want to work with EntityDeathEvent -.-
 //                   }
 //   }

	
}
