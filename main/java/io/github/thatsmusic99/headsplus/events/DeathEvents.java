package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;
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
	
	private static final List<EntityType> ableEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HORSE, EntityType.IRON_GOLEM, EntityType.MAGMA_CUBE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.RABBIT, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER, EntityType.WITCH, EntityType.WITHER, EntityType.ZOMBIE));

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (!HeadsPlus.getInstance().drops) return;
		if (ableEntities.contains(e.getEntityType())) {
			if (e.getEntity().getKiller() != null) {
				if (!HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(e.getEntity().getWorld().getName())) {
				    if (!e.getEntity().getKiller().hasPermission("headsplus.bypass.whitelistw")) {
				        if (HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
				            return;
                        }
                    }
                }
			    if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(e.getEntity().getWorld().getName()) || e.getEntity().getKiller().hasPermission("headsplus.bypass.blacklistw") || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn")) {
		            String entity = e.getEntityType().toString().toLowerCase().replaceAll("_", "");
		            Random rand = new Random();
		            double chance1 = HeadsPlusConfigHeads.getHeads().getDouble(entity + "HeadC");
		            double chance2 = (double) rand.nextInt(100);
		            if (chance1 == 0.0) return;
		            if (chance2 <= chance1) {
			            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			            SkullMeta headM = (SkullMeta) head.getItemMeta();
			            if (HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN").isEmpty()) return;
			            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN")))  {
			                ItemStack i = HeadsPlusConfigHeadsX.getSkull(HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN"));
			                SkullMeta sm = (SkullMeta) i.getItemMeta();
			                sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString(entity + "HeadDN")));
                            List<String> ls = new ArrayList<>();
                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                ls.add(ChatColor.translateAlternateColorCodes('&', str));
                            }
                            sm.setLore(ls);
                            i.setItemMeta(sm);
                            Location entityLoc = e.getEntity().getLocation();
                            double entityLocY = entityLoc.getY() + 1;
                            entityLoc.setY(entityLocY);
                            World world = e.getEntity().getWorld();
                            world.dropItem(entityLoc, i);
                            return;
                        }
			            headM.setOwner(HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN"));
			            headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString(entity + "HeadDN")));
                        List<String> ls = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                            ls.add(ChatColor.translateAlternateColorCodes('&', str));
                        }
                        headM.setLore(ls);
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
	} 
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ep) {
        if (!HeadsPlus.getInstance().drops) return;
        if (ep.getEntity().getKiller() != null) {
            if (!HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(ep.getEntity().getWorld().getName())) {
                if (!ep.getEntity().getKiller().hasPermission("headsplus.bypass.whitelistw")) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
                        return;
                    }
                }
            }
            if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(ep.getEntity().getWorld().getName()) || ep.getEntity().getKiller().hasPermission("headsplus.bypass.blacklistw") || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn")) {
            Random rand = new Random();
            double chance1 = HeadsPlusConfigHeads.getHeads().getDouble("playerHeadC");
            double chance2 = (double) rand.nextInt(100);
            if (chance1 == 0.0) return;
            if (chance2 <= chance1) {
                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta headM = (SkullMeta) head.getItemMeta();
                headM.setOwner(ep.getEntity().getName());
                headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("playerHeadDN").replaceAll("%d", ep.getEntity().getName())));
                if ((HeadsPlus.getInstance().sellable) && (ep.getEntity().getKiller().hasPermission("headsplus.sellhead"))) {
                    List<String> ls = new ArrayList<>();
                    for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                        ls.add(ChatColor.translateAlternateColorCodes('&', str));
                    }
                    headM.setLore(ls);
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

	public static void createList() {
        String bukkitVersion = org.bukkit.Bukkit.getVersion();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
        if (bukkitVersion.contains("1.11")) {
			ableEntities.addAll(Arrays.asList(EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.EVOKER, EntityType.HUSK, EntityType.LLAMA, EntityType.MULE, EntityType.POLAR_BEAR, EntityType.SHULKER, EntityType.SKELETON_HORSE, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON));
		}
		if (bukkitVersion.contains("1.12")) {
			ableEntities.addAll(Arrays.asList(EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.EVOKER, EntityType.HUSK, EntityType.LLAMA, EntityType.MULE, EntityType.PARROT, EntityType.POLAR_BEAR, EntityType.SHULKER, EntityType.SKELETON_HORSE, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON));
		}
	}
}