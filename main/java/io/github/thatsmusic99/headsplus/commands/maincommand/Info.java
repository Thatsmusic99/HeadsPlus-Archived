package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.HashMap;

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
            String version = HeadsPlus.getInstance().getVersion();
            String author = HeadsPlus.getInstance().getAuthor();
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "HeadsPlus" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().versionWord() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + version);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().author() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + author);
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().language() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + LocaleManager.getLocale().getLanguage());
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + LocaleManager.getLocale().contributors() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Toldi, DariusTK, AlansS53");
        } catch (Exception e) {
		    new DebugPrint(e, "Subcommand (info)", true, sender);
        }
		return true;
	}
}
