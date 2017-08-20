package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WhitelistwList {

    public static void wlwListNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelistw.list")) {
            int worldsN = 1;
            List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelistw");
            int bls = bl.size();
            if (bls < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("empty-wlw"))));
                return;
            }
            while (bls > 8) {
                worldsN++;
                bls = bls - 8;
            }
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + worldsN + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
            int TimesSent = 0;
            for (String key : bl) {
                if (TimesSent <= 7) {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    TimesSent++;
                }
            }
        } else {
            sender.sendMessage(HeadsPlusCommand.noPerms);
        }
    }
    public static void wlwList(CommandSender sender, String i) {
        if (sender.hasPermission("headsplus.maincommand.whitelistw.list")) {
            if (i.matches("^[0-9]+$")) {
                List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelistw");
                int entries = 8;
                int page = Integer.parseInt(i);
                int sIndex = (page - 1) * entries;
                int eIndex = entries + sIndex;
                if (eIndex > bl.size()) {
                    eIndex = bl.size();
                }
                int pages = 1;
                int bls = bl.size();
                while (bls > 8) {
                    pages++;
                    bls = bls - 8;
                }

                if ((page > pages) || (0 >= page)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-pg-no"))));
                } else {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Whitelist: "
                            + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pages
                            + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");
                    List<String> blsl = bl.subList(sIndex, eIndex);
                    for (String key : blsl) {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-input-int"))));
            }
        } else {
            sender.sendMessage(HeadsPlusCommand.noPerms);
        }
    }
}
