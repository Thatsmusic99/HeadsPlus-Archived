package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WhitelistList implements IHeadsPlusCommand {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistl";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.list";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistList();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistl";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistl [Page No.]";
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            if (args.length == 1) {
                List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
                if (bl.size() < 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("empty-wl"))));
                    return true;
                }
                PagedLists<String> pl = new PagedLists<>(bl, 8);
                sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
                for (String key : pl.getContentsInPage(1)) {
                    sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                }
            } else {
                if (args[1].matches("^[0-9]+$")) {
                    List<String> wl = HeadsPlus.getInstance().getConfig().getStringList("whitelist");
                    int page = Integer.parseInt(args[1]);
                    PagedLists<String> pl = new PagedLists<>(wl, 8);
                    if ((page > pl.getTotalPages()) || (0 >= page)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("invalid-pg-no"))));
                    } else {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Whitelist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");

                        for (String key : pl.getContentsInPage(page)) {
                            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("invalid-input-int"))));
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
                    String s = new DebugFileCreator().createReport(e, "Subcommand (whitelistl)");
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
        return true;
    }
}
