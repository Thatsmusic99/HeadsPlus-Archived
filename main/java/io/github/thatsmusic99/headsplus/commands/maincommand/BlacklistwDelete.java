package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwDelete implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

	@Override
	public String getCmdName() {
		return "blacklistwdel";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklistw.delete";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistwDelete();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistwdel";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistwdel <World Name>";
	}

	@Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		if (args.length > 1) {
			if (args[1].matches("^[A-Za-z0-9_]+$")) {
                config.options().copyDefaults(true);
                HeadsPlus.getInstance().saveConfig();
                File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                if (!(cfile.exists())) {
                    HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                }
                try {
                    List<String> blacklist = config.getStringList("blacklistw");
                    String rHead = args[1].toLowerCase();
                    if (blacklist.contains(rHead)) {
                        blacklist.remove(rHead);
                        config.set("blacklistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-removed-bl").replaceAll("%w", args[1]))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-a-removed-bl"))));

                    }
                } catch (Exception e) {
                	if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-fail"))));
					if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
						Logger log = HeadsPlus.getInstance().getLogger();
						log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
						try {
							String s = new DebugFileCreator().createReport(e, "Subcommand (blacklistwdel)");
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
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("alpha-names"))));
			}
		} else {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
        }
		return true;
	}
}
