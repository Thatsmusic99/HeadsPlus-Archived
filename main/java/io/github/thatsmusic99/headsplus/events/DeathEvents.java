package io.github.thatsmusic99.headsplus.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.HeadsPlusConfigHeads;
import simple.brainsynder.nbt.ItemNBT;

public class DeathEvents implements Listener {
	
	List<EntityType> ableEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER, EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HORSE, EntityType.HUSK, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER, EntityType.WITHER_SKELETON, EntityType.ZOMBIE ));

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Zombie) {
			Random zRand = new Random();
			int ZDC1 = HeadsPlusConfigHeads.getHeads().getInt("zombieHeadC");
			int ZDC2 = zRand.nextInt(100) + 1;
			if (ZDC2 <= ZDC1) {
				ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta zHeadM = (SkullMeta) zHead.getItemMeta();
				zHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("zombieHeadN"));
				zHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("zombieHeadDN")));
				zHead.setItemMeta(zHeadM);
				e.getDrops().add(zHead);
				
			}
			
		}
		if (e.getEntity() instanceof Skeleton) {
			Random sRand = new Random();
			int SDC1 = HeadsPlusConfigHeads.getHeads().getInt("skeletonHeadC");
			int SDC2 = sRand.nextInt(100) + 1;
			if (SDC2 <= SDC1) {
				ItemStack sHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta sHeadM = (SkullMeta) sHead.getItemMeta();
				sHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("skeletonHeadN"));
				sHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("skeletonHeadDN")));			
				sHead.setItemMeta(sHeadM);
				if (HeadsPlus.getInstance().sellable) {
					ItemNBT skullnbt = ItemNBT.getItemNBT(sHead);
					skullnbt.setBoolean("sellable-head", true);
				}
				e.getDrops().add(sHead);
			}
		} 
		if (e.getEntity() instanceof Blaze) {
			Random bRand = new Random();
			int BDC1 = HeadsPlusConfigHeads.getHeads().getInt("blazeHeadC");
			int BDC2 = bRand.nextInt(100) + 1;
			if (BDC2 <= BDC1) {
				ItemStack bHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta bHeadM = (SkullMeta) bHead.getItemMeta();
				bHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("blazeHeadN"));
				bHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("blazeHeadDN")));
				if (HeadsPlus.getInstance().sellable) {
					ItemNBT skullnbt = ItemNBT.getItemNBT(bHead);
					skullnbt.setBoolean("sellable-head", true);
				}
		        bHead.setItemMeta(bHeadM);
				e.getDrops().add(bHead);
			}
		}
		/*if (e.getEntity() instanceof CaveSpider) {
			Random csRand = new Random();
			int CSDC1 = HeadsPlusConfigHeads.getHeads().getInt("cavespiderHeadC");
			int CSDC2 = csRand.nextInt(100) + 1;
			if (CSDC2 <= CSDC1) {
				ItemStack csHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta csHeadM = (SkullMeta) csHead.getItemMeta();
				csHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadN"));
				csHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadDN")));
				if (HeadsPlus.getInstance().sellable) {
					ItemNBT skullnbt = ItemNBT.getItemNBT(csHead);
					skullnbt.setBoolean("sellable-head", true);
				}
		        csHead.setItemMeta(csHeadM);
				e.getDrops().add(csHead);
			} 
		}*/
		/*if (e.getEntity() instanceof Chicken) {
			Random cRand = new Random();
			int CDC1 = HeadsPlusConfigHeads.getHeads().getInt("chickenHeadC");
			int CDC2 = cRand.nextInt(100) + 1;
			if (CDC2 <= CDC1) {
				ItemStack cHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta cHeadM = (SkullMeta) cHead.getItemMeta();
				cHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("chickenHeadN"));
				cHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("chickenHeadDN")));
				if (HeadsPlus.getInstance().sellable) {
					ItemNBT skullnbt = ItemNBT.getItemNBT(cHead);
					skullnbt.setBoolean("sellable-head", true);
				}
		        cHead.setItemMeta(cHeadM);
				e.getDrops().add(cHead);
			}
		} */
		String entity = e.getEntityType().toString().toLowerCase();
		Random rand = new Random();
		int chance1 = HeadsPlusConfigHeads.getHeads().getInt(entity + "HeadC");
		int chance2 = rand.nextInt(100) + 1;
		if (chance2 <= chance1) {
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta headM = (SkullMeta) head.getItemMeta();
			headM.setOwner(HeadsPlusConfigHeads.getHeads().getString(entity + "HeadN"));
			headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString(entity + "HeadDN")));
			if (HeadsPlus.getInstance().sellable) {
				ItemNBT skullnbt = ItemNBT.getItemNBT(head);
				skullnbt.setBoolean("sellable-head", true);
			}
			head.setItemMeta(headM);
			e.getDrops().add(head);
		}
		
		
	} 
}
