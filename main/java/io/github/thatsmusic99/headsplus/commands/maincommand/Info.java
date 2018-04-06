package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

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
		String version = HeadsPlus.getInstance().version;
		String author = HeadsPlus.getInstance().author;
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "HeadsPlus" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().versionWord() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + version);
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().author() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + author);
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().language() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + LocaleManager.getLocale().getLanguage());
		sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().contributors() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Toldi, DariusTK");
		return true;
	}
}
