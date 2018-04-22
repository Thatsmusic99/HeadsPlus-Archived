package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HPPlayer {

    private OfflinePlayer player;
    private int xp;
    private Level level;
    private List<Challenge> completeChallenges;
    private Level nextLevel;
    public static List<HPPlayer> players = new ArrayList<>();

    public HPPlayer(OfflinePlayer p) {
        this.player = p;
        HeadsPlusChallenges hpchl = HeadsPlus.getInstance().getChallengeConfig();
        HeadsPlusAPI hapi = HeadsPlus.getInstance().getAPI();
        HashMap<Integer, Level> levels = HeadsPlus.getInstance().getLevels();
        this.xp = hpchl.getConfig().getInt("player-data." + getPlayer().getUniqueId().toString() + ".profile.xp");
        List<Challenge> sc = new ArrayList<>();
        for (String str : hpchl.getConfig().getStringList("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges")) {
            sc.add(hapi.getChallengeByConfigName(str));
        }
        if (hpchl.getConfig().getString("player-data." + getPlayer().getUniqueId().toString() + ".profile.level") == null) {
            for (int i = levels.size() - 1; i > 0; i--) {
                if (levels.get(i).getRequiredXP() <= getXp()) {
                    level = levels.get(i);
                    try {
                        nextLevel = levels.get(i + 1);
                    } catch (IndexOutOfBoundsException e) { // End of levels
                        nextLevel = null;
                    }
                    break;
                }
            }
        } else {
            for (int i = levels.size() - 1; i > 0; i--) {
                if (levels.get(i).getConfigName().equals(hpchl.getConfig().getString("player-data." + getPlayer().getUniqueId().toString() + ".profile.level"))) {
                    level = levels.get(i);
                    try {
                        nextLevel = levels.get(i + 1);
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
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().getChallengeConfig();
        List<String> str = hpc.getConfig().getStringList("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges");
        str.add(c.getConfigName());
        hpc.getConfig().set("player-data." + getPlayer().getUniqueId().toString() + ".completed-challenges", str);
        completeChallenges.add(c);
    }

    public void addXp(int xp) {
        HeadsPlusChallenges hpc = HeadsPlus.getInstance().getChallengeConfig();
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
                    p.sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("level-up")
                            .replaceAll("%p", this.getPlayer().getName())
                            .replaceAll("%lvl", ChatColor.translateAlternateColorCodes('&', level.getDisplayName())));
                }
                HashMap<Integer, Level> levels = HeadsPlus.getInstance().getLevels();
                hpc.getConfig().set("player-data." + getPlayer().getUniqueId().toString() + ".profile.level", level.getConfigName());
                for (int i = 1; i < levels.size(); i++) {
                    if (levels.get(i) == level) {
                        try {
                            nextLevel = levels.get(i + 1);
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
