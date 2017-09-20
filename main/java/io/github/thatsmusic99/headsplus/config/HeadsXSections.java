package io.github.thatsmusic99.headsplus.config;

public enum HeadsXSections {

    ALPHABET("alphabet", "&8[&6Alphabet&8]", "HP#wood_a"),
    ANIMALS("animals", "&8[&aAnimals&8]", "HP#elephant"),
    DECORATION("decoration", "&8[&eDecoration&8]", "HP#paper_lantern"),
    FOOD_AND_DRINK("food_and_drink", "&8[&6Food &7and &9Drink&8]", "HP#pepsi"),
    LOGOS("logos", "&8[&cLogos&8]", "HP#youtube");

    public String let;
    public String dn;
    public String tx;

    HeadsXSections(String l, String dn, String t) {
        this.let = l;
        this.dn = dn;
        this.tx = t;
    }
}
