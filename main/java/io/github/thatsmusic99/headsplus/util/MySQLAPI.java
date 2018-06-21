package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
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
                hpl.getConfig().addDefault("player-data." + uuid + "." + section, 0);
                int s = hpl.getConfig().getInt("server-total.total");
                s++;
                hpl.getConfig().set("server-total.total", s);
                int is = hpl.getConfig().getInt("server-total." + section);
                is++;
                hpl.getConfig().set("server-total." + section, is);
                hpl.getConfig().options().copyDefaults(true);
                hpl.save();
            } else if (database.equalsIgnoreCase("headsplussh")) {
                hpc.getConfig().addDefault("player-data." + uuid + ".sellhead." + section, 0);
                int s = hpc.getConfig().getInt("server-total.sellhead.total");
                s += shAmount;
                hpc.getConfig().set("server-total.sellhead.total", s);
                int is = hpc.getConfig().getInt("server-total." + section);
                is += shAmount;
                hpc.getConfig().set("server-total." + section, is);
                hpc.getConfig().options().copyDefaults(true);
                hpc.save();
            } else {
                try {
                    hpc.getConfig().addDefault("player-data." + uuid + ".crafting." + section, 0);
                    int s = hpc.getConfig().getInt("server-total.crafting.total");
                    s += shAmount;
                    hpc.getConfig().set("server-total.crafting.total", s);
                    int is = hpc.getConfig().getInt("server-total.crafting." + section);
                    is += shAmount;
                    hpc.getConfig().set("server-total.crafting." + section, is);
                    hpc.getConfig().options().copyDefaults(true);
                    hpc.save();
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
                    int i = hpl.getConfig().getInt("player-data." + uuid + "." + section);
                    i++;
                    hpl.getConfig().set("player-data." + uuid + "." + section, i);
                    int is = hpl.getConfig().getInt("player-data." + uuid+ ".total");
                    is++;
                    hpl.getConfig().set("player-data." + uuid + ".total", is);
                    int s = hpl.getConfig().getInt("server-total." + section);
                    s++;
                    hpl.getConfig().set("server-total." + section, s);
                    int h = hpl.getConfig().getInt("server-total.total");
                    h++;
                    hpl.getConfig().set("server-total.total", h);
                    hpl.getConfig().options().copyDefaults(true);
                    hpl.save();
                } catch (Exception e) {
                    addNewPlayerValue(p, section, database, shAmount);
                }
            } else {
                if (database.equalsIgnoreCase("headsplussh")) {
                    try {
                        FileConfiguration ch = hpc.getConfig();
                        int i = ch.getInt("player-data." + uuid + ".sellhead." + section);
                        i += shAmount;
                        ch.set("player-data." + uuid + ".sellhead." + section, i);
                        int is = ch.getInt("player-data." + uuid + ".sellhead.total");
                        is += shAmount;
                        ch.set("player-data." + uuid + ".sellhead.total", is);
                        int s = hpc.getConfig().getInt("server-total.sellhead.total");
                        s += shAmount;
                        hpc.getConfig().set("server-total.sellhead.total", s);
                        int h = hpc.getConfig().getInt("server-total.sellhead." + section.toUpperCase());
                        h += shAmount;
                        hpc.getConfig().set("server-total.sellhead." + section, h);
                        hpc.getConfig().options().copyDefaults(true);
                        hpc.save();
                    } catch (Exception e) {
                        addNewPlayerValue(p, section, database, shAmount);
                    }
                } else {
                    try {
                        int i = hpc.getConfig().getInt("player-data." + uuid + ".crafting." + section);
                        i += shAmount;
                        hpc.getConfig().set("player-data." + uuid + ".crafting." + section, i);
                        int is = hpc.getConfig().getInt("player-data." + uuid + ".crafting.total");
                        is += shAmount;
                        hpc.getConfig().set("player-data." + uuid + ".crafting.total", is);
                        int s = hpc.getConfig().getInt("server-total.crafting.total");
                        s += shAmount;
                        hpc.getConfig().set("server-total.crafting.total", s);
                        int h = hpc.getConfig().getInt("server-total.crafting." + section.toUpperCase());
                        h += shAmount;
                        hpc.getConfig().set("server-total.crafting." + section.toUpperCase(), h);
                        hpc.getConfig().options().copyDefaults(true);
                        hpc.save();
                    } catch (Exception e) {
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
            if (database.equalsIgnoreCase("headspluslb")) {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpl.getConfig().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpl.getConfig().getInt("player-data." + p.getUniqueId().toString() + "." + (section.equalsIgnoreCase("total") ? section : section.toUpperCase()));
                    hs.put(p, i);
                }
                hs = sortHashMapByValues(hs);
                return hs;
            } else if (database.equalsIgnoreCase("headsplussh")) {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpc.getConfig().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpc.getConfig().getInt("player-data." + p.getUniqueId().toString() + ".sellhead." + section);
                    hs.put(p, i);
                }
                hs = sortHashMapByValues(hs);
                return hs;
            } else {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpc.getConfig().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpc.getConfig().getInt("player-data." + p.getUniqueId().toString() + ".crafting." + section);
                    hs.put(p, i);
                }
                hs = sortHashMapByValues(hs);
                return hs;
            }
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
}
