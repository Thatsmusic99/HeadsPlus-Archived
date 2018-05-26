package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class HeadInfoCommand implements IHeadsPlusCommand {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
        return "Displays or changes information about a specific entity's head. Further usage: /hp head <view> <Entity Type> <Name|Mask-Amplifiers|Mask-Effects|Lore>";
    }

    @Override
    public String getSubCommand() {
        return "head";
    }

    @Override
    public String getUsage() {
        return "/hp head <view|set|add|remove> <Entity Type> <Option> [Value|Position]";
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
                    } else {
                        g.put(false, hpc.getString("invalid-args"));
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("chance") || args[3].equalsIgnoreCase("price") || args[3].equalsIgnoreCase("display-name") || args[3].equalsIgnoreCase("interact-name")) {
                            if (isValueValid(args[3], args[4])) {
                                g.put(true, "");
                            } else {
                                g.put(false, hpc.getString("invalid-args"));
                            }
                        } else {
                            g.put(false, hpc.getString("invalid-args"));
                        }
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else if (args[1].equalsIgnoreCase("add")) {

            }
        } else {
            g.put(false, hpc.getString("invalid-args"));
        }
        return g;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        return false;
    }

    private boolean isValueValid(String option, String value) {
        if (option.equalsIgnoreCase("chance") || option.equalsIgnoreCase("price")) {
            try {
                Double.valueOf(value);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        } else {
            return true;
        }
    }
}
