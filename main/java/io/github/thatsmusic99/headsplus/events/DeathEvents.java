package io.github.thatsmusic99.headsplus.events;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlusConfigHeads;

public class DeathEvents implements Listener {
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
			    zHeadM.addEnchant(Enchantment.DURABILITY, 1, true);
				zHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
				sHeadM.addEnchant(Enchantment.DURABILITY, 1, true);
				sHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				sHead.setItemMeta(sHeadM);
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
			    bHeadM.addEnchant(Enchantment.DURABILITY, 1, true);
				bHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		        bHead.setItemMeta(bHeadM);
				e.getDrops().add(bHead);
			}
		}
		if (e.getEntity() instanceof CaveSpider) {
			Random csRand = new Random();
			int CSDC1 = HeadsPlusConfigHeads.getHeads().getInt("cavespiderHeadC");
			int CSDC2 = csRand.nextInt(100) + 1;
			if (CSDC2 <= CSDC1) {
				ItemStack csHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta csHeadM = (SkullMeta) csHead.getItemMeta();
				csHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadN"));
				csHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadDN")));
			    csHeadM.addEnchant(Enchantment.DURABILITY, 1, true);
				csHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		        csHead.setItemMeta(csHeadM);
				e.getDrops().add(csHead);
			}
		}
		if (e.getEntity() instanceof Chicken) {
			Random cRand = new Random();
			int CDC1 = HeadsPlusConfigHeads.getHeads().getInt("cavespiderHeadC");
			int CDC2 = cRand.nextInt(100) + 1;
			if (CDC2 <= CDC1) {
				ItemStack cHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta cHeadM = (SkullMeta) cHead.getItemMeta();
				cHeadM.setOwner(HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadN"));
				cHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadDN")));
			    cHeadM.addEnchant(Enchantment.DURABILITY, 1, true);
				cHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		        cHead.setItemMeta(cHeadM);
				e.getDrops().add(cHead);
			}
		}
	} 

}
