package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;
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
        s[0] = new Favourites();
        s[1] = new Glass();
        s[2] = new Glass();
        s[3] = new Glass();
        s[4] = new Stats();
        s[5] = new Glass();
        s[6] = new Glass();
        s[7] = new Glass();
        s[8] = new Glass();
        s[9] = new Search();
        s[10] = new Head();
        s[11] = new Head();
        s[12] = new Head();
        s[13] = new Head();
        s[14] = new Head();
        s[15] = new Head();
        s[16] = new Head();
        s[17] = new Glass();
        s[18] = new Glass();
        s[19] = new Head();
        s[20] = new Head();
        s[21] = new Head();
        s[22] = new Head();
        s[23] = new Head();
        s[24] = new Head();
        s[25] = new Head();
        s[26] = new Glass();
        s[27] = new Glass();
        s[28] = new Head();
        s[29] = new Head();
        s[30] = new Head();
        s[31] = new Head();
        s[32] = new Head();
        s[33] = new Head();
        s[34] = new Head();
        s[35] = new Glass();
        s[36] = new Glass();
        s[37] = new Head();
        s[38] = new Head();
        s[39] = new Head();
        s[40] = new Head();
        s[41] = new Head();
        s[42] = new Head();
        s[43] = new Head();
        s[44] = new Glass();
        s[45] = new Glass();
        s[46] = new Glass();
        s[47] = new Glass();
        s[48] = new Back();
        s[49] = new Close();
        s[50] = new Next();
        s[51] = new Glass();
        s[52] = new Glass();
        s[53] = new Glass();

        return s;
    }

    @Override
    public String getDefaultId() {
        return "menu";
    }
}
