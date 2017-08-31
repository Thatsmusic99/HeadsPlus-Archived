package io.github.thatsmusic99.headsplus.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static Inventory create(int slots, String name) {
        return Bukkit.createInventory(null, slots, name);
    }
}
