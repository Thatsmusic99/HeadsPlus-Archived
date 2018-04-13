package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.io.IOException;
import java.util.logging.Logger;

public class Info implements IHeadsPlusCommand {

	@Override
	public String getCmdName() {
		return "info";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.info";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descInfo();
	}

	@Override
	public String getSubCommand() {
		return "Info";
	}

	@Override
	public String getUsage() {
		return "/hp info";
	}

	@Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		try {
            String version = HeadsPlus.getInstance().version;
            String author = HeadsPlus.getInstance().author;
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "HeadsPlus" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().versionWord() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + version);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().author() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + author);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().language() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + LocaleManager.getLocale().getLanguage());
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().contributors() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Toldi, DariusTK");
        } catch (Exception e) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (info)");
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
