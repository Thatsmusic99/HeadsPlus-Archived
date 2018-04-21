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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class WhitelistwDelete implements IHeadsPlusCommand {
    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelistwdel";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.delete";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwDelete();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistwdel";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistwdel <World Name>";
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
                    List<String> blacklist = config.getStringList("whitelistw");
                    String rHead = args[1].toLowerCase();
                    if (blacklist.contains(rHead)) {
                        blacklist.remove(rHead);
                        config.set("whitelistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("world-removed-wl").replaceAll("%w", args[1]));
                    } else {
                        sender.sendMessage(hpc.getString("world-a-removed-wl"));
                    }
                } else {
                    sender.sendMessage(hpc.getString("alpha-names"));
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            }
        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelistwdel)", true, sender);
        }

        return false;
    }
}
