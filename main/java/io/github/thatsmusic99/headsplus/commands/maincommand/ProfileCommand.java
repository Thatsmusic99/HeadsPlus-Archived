package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

public class ProfileCommand implements IHeadsPlusCommand {

    private String prof(OfflinePlayer p) throws SQLException {
        try {
            HPPlayer pl = HPPlayer.getHPPlayer(p);
            HeadsPlusAPI api = HeadsPlus.getInstance().getAPI();
            return String.valueOf(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Player: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + p.getName() +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "XP: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + pl.getXp() +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Completed challenges: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + pl.getCompleteChallenges().size() +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads dropped: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + api.getPlayerInLeaderboards(p, "total", "headspluslb") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads sold: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + api.getPlayerInLeaderboards(p, "total", "headsplussh") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Total heads crafted: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + api.getPlayerInLeaderboards(p, "total", "headspluscraft") +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "Current level: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + ChatColor.translateAlternateColorCodes('&', pl.getLevel().getDisplayName()) +
                    "\n" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + "XP until next level: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + String.valueOf(pl.getNextLevel() != null ? (pl.getNextLevel().getRequiredXP() - pl.getXp()) : 0);

        } catch (NullPointerException ex) {
            return HeadsPlus.getInstance().getMessagesConfig().getString("no-data");
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
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        h.put(true, "");
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender cs) {
        try {
            OfflinePlayer p;
            NMSManager nms = HeadsPlus.getInstance().getNMS();
            if (args.length == 1) {
                p = nms.getOfflinePlayer(cs.getName());
            } else {
                p = nms.getOfflinePlayer(args[1]);
            }
            if (cs instanceof Player) {
                if (cs.getName().equalsIgnoreCase(p.getName())) {
                    cs.sendMessage(prof(p));
                } else {
                    if (cs.hasPermission("headsplus.maincommand.profile.others")) {
                        cs.sendMessage(prof(p));
                    } else {
                        cs.sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("no-perm"));
                    }
                }
            } else {
                if (cs.getName().equalsIgnoreCase(p.getName())) {
                    cs.sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("cant-view-data"));
                } else {
                    cs.sendMessage(prof(p));
                }
            }
        } catch (Exception e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (profile)");
                    log.severe("Report name: " + s);
                    log.severe("Please submit this report to the developer at one of the following links:");
                    log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    log.severe("https://discord.gg/nbT7wC2");
                    log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }


        return false;
    }
}
