package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Menu extends ItemStack implements Icon {
    @Override
    public String getIconName() {
        return "main_menu";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {

    }

    @Override
    public int getDefaultPosition() {
        return 0;
    }

    @Override
    public Material getDefaultMaterial() {
        return null;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return null;
    }
}
