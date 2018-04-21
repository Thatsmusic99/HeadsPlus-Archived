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
import java.util.logging.Logger;

public class WhitelistToggle implements IHeadsPlusCommand {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelist";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.toggle";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistToggle();
    }

    @Override
    public String getSubCommand() {
        return "Whitelist";
    }

    @Override
    public String getUsage() {
        return "/hp whitelist [On|Off]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        h.put(true, "");
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            if (args.length == 1) {
                if (config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(hpc.getString("wl-off"));
                } else if (!config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(hpc.getString("wl-on"));
                }
            } else {
                String str = args[1];
                if (str.equalsIgnoreCase("on")) {
                    if (!config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("wl-on"));
                    } else {
                        sender.sendMessage(hpc.getString("wl-a-on"));
                    }
                } else if (str.equalsIgnoreCase("off")) {
                    if (config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("wl-off"));
                    } else {
                        sender.sendMessage(hpc.getString("wl-a-off"));
                    }
                } else if (!(str.equalsIgnoreCase("on"))) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
                }
            }
        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelist)", true, sender);
        }

        return false;
    }
}
