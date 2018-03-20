package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HPPlayer {

    private OfflinePlayer player;
    private int xp;
    private Level level;
    private List<Challenge> completeChallenges;
    private Level nextLevel;
    private static List<HPPlayer> players = new ArrayList<>();

    public HPPlayer(OfflinePlayer p) {
        this.player = p;
        this.xp = HeadsPlus.getInstance().hpchl.getConfig().getInt("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp");
        List<Challenge> sc = new ArrayList<>();
        for (String str : HeadsPlus.getInstance().hpchl.getConfig().getStringList("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges")) {
            sc.add(HeadsPlus.getInstance().hapi.getChallengeByConfigName(str));
        }
        if (HeadsPlus.getInstance().hpchl.getConfig().getString("player-data." + getPlayer().getUniqueId().toString() + ".profile.level") == null) {
            for (int i = HeadsPlus.getInstance().levels.size() - 1; i > 0; i--) {
                if (HeadsPlus.getInstance().levels.get(i).getRequiredXP() <= getXp()) {
                    level = HeadsPlus.getInstance().levels.get(i);
                    try {
                        nextLevel = HeadsPlus.getInstance().levels.get(i + 1);
                    } catch (IndexOutOfBoundsException e) { // End of levels
                        nextLevel = null;
                    }
                    break;
                }
            }
        } else {
            for (int i = HeadsPlus.getInstance().levels.size() - 1; i > 0; i--) {
                if (HeadsPlus.getInstance().levels.get(i).getConfigName().equals(HeadsPlus.getInstance().hpchl.getConfig().getString("player-data." + getPlayer().getUniqueId().toString() + ".profile.level"))) {
                    level = HeadsPlus.getInstance().levels.get(i);
                    try {
                        nextLevel = HeadsPlus.getInstance().levels.get(i + 1);
                    } catch (IndexOutOfBoundsException e) { // End of levels
                        nextLevel = null;
                    }
                    break;
                }
            }
        }
        this.completeChallenges = sc;
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

    public OfflinePlayer getPlayer() {
        return player;
    }

    public static HPPlayer getHPPlayer(OfflinePlayer p) {
        for (HPPlayer hp : players) {
            if (hp.player.equals(p)) {
                return hp;
            }
        }
        HPPlayer pl = new HPPlayer(p);
        players.add(pl);
        return pl;
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
        this.xp += xp;
        hpc.getConfig().options().copyDefaults(true);
        hpc.save();
        if (nextLevel != null) {
            if (nextLevel.getRequiredXP() <= getXp()) {
                level = nextLevel;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().hpc.getConfig().getString("level-up")
                            .replaceAll("%p", this.getPlayer().getName())
                            .replaceAll("%lvl", level.getDisplayName())
                            .replaceAll("%h", HeadsPlus.getInstance().hpc.getConfig().getString("prefix"))));
                }
                hpc.getConfig().set("player-data." + getPlayer().getUniqueId().toString() + ".profile.level", level.getConfigName());
                for (int i = 1; i < HeadsPlus.getInstance().levels.size(); i++) {
                    if (HeadsPlus.getInstance().levels.get(i) == level) {
                        try {
                            nextLevel = HeadsPlus.getInstance().levels.get(i + 1);
                        } catch (IndexOutOfBoundsException e) { // End of levels
                            nextLevel = null;
                        }
                    }
                }
            }
        }
        hpc.getConfig().options().copyDefaults(true);
        hpc.save();
    }
}
