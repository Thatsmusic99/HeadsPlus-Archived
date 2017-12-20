package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class HeadsPlusLeaderboards {

    private static FileConfiguration lb;
    private static File lbF;

    public static FileConfiguration getLeaderboards() {
        return lb;
    }
    public static void lbFileEnable() {
        reloadLeaderboards();
    }

    public static void reloadLeaderboards() {
        boolean n = false;
        if (lbF == null) {
            lbF = new File(HeadsPlus.getInstance().getDataFolder(), "leaderboards.yml");
            n = true;
        }
        lb = YamlConfiguration.loadConfiguration(lbF);
        if (n) {
            loadLeaderboards();
        }
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

    private static void loadLeaderboards() {
        try {
            getLeaderboards().options().header("HeadsPlus by Thatsmusic99 - Config wiki: https://github.com/Thatsmusic99/HeadsPlus/wiki/Configuration");
            getLeaderboards().addDefault("server-total", 0);
            getLeaderboards().createSection("player-data");
            getLeaderboards().options().copyDefaults(true);
            saveLeaderboards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPlayer(Player p, String section) {
        if (HeadsPlus.con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("INSERT INTO `headspluslb` (uuid, total");
            for (EntityType e : DeathEvents.ableEntities) {
                sb2.append(", ").append(e.name());
            }
            sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
            for (EntityType ignored : DeathEvents.ableEntities) {
                sb2.append(", 0");
            }
            sb2.append(")");
            try {
                s.executeUpdate(sb2.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + ".total", 1);
            getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 1);
            int s = getLeaderboards().getInt("server-total");
            s++;
            getLeaderboards().set("server-total", s);
            getLeaderboards().options().copyDefaults(true);
            saveLeaderboards();
        }
    }

    public static void addNewPlayerValue(Player p, String section) throws SQLException {
        if (HeadsPlus.con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            ResultSet rs = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId().toString() + "'");
                Integer.parseInt(rs.getString(section));
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `headspluslb` (uuid, total");
                for (EntityType e : DeathEvents.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : DeathEvents.ableEntities) {
                    sb2.append(", '0'");
                }
                sb2.append(");");

                s.executeUpdate(sb2.toString());

                rs = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId().toString() + "'");

                rs.next();

                int val = Integer.parseInt(rs.getString(section));

                val++;
                s.executeUpdate("UPDATE `headspluslb` SET `" + section + "`='" + val + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
                int val2;
                ResultSet rs3 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs3.next();
                val2 = Integer.parseInt(rs3.getString("total"));

                val2++;
                s.executeUpdate("UPDATE `headspluslb` SET total='" + val2 + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");

                ResultSet rs4 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='server-total'");

                rs4.next();

                int val3 = Integer.parseInt(rs4.getString(section));
                val3++;
                s.executeUpdate("UPDATE `headspluslb` SET `" + section + "`='" + val3 + "' WHERE `uuid`='server-total'");

                ResultSet rs2;
                rs2 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='server-total'");

                rs2.next();

                val2 = Integer.parseInt(rs2.getString("total"));

                val2++;
                s.executeUpdate("UPDATE `headspluslb` SET total='" + val2 + "' WHERE `uuid`='server-total'");

            }
        } else {
            getLeaderboards().addDefault("player-data." + p.getUniqueId().toString() + "." + section, 1);
            int s = getLeaderboards().getInt("server-total");
            s++;
            getLeaderboards().set("server-total", s);
            getLeaderboards().options().copyDefaults(true);
            saveLeaderboards();
        }
    }

    public static void addOntoValue(Player p, String section) {
        if (HeadsPlus.con) {
            try {
                Connection c = HeadsPlus.getInstance().connection;
                Statement s = c.createStatement();
                ResultSet rs;
                rs = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs.next();
                int val = Integer.parseInt(rs.getString(section));
                val++;
                s.executeUpdate("UPDATE `headspluslb` SET `" + section + "`='" + val + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
                ResultSet rs3 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId().toString() + "'");
                rs3.next();
                int val2 = Integer.parseInt(rs3.getString("total"));
                val2++;
                s.executeUpdate("UPDATE `headspluslb` SET `total`='" + val2 + "' WHERE `uuid`='" + p.getUniqueId().toString() + "'");
                ResultSet rs2;
                rs2 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='server-total'");
                rs2.next();
                int val3 = Integer.parseInt(rs2.getString(section));
                val3++;
                s.executeUpdate("UPDATE `headspluslb` SET `" + section + "`='" + val3 + "' WHERE `uuid`='server-total'");
                ResultSet rs4 = s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='server-total'");
                rs4.next();
                val2 = Integer.parseInt(rs4.getString("total"));
                val2++;
                s.executeUpdate("UPDATE `headspluslb` SET `total`='" + val2 + "' WHERE `uuid`='server-total'");
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    addNewPlayerValue(p, section);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } else {
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
        if (HeadsPlus.con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

                s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId() + "'");
                return true;
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `headspluslb` (uuid, total");
                for (EntityType e : DeathEvents.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : DeathEvents.ableEntities) {
                    sb2.append(", 0");
                }
                sb2.append(")");
                try {
                    s.executeUpdate(sb2.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addOntoValue(p, section);
                return false;
            }

        } else {
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
    }

    public static boolean addSectionOnFileIfNotFound(Player p, String section) {
        if (HeadsPlus.con) {
            Connection c = HeadsPlus.getInstance().connection;
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

                s.executeQuery("SELECT * FROM `headspluslb` WHERE uuid='" + p.getUniqueId() + "'");
                return true;
            } catch (SQLException ex) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO `headspluslb` (uuid, total");
                for (EntityType e : DeathEvents.ableEntities) {
                    sb2.append(", ").append(e.name());
                }
                sb2.append(") VALUES('").append(p.getUniqueId().toString()).append("', '0'");
                for (EntityType ignored : DeathEvents.ableEntities) {
                    sb2.append(", 0");
                }
                sb2.append(")");
                try {
                    s.executeUpdate(sb2.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addOntoValue(p, section);
                return false;
            }
        } else {
            try {
                if (getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section) != 0) {
                    getLeaderboards().getInt("player-data." + p.getUniqueId().toString() + "." + section);
                    return true;
                } else {
                    addNewPlayerValue(p, section);
                    return false;
                }
            } catch (Exception ex) {
                try {
                    addNewPlayerValue(p, section);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
    }
}
