package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistDelete implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

	@Override
	public String getCmdName() {
		return "blacklistdel";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklist.delete";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistDelete();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistdel";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistdel <Username>";
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
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    if (!(cfile.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                    }
                    try {
                        List<String> blacklist = config.getStringList("blacklist");
                        String rHead = args[1].toLowerCase();
                        if (blacklist.contains(rHead)) {
                            blacklist.remove(rHead);
                            config.set("blacklist", blacklist);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-removed-bl").replaceAll("%p", args[1]))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-a-removed-bl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("bl-fail"))));
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
