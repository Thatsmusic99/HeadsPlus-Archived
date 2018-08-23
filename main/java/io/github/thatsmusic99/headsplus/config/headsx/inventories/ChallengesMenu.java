package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Air;
import io.github.thatsmusic99.headsplus.config.headsx.icons.ChallengeSection;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Glass;

public class ChallengesMenu extends HeadInventory {
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
        s[10] = new Air();
        s[11] = new Air();
        s[12] = new Air();
        s[13] = new Air();
        s[14] = new Air();
        s[15] = new Air();
        s[16] = new Air();
        s[17] = new Glass();
        s[18] = new Glass();
        s[20] = new ChallengeSection.Easy();
        s[21] = new Air();
        s[22] = new ChallengeSection.EasyMedium();
        s[23] = new Air();
        s[24] = new ChallengeSection.Medium();
        s[25] = new Air();
        s[26] = new Glass();
        s[27] = new Glass();
        s[28] = new Air();
        s[29] = new Air();
        s[30] = new ChallengeSection.MediumHard();
        s[31] = new Air();
        s[32] = new ChallengeSection.Hard();
        s[35] = new Glass();
        s[36] = new Glass();
        s[37] = new Air();
        s[38] = new Air();
        s[39] = new Air();
        s[40] = new Air();
        s[41] = new Air();
        s[42] = new Air();
        s[43] = new Air();
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
        return "challenges-menu";
    }

    @Override
    public String getName() {
        return "challenges-menu";
    }
}
