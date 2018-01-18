package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WhitelistList {

    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    public void wlListNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.list")) {
            List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
            if (bl.size() < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("empty-wl"))));
                return;
            }
            PagedLists pl = new PagedLists(bl, 8);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
            for (Object key2 : pl.getContentsInPage(1)) {
                String key = (String) key2;
                sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
    public void wlList(CommandSender sender, String i) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.list")) {
            if (i.matches("^[0-9]+$")) {
                List<String> wl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
                int page = Integer.parseInt(i);
                PagedLists pl = new PagedLists(wl, 8);
                if ((page > pl.getTotalPages()) || (0 >= page)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-pg-no"))));
                } else {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");

                    for (Object key2 : pl.getContentsInPage(page)) {
                        String key = (String) key2;
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("invalid-input-int"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
}
