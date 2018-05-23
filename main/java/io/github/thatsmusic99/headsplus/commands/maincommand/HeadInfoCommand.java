package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class HeadInfoCommand implements IHeadsPlusCommand {
    @Override
    public String getCmdName() {
        return "head";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.head";
    }

    @Override
    public String getCmdDescription() {
        return "Displays or changes information about a specific entity's head.";
    }

    @Override
    public String getSubCommand() {
        return "head";
    }

    @Override
    public String getUsage() {
        return "/hp head <view|set|add|remove> <Entity Type> [Option] [Value]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> g = new HashMap<>();
        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("view")) {
                if (args.length > 2) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        g.put(true, "");
                    }
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length > 2) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args.length > 3) {
                            if (args[3].equalsIgnoreCase("chance") || args[3].equalsIgnoreCase("price") || args[3].equalsIgnoreCase("display-name"))
                        }
                    }
                }
            }
        } else {
            // Invalid args
        }
        return null;
    }

    @Override
    public boolean isMainCommand() {
        return false;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        return false;
    }
}
