package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;

public class ChallengeSection extends HeadInventory {
    @Override
    public String getDefaultTitle() {
        return null;
    }

    @Override
    public Icon[] getDefaultItems() {
        return new Icon[0];
    }

    @Override
    public String getDefaultId() {
        return "challenges:{section}";
    }

    @Override
    public String getName() {
        return "challenge-section";
    }
}
