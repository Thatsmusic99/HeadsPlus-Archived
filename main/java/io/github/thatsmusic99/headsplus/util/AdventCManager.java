package io.github.thatsmusic99.headsplus.util;

public enum AdventCManager {

    FIRST(1, 10, "", "", "&2[&a1st December&2]", ""),
    SECOND(2, 11, "", "", "&4[&c2nd December&4]", ""),
    THIRD(3, 12, "", "", "&2[&a3rd December&2]", ""),
    FOURTH(4, 13, "", "", "&4[&c4th December&4]", ""),
    FIFTH(5, 14, "", "", "&2[&a5th December&2]", ""),
    SIXTH(6, 15, "", "", "&4[&c6th December&4]", ""),
    SEVENTH(7, 16, "", "", "&2[&a7th December&2]", ""),
    EIGHTH(8, 19, "", "", "&4[&c8th December&4]", ""),
    NINTH(9, 20, "", "", "&2[&a9th December&2]", ""),
    TENTH(10, 21, "", "", "&4[&c10th December&4]", ""),
    ELEVENTH(11, 22, "", "", "&2[&a11th December&2]", ""),
    TWELFTH(12, 23, "", "", "&4[&c12th December&4]", ""),
    THIRTEENTH(13, 24, "", "", "&2[&a13th December&2]", ""),
    FORTEENTH(14, 25, "", ""),
    FIFTEENTH(15, 28, "", ""),
    SIXTEENTH(16, 29, "", ""),
    SEVENTEENTH(17, 30, "", ""),
    EIGHTEENTH(18, 31, "", ""),
    NINETEENTH(19, 32, "", ""),
    TWENTIETH(20, 33, "", ""),
    TWENTY_FIRST(21, 34, "", ""),
    TWENTY_SECOND(22, 39, "", ""),
    TWENTY_THIRD(23, 40, "", ""),
    TWENTY_FOURTH(24, 41, "", ""),
    TWENTY_FIFTH(25, 43, "", "");

    int date;
    int place;
    String name;
    String texture;
    String wName;
    String wTexture;

    AdventCManager(int i, int place, String s, String sr, String wName, String wTexture) {
        date = i;
        this.place = place;
        name = s;
        texture = sr;
        this.wName = wName;
        this.wTexture = wTexture;
    }

    
}
