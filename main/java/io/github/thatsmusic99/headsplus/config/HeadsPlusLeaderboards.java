package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HeadsPlusLeaderboards {

    private static FileConfiguration lb;
    private static File lbF;

    public static FileConfiguration getLeaderboards() {
        return lb;
    }
    public static void lbFileEnable() {
        reloadLeaderboards();
        loadLeaderboards(false);
    }

    public static void reloadLeaderboards() {
        boolean n = false;
        if (lbF == null) {
            lbF = new File(HeadsPlus.getInstance().getDataFolder(), "leaderboards.yml");
            n = true;
        }

        lb = YamlConfiguration.loadConfiguration(lbF);
        loadLeaderboards(n);
        saveLeaderboards();

    }
    private static void saveLeaderboards() {

        if (lb == null || lbF == null) {
            return;
        }

        try {
            lb.save(lbF);
        } catch (IOException e) {
            HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save leaderboards.yml!");
            e.printStackTrace();
        }

    }

    private static void loadLeaderboards(boolean neww) {
        try {
            getLeaderboards().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
            getLeaderboards().addDefault("server-total", 0);
            if (neww) {
                getLeaderboards().createSection("player-data");
            }
            getLeaderboards().options().copyDefaults(true);
            saveLeaderboards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPlayer(Player p, String section) {
        getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + ".total", 1);
        getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 1);
        int s = getLeaderboards().getInt("server-total");
        s++;
        getLeaderboards().set("server-total", s);
        getLeaderboards().options().copyDefaults(true);
        saveLeaderboards();
    }

    public static void addNewPlayerValue(Player p, String section) {
        getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 1);
        int s = getLeaderboards().getInt("server-total");
        s++;
        getLeaderboards().set("server-total", s);
        getLeaderboards().options().copyDefaults(true);
        saveLeaderboards();
    }

    public static void addOntoValue(Player p, String section) {
        int i = getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
        i++;
        getLeaderboards().set("player-data." + p.getUniqueId().toString() + "." + section, i);
        int is = getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + ".total");
        is++;
        getLeaderboards().set("player-data." + p.getUniqueId().toString() + ".total", is);
        int s = getLeaderboards().getInt("server-total");
        s++;
        getLeaderboards().set("server-total", s);
        getLeaderboards().options().copyDefaults(true);
        saveLeaderboards();
    }

    public static LinkedHashMap<Player, Integer> getScores(String section) {

        LinkedHashMap<Player, Integer> hs = new LinkedHashMap<>();
        for (String cs : getLeaderboards().getConfigurationSection("player-data").getKeys(false)) {
            Player p = Bukkit.getPlayer(UUID.fromString(cs));
            int i = getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
            hs.put(p, i);
        }
        hs = sortHashMapByValues(hs);
        return hs;

    }

    private static LinkedHashMap<Player, Integer> sortHashMapByValues(HashMap<Player, Integer> passedMap) {
        List<Player> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);

        LinkedHashMap<Player, Integer> sortedMap =
                new LinkedHashMap<>();

        for (int val : mapValues) {
            Iterator<Player> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Player key = keyIt.next();
                Integer comp1 = passedMap.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    public static boolean addPlayerOnFileIfNotFound(Player p, String section) {
        try {
            if (getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + ".total") != 0) {
                return true;
            } else {
                addPlayer(p, section);
                return false;
            }

        } catch (Exception ex) {
            addPlayer(p, section);
            return false;
        }
    }

    public static boolean addSectionOnFileIfNotFound(Player p, String section) {
        try {
            if (getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section) != 0) {
                getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
                return true;
            } else {
                addNewPlayerValue(p, section);
                return false;
            }
        } catch (Exception ex) {
            addNewPlayerValue(p, section);
            return false;
        }
    }
}
