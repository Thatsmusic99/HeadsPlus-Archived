package io.github.thatsmusic99.headsplus.events;

import java.util.Random;

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
			int ZDC1 = HeadsPlus.getInstance().getHConfig().getInt("zombieHeadC");
			int ZDC2 = zRand.nextInt(100) + 1;
			if (ZDC2 >= ZDC1) {
				ItemStack zHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta zHeadM = (SkullMeta) zHead.getItemMeta();
				zHeadM.setOwner(HeadsPlus.getInstance().getHConfig().getString("zombieHeadN"));
				zHeadM.addEnchant(Enchantment.LUCK, 1, true);
				zHeadM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				zHead.setItemMeta(zHeadM);
				e.getDrops().add(zHead);
			}
			
		}
	}

}
