package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class WhitelistwAdd implements IHeadsPlusCommand {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelistwadd";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.add";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwAdd();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistwadd";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistwadd <World Name>";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                h.put(true, "");
            } else {
                h.put(false, hpc.getString("alpha-names"));
            }
        } else {
            h.put(false, hpc.getString("invalid-args"));
        }

        return h;
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
                        List<String> blacklist = config.getStringList("whitelistw");
                        String aWorld = args[1].toLowerCase();
                        if (blacklist.contains(aWorld)) {
                            sender.sendMessage(hpc.getString("world-a-add"));
                        } else {
                            blacklist.add(aWorld);
                            config.set("whitelistw", blacklist);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(hpc.getString("world-added-wl").replaceAll("%w", args[1]));
                        }
                } else {
                    sender.sendMessage(hpc.getString("alpha-names"));
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            }
        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelistwadd)", true, sender);
        }
        return false;
    }
}

