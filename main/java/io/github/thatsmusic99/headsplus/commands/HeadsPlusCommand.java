package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HeadsPlusCommand implements CommandExecutor {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	public final String noPerms = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("no-perm")));

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

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
	}

	private IHeadsPlusCommand getCommandByName(String name) {
	    for (IHeadsPlusCommand hpc : HeadsPlus.getInstance().commands) {
	        if (hpc.getCmdName().equalsIgnoreCase(name)) {
	            return hpc;
            }
        }
        return null;
    }
}