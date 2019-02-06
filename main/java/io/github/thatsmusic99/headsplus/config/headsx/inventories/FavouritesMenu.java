package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;

public class FavouritesMenu extends HeadInventory {

    @Override
    public String getDefaultTitle() {
        return "HeadsPlus Head selector: {page}/{pages}";
    }

    @Override
    public String getDefaultItems() {
        return  "GGGGSGGGK" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "GHHHHHHHG" +
                "MGGBXNGGG";
    }

    @Override
    public String getDefaultId() {
        return "favourites";
    }

    @Override
    public String getName() {
        return "favourites";
    }
}
