package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Glass;

public class ChallengeSection extends HeadInventory {
    @Override
    public String getDefaultTitle() {
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
        s[18] = new Glass();
        s[20] = new io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection.Easy();
        s[22] = new io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection.EasyMedium();
        s[24] = new io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection.Medium();
        s[26] = new Glass();
        s[27] = new Glass();
        s[30] = new io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection.MediumHard();
        s[32] = new io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection.Hard();
        s[35] = new Glass();
        s[36] = new Glass();
        s[44] = new Glass();
        s[45] = new Glass();
        s[46] = new Glass();
        s[47] = new Glass();
        s[48] = new Glass();
        s[49] = new Glass();
        s[50] = new Glass();
        s[51] = new Glass();
        s[52] = new Glass();
        s[53] = new Glass();
        return s;
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
