package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ProfileCommand {

    public void getProfile(OfflinePlayer p, CommandSender cs) {
        if (cs.hasPermission("headsplus.profile.display")) {
            if (cs instanceof Player) {
                if (cs.getName().equalsIgnoreCase(p.getName())) {
                    try {
                        cs.sendMessage(prof(p));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (cs.hasPermission("headsplus.profile.display.others")) {
                        try {
                            cs.sendMessage(prof(p));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                try {
                    if (cs.getName().equalsIgnoreCase(p.getName())) {
                        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlus.getInstance().hpc.getConfig().getString("cant-view-data"))));
                    } else {
                        cs.sendMessage(prof(p));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
          }

    private String prof(OfflinePlayer p) throws SQLException {
        try {
            return String.valueOf(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Player: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + p.getName() +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "XP: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + HeadsPlus.getInstance().hpchl.getConfig().getInt("player-data." + p.getUniqueId().toString() + ".profile.xp") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Completed challenges: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + HeadsPlus.getInstance().hpchl.getConfig().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").size() +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads dropped: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, "total", "headspluslb") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads sold: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, "total", "headsplussh") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads crafted: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + HeadsPlus.getInstance().hapi.getPlayerInLeaderboards(p, "total", "headspluscraft");

        } catch (NullPointerException ex) {
            return HeadsPlus.getInstance().hpc.getConfig().getString("no-data"); // TODO translations
        }
    }
}
