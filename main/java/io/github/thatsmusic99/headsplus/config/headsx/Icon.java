package io.github.thatsmusic99.headsplus.config.headsx;

import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public interface Icon {

    String getIconName();

    void onClick(Player p, InventoryManager im, InventoryClickEvent e);

    int getDefaultPosition();

    Material getDefaultMaterial();

    List<String> getDefaultLore();

    String getDefaultDisplayName();

}
