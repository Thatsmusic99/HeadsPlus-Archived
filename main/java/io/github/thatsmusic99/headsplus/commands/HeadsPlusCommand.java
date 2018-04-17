package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import java.io.IOException;
import java.util.logging.Logger;

public class HeadsPlusCommand implements CommandExecutor {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
	private final String noPerms = hpc.getString("no-perm");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
                if (args.length > 0) {
                    if (getCommandByName(args[0]) != null) {
                        IHeadsPlusCommand command = getCommandByName(args[0]);
                        assert command != null;
                        if (sender.hasPermission(command.getPermission())) {
                            if (command.isMainCommand()) {
                                return command.fire(args, sender);
                            } else {
                                getCommandByName("help").fire(args, sender);
                            }
                        } else {
                            sender.sendMessage(noPerms);
                        }
                    } else {
                        getCommandByName("help").fire(args, sender);
                    }
                } else {
                    getCommandByName("help").fire(args, sender);
                }

            }
            return false;
        } catch (Exception e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger l = HeadsPlus.getInstance().getLogger();
                l.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Command (headsplus)");
                    l.severe("Report name: " + s);
                    l.severe("Please submit this report to the developer at one of the following links:");
                    l.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    l.severe("https://discord.gg/nbT7wC2");
                    l.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }
		return false;
	}

	private IHeadsPlusCommand getCommandByName(String name) {
	    for (IHeadsPlusCommand hpc : HeadsPlus.getInstance().getCommands()) {
	        if (hpc.getCmdName().equalsIgnoreCase(name)) {
	            return hpc;
            }
        }
        return null;
    }
}