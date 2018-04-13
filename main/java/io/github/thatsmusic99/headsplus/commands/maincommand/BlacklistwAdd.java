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

public class BlacklistwAdd implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

	@Override
	public String getCmdName() {
		return "blacklistwadd";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklistw.add";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistwAdd();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistwadd";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistwadd <World Name>";
	}

	@Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                try {

                    if (!(configF.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                    }
                    List<String> blacklist = config.getStringList("blacklistw");
                    String aWorld = args[1].toLowerCase();
                    if (blacklist.contains(aWorld)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-a-add"))));
                    } else {
                        blacklist.add(aWorld);
                        config.set("blacklistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-added-bl").replaceAll("%w", args[1]))));
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
                            String s = new DebugFileCreator().createReport(e, "Subcommand (blacklistwadd)");
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
		return false;
	}
}
