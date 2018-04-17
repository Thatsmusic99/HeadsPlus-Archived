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
import java.util.logging.Logger;

public class BlacklistwToggle implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
    public boolean isCorrectUsage(String[] args, CommandSender sender) {
        return false;
    }

    @Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		try {
            if (args.length == 1) {
                if (config.getBoolean("blacklistwOn")) {
                    config.set("blacklistwOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(hpc.getString("blw-off"));
                } else if (!config.getBoolean("blacklistwOn")) {
                    config.set("blacklistwOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(hpc.getString("blw-on"));
                }
            } else {
                if (args[1].equalsIgnoreCase("on")) {
                    if (!config.getBoolean("blacklistwOn")) {
                        config.set("blacklistwOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("blw-on"));
                    } else {
                        sender.sendMessage(hpc.getString("blw-a-on"));
                    }
                } else if (args[1].equalsIgnoreCase("off")) {
                    if (config.getBoolean("blacklistwOn")) {
                        config.set("blacklistwOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(hpc.getString("blw-off"));
                    } else {
                        sender.sendMessage(hpc.getString("blw-a-off"));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
                }
            }
		} catch (Exception e) {
            sender.sendMessage(hpc.getString("blw-fail"));
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (blacklistw)");
                    log.severe("Report name: " + s);
                    log.severe("Please submit this report to the developer at one of the following links:");
                    log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    log.severe("https://discord.gg/nbT7wC2");
                    log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return true;
	}
}
