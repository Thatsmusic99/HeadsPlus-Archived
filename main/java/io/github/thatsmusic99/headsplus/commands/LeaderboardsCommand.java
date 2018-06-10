package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.events.DeathEvents;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedHashmaps;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class LeaderboardsCommand implements CommandExecutor, IHeadsPlusCommand {

    private PagedHashmaps<OfflinePlayer, Integer> ph;
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        try {
            if (cs.hasPermission("headsplus.leaderboards")) {
                if (args.length > 0) {
                    try {
                        if (new DeathEvents().ableEntities.contains(EntityType.valueOf(args[0].toUpperCase()))) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1])));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1));
                            }
                        } else if (args[0].equalsIgnoreCase("player")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1])));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1));
                            }
                        }
                    } catch (IllegalArgumentException ex) {
                        if (args[0].equalsIgnoreCase("total")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1])));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1));
                            }
                        } else if (args[0].matches("^[0-9]+$")) {
                            cs.sendMessage(getLeaderboard("total", Integer.parseInt(args[0])));
                        } else if (args[0].equalsIgnoreCase("player")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1])));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1));
                            }
                        } else {
                            cs.sendMessage(hpc.getString("invalid-args"));
                        }
                    }

                } else {
                    cs.sendMessage(getLeaderboard("total", 1));
                }
            }
        } catch (Exception e) {
            new DebugPrint(e, "Command (leaderboard)", true, cs);
        }

        return false;
    }

    private String getLeaderboard(String sec, int page) throws SQLException {
        try {
            FileConfiguration fc = HeadsPlus.getInstance().getConfig();
            StringBuilder sb = new StringBuilder();
            ph = new PagedHashmaps<>(HeadsPlus.getInstance().getMySQLAPI().getScores(sec, "headspluslb"), 8);

            sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("=======").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus Leaderboards: ").append(WordUtils.capitalize(sec)).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(page).append("/").append(String.valueOf(ph.getTotalPages())).append(" ").append(ChatColor.valueOf(fc.getString("themeColor1"))).append("=======");
            Set<OfflinePlayer> it = ph.getContentsInPage(page).keySet();
            Collection<Integer> it2 = ph.getContentsInPage(page).values();
            for (int i = 0; i < it.size(); i++) {
                int in = i + (ph.getContentsPerPage() * (ph.getCurrentPage() - 1));
                sb.append("\n").append(ChatColor.valueOf(fc.getString("themeColor4"))).append(in + 1).append(". ").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(((OfflinePlayer)it.toArray()[i]).getName()).append(ChatColor.valueOf(fc.getString("themeColor3"))).append(" - ").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(it2.toArray()[i]);
            }
            return sb.toString();
        } catch (IllegalArgumentException ex) {
            if (ph.getHs().size() > 0) {
                return hpc.getString("invalid-pg-no");
            } else {
                return hpc.getString("no-data-lb");
            }
        } catch (NullPointerException ex) {
            return hpc.getString("no-data-lb");
        }
    }

    @Override
    public String getCmdName() {
        return "hplb";
    }

    @Override
    public String getPermission() {
        return "headsplus.leaderboards";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descHPLeaderboards();
    }

    @Override
    public String getSubCommand() {
        return "Hplb";
    }

    @Override
    public String getUsage() {
        return "/hplb [Entity|Page No.] [Page No.]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        h.put(true, "");
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return false;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        return false;
    }
}
