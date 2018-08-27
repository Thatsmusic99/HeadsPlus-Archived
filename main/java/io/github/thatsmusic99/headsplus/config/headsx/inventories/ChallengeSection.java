package io.github.thatsmusic99.headsplus.config.headsx.inventories;

import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.*;

public class ChallengeSection extends HeadInventory {

    @Override
    public String getDefaultTitle() {
        return "HeadsPlus Challenges: {section}";
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
        s[10] = new Challenge();
        s[11] = new Challenge();
        s[12] = new Challenge();
        s[13] = new Challenge();
        s[14] = new Challenge();
        s[15] = new Challenge();
        s[16] = new Challenge();
        s[17] = new Glass();
        s[18] = new Glass();
        s[19] = new Challenge();
        s[20] = new Challenge();
        s[21] = new Challenge();
        s[22] = new Challenge();
        s[23] = new Challenge();
        s[24] = new Challenge();
        s[25] = new Challenge();
        s[26] = new Glass();
        s[27] = new Glass();
        s[28] = new Challenge();
        s[29] = new Challenge();
        s[30] = new Challenge();
        s[31] = new Challenge();
        s[32] = new Challenge();
        s[33] = new Challenge();
        s[34] = new Challenge();
        s[35] = new Glass();
        s[36] = new Glass();
        s[37] = new Challenge();
        s[38] = new Challenge();
        s[39] = new Challenge();
        s[40] = new Challenge();
        s[41] = new Challenge();
        s[42] = new Challenge();
        s[43] = new Challenge();
        s[44] = new Glass();
        s[45] = new Menu();
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
        return "challenges:{section}";
    }

    @Override
    public String getName() {
        return "challenge-section";
    }
}
