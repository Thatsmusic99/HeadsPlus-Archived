package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.api.HeadsPlusAPI;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;

public class ProfileCommand implements IHeadsPlusCommand {

    private String prof(OfflinePlayer p) throws SQLException {
        try {
            HeadsPlus hp = HeadsPlus.getInstance();
            HPPlayer pl = HPPlayer.getHPPlayer(p);
            HeadsPlusAPI api = hp.getAPI();
            return String.valueOf(hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(1) + "===============" +
                    "\n" + hp.getThemeColour(4) + "Player: " + hp.getThemeColour(2) + p.getName() +
                    "\n" + hp.getThemeColour(4) + "XP: " + hp.getThemeColour(2) + pl.getXp() +
                    "\n" + hp.getThemeColour(4) + "Completed challenges: " + hp.getThemeColour(2) + pl.getCompleteChallenges().size() +
                    "\n" + hp.getThemeColour(4) + "Total heads dropped: " + hp.getThemeColour(2) + api.getPlayerInLeaderboards(p, "total", "headspluslb") +
                    "\n" + hp.getThemeColour(4) + "Total heads sold: " + hp.getThemeColour(2) + api.getPlayerInLeaderboards(p, "total", "headsplussh") +
                    "\n" + hp.getThemeColour(4) + "Total heads crafted: " + hp.getThemeColour(2) + api.getPlayerInLeaderboards(p, "total", "headspluscraft") +
                    (hp.usingLevels() ? ("\n" + hp.getThemeColour(4) + "Current level: " + hp.getThemeColour(2) + ChatColor.translateAlternateColorCodes('&', pl.getLevel().getDisplayName()) +
                    "\n" + hp.getThemeColour(4) + "XP until next level: " + hp.getThemeColour(2) + (pl.getNextLevel() != null ? (pl.getNextLevel().getRequiredXP() - pl.getXp()) : 0)) : ""));

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
            HeadsPlus hp = HeadsPlus.getInstance();
            OfflinePlayer p;
            NMSManager nms = hp.getNMS();
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
                        cs.sendMessage(hp.getMessagesConfig().getString("no-perm"));
                    }
                }
            } else {
                if (cs.getName().equalsIgnoreCase(p.getName())) {
                    cs.sendMessage(hp.getMessagesConfig().getString("cant-view-data"));
                } else {
                    cs.sendMessage(prof(p));
                }
            }
        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (profile)", true, cs);
        }


        return false;
    }
}
