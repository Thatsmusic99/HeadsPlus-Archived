package io.github.thatsmusic99.headsplus.crafting;

import java.util.List;

import io.github.thatsmusic99.headsplus.api.events.HeadCraftEvent;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.reflection.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                HeadsPlusAPI hapi = hp.getAPI();
                HeadsPlusMainConfig c = hp.getConfiguration();
                if (c.getPerks().craft_heads) {
                    if (player.hasPermission("headsplus.craft")) {
                        if ((!c.getBlacklist().enabled || !c.getBlacklist().list.contains(player.getWorld().getName())) || player.hasPermission("headsplus.bypass.blacklistw")) {
                            if (e.getCurrentItem() != null) {
                                if (e.getCurrentItem().getItemMeta() instanceof SkullMeta) {
                                    if (!hapi.getSkullType(e.getCurrentItem()).isEmpty()) {
                                        if (c.getWhitelist().list.contains(player.getWorld().getName())) {
                                            fireEvent(e);
                                            return;

                                        } else if (player.hasPermission("headsplus.bypass.whitelistw")){
                                            try {
                                                fireEvent(e);
                                            } catch (NullPointerException | ClassCastException ignored) {
                                            }
                                        } else if (!c.getWhitelist().enabled) {
                                            fireEvent(e);
                                            return;
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    } else {
                        if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                            denyPermission(e);
                        } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
                            denyPermission(e);
                        }
                    }
                }
                if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                    denyPermission(e);
                } else if (e.getInventory().getType().equals(InventoryType.CRAFTING)){
                    denyPermission(e);
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

    private void fireEvent(InventoryClickEvent e) {
        HeadsPlus hp = HeadsPlus.getInstance();
        HeadCraftEvent event;
        NBTManager nbt = hp.getNBTManager();
        HeadsPlusAPI hapi = hp.getAPI();
        int amount = shift(e);
        event = new HeadCraftEvent((Player) e.getWhoClicked(), e.getCurrentItem(), e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation(), amount, hapi.getSkullType(e.getCurrentItem()));
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            e.setCurrentItem(nbt.makeSellable(e.getCurrentItem()));
            e.setCurrentItem(nbt.setType(e.getCurrentItem(), hapi.getSkullType(e.getCurrentItem())));
        } else {
            e.setCancelled(true);
        }
    }

    private void denyPermission(InventoryClickEvent e) {
        if(e.getRawSlot() == 0){
            if(!HeadsPlus.getInstance().getNBTManager().getType(e.getCurrentItem()).isEmpty()){
                e.getWhoClicked().sendMessage(ChatColor.RED + "You can not craft heads!");
                e.setCancelled(true);
            }
        }
    }
}
