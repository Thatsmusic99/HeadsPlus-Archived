package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedHashmaps;
import org.apache.commons.lang.WordUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class LeaderboardsCommand implements CommandExecutor, IHeadsPlusCommand {

    private PagedHashmaps<OfflinePlayer, Integer> ph;
    private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
    private final HashMap<String, Boolean> tests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        try {
            tests.put("No permission", !cs.hasPermission("headsplus.leaderboards"));
            tests.put("Arguments", args.length > 0);
            if (cs.hasPermission("headsplus.leaderboards")) {
                if (args.length > 0) {
                    try {
                        boolean b = HeadsPlus.getInstance().getDeathEvents().ableEntities.contains(EntityType.valueOf(args[0].toUpperCase()));
                        tests.put("Valid Entity", b);
                        if (b || args[0].equalsIgnoreCase("player")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("crafting")
                                                || args[2].equalsIgnoreCase("selling")
                                                || args[2].equalsIgnoreCase("hunting")) {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), args[2]));
                                        } else {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                        }
                                    } else {
                                        cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                    }
                                } else if (args[1].equalsIgnoreCase("crafting")
                                        || args[1].equalsIgnoreCase("selling")
                                        || args[1].equalsIgnoreCase("hunting")) {
                                    if (args.length > 2) {
                                        if (args[2].matches("^[0-9]+$")) {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[2]), args[1]));
                                        } else {
                                            cs.sendMessage(getLeaderboard(args[0], 1, args[1]));
                                        }
                                    } else {
                                        cs.sendMessage(getLeaderboard(args[0], 1, args[1]));
                                    }
                                } else {
                                    cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                            }
                            printDebugResults(tests, true);
                            return true;
                        }
                    } catch (IllegalArgumentException ex) {
                        tests.put("Valid Entity", false);
                        if (args[0].equalsIgnoreCase("total")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("crafting")
                                                || args[2].equalsIgnoreCase("selling")
                                                || args[2].equalsIgnoreCase("hunting")) {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), args[2]));
                                        } else {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                        }
                                    } else {
                                        cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                    }
                                } else if (args[1].equalsIgnoreCase("crafting")
                                        || args[1].equalsIgnoreCase("selling")
                                        || args[1].equalsIgnoreCase("hunting")) {
                                    cs.sendMessage(getLeaderboard(args[0], 1, args[1]));
                                } else {
                                    cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                                }
                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                            }
                            printDebugResults(tests, true);
                            return true;
                        } else if (args[0].matches("^[0-9]+$")) {
                            cs.sendMessage(getLeaderboard("total", Integer.parseInt(args[0]), "hunting"));
                            printDebugResults(tests, true);
                            return true;
                        } else if (args[0].equalsIgnoreCase("player")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("crafting")
                                                || args[2].equalsIgnoreCase("selling")
                                                || args[2].equalsIgnoreCase("hunting")) {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), args[2]));
                                        } else {
                                            cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                        }
                                    } else {
                                        cs.sendMessage(getLeaderboard(args[0], Integer.parseInt(args[1]), "hunting"));
                                    }
                                } else {
                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("crafting")
                                                || args[2].equalsIgnoreCase("selling")
                                                || args[2].equalsIgnoreCase("hunting")) {
                                            cs.sendMessage(getLeaderboard(args[0], 1, args[2]));
                                        } else {
                                            cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                                        }
                                    } else {
                                        cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                                    }
                                }

                            } else {
                                cs.sendMessage(getLeaderboard(args[0], 1, "hunting"));
                            }
                            printDebugResults(tests, true);
                            return true;
                        }  else if (args[0].equalsIgnoreCase("crafting")
                                || args[0].equalsIgnoreCase("selling")
                                || args[0].equalsIgnoreCase("hunting")) {
                            if (args.length > 1) {
                                if (args[1].matches("^[0-9]+$")) {
                                    cs.sendMessage(getLeaderboard("total", Integer.parseInt(args[1]), args[0]));
                                } else {
                                    cs.sendMessage(getLeaderboard("total", 1, args[0]));
                                }
                            }


                        } else {
                            cs.sendMessage(getLeaderboard("total", 1, "hunting"));
                        }
                    }

                } else {
                    cs.sendMessage(getLeaderboard("total", 1, "hunting"));
                    printDebugResults(tests, true);
                    return true;
                }
            }
        } catch (Exception e) {
            new DebugPrint(e, "Command (leaderboard)", true, cs);
        }
        printDebugResults(tests, false);
        return false;
    }

    private String getLeaderboard(String sec, int page, String part) throws SQLException {
        try {
            HeadsPlus hp = HeadsPlus.getInstance();
            StringBuilder sb = new StringBuilder();
            String database = "headspluslb";
            switch (part.toLowerCase()) {
                case "selling":
                    database = "headsplussh";
                    break;
                case "crafting":
                    database = "headspluscraft";
                    break;
            }
            ph = new PagedHashmaps<>(HeadsPlus.getInstance().getMySQLAPI().getScores(sec, database), 8);

            sb.append(hp.getThemeColour(1)).append("=======").append(hp.getThemeColour(2)).append(" HeadsPlus Leaderboards: ").append(WordUtils.capitalize(sec)).append(" ").append(hp.getThemeColour(3)).append(page).append("/").append(ph.getTotalPages()).append(" ").append(hp.getThemeColour(1)).append("=======");
            Set<OfflinePlayer> it = ph.getContentsInPage(page).keySet();
            Collection<Integer> it2 = ph.getContentsInPage(page).values();
            for (int i = 0; i < it.size(); i++) {
                int in = i + (ph.getContentsPerPage() * (ph.getCurrentPage() - 1));
                sb.append("\n").append(hp.getThemeColour(4)).append(in + 1).append(". ").append(hp.getThemeColour(2)).append(((OfflinePlayer)it.toArray()[i]).getName()).append(hp.getThemeColour(3)).append(" - ").append(hp.getThemeColour(2)).append(it2.toArray()[i]);
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
        return "/hplb [Entity|Page No.] [Page No.] [Hunting|Selling|Crafting]";
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
