package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import org.bukkit.entity.Player;

import java.util.List;

public class HPPlayer {

    private Player player;
    private int xp;
    private Level level;
    private List<Challenge> completeChallenges;
    private Level nextLevel;
    private static List<HPPlayer> players;

    public HPPlayer(Player p) {
        this.player = p;
    }

    public int getXp() {
        return xp;
    }

    public Level getLevel() {
        return level;
    }

    public Level getNextLevel() {
        return nextLevel;
    }

    public List<Challenge> getCompleteChallenges() {
        return completeChallenges;
    }

    public Player getPlayer() {
        return player;
    }

    public static HPPlayer getHPPlayer(Player p) {
        for (HPPlayer hp : players) {
            if (hp.player.equals(p)) {
                return hp;
            }
        }
        return new HPPlayer(p);
    }

    public void addCompleteChallenge(Challenge c) {
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().hpchl;
        List<String> str = hpc.getConfig().getStringList("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges");
        str.add(c.getConfigName());
        hpc.getConfig().set("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges", str);
        completeChallenges.add(c);
    }

    public void addXp(int xp) {
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().hpchl;
        if (hpc.getConfig().getInt("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp") <= 0) {
            hpc.getConfig().addDefault("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp", xp);
        } else {
            int a = hpc.getConfig().getInt("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp");
            a += xp;
            hpc.getConfig().set("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp", a);
        }
        hpc.getConfig().options().copyDefaults(true);
        hpc.save();
    }
}
