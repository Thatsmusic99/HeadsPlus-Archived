package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Head extends ItemStack implements Icon {

    private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
    @Override
    public String getIconName() {
        return "head";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {
        if (e.getClick().isRightClick()) {
            HPPlayer hpp = HPPlayer.getHPPlayer(p);
            String id = HeadsPlus.getInstance().getNMS().getId(e.getCurrentItem());
            ItemMeta im2 = e.getCurrentItem().getItemMeta();
            List<String> s = im2.getLore();
            if (hpp.hasHeadFavourited(id)) {
                hpp.removeFavourite(id);
                s.remove(s.size() - 1);
            } else {
                hpp.addFavourite(id);
                for (String r : getLore()) {

                }
            }
            im2.setLore(s);
            e.getCurrentItem().setItemMeta(im2);
            e.getInventory().setItem(e.getSlot(), e.getCurrentItem());
            e.setCancelled(true);
        } else {
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(hpc.getString("full-inv"));
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getLore() != null) {
                Economy ef = HeadsPlus.getInstance().getEconomy();
                Double price = HeadsPlus.getInstance().getNMS().getPrice(e.getCurrentItem());
                if (price > ef.getBalance(p)) {
                    p.sendMessage(hpc.getString("not-enough-money"));
                    return;
                }
                EconomyResponse er = HeadsPlus.getInstance().getEconomy().withdrawPlayer(p, price);
                String success = hpc.getString("buy-success").replaceAll("\\{price}", Double.toString(er.amount)).replaceAll("\\{balance}", Double.toString(er.balance));
                String fail = hpc.getString("cmd-fail");
                if (er.transactionSuccess()) {
                    p.sendMessage(success);
                    p.getInventory().addItem(e.getCurrentItem());
                    e.setCancelled(true);
                    return;
                } else {
                    p.sendMessage(fail + ": " + er.errorMessage);
                    e.setCancelled(true);
                    return;
                }
            }
            p.getInventory().addItem(e.getCurrentItem());
            e.setCancelled(true);
        }
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
