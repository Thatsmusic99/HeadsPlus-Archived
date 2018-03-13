package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ProfileCommand implements IHeadsPlusCommand {

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

    @Override
    public String getCmdName() {
        return "profile";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.profile";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descProfile();
    }

    @Override
    public String getSubCommand() {
        return "Profile";
    }

    @Override
    public String getUsage() {
        return "/hp profile [Player]";
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender cs) {
        OfflinePlayer p;
        if (args.length == 1) {
            p = Bukkit.getOfflinePlayer(cs.getName());
        } else {
            p = Bukkit.getOfflinePlayer(args[1]);
        }
        if (cs instanceof Player) {
            if (cs.getName().equalsIgnoreCase(p.getName())) {
                try {
                    cs.sendMessage(prof(p));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                    if (cs.hasPermission("headsplus.maincommand.profile.others")) {
                        try {
                            cs.sendMessage(prof(p));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlus.getInstance().hpc.getConfig().getString("no-perm"))));
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

        return false;
    }
}
