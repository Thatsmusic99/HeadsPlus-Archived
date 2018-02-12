package io.github.thatsmusic99.headsplus.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum HeadsPlusChallengeEnums {

    STARTER("starter", "Starter", "&8[&6&lStarter Challenge&8]", Arrays.asList("&7Don't worry, just", "&7try this out ;)"), 0, HeadsPlusChallengeTypes.MISC, HPChallengeRewardTypes.ECO, 50, 0, HeadsPlusChallengeDifficulty.EASY, "", 20, 0.0),
    FIRST_HEADS_SOLD("first_heads_sold", "First Heads Sold", "&8[&a&lFirst Heads sold&8]", Arrays.asList("&7Sell your first", "&7ever 5 heads!"), 5, HeadsPlusChallengeTypes.SELLHEAD, HPChallengeRewardTypes.ECO, 100, 0, HeadsPlusChallengeDifficulty.EASY, "total", 50, 0.0),
    BEGINNING_CRAFTER("beginning_crafter", "Beginning Crafter", "&8[&e&lBeginning Crafter&8]", Arrays.asList("&7Craft your first", "&73 heads!"), 3, HeadsPlusChallengeTypes.CRAFTING, HPChallengeRewardTypes.ECO, 100, 0, HeadsPlusChallengeDifficulty.EASY, "total", 50, 0.0),
    HUNTING_STARTER("hunting_starter", "Hunting Starter", "&8[&c&lHunting Starter&8]", Arrays.asList("&7Kill mobs until you", "&7get 5 heads!"), 5, HeadsPlusChallengeTypes.LEADERBOARD, HPChallengeRewardTypes.ECO, 100, 0, HeadsPlusChallengeDifficulty.EASY, "total", 50, 0.0),
    NEW_PLAYER_HUNTER("new_player_hunter", "New Player Hunter", "&8[&c&lNew Player Hunter&8]", Arrays.asList("&7Kill a player and", "&7get their head! :o"), 1, HeadsPlusChallengeTypes.LEADERBOARD, HPChallengeRewardTypes.ECO, 150, 0, HeadsPlusChallengeDifficulty.EASY, "player", 200, 0.0),
    MOB_HUNTER("mob_hunter", "Mob Hunter", "&8[&c&lMob Hunter&8]", Arrays.asList("&7Get at least 10 heads", "&7From killing zombies!"), 10, HeadsPlusChallengeTypes.LEADERBOARD, HPChallengeRewardTypes.ECO, 200, 0, HeadsPlusChallengeDifficulty.EASY, "zombie", 150, 0.0),
    PROFIT("profit", "Profit", "&8[&a&lProfit!&8]", Collections.singletonList("Sell a total of 15 heads!"), 15, HeadsPlusChallengeTypes.SELLHEAD, HPChallengeRewardTypes.ECO, 200, 0, HeadsPlusChallengeDifficulty.EASY, "total");

    public String n;
    public String dName;
    public String h;
    public List<String> d;
    public int m;
    public HeadsPlusChallengeTypes p;
    public HPChallengeRewardTypes r;
    public HeadsPlusChallengeDifficulty cd;
    public Object o;
    public int a;
    public String t;
    public int exp;
    public double v;

    HeadsPlusChallengeEnums(String name, String n, String header, List<String> desc, int min, HeadsPlusChallengeTypes hpct, HPChallengeRewardTypes hpcrt, Object o, int amount, HeadsPlusChallengeDifficulty difficulty, String type, int exp, double v) {
        this.n = name;
        this.h = header;
        this.d = desc;
        this.m = min;
        this.p = hpct;
        this.r = hpcrt;
        this.o = o;
        this.a = amount;
        this.cd = difficulty;
        this.t = type;
        this.exp = exp;
        this.dName = n;
        this.v = v;
    }

}
