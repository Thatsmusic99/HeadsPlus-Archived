package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WhitelistwList {

    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    public void wlwListNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelistw.list")) {
            List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelistw");
            if (bl.size() < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("empty-wlw"))));
                return;
            }
            PagedLists<String> pl = new PagedLists<>(bl, 8);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );

            for (String key : pl.getContentsInPage(1)) {
                sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
    public void wlwList(CommandSender sender, String i) {
        if (sender.hasPermission("headsplus.maincommand.whitelistw.list")) {
            if (i.matches("^[0-9]+$")) {
                List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelistw");
                int page = Integer.parseInt(i);
                PagedLists<String> pl = new PagedLists<>(bl, 8);

                if ((page > pl.getTotalPages()) || (0 >= page)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("invalid-pg-no"))));
                } else {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "World Whitelist: "
                            + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages()
                            + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");

                    for (String key : pl.getContentsInPage(page)) {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("invalid-input-int"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
}
