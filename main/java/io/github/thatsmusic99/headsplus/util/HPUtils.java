package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HPUtils {

    public static String getHeader(int page, int pages) {
        HeadsPlus hp = HeadsPlus.getInstance();
        return hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(3) + page + "/" + pages + " " + hp.getThemeColour(1) + "===============\n";
    }

    public static boolean switchBoolean(boolean b) {
        return !b;
    }
}
