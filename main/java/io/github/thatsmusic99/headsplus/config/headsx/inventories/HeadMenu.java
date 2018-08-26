package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;
import io.github.thatsmusic99.headsplus.config.headsx.icons.HeadSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HeadMenu extends HeadInventory {

    @Override
    public String getDefaultTitle() {
        return "HeadsPlus Head Selector: {page}/{pages}";
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
        s[8] = new Search();
        s[9] = new Glass();
        s[10] = new HeadSection();
        s[11] = new HeadSection();
        s[12] = new HeadSection();
        s[13] = new HeadSection();
        s[14] = new HeadSection();
        s[15] = new HeadSection();
        s[16] = new HeadSection();
        s[17] = new Glass();
        s[18] = new Glass();
        s[19] = new HeadSection();
        s[20] = new HeadSection();
        s[21] = new HeadSection();
        s[22] = new HeadSection();
        s[23] = new HeadSection();
        s[24] = new HeadSection();
        s[25] = new HeadSection();
        s[26] = new Glass();
        s[27] = new Glass();
        s[28] = new HeadSection();
        s[29] = new HeadSection();
        s[30] = new HeadSection();
        s[31] = new HeadSection();
        s[32] = new HeadSection();
        s[33] = new HeadSection();
        s[34] = new HeadSection();
        s[35] = new Glass();
        s[36] = new Glass();
        s[37] = new HeadSection();
        s[38] = new HeadSection();
        s[39] = new HeadSection();
        s[40] = new HeadSection();
        s[41] = new HeadSection();
        s[42] = new HeadSection();
        s[43] = new HeadSection();
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

    @Override
    public String getName() {
        return "headmenu";
    }
}
