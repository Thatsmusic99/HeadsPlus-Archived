package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WhitelistList {

    private HeadsPlusConfig hpc = new HeadsPlusConfig();

    public void wlListNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.list")) {
            List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
            if (bl.size() < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(.getMessages().getString("empty-wl"))));
                return;
            }
            PagedLists pl = new PagedLists(bl, 8);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + headsN + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
            int TimesSent = 0;
            for (String key : bl) {
                if (TimesSent <= 7) {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    TimesSent++;
                }
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
    public void wlList(CommandSender sender, String i) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.list")) {
            if (i.matches("^[0-9]+$")) {
                List<String> wl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
                int entries = 8;
                int page = Integer.parseInt(i);
                int sIndex = (page - 1) * entries;
                int eIndex = entries + sIndex;
                if (eIndex > wl.size()) {
                    eIndex = wl.size();
                }
                int pages = 1;
                int wls = wl.size();
                while (wls > 8) {
                    pages++;
                    wls = wls - 8;
                }

                if ((page > pages) || (0 >= page)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-pg-no"))));
                } else {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pages + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");
                    List<String> wlsl = wl.subList(sIndex, eIndex);
                    for (String key : wlsl) {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("invalid-input-int"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
}
