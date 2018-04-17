package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WhitelistAdd implements IHeadsPlusCommand {
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelistadd";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.add";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistAdd();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistadd";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistadd <Username>";
    }

    @Override
    public boolean isCorrectUsage(String[] args, CommandSender sender) {
        return false;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            if (args.length > 1) {
                if (args[1].matches("^[A-Za-z0-9_]+$")) {
                    FileConfiguration config = HeadsPlus.getInstance().getConfig();
                    List<String> wl = config.getStringList("whitelist");
                    String aHead = args[1].toLowerCase();
                    if (wl.contains(aHead)) {
                        sender.sendMessage(hpc.getString("head-a-add"));
                    } else {
                        wl.add(aHead);
                        config.set("whitelist", wl);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("head-added-wl").replaceAll("%p", args[1]));
                    }
                } else {
                    sender.sendMessage(hpc.getString("alpha-names"));
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            }
        } catch (Exception e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            sender.sendMessage(hpc.getString("wl-fail"));
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (whitelistadd)");
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
        return false;
    }
}
