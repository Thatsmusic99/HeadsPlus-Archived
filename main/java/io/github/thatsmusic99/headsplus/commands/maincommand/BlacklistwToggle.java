package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.HPUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;

import java.util.HashMap;

public class BlacklistwToggle implements IHeadsPlusCommand {
	
	private final HeadsPlus hp = HeadsPlus.getInstance();
	private final HeadsPlusMessagesConfig hpc = hp.getMessagesConfig();

	@Override
	public String getCmdName() {
		return "blacklistw";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklistw.toggle";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistwToggle();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistw";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistw [On|Off]";
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
            HeadsPlusMainConfig config = hp.getConfiguration();
            boolean enabled = config.getBlacklist("world").getBoolean("enabled");
            if (args.length == 1) {
                config.getBlacklist("world").set("enabled", HPUtils.switchBoolean(enabled));
                config.save();
                sender.sendMessage(enabled ? "blw-off" : "blw-on");
            } else {
                if (args[1].equalsIgnoreCase("on")) {
                    if (!config.getBlacklist("world").getBoolean("enabled")) {
                        config.getBlacklist("world").set("enabled", true);
                        config.save();
                        sender.sendMessage(hpc.getString("blw-on"));
                    } else {
                        sender.sendMessage(hpc.getString("blw-a-on"));
                    }
                } else if (args[1].equalsIgnoreCase("off")) {
                    if (config.getBlacklist("world").getBoolean("enabled")) {
                        config.getBlacklist("world").set("enabled", false);
                        config.save();
                        sender.sendMessage(hpc.getString("blw-off"));
                    } else {
                        sender.sendMessage(hpc.getString("blw-a-off"));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
                }
            }
		} catch (Exception e) {
            new DebugPrint(e, "Subcommand (blacklistw)", true, sender);
        }
        return true;
	}
}
