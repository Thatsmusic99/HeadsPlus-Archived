package io.github.thatsmusic99.headsplus.crafting;

import java.util.List;

import io.github.thatsmusic99.headsplus.api.HeadCraftEvent;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
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
	    try {
            if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                Player player = (Player) e.getWhoClicked();
                HeadsPlus hp = HeadsPlus.getInstance();
                NMSManager nms = hp.getNMS();
                HeadsPlusAPI hapi = hp.getAPI();
                HeadsPlusMainConfig c = hp.getConfiguration();
                Material m = nms.getSkullMaterial(1).getType();
                if (c.getPerks().getBoolean("craft-heads")) {
                    if (player.hasPermission("headsplus.craft")) {
                        List<String> worlds = c.getBlacklist("world").getStringList("list");
                        if ((!worlds.contains(player.getWorld().getName())) || !c.getBlacklist("world").getBoolean("enabled") || player.hasPermission("headsplus.bypass.blacklistw")) {
                            if (c.getWhitelist("world").getStringList("list").contains(player.getWorld().getName())) {
                                HeadCraftEvent event;
                                int amount = shift(e);
                                event = new HeadCraftEvent(player, e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, hapi.getSkullType(e.getCurrentItem()));
                                Bukkit.getServer().getPluginManager().callEvent(event);
                                if (!event.isCancelled()) {
                                    e.setCurrentItem(nms.addNBTTag(e.getCurrentItem()));
                                    e.setCurrentItem(nms.setType(hapi.getSkullType(e.getCurrentItem()), e.getCurrentItem()));
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                                return;

                            } else if (player.hasPermission("headsplus.bypass.whitelistw")){
                                if (e.getCurrentItem() != null) {
                                    if (e.getCurrentItem().getItemMeta() instanceof SkullMeta) {
                                        if (hapi.getSkullType(e.getCurrentItem()) != null) {
                                            try {
                                                HeadCraftEvent event;
                                                    int amount = shift(e);
                                                    event = new HeadCraftEvent(player, e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, hapi.getSkullType(e.getCurrentItem()));
                                                    Bukkit.getServer().getPluginManager().callEvent(event);
                                                    if (!event.isCancelled()) {
                                                        e.setCurrentItem(nms.addNBTTag(e.getCurrentItem()));
                                                        e.setCurrentItem(nms.setType(hapi.getSkullType(e.getCurrentItem()), e.getCurrentItem()));
                                                        return;
                                                    } else {
                                                        e.setCancelled(true);
                                                    }
                                                } catch (NullPointerException | ClassCastException ignored) {

                                                }
                                            }
                                        }
                                    }

                                return;

                            } else if (!c.getWhitelist("world").getBoolean("enabled")) {
                                HeadCraftEvent event;
                                int amount = shift(e);
                                event = new HeadCraftEvent(player, e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, hapi.getSkullType(e.getCurrentItem()));
                                Bukkit.getServer().getPluginManager().callEvent(event);
                                if (!event.isCancelled()) {
                                    e.setCurrentItem(nms.addNBTTag(e.getCurrentItem()));
                                    e.setCurrentItem(nms.setType(hapi.getSkullType(e.getCurrentItem()), e.getCurrentItem()));
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                                return;
                            }
                        } else {
                            if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                                if(e.getSlot() == 0){
                                    if(e.getCurrentItem().getType() == m){
                                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                                        e.setCancelled(true);
                                    }

                                }
                            } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
                                if (e.getRawSlot() == 0) {
                                    if(e.getCurrentItem().getType() == m){
                                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                                        e.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                }
                if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                    if(e.getSlot() == 0){
                        if(e.getCurrentItem().getType() == m){
                            e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                            e.setCancelled(true);
                        }

                    }
                } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
                    if (e.getRawSlot() == 0) {
                        if(e.getCurrentItem().getType() == m){
                            e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new DebugPrint(ex, "Event (RecipeCheckers)", false, null);
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
