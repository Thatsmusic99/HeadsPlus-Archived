package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import io.github.thatsmusic99.headsplus.listeners.DeathEvents;
import io.github.thatsmusic99.headsplus.storage.PlayerScores;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MySQLAPI {

    private final HeadsPlusLeaderboards hpl;
    private final HeadsPlusChallenges hpc;
    private final DeathEvents de;
    private final HeadsPlus hp;

    public MySQLAPI() {
        hp = HeadsPlus.getInstance();
        hpl = hp.getLeaderboardsConfig();
        hpc = hp.getChallengeConfig();
        de = hp.getDeathEvents();
        if (hpc.getConfig().get("player-data") instanceof ConfigurationSection) {
            HeadsPlus.getInstance().getLogger().info("Old storage detected! Transfering data (this will be saved when the server stops)...");
            transferScoresToJSON();
        }
    }

    private void addNewPlayerValue(Player p, String section, String database, int shAmount) throws SQLException {
        String uuid = p.getUniqueId().toString();
        if (hp.isConnectedToMySQLDatabase()) {
            Connection c = hp.getConnection();
            Statement s;
            ResultSet rs;
            s = c.createStatement();

            try {
                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + uuid + "'");
                Integer.parseInt(rs.getString(section)); // I don't care if it's ignored
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `").append(database).append("` (uuid, total");
                for (EntityType e : de.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : de.ableEntities) {
                    sb2.append(", '0'");
                }
                sb2.append(");");

                s.executeUpdate(sb2.toString());

                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + uuid + "'");
                rs.next();
                int val = Integer.parseInt(rs.getString(section));

                val += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val + "' WHERE `uuid`='" + uuid + "'");
                int val2;
                ResultSet rs3 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + uuid + "'");
                rs3.next();
                val2 = Integer.parseInt(rs3.getString("total"));

                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET total='" + val2 + "' WHERE `uuid`='" + uuid + "'");

                ResultSet rs4 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='server-total'");

                rs4.next();

                int val3 = Integer.parseInt(rs4.getString(section));
                val3++;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val3 + "' WHERE `uuid`='server-total'");

                ResultSet rs2;
                rs2 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='server-total'");

                rs2.next();

                val2 = Integer.parseInt(rs2.getString("total"));

                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET total='" + val2 + "' WHERE `uuid`='server-total'");

            }
        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                HeadsPlus.getInstance().getScores().setPlayerTotal(uuid, section, database, 0);
                HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, 1);
            } else if (database.equalsIgnoreCase("headsplussh")) {
                HeadsPlus.getInstance().getScores().setPlayerTotal(uuid, section, database, 0);
                HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, shAmount);
            } else {
                try {
                    HeadsPlus.getInstance().getScores().setPlayerTotal(uuid, section, database, 0);
                    HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, shAmount);
                } catch (IllegalArgumentException ignored) { // Idk why the hell this happens????

                }

            }

        }
    }

    public void addOntoValue(Player p, String section, String database, int shAmount) throws SQLException {
        String uuid = p.getUniqueId().toString();
        if (hp.isConnectedToMySQLDatabase()) {
            try {

                Connection c = hp.getConnection();
                Statement s = c.createStatement();
                ResultSet rs;
                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + uuid + "'");
                rs.next();
                int val = Integer.parseInt(rs.getString(section));
                val += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val + "' WHERE `uuid`='" + uuid + "'");
                ResultSet rs3 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + uuid + "'");
                rs3.next();
                int val2 = Integer.parseInt(rs3.getString("total"));
                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `total`='" + val2 + "' WHERE `uuid`='" + uuid + "'");
                ResultSet rs2;
                rs2 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='server-total'");
                rs2.next();
                int val3 = Integer.parseInt(rs2.getString(section));
                val3 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val3 + "' WHERE `uuid`='server-total'");
                ResultSet rs4 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='server-total'");
                rs4.next();
                val2 = Integer.parseInt(rs4.getString("total"));
                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `total`='" + val2 + "' WHERE `uuid`='server-total'");
            } catch (SQLException e) {
                addNewPlayerValue(p, section, database, shAmount);
            }

        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                try {
                    HeadsPlus.getInstance().getScores().addPlayerTotal(uuid, section.toUpperCase(), database, 1);
                    HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    addNewPlayerValue(p, section, database, shAmount);
                }
            } else {
                if (database.equalsIgnoreCase("headsplussh")) {
                    try {
                        HeadsPlus.getInstance().getScores().addPlayerTotal(uuid, section.toUpperCase(), database, shAmount);
                        HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, shAmount);
                    } catch (Exception e) {
                        addNewPlayerValue(p, section, database, shAmount);
                    }
                } else {
                    try {
                        HeadsPlus.getInstance().getScores().addPlayerTotal(uuid, section.toUpperCase(), database, shAmount);
                        HeadsPlus.getInstance().getScores().addPlayerTotal("server-total", section.toUpperCase(), database, shAmount);
                    } catch (Exception e) {
                        e.printStackTrace();
                        addNewPlayerValue(p, section, database, shAmount);
                    }
                }
            }
        }
    }

    public LinkedHashMap<OfflinePlayer, Integer> getScores(String section, String database) throws SQLException {
        if (hp.isConnectedToMySQLDatabase()) {
            LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
            Connection c = hp.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `" + database + "` ORDER BY id");
            while (rs.next()) {

                boolean player = false;
                UUID uuid = null;
                OfflinePlayer name;
                try {
                    uuid = UUID.fromString(rs.getString("uuid"));
                    player = true;
                } catch (Exception ex) {
                    //
                }
                if (player) {
                    name = Bukkit.getOfflinePlayer(uuid);
                    Statement st = c.createStatement();
                    ResultSet rs2 = st.executeQuery("SELECT * FROM `" + database + "` WHERE `uuid`='" + name.getUniqueId().toString() + "'");
                    rs2.next();
                    try {
                        hs.put(name, Integer.valueOf(rs2.getString(section)));
                    } catch (NumberFormatException ex) {
                        //
                    }

                }
            }
            hs = sortHashMapByValues(hs);
            return hs;
        } else {
            PlayerScores scores = HeadsPlus.getInstance().getScores();
            LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
            for (Object cs : scores.getJSON().keySet()) {
                if (String.valueOf(cs).equalsIgnoreCase("server-total")) continue;
                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(String.valueOf(cs)));
                int i = HeadsPlus.getInstance().getScores().getPlayerTotal(String.valueOf(cs), (section.equalsIgnoreCase("total") || section.equalsIgnoreCase("player") ? section : section.toUpperCase()), database);
                hs.put(p, i);
            }
            hs = sortHashMapByValues(hs);
            return hs;

        }

    }

    private LinkedHashMap<OfflinePlayer, Integer> sortHashMapByValues(HashMap<OfflinePlayer, Integer> passedMap) {
        List<OfflinePlayer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);

        LinkedHashMap<OfflinePlayer, Integer> sortedMap =
                new LinkedHashMap<>();

        for (int val : mapValues) {
            Iterator<OfflinePlayer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                OfflinePlayer key = keyIt.next();
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

    private void transferScoresToJSON() {
        PlayerScores scores = HeadsPlus.getInstance().getScores();
        for (String uuid : hpc.getConfig().getConfigurationSection("player-data").getKeys(false)) {
            for (String database : hpc.getConfig().getConfigurationSection("player-data." + uuid).getKeys(false)) {
                if (database.equalsIgnoreCase("sellhead") || database.equalsIgnoreCase("crafting")) {
                    for (String section : hpc.getConfig().getConfigurationSection("player-data." + uuid + "." + database).getKeys(false)) {

                        scores.setPlayerTotal(uuid, section, database,
                                hpc.getConfig().getInt("player-data." + uuid + "." + database + "." + section));


                    }
                }
            }
            scores.setCompletedChallenges(uuid, hpc.getConfig().getStringList("player-data." + uuid + ".completed-challenges"));
            scores.setXp(uuid, hpc.getConfig().getInt("player-data." + uuid + ".profile.xp"));
            scores.setLevel(uuid, hpc.getConfig().getString("player-data." + uuid + ".profile.level"));
        }
        for (String uuid : hpl.getConfig().getConfigurationSection("player-data").getKeys(false)) {
            for (String section : hpl.getConfig().getConfigurationSection("player-data." + uuid).getKeys(false)) {
                HeadsPlus.getInstance().getScores().setPlayerTotal(uuid, section, "headspluslb",
                        hpl.getConfig().getInt("player-data." + uuid + "." + section));
            }
        }
        for (String section : hpl.getConfig().getConfigurationSection("server-total").getKeys(false)) {
            HeadsPlus.getInstance().getScores().setPlayerTotal("server-total", section, "headspluslb",
                    hpl.getConfig().getInt("server-total." + section));
        }
        for (String database : hpc.getConfig().getConfigurationSection("server-total").getKeys(false)) {
            for (String section : hpc.getConfig().getConfigurationSection("server-total." +  database).getKeys(false)) {
                HeadsPlus.getInstance().getScores().setPlayerTotal("server-total", section, database,
                        hpc.getConfig().getInt("server-total." + database + section));
            }
        }

        hpc.getConfig().set("player-data", null);
        hpl.getConfig().set("player-data", null);
        hpc.getConfig().set("server-total", null);
        hpl.getConfig().set("server-total", null);
        hpl.selfDestruct();
        hpc.getConfig().options().copyDefaults(true);
        hpc.save();
    }
}
