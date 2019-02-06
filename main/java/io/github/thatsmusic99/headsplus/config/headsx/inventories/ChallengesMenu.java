package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Air;
import io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Close;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Glass;

public class ChallengesMenu extends HeadInventory {
    @Override
    public String getDefaultTitle() {
        return "HeadsPlus Challenges: Menu";
    }

    @Override
    public String getDefaultItems() {
        return  "GGGGGGGGG" +
                "GAAAAAAAG" +
                "GAEARAZAG" +
                "GAAVABAAG" +
                "GAAAAAAAG" +
                "GGGGXGGGG";
    }

    @Override
    public String getDefaultId() {
        return "challenges-menu";
    }

    @Override
    public String getName() {
        return "challenges-menu";
    }
}
