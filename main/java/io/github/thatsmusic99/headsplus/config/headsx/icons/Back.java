package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Back extends ItemStack implements Icon {
    @Override
    public String getIconName() {
        return "back";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {
        e.setCancelled(true);
        p.closeInventory();
        try {
            p.openInventory(im.changePage(false, false, p, im.getSection()));
        } catch (NoSuchFieldException | IllegalAccessException e1) {
            new DebugPrint(e1, "Changing page (back)", false, p);
        }
    }

    @Override
    public Material getDefaultMaterial() {
        return Material.ARROW;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultDisplayName() {
        return "&a&lNext";
    }

    @Override
    public List<String> getLore() {
        System.out.println("icons." + getIconName() + ".lore");
        return HeadsPlus.getInstance().getItems().getConfig().getStringList("icons." + getIconName() + ".lore");
    }
}
