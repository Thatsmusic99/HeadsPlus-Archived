package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;

public class HeadSection extends HeadInventory
{
    @Override
    public String getDefaultTitle() {
        return "HeadsPlus Head Selector: {page}/{pages}";
    }

    @Override
    public String getDefaultItems() {
        return  "FGGGSGGGK" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "MGGBXNGGG";
    }

    @Override
    public String getDefaultId() {
        return "section:{section}";
    }

    @Override
    public String getName() {
        return "headsection";
    }
}
