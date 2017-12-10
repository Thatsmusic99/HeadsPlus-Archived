package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusLeaderboards;
import io.github.thatsmusic99.headsplus.util.PagedHashmaps;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class LeaderboardsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if (cs.hasPermission("headsplus.leaderboards.display")) {
            if (args.length > 1) {

            } else {
                StringBuilder sb = new StringBuilder();
                PagedHashmaps ph = new PagedHashmaps(HeadsPlusLeaderboards.getScores("total"), 8);
                sb.append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))).append("===========").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(" HeadsPlus Leaderboards: ").append("Total ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))).append("1/").append(String.valueOf(ph.getTotalPages())).append(" ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1"))).append("===========");
                Set<Object> it = ph.getContentsInPage(1).keySet();
                Collection<Object> it2 = ph.getContentsInPage(1).values();
                for (int i = 0; i < it.size(); i++) {
                     sb.append("\n").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4"))).append(i + 1).append(". ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(((Player)it.toArray()[i]).getName()).append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))).append(" - ").append(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2"))).append(it2.toArray()[i]);
                }
                cs.sendMessage(sb.toString());
            }
        }
        return false;
    }
}
