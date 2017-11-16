package io.github.thatsmusic99.headsplus.util;

public enum AdventCManager {

    FIRST(1, 10, "", ""),
    SECOND(2, 11, "", ""),
    THIRD(3, 12, "", ""),
    FOURTH(4, 13, "", ""),
    FIFTH(5, 14, "", ""),
    SIXTH(6, 15, "", ""),
    SEVENTH(7, 16, "", ""),
    EIGHTH(8, 19, "", ""),
    NINTH(9, 20, "", ""),
    TENTH(10, 21, "", ""),
    ELEVENTH(11, 22, "", ""),
    TWELFTH(12, 23, "", ""),
    THIRTEENTH(13, 24, "", ""),
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

    AdventCManager(int i, int place, String s, String sr) {
        date = i;
        this.place = place;
        name = s;
        texture = sr;
    }

    
}
