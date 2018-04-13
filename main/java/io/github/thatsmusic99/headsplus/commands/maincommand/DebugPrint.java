package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class DebugPrint implements IHeadsPlusCommand {
    @Override
    public String getCmdName() {
        return "debug";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.debug";
    }

    @Override
    public String getCmdDescription() {
        return "Dumps a debug file.";
    }

    @Override
    public String getSubCommand() {
        return "Debug";
    }

    @Override
    public String getUsage() {
        return "/hp debug <dump>";
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("dump")) {
                try {
                    String s = new DebugFileCreator().createReport(null, "Debug command");
                    sender.sendMessage(ChatColor.GREEN + "Report name: " + s);
                } catch (IOException e) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e.printStackTrace();
                    }

                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            sender.sendMessage(ChatColor.RED + "Remember that debug is still being built on so it only has \"dump\" currently."); // TODO
        }
        return false;
    }
}
