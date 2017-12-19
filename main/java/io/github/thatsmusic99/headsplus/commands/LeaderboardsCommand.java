package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import io.github.thatsmusic99.headsplus.util.PagedHashmaps;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class LeaderboardsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if (cs.hasPermission("headsplus.leaderboards.display")) {
            if (args.length > 1) {
                if (DeathEvents.ableEntities.contains(EntityType.valueOf(args[1]))) {
                    if (args.length > 2) {
                        if (args[2].matches("^[0-9]+$")) {
                            cs.sendMessage(getLeaderboard(args[1], Integer.parseInt(args[2])));
                        }
                    } else {
                        cs.sendMessage(getLeaderboard(args[1], 1));
                    }
                } else if (args[1].equalsIgnoreCase("total")) {
                    if (args.length > 2) {
                        if (args[2].matches("^[0-9]+$")) {
                            cs.sendMessage(getLeaderboard(args[1], Integer.parseInt(args[2])));
                        }
                    } else {
                        cs.sendMessage(getLeaderboard(args[1], 1));
                    }
                } else if (args[1].matches("^[0-9]+$")) {
                    cs.sendMessage(getLeaderboard("total", Integer.parseInt(args[1])));
                }
            } else {
                cs.sendMessage(getLeaderboard("total", 1));
            }
        }
        return false;
    }

    private static String getLeaderboard(String sec, int page) {
        StringBuilder sb = new StringBuilder();
        PagedHashmaps ph = new PagedHashmaps(HeadsPlusLeaderboards.getScores(sec), 8);
        sb.append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))).append("===========").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(" HeadsPlus Leaderboards: ").append(WordUtils.capitalize(sec)).append(" ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))).append(page).append("/").append(String.valueOf(ph.getTotalPages())).append(" ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))).append("===========");
        Set<Object> it = ph.getContentsInPage(page).keySet();
        Collection<Object> it2 = ph.getContentsInPage(page).values();
        for (int i = 0; i < it.size(); i++) {
            sb.append("\n").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4"))).append(i + 1).append(". ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(((Player)it.toArray()[i]).getName()).append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))).append(" - ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(it2.toArray()[i]);
        }
        return sb.toString();
    }
}
