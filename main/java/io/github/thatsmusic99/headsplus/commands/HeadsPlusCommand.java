package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
                            if (command.isCorrectUsage(args, sender).get(true) != null) {
                                if (command.isMainCommand()) {
                                    return command.fire(args, sender);
                                } else {
                                    getCommandByName("help").fire(args, sender);
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + command.getUsage());
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
            new DebugPrint(e, "Command (headsplus)", true, sender);
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