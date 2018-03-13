package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistToggle implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

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
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		if (args.length == 1) {
			try {
				if (config.getBoolean("blacklistOn")) {
					config.set("blacklistOn", false);
					config.options().copyDefaults(true);
					HeadsPlus.getInstance().saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-off"))));
				} else if (!config.getBoolean("blacklistOn")) {
					config.set("blacklistOn", true);
					config.options().copyDefaults(true);
					HeadsPlus.getInstance().saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-on"))));
				}
			} catch (Exception e) {
				HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
				e.printStackTrace();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-fail"))));
			}
		} else {
            if (args[1].equalsIgnoreCase("on")) {
                if (!config.getBoolean("blacklistOn")) {
                    config.set("blacklistOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-on"))));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-a-on"))));
                }

            } else if (args[1].equalsIgnoreCase("off")) {
                    if (config.getBoolean("blacklistOn")) {
                        config.set("blacklistOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-a-off"))));
                    }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
            }
        }
        return true;
	}
}
