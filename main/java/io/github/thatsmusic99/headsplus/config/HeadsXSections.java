package io.github.thatsmusic99.headsplus.config;

public enum HeadsXSections {

    LETTERS("Letters", "HX#wood_a"),
    ;

    String let;
    String tx;

    HeadsXSections(String l, String t) {
        this.let = l;
        this.tx = t;
    }
}
