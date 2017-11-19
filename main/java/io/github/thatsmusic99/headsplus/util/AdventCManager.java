package io.github.thatsmusic99.headsplus.util;

public enum AdventCManager {

    FIRST(1, 10, "&7[&9Present&7]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19", "&2[&a1st December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I3ZTU4OTEzOGJiOGU3Y2FiYWJjMjNiNjJkMzEyYTM2OWNjMzRiN2Y0Y2E0MTU0ZDg3YjJkZTEwMWE4YzRkIn19fQ"),
    SECOND(2, 11, "", "", "&4[&c2nd December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTM4N2YwNWY4NTM0MWNlM2NmNGIzNWUzYTNiMzljNGE2N2Q5NmEyMjBlMzk1NDM5YzM1ZjQ4YTM2OWQzNWZhIn19fQ"),
    THIRD(3, 12, "", "", "&2[&a3rd December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ3ZmRhNTk0NzQxOGY3OTE0NzVhMWE0ZGU0YTQ3ZDUzOWZhNmJkNWY0MjhhM2UzZDQ4Yjg2MjRhZDE1NTcifX19"),
    FOURTH(4, 13, "", "", "&4[&c4th December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E0ZTdjZGQzZjg3YzNmNDRlMWE2ZGY3OGYxZmZiNzU0MmEyYjBlMzM2YThiN2UyN2UxMWE5ZTY4YTdmODk5OSJ9fX0"),
    FIFTH(5, 14, "", "", "&2[&a5th December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1ODVkNzg0MWQwNWY4OTM3NTYzMTU2NGZjYTc3NGIzZWRlZmU4NmFmMmI2YWUzOWYzYmFlY2ViYjU0OSJ9fX0"),
    SIXTH(6, 15, "", "", "&4[&c6th December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1NWI0YzM2NTMxOTNiMmJhNjgzOTJlMjgwYWQ3MjE0N2Q4ZWFiMWIxYTQ4Y2Y5N2ZlOWFjZjQyNDI2MzgifX19"),
    SEVENTH(7, 16, "", "", "&2[&a7th December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU1YzRlNTJkZTNhMzhjZDg3YmNmZjQ4NjY4YTU1Y2JiYWU1MDZjOWE5N2U1ZDhkZjZmNTk3NWU3NDQyNyJ9fX0"),
    EIGHTH(8, 19, "", "", "&4[&c8th December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjEwMzM2NjdjZmYzYzdmZDkxZmM5OTU1MmE0MjgxZTc3ZjhhOWRiYTRjNzlkNzA0MWIyNDdkZDJjYThhMyJ9fX0"),
    NINTH(9, 20, "", "", "&2[&a9th December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU5YWRhMzE2MjNhODdmOGQ0YTBmOTkzNzBhYjYwMTAzZGJmZmYzNWE1OTE3ODk5OTg0ZTBhMGZiODlkZmZlIn19fQ"),
    TENTH(10, 21, "", "", "&4[&c10th December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE5YTJiNWExOTZmZDBlNzIxZWU1MTlmZmM0YmVmNTFhMGFhY2I5OGExNDY0NmRkYmFjN2ZiNWI3ZjI3ZjIifX19"),
    ELEVENTH(11, 22, "", "", "&2[&a11th December&2]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1ZGQwNDZlMGQ1MDk0OTQyYjgwN2IzYTNhNzU5OWIyMjc4ODc2NjcxMzcxODNjZThmYmYxOTBjZWMyNjEifX19"),
    TWELFTH(12, 23, "", "", "&4[&c12th December&4]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI5ZWQxYTQzYjMyNGY4NWFlNTlkN2ZhZmMzNGE5MTFjNWJhYmM1ZWRkZmZjOTZiZWJiYWNjMzVjYjlhZjc2ZSJ9fX0"),
    THIRTEENTH(13, 24, "", "", "&2[&a13th December&2]", ""),
    FOURTEENTH(14, 25, "", "", "&4[&c14th December&4]", ""),
    FIFTEENTH(15, 28, "", "", "&2[&a15th December&2]", ""),
    SIXTEENTH(16, 29, "", "", "&4[&c16th December&4]", ""),
    SEVENTEENTH(17, 30, "", "", "&2[&a17th December&2]", ""),
    EIGHTEENTH(18, 31, "", "", "&4[&c18th December&4]", ""),
    NINETEENTH(19, 32, "", "", "&2[&a19th December&2]", ""),
    TWENTIETH(20, 33, "", "", "&4[&c20th December&4]", ""),
    TWENTY_FIRST(21, 34, "", "", "&2[&a21st December&2]", ""),
    TWENTY_SECOND(22, 39, "", "", "&4[&c22nd December&4]", ""),
    TWENTY_THIRD(23, 40, "", "", "&2[&a23rd December&2]", ""),
    TWENTY_FOURTH(24, 41, "", "", "&4[&c24th December&4]", ""),
    TWENTY_FIFTH(25, 43, "", "", "&2[&a&l?&2]", "");

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
