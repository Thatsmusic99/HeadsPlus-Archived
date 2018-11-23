package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.util.AdventCManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Head extends ItemStack implements Icon {

    private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
    @Override
    public String getIconName() {
        return "head";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {
        NMSManager nms = HeadsPlus.getInstance().getNMS();
        if (im.getSection().equalsIgnoreCase("advent-calendar")) {
            if (nms.isOpen(e.getCurrentItem())) {
                giveHead(p, e);
                return;
            } else {
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                int day   = localDate.getDayOfMonth();
                if (!(month == 12)) { // TODO Change for testing
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', LocaleManager.getLocale().getChristmasDeniedMessage()));
                    e.setCancelled(true);
                    return;
                }
                AdventCManager a = nms.getCalendarValue(e.getCurrentItem());
                if (day >= a.date) {
                    HeadsPlusConfigHeadsX config = HeadsPlus.getInstance().getHeadsXConfig();
                    ItemStack open = nms.setOpen(e.getCurrentItem(), true);
                    ItemMeta meta = open.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', a.name));
                    open.setItemMeta(meta);
                    try {
                        open = HeadsPlus.getInstance().getHeadsXConfig().setTexture(a.texture, open);
                    } catch (IllegalAccessException | NoSuchFieldException e1) {
                        e1.printStackTrace();
                    }
                    e.setCurrentItem(open);
                    e.setCancelled(true);
                    HeadsPlus.getInstance().getHeadsXConfig().addChristmasHype();
                    List<String> list = config.getConfig().getStringList("advent-18." + a.name());
                    list.add(e.getWhoClicked().getUniqueId().toString());
                    config.getConfig().set("advent-18." + a.name(), list);
                    config.save();
                    return;
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getMessagesConfig().getString("xmas-denied")));
                    e.setCancelled(true);
                    return;
                }


            }
        }
        if (e.getClick().isRightClick()) {
            HPPlayer hpp = HPPlayer.getHPPlayer(p);
            String id = HeadsPlus.getInstance().getNMS().getId(e.getCurrentItem());
            ItemMeta im2 = e.getCurrentItem().getItemMeta();
            List<String> s = new ArrayList<>();
            if (hpp.hasHeadFavourited(id)) {
                hpp.removeFavourite(id);
                for (String r : getLore()) {
                    s.add(ChatColor.translateAlternateColorCodes('&', r.replaceAll("\\{price}", String.valueOf(nms.getPrice(e.getCurrentItem())))
                            .replaceAll("\\{favourite}", HPPlayer.getHPPlayer(p).hasHeadFavourited(nms.getId(e.getCurrentItem())) ? ChatColor.GOLD + "Favourite!" : "")));

                }
            } else {
                hpp.addFavourite(id);
                for (String r : getLore()) {
                    s.add(ChatColor.translateAlternateColorCodes('&', r.replaceAll("\\{price}", String.valueOf(nms.getPrice(e.getCurrentItem())))
                            .replaceAll("\\{favourite}", HPPlayer.getHPPlayer(p).hasHeadFavourited(nms.getId(e.getCurrentItem())) ? ChatColor.GOLD + "Favourite!" : "")));

                }
            }
            im2.setLore(s);
            e.getCurrentItem().setItemMeta(im2);
            e.getInventory().setItem(e.getSlot(), e.getCurrentItem());
            e.setCancelled(true);
        } else {
            giveHead(p, e);
        }
    }

    private void giveHead(Player p, InventoryClickEvent e) {
        NMSManager nms = HeadsPlus.getInstance().getNMS();
        ItemStack i = e.getCurrentItem();
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage(hpc.getString("full-inv"));
            e.setCancelled(true);
            return;
        }
        if (nms.getPrice(e.getCurrentItem())  != 0.0 && HeadsPlus.getInstance().econ()) {

            Economy ef = HeadsPlus.getInstance().getEconomy();
            Double price = nms.getPrice(e.getCurrentItem());
            if (price > ef.getBalance(p)) {
                p.sendMessage(hpc.getString("not-enough-money"));
                return;
            }
            EconomyResponse er = ef.withdrawPlayer(p, price);
            String success = hpc.getString("buy-success").replaceAll("\\{price}", Double.toString(er.amount)).replaceAll("\\{balance}", Double.toString(er.balance));
            String fail = hpc.getString("cmd-fail");
            if (er.transactionSuccess()) {
                p.sendMessage(success);
                p.getInventory().addItem(HeadsPlus.getInstance().getNMS().removeIcon(e.getCurrentItem()));
                e.setCancelled(true);
                return;
            } else {
                p.sendMessage(fail + ": " + er.errorMessage);
                e.setCancelled(true);
                return;
            }
        }
        ItemStack is = e.getCurrentItem();
        is = nms.removeIcon(is);
        p.getInventory().addItem(is);
        e.setCancelled(true);
    }

    @Override
    public Material getDefaultMaterial() {
        return HeadsPlus.getInstance().getNMS().getSkullMaterial(1).getType();
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<>(Arrays.asList("&6[&ePrice&6] &a{price}", "{favourite}"));
    }

    @Override
    public String getDefaultDisplayName() {
        return "{head-name}";
    }

    @Override
    public Icon getReplacementIcon() {
        return new Air();
    }

    @Override
    public List<String> getLore() {
        return HeadsPlus.getInstance().getItems().getConfig().getStringList("icons." + getIconName() + ".lore");
    }
}
