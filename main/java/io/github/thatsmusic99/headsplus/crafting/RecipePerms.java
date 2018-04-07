package io.github.thatsmusic99.headsplus.crafting;

import java.util.List;

import io.github.thatsmusic99.headsplus.api.HeadCraftEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.inventory.meta.SkullMeta;

public class RecipePerms implements Listener {
	
	@EventHandler
	public void onCraft(InventoryClickEvent e) {
		if (HeadsPlus.getInstance().getConfig().getBoolean("craftHeads")) {
			Player player = (Player) e.getWhoClicked();
		    List<String> worlds = HeadsPlus.getInstance().getConfig().getStringList("blacklistw");
		    if ((!worlds.contains(player.getWorld().getName())) || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn") || player.hasPermission("headsplus.bypass.blacklistw")) {
				if (HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(player.getWorld().getName())) {
					if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            HeadCraftEvent event;
                            int amount = shift(e);
                            try {
                                event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, HeadsPlus.getInstance().hapi.getSkullType(e.getCurrentItem()));
                            } catch (NoSuchFieldException | IllegalAccessException e1) {
                                e1.printStackTrace();
                                return;
                            }
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                e.setCurrentItem(HeadsPlus.getInstance().nms.addNBTTag(e.getCurrentItem()));
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
						return;
					}
				} else if (player.hasPermission("headsplus.bypass.whitelistw")){
                    if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            try {
                                if (e.getCurrentItem() != null) {
                                    if (e.getCurrentItem().getItemMeta() instanceof SkullMeta) {
                                        if (HeadsPlus.getInstance().hapi.getSkullType(e.getCurrentItem()) != null) {
                                            try {
                                                HeadCraftEvent event;
                                                int amount = shift(e);
                                                try {
                                                    event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, HeadsPlus.getInstance().hapi.getSkullType(e.getCurrentItem()));
                                                } catch (NoSuchFieldException | IllegalAccessException e1) {
                                                    e1.printStackTrace();
                                                    return;
                                                }
                                                Bukkit.getServer().getPluginManager().callEvent(event);
                                                if (!event.isCancelled()) {
                                                    e.setCurrentItem(HeadsPlus.getInstance().nms.addNBTTag(e.getCurrentItem()));
                                                    return;
                                                } else {
                                                    e.setCancelled(true);
                                                }
                                            } catch (NullPointerException | ClassCastException ignored) {
                                            }
                                        }
                                    }
                                }
                            } catch (NoSuchFieldException | IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                        return;
                    }
                } else if (!HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
                    if ((player.hasPermission("headsplus.craft"))) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {

                            HeadCraftEvent event;
                            int amount = shift(e);
                            try {
                                event = new HeadCraftEvent(((Player) e.getWhoClicked()).getPlayer(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, HeadsPlus.getInstance().hapi.getSkullType(e.getCurrentItem()));
                            } catch (NoSuchFieldException | IllegalAccessException e1) {
                                e1.printStackTrace();
                                return;
                            }
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                e.setCurrentItem(HeadsPlus.getInstance().nms.addNBTTag(e.getCurrentItem()));
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                        return;
                    }
                }

		    }
            if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                if(e.getSlot() == 0){
                    if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                    }

                }
            } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
                if (e.getRawSlot() == 0) {
                    if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                    }
                }
            }
		} else {
		    if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                if(e.getSlot() == 0){
                    if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                    }

                }
            } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
		        if (e.getRawSlot() == 0) {
                    if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                        e.setCancelled(true);
                    }
                }
            }
		}
	}

	private int shift(InventoryClickEvent e) {
	    int amount;
        if (e.isShiftClick()) {
            int a = 0;
            if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                for (int i = 1; i <= 9; i++) {
                    if (e.getInventory().getItem(i) != null) {
                        a += e.getInventory().getItem(i).getAmount();
                    }
                }
                if (a % 2 == 0) {
                    amount = a / 2;
                } else {
                    amount = (a - 1) / 2;
                }
            } else {
                for (int i = 80; i <= 83; i++) {
                    if (e.getInventory().getItem(i) != null) {
                        a += e.getInventory().getItem(i).getAmount();
                    }
                }
                if (a % 2 == 0) {
                    amount = a / 2;
                } else {
                    amount = (a - 1) / 2;
                }
            }
        } else {
            amount = 1;
        }
        return amount;
    }
}
