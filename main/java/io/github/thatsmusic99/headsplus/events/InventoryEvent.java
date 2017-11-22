package io.github.thatsmusic99.headsplus.events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.HeadsXSections;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.AdventCManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: page " + InventoryManager.getPage() + "/" + InventoryManager.getPages())) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                HeadsPlus.getInstance().log.info("Rawr!");
                if (InventoryManager.getSection().equalsIgnoreCase("menu")) {
                    HeadsPlus.getInstance().log.info("Hm, nah.");
                    for (HeadsXSections h : HeadsXSections.values()) {
                        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', h.dn)))) {
                            InventoryManager.setSection(h.let);
                            e.getWhoClicked().openInventory(InventoryManager.changePage(false, true, (Player) e.getWhoClicked(), h.let));
                            e.setCancelled(true);
                            return;
                        }
                    }
                    InventoryManager.setSection("advent_calender");
                    e.getWhoClicked().openInventory(InventoryManager.changePage(false, true, (Player) e.getWhoClicked(), "advent_calender"));
                    e.setCancelled(true);
                    return;
                } else if (InventoryManager.getSection().equalsIgnoreCase("advent_calender")) {
                    if (!(month == 10)) { // TODO Change for testing
                        e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getChristmasDeniedMessage()));
                        e.setCancelled(true);
                        return;
                    }
                    for (AdventCManager acm : AdventCManager.values()) {
                        if (e.getSlot() == acm.place) {
                            HeadsPlus.getInstance().log.info("Pong!");
                            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1 >= acm.date) {
                                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', acm.wName)))) {
                                    ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                                    SkullMeta sm = (SkullMeta) s.getItemMeta();
                                    GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
                                    gm.getProperties().put("textures", new Property("texture", acm.texture));


                                    Field profileField = null;
                                    try {
                                        profileField = sm.getClass().getDeclaredField("profile");
                                    } catch (NoSuchFieldException | SecurityException ex) {
                                        ex.printStackTrace();
                                    }
                                    profileField.setAccessible(true);
                                    try {
                                        profileField.set(sm, gm);
                                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                                        ex.printStackTrace();
                                    }
                                    sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.name));

                                    s.setItemMeta(sm);
                                    e.getClickedInventory().setItem(e.getSlot(), s);

                                    HeadsPlusConfigHeadsX.getHeadsX().getStringList("advent." + acm.name()).add((e.getWhoClicked().getUniqueId().toString()));
                                    HeadsPlusConfigHeadsX.reloadHeadsX();
                                    e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
                                    e.setCancelled(true);
                                    return;
                                } else {
                                    HeadsPlus.getInstance().log.info(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                                    HeadsPlus.getInstance().log.info(ChatColor.stripColor(acm.wName));
                                    if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                                        e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("full-inv")));
                                        e.setCancelled(true);
                                        return;
                                    }
                                    e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                    e.setCancelled(true);
                                    return;
                                }
                            } else {
                                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getChristmasDeniedMessage()));
                                e.setCancelled(true);
                                return;
                            }

                        }
                    }
                }
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
                        lore.add(ChatColor.GREEN + "Total sections: " + InventoryManager.getSections());
                        lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance(((OfflinePlayer) e.getWhoClicked()).getPlayer()));
                        lore.add(ChatColor.GREEN + "Current section: " + InventoryManager.getSection());
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
                lore.add(ChatColor.GREEN + "Total sections: " + InventoryManager.getSections()) ;
                lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance((Player) e.getWhoClicked()));
                lore.add(ChatColor.GREEN + "Current section: " + InventoryManager.getSection());
                im.setLore(lore);
                i.setItemMeta(im);
                e.getInventory().setItem(4, i);
                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                e.setCancelled(true);
            } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(true, false, (Player) e.getWhoClicked(), InventoryManager.getSection()));
                } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(InventoryManager.changePage(false, false, (Player) e.getWhoClicked(), InventoryManager.getSection()));
                } else {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    InventoryManager.setSection("Menu");
                    e.getWhoClicked().openInventory(InventoryManager.changePage(false, true, (Player) e.getWhoClicked(), "Menu"));

                }
            } else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("[Stats]")) {
                    e.setCancelled(true);
                }
            } else if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                e.setCancelled(true);
            }
        }
    }
}
