package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeDifficulty;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsXSections;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import io.github.thatsmusic99.headsplus.util.InventoryManager;

import io.github.thatsmusic99.headsplus.nms.v1_12_NMS.SearchGUI1_12;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.logging.Level;

public class InventoryEvent implements Listener {

    private InventoryManager im;
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        try {
            if (!(e.getWhoClicked() instanceof Player)) return;
            Player p = (Player) e.getWhoClicked();
            if (InventoryManager.getIM(p) == null) return;
            im = InventoryManager.getIM(p);
            // int month = Calendar.getInstance().get(Calendar.MONTH);
            if (e.getInventory().getName().equalsIgnoreCase("HeadsPlus Head selector: " + im.getPage() + "/" + im.getPages())) {
                try {
                    if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                        e.setCancelled(true);
                        e.getWhoClicked().closeInventory();
                    } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                        if (im.getSection().equalsIgnoreCase("menu")) {
                            for (HeadsXSections h : HeadsXSections.values()) {
                                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', h.dn)))) {
                                    im.setSection(h.let);
                                    e.getWhoClicked().openInventory(im.changePage(false, true, p, h.let));
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                            im.setSection("advent_calender");
                            e.getWhoClicked().openInventory(im.changePage(false, true, p, "advent_calender"));
                            e.setCancelled(true);
                            return;
                        } /* else if (InventoryManager.getSection().equalsIgnoreCase("advent_calender")) {
                        if (!(month == 11)) { // TODO Change for testing
                            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getChristmasDeniedMessage()));
                            e.setCancelled(true);
                            return;
                        }
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date d = new Date();
                        String st = dateFormat.format(d);
                        int day = Integer.parseInt(st.split(" ")[0].split("/")[2]);
                        for (AdventCManager acm : AdventCManager.values()) {
                            if (e.getSlot() == acm.place) {
                                if (day >= acm.date) {
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

                                        List<String> list = HeadsPlusConfigHeadsX.getHeadsX().getStringList("advent." + acm.name());
                                        list.add(e.getWhoClicked().getUniqueId().toString());
                                        HeadsPlusConfigHeadsX.getHeadsX().set("advent." + acm.name(), list);
                                        HeadsPlusConfigHeadsX.saveHeadsX();
                                        if (!Bukkit.getVersion().contains("1.8")) {
                                            e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
                                        }
                                        e.setCancelled(true);
                                        return;
                                    } else {
                                        if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                                            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getConfig().getString("full-inv")));
                                            e.setCancelled(true);
                                            return;
                                        }
                                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                        e.setCancelled(true);
                                        return;
                                    }
                                } else {
                                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getConfig().getString("xmas-denied")));
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                        e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getConfig().getString("xmas-denied")));
                        e.setCancelled(true);
                        return;
                    } */
                        if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                            e.getWhoClicked().sendMessage(hpc.getString("full-inv"));
                            e.setCancelled(true);
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta().getLore() != null) {
                            Double price;
                            try {
                                price = Double.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[1]));
                            } catch (NumberFormatException ex) {
                                HeadsPlus.getInstance().getLogger().log(Level.SEVERE, "[HeadsPlus] HeadsX.yml fault! Please check your config, and make sure the price value for your heads are set to a double value, or 'Free' or 'default'!");
                                HeadsPlus.getInstance().getLogger().log(Level.SEVERE, "Value: " + e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[1]);
                                String fail = hpc.getString("cmd-fail");
                                e.getWhoClicked().sendMessage(fail);
                                e.setCancelled(true);
                                return;
                            }
                            EconomyResponse er = HeadsPlus.getInstance().getEconomy().withdrawPlayer(p, price);
                            String success = hpc.getString("buy-success").replaceAll("%l", Double.toString(er.amount)).replaceAll("%b", Double.toString(er.balance));
                            String fail = hpc.getString("cmd-fail");
                            if (er.transactionSuccess()) {
                                e.getWhoClicked().sendMessage(success);
                                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                ItemStack i = new ItemStack(Material.PAPER);
                                ItemMeta im = i.getItemMeta();
                                im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
                                List<String> lore = new ArrayList<>();
                                lore.add(ChatColor.GREEN + "Total heads: " + this.im.getHeads());
                                lore.add(ChatColor.GREEN + "Total pages: " + this.im.getPages());
                                lore.add(ChatColor.GREEN + "Total sections: " + this.im.getSections());
                                if (HeadsPlus.getInstance().econ()) {
                                    lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().getEconomy().getBalance(p));
                                }
                                lore.add(ChatColor.GREEN + "Current section: " + this.im.getSection());
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
                        lore.add(ChatColor.GREEN + "Total heads: " + this.im.getHeads());
                        lore.add(ChatColor.GREEN + "Total pages: " + this.im.getPages());
                        lore.add(ChatColor.GREEN + "Total sections: " + this.im.getSections());
                        if (HeadsPlus.getInstance().econ()) {
                            lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().getEconomy().getBalance(p));
                        }
                        lore.add(ChatColor.GREEN + "Current section: " + this.im.getSection());
                        im.setLore(lore);
                        i.setItemMeta(im);
                        e.getInventory().setItem(4, i);
                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().openInventory(this.im.changePage(true, false, p, this.im.getSection()));
                        } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back")) {
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().openInventory(this.im.changePage(false, false, p, this.im.getSection()));
                        } else {
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();
                            this.im.setSection("Menu");
                            e.getWhoClicked().openInventory(this.im.changePage(false, true, p, "Menu"));

                        }
                    } else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("[Stats]")) {
                            e.setCancelled(true);
                        }
                    } else if (e.getCurrentItem().getType().equals(Material.NAME_TAG)) {
                        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("[Search Heads]")) {
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();
                            final InventoryClickEvent ev = e;
                            SearchGUI s = HeadsPlus.getInstance().getNMS().getSearchGUI(p, event -> {
                                try {
                                    if (event.getSlot().equals(SearchGUI1_12.AnvilSlot.OUTPUT)) {
                                        event.setWillClose(false);
                                        event.setWillDestroy(false);
                                        im.setSection("search:" + event.getName());
                                        event.getPlayer().closeInventory();
                                        event.getPlayer().openInventory(im.changePage(false, true, event.getPlayer(), "search:" + event.getName()));
                                    }
                                } catch (Exception ex) {
                                    new DebugPrint(ex, "Event (InventoryEvent)", false, null);
                                }

                                ev.setCancelled(true);
                            });
                            s.setSlot(SearchGUI1_12.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));
                            s.open();
                        }
                    }

                    else if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                        e.setCancelled(true);
                    }
                } catch (NullPointerException ex) {
                    e.setCancelled(true);
                }
            } else if (im.getType().equalsIgnoreCase("chal")) {
                if (e.getInventory().getName().startsWith("HeadsPlus")) {
                    try {
                        if (im.getSection().equalsIgnoreCase("menu")) {
                            ItemStack i = e.getCurrentItem();
                            if (i.getType().equals(Material.STAINED_CLAY)) {
                                for (HeadsPlusChallengeDifficulty hpcd : HeadsPlusChallengeDifficulty.values()) {
                                    if (i.getDurability() == hpcd.color.ordinal()) {
                                        e.setCancelled(true);
                                        e.getWhoClicked().closeInventory();
                                        im.setSection(hpcd.key);
                                        e.getWhoClicked().openInventory(this.im.changePage(false, true, p, this.im.getSection()));
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        } else {
                            if (im != null) {
                                if (e.getCurrentItem().getType().equals(Material.STAINED_CLAY)) {
                                    Challenge challenge = HeadsPlus.getInstance().getAPI().getChallenge(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                                    try {
                                        if (challenge != null) {
                                            if (!challenge.isComplete(p)) {
                                                if (challenge.canComplete(p)) {
                                                    challenge.complete(p, e.getInventory(), e.getSlot());
                                                } else {
                                                    p.sendMessage(hpc.getString("cant-complete-challenge"));
                                                }
                                            } else {
                                                p.sendMessage(hpc.getString("already-complete-challenge"));
                                            }
                                        }
                                    }catch (NullPointerException ignored) {
                                    }
                                } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                                    if (!im.getSection().equalsIgnoreCase("menu")) {
                                        e.setCancelled(true);
                                        e.getWhoClicked().closeInventory();
                                        im.setSection("Menu");
                                        e.getWhoClicked().openInventory(im.changePage(false, true, p, im.getSection()));
                                    }
                                }
                                e.setCancelled(true);
                            }
                        }
                    } catch (NullPointerException ex) {
                        e.setCancelled(true);
                    }
                }
            }
        } catch (Exception ex) {
            new DebugPrint(ex, "Event (InventoryInteractEvent)", false, null);
        }
    }
}
