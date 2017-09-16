package io.github.thatsmusic99.headsplus.config;

public enum HeadsXSections {

    ALPHABET("Alphabet", "&8[&6Alphabet&8]", "HP#wood_a"),
    ANIMALS("Animals", "&8[&aAnimals&8]", "HP#elephant"),
    DECORATION("Decoration", "&8[&eDecoration&8]", "HP#paper_lantern"),
    FOOD_AND_DRINK("Food-and-drink", "&8[&6Food &7and &9Drink&8]", "HP#pepsi");

    String let;
    String dn;
    String tx;

    HeadsXSections(String l, String dn, String t) {
        this.let = l;
        this.dn = dn;
        this.tx = t;
    }
}
