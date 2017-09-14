package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: page " + InventoryManager.getPage() + "/" + InventoryManager.getPages())) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("full-inv")));
                    e.setCancelled(true);
                    return;
                }
                if (e.getCurrentItem().getItemMeta().getLore() != null) {
                    Double price;
                    try {
                        price = Double.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[1]));
                    } catch (NumberFormatException ex) {
                        HeadsPlus.getInstance().log.log(Level.SEVERE, "[HeadsPlus] HeadsX.yml fault! Please check your config, and make sure the price value for your heads are set to a double value, or 'Free' or 'default'!");
                        HeadsPlus.getInstance().log.log(Level.SEVERE, "Value: " + e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[1]);
                        String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("buy-fail"));
                        fail = ChatColor.translateAlternateColorCodes('&', fail);
                        e.getWhoClicked().sendMessage(fail);
                        e.setCancelled(true);
                        return;
                    }
                    EconomyResponse er = HeadsPlus.getInstance().econ.withdrawPlayer((OfflinePlayer) e.getWhoClicked(), price);
                    String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("buy-success")).replaceAll("%l", Double.toString(er.amount)).replaceAll("%b", Double.toString(er.balance));
                    success = ChatColor.translateAlternateColorCodes('&', success);
                    String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("buy-fail"));
                    fail = ChatColor.translateAlternateColorCodes('&', fail);
                    if (er.transactionSuccess()) {
                        e.getWhoClicked().sendMessage(success);
                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                        ItemStack i = new ItemStack(Material.PAPER);
                        ItemMeta im = i.getItemMeta();
                        im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
                        List<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GREEN + "Total heads: " + InventoryManager.getHeads());
                        lore.add(ChatColor.GREEN + "Total pages: " + InventoryManager.getPages());
                        lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance((Player) e.getWhoClicked()));
                        im.setLore(lore);
                        i.setItemMeta(im);
                        e.getInventory().setItem(4, i);
                        e.setCancelled(true);
                        return;
                    } else {
                        e.getWhoClicked().sendMessage(fail + ": " + er.errorMessage);
                        e.setCancelled(true);
                        return;
                    }
                }
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta im = i.getItemMeta();
                im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "Total heads: " + InventoryManager.getHeads());
                lore.add(ChatColor.GREEN + "Total pages: " + InventoryManager.getPages());
                lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance((Player) e.getWhoClicked()));
                im.setLore(lore);
                i.setItemMeta(im);
                e.getInventory().setItem(4, i);
                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                e.setCancelled(true);
            } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(true, false, (Player) e.getWhoClicked()));
                } else {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(false, false, (Player) e.getWhoClicked()));
                }
            } else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("[Stats]")) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
