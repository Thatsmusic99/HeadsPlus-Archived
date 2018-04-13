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

public class WhitelistDel implements IHeadsPlusCommand {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistdel";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.delete";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistDelete();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistdel";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistdel";
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
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    List<String> wl = config.getStringList("whitelist");
                    String rHead = args[1].toLowerCase();
                    if (wl.contains(rHead)) {
                        wl.remove(rHead);
                        config.set("whitelist", wl);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-removed-wl").replaceAll("%p", args[1]))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-a-removed-wl"))));

                    }

                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("alpha-names"))));
                }

            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (whitelistdel)");
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
