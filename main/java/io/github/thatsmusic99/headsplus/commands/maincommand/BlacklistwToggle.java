package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistwToggle implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

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
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		if (args.length == 1) {
			try {
				if (config.getBoolean("blacklistwOn")) {
					config.set("blacklistwOn", false);
					config.options().copyDefaults(true);
					HeadsPlus.getInstance().saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-off"))));
				} else if (!config.getBoolean("blacklistwOn")) {
					config.set("blacklistwOn", true);
					config.options().copyDefaults(true);
					HeadsPlus.getInstance().saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-on"))));
				}
			} catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
				e.printStackTrace();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-fail"))));
			}
		} else {
            try {
                if (args[1].equalsIgnoreCase("on")) {
                    if (!config.getBoolean("blacklistwOn")) {
                        config.set("blacklistwOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-on"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-a-on"))));
                    }

                } else if (args[1].equalsIgnoreCase("off")) {
                    if (config.getBoolean("blacklistwOn")) {
                        config.set("blacklistwOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-a-off"))));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world blacklist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("blw-fail"))));
            }
        }
        return true;
	}
}
