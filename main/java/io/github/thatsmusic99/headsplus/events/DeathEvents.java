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

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class DeathEvents implements Listener {
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Zombie) {
			Random zRand = new Random();
			int ZDC1 = HeadsPlus.getInstance().getConfig().getInt("zombieHeadC");
			int ZDC2 = zRand.nextInt(100) + 1;
			if (ZDC2 <= ZDC1) {
				ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta zHeadM = (SkullMeta) zHead.getItemMeta();
				zHeadM.setOwner(HeadsPlus.getInstance().getConfig().getString("zombieHeadN"));
				zHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getString("zombieHeadDN")));
			    zHeadM.addEnchant(Enchantment.LUCK, 1, true);
				zHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				zHead.setItemMeta(zHeadM);
				e.getDrops().add(zHead);
			}
			
		}
		if (e.getEntity() instanceof Skeleton) {
			Random sRand = new Random();
			int SDC1 = HeadsPlus.getInstance().getConfig().getInt("skeletonHeadC");
			int SDC2 = sRand.nextInt(100) + 1;
			if (SDC2 <= SDC1) {
				ItemStack sHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta sHeadM = (SkullMeta) sHead.getItemMeta();
				sHeadM.setOwner(HeadsPlus.getInstance().getConfig().getString("skeletonHeadN"));
				sHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getString("skeletonHeadDN")));			    
				sHeadM.addEnchant(Enchantment.LUCK, 1, true);
				sHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				sHead.setItemMeta(sHeadM);
				e.getDrops().add(sHead);
			}
		} 
		if (e.getEntity() instanceof Blaze) {
			Random bRand = new Random();
			int BDC1 = HeadsPlus.getInstance().getConfig().getInt("blazeHeadC");
			int BDC2 = bRand.nextInt(100) + 1;
			if (BDC2 <= BDC1) {
				ItemStack bHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta bHeadM = (SkullMeta) bHead.getItemMeta();
				bHeadM.setOwner(HeadsPlus.getInstance().getConfig().getString("blazeHeadN"));
				bHeadM.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getString("blazeHeadDN")));
			    bHeadM.addEnchant(Enchantment.LUCK, 1, true);
				bHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		        bHead.setItemMeta(bHeadM);
				e.getDrops().add(bHead);
			}
		}
	} 

}
