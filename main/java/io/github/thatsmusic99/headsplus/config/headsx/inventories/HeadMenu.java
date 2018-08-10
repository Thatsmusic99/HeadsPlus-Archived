package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Glass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HeadMenu extends HeadInventory {

    private Inventory inventory;


    @Override
    protected String getDefaultTitle() {
        return null;
    }

    @Override
    public Icon[] getDefaultItems() {
        Icon[] s = new Icon[54];
        s[0] = new Glass();
        s[1] = new Glass();
        s[2] = new Glass();
        s[3] = new Glass();
        s[4] = new Glass();
        s[5] = new Glass();
        s[6] = new Glass();
        s[7] = new Glass();
        s[8] = new Glass();
        s[9] = new Glass();
        s[17] = new Glass();
        return s;
    }

    @Override
    public String getDefaultId() {
        return null;
    }
}
