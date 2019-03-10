package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.HPUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.HashMap;

public class BlacklistToggle implements IHeadsPlusCommand {

	// F
	private final HeadsPlusMainConfig config = HeadsPlus.getInstance().getConfiguration();
	private final ConfigurationSection blacklist = config.getBlacklist("default");
	private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
				blacklist.set("enabled", HPUtils.switchBoolean(blacklist.getBoolean("enabled")));
				config.save();
				sender.sendMessage(blacklist.getBoolean("enabled") ? "bl-on" : "bl-off");
			} else {
				if (args[1].equalsIgnoreCase("on")) {
					if (!blacklist.getBoolean("enabled")) {
						blacklist.set("enabled", true);
						config.save();
						sender.sendMessage(hpc.getString("bl-on"));
					} else {
						sender.sendMessage(hpc.getString("bl-a-on"));
					}

				} else if (args[1].equalsIgnoreCase("off")) {
					if (blacklist.getBoolean("enabled")) {
						blacklist.set("enabled", false);
						config.save();
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
