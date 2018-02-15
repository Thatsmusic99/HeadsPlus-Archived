package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MySQLAPI {

    private HeadsPlusLeaderboards hpl;
    private HeadsPlusChallenges hpc;
    private DeathEvents de;

    public MySQLAPI() {
        hpl = HeadsPlus.getInstance().hplb;
        hpc = HeadsPlus.getInstance().hpchl;
        de = HeadsPlus.getInstance().de;
    }

    private void addPlayer(Player p, String section, String database, int shAmount) {
        if (HeadsPlus.getInstance().con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("INSERT INTO `").append(database).append("` (uuid, total");
            for (EntityType e : de.ableEntities) {
                sb2.append(", ").append(e.name());
            }
            sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
            for (EntityType ignored : de.ableEntities) {
                sb2.append(", 0");
            }
            sb2.append(")");
            try {
                s.executeUpdate(sb2.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                hpl.getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + ".total", 0);
                hpl.getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 0);
                int s = hpl.getLeaderboards().getInt("server-total.total");
                s++;
                hpl.getLeaderboards().set("server-total.total", s);
                int i = hpl.getLeaderboards().getInt("server-total." + section);
                i++;
                hpl.getLeaderboards().set("server-total." + section, i);
                hpl.getLeaderboards().options().copyDefaults(true);
                hpl.saveLeaderboards();
            } else {
                if (database.equalsIgnoreCase("headsplussh")) {
                    hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".sellhead.total", 0);
                    hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".sellhead." + section, 0);
                    int s = hpc.getChallenges().getInt("server-total.sellhead.total");
                    s += shAmount;
                    hpc.getChallenges().set("server-total.sellhead.total", s);
                    int i = hpc.getChallenges().getInt("server-total.sellhead." + section);
                    i += shAmount;
                    hpc.getChallenges().set("server-total.sellhead." + section, i);
                    hpc.getChallenges().options().copyDefaults(true);
                    hpc.saveChallenges();
                } else {
                    hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".crafting.total", 0);
                    hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".crafting." + section, 0);
                    int s = hpc.getChallenges().getInt("server-total.crafting.total");
                    s += shAmount;
                    hpc.getChallenges().set("server-total.crafting.total", s);
                    int i = hpc.getChallenges().getInt("server-total.crafting." + section);
                    i += shAmount;
                    hpc.getChallenges().set("server-total.crafting." + section, i);
                    hpc.getChallenges().options().copyDefaults(true);
                    hpc.saveChallenges();
                }
            }
        }
    }

    public void addNewPlayerValue(Player p, String section, String database, int shAmount) throws SQLException {
        if (HeadsPlus.getInstance().con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            ResultSet rs;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId().toString() + "'");
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

                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId().toString() + "'");

                rs.next();

                int val = Integer.parseInt(rs.getString(section));

                val += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
                int val2;
                ResultSet rs3 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs3.next();
                val2 = Integer.parseInt(rs3.getString("total"));

                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET total='" + val2 + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");

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
                hpl.getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 0);
                int s = hpl.getLeaderboards().getInt("server-total.total");
                s++;
                hpl.getLeaderboards().set("server-total.total", s);
                int is = hpl.getLeaderboards().getInt("server-total." + section);
                is++;
                hpl.getLeaderboards().set("server-total." + section, is);
                hpl.getLeaderboards().options().copyDefaults(true);
                hpl.saveLeaderboards();
            } else if (database.equalsIgnoreCase("headsplussh")) {
                hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".sellhead." + section, 0);
                int s = hpc.getChallenges().getInt("server-total.sellhead.total");
                s += shAmount;
                hpc.getChallenges().set("server-total.sellhead.total", s);
                int is = hpc.getChallenges().getInt("server-total." + section);
                is += shAmount;
                hpc.getChallenges().set("server-total." + section, is);
                hpc.getChallenges().options().copyDefaults(true);
                hpc.saveChallenges();
            } else {
                hpc.getChallenges().addDefault("player-data." + p.getUniqueId().toString() + ".crafting." + section, 0);
                int s = hpc.getChallenges().getInt("server-total.crafting.total");
                s += shAmount;
                hpc.getChallenges().set("server-total.crafting.total", s);
                int is = hpc.getChallenges().getInt("server-total.crafting." + section);
                is += shAmount;
                hpc.getChallenges().set("server-total.crafting." + section, is);
                hpc.getChallenges().options().copyDefaults(true);
                hpc.saveChallenges();
            }

        }
    }

    public void addOntoValue(Player p, String section, String database, int shAmount) {
        if (HeadsPlus.getInstance().con) {
            try {
                Connection c = HeadsPlus.getInstance().connection;
                Statement s = c.createStatement();
                ResultSet rs;
                rs = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs.next();
                int val = Integer.parseInt(rs.getString(section));
                val += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `" + section + "`='" + val + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
                ResultSet rs3 = s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs3.next();
                int val2 = Integer.parseInt(rs3.getString("total"));
                val2 += shAmount;
                s.executeUpdate("UPDATE `" + database + "` SET `total`='" + val2 + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
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
                try {
                    addNewPlayerValue(p, section, database, shAmount);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                try {
                    int i = hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
                    i++;
                    hpl.getLeaderboards().set("player-data." + p.getUniqueId().toString() + "." + section, i);
                    int is = hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + ".total");
                    is++;
                    hpl.getLeaderboards().set("player-data." + p.getUniqueId().toString() + ".total", is);
                    int s = hpl.getLeaderboards().getInt("server-total." + section);
                    s++;
                    hpl.getLeaderboards().set("server-total." + section, s);
                    int h = hpl.getLeaderboards().getInt("server-total.total");
                    h++;
                    hpl.getLeaderboards().set("server-total.total", h);
                    hpl.getLeaderboards().options().copyDefaults(true);
                    hpl.saveLeaderboards();
                } catch (Exception e) {
                    try {
                        addNewPlayerValue(p, section, database, shAmount);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                if (database.equalsIgnoreCase("headsplussh")) {
                    try {
                        int i = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".sellhead." + section);
                        i += shAmount;
                        hpc.getChallenges().set("player-data." + p.getUniqueId().toString() + ".sellhead." + section, i);
                        int is = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".sellhead.total");
                        is += shAmount;
                        hpc.getChallenges().set("player-data." + p.getUniqueId().toString() + ".sellhead.total", is);
                        int s = hpc.getChallenges().getInt("server-total.sellhead.total");
                        s += shAmount;
                        hpc.getChallenges().set("server-total.sellhead.total", s);
                        int h = hpc.getChallenges().getInt("server-total.sellhead." + section.toUpperCase());
                        h += shAmount;
                        hpc.getChallenges().set("server-total.sellhead." + section, h);
                        hpc.challenges.options().copyDefaults(true);
                        hpc.saveChallenges();
                    } catch (Exception e) {
                        try {
                            addNewPlayerValue(p, section, database, shAmount);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    try {
                        int i = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".crafting." + section);
                        i += shAmount;
                        hpc.getChallenges().set("player-data." + p.getUniqueId().toString() + ".crafting." + section, i);
                        int is = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".crafting.total");
                        is += shAmount;
                        hpc.getChallenges().set("player-data." + p.getUniqueId().toString() + ".crafting.total", is);
                        int s = hpc.getChallenges().getInt("server-total.crafting.total");
                        s += shAmount;
                        hpc.getChallenges().set("server-total.crafting.total", s);
                        int h = hpc.getChallenges().getInt("server-total.crafting." + section.toUpperCase());
                        h += shAmount;
                        hpc.getChallenges().set("server-total.crafting." + section.toUpperCase(), h);
                        hpc.challenges.options().copyDefaults(true);
                        hpc.saveChallenges();
                    } catch (Exception e) {
                        try {
                            addNewPlayerValue(p, section, database, shAmount);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public LinkedHashMap<OfflinePlayer, Integer> getScores(String section, String database) throws SQLException {
        if (HeadsPlus.getInstance().con) {
            LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
            Connection c = HeadsPlus.getInstance().connection;
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

                    }

                }
            }
            hs = sortHashMapByValues(hs);
            return hs;
        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpl.getLeaderboards().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + (section.equalsIgnoreCase("total") ? section : section.toUpperCase()));
                    hs.put(p, i);
                }
                hs = sortHashMapByValues(hs);
                return hs;
            } else if (database.equalsIgnoreCase("headsplussh")) {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpc.getChallenges().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".sellhead." + section);
                    hs.put(p, i);
                }
                hs = sortHashMapByValues(hs);
                return hs;
            } else {
                LinkedHashMap<OfflinePlayer, Integer> hs = new LinkedHashMap<>();
                for (String cs : hpc.getChallenges().getConfigurationSection("player-data").getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(cs));
                    int i = hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".crafting." + section);
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
    public boolean addPlayerOnFileIfNotFound(Player p, String section, String database, int shAmount) {
        if (HeadsPlus.getInstance().con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

                s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId() + "'");
                return true;
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `").append(database).append("` (uuid, total");
                for (EntityType e : de.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : de.ableEntities) {
                    sb2.append(", 0");
                }
                sb2.append(")");
                try {
                    s.executeUpdate(sb2.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addOntoValue(p, section, database, shAmount);
                return false;
            }

        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                try {
                    if (hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + ".total") != 0) {
                        return true;
                    } else {
                        addPlayer(p, section, database, shAmount);
                        return false;
                    }

                } catch (Exception ex) {
                    addPlayer(p, section, database, shAmount);
                    return false;
                }
            } else if (database.equalsIgnoreCase("headsplussh")) {
                try {
                    if (hpc.getChallenges().getInt("player-data." + p.getUniqueId() + ".sellhead.total") != 0) {
                        return true;
                    } else {
                        addPlayer(p, section, database, shAmount);
                        return false;
                    }
                } catch (Exception ex) {
                    addPlayer(p, section, database, shAmount);
                    return false;
                }
            }

        }
        return false;
    }

    public boolean addSectionOnFileIfNotFound(Player p, String section, String database, int shAmount) {
        if (HeadsPlus.getInstance().con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

                s.executeQuery("SELECT * FROM `" + database + "` WHERE uuid='" + p.getUniqueId() + "'");
                return true;
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `").append(database).append("` (uuid, total");
                for (EntityType e : de.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : de.ableEntities) {
                    sb2.append(", 0");
                }
                sb2.append(")");
                try {
                    s.executeUpdate(sb2.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addOntoValue(p, section, database, shAmount);
                return false;
            }
        } else {
            if (database.equalsIgnoreCase("headspluslb")) {
                try {
                    if (hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section) != 0) {
                        hpl.getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
                        return true;
                    } else {
                        addNewPlayerValue(p, section, database, shAmount);
                        return false;
                    }
                } catch (Exception ex) {
                    try {
                        addNewPlayerValue(p, section, database, shAmount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            } else {
                if (database.equalsIgnoreCase("headsplussh")) {
                    if (hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".sellhead." + section) != 0) {
                        hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".sellhead." + section);
                        return true;
                    } else {
                        try {
                            addNewPlayerValue(p, section, database, shAmount);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                } else {
                    if (hpc.getChallenges().getInt("player-data." + p.getUniqueId() + ".crafting." + section) != 0) {
                        hpc.getChallenges().getInt("player-data." + p.getUniqueId().toString() + ".crafting." + section);
                        return true;
                    } else {
                        try {
                            addNewPlayerValue(p, section, database, shAmount);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                }
            }
        }
    }
}
