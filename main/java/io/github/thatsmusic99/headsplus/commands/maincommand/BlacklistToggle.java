package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class BlacklistToggle implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

	@Override
	public String getCmdName() {
		return "blacklist";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklist.toggle";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistToggle();
	}

	@Override
	public String getSubCommand() {
		return "Blacklist";
	}

	@Override
	public String getUsage() {
		return "/hp blacklist [On|Off]";
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
			    if (config.getBoolean("blacklistOn")) {
			        config.set("blacklistOn", false);
			        config.options().copyDefaults(true);
			        HeadsPlus.getInstance().saveConfig();
			        sender.sendMessage(hpc.getString("bl-off"));
			    } else if (!config.getBoolean("blacklistOn")) {
			        config.set("blacklistOn", true);
			        config.options().copyDefaults(true);
			        HeadsPlus.getInstance().saveConfig();
			        sender.sendMessage(hpc.getString("bl-on"));
			    }
			} else {
				if (args[1].equalsIgnoreCase("on")) {
					if (!config.getBoolean("blacklistOn")) {
						config.set("blacklistOn", true);
						config.options().copyDefaults(true);
						HeadsPlus.getInstance().saveConfig();
						sender.sendMessage(hpc.getString("bl-on"));
					} else {
						sender.sendMessage(hpc.getString("bl-a-on"));
					}

				} else if (args[1].equalsIgnoreCase("off")) {
					if (config.getBoolean("blacklistOn")) {
						config.set("blacklistOn", false);
						config.options().copyDefaults(true);
						HeadsPlus.getInstance().saveConfig();
						sender.sendMessage(hpc.getString("bl-off"));
					} else {
						sender.sendMessage(hpc.getString("bl-a-off"));
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
				}
			}
		} catch (Exception e) {
            new DebugPrint(e, "Subcommand (blacklist)", true, sender);
		}
        return true;
	}
}
