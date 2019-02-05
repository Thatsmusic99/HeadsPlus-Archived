package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.Locale;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.HashMap;

public class Info implements IHeadsPlusCommand {

	// D
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
			HeadsPlus hp = HeadsPlus.getInstance();
            String version = hp.getVersion();
            String author = hp.getAuthor();
            Locale l = LocaleManager.getLocale();
            sender.sendMessage(hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + "HeadsPlus" + hp.getThemeColour(1) + "===============");
            sender.sendMessage(hp.getThemeColour(4) + l.versionWord() + " " + hp.getThemeColour(3) + version);
            sender.sendMessage(hp.getThemeColour(4) + l.author() + " " + hp.getThemeColour(3) + author);
            sender.sendMessage(hp.getThemeColour(4) + l.language() + " " + hp.getThemeColour(3) + l.getLanguage());
            sender.sendMessage(hp.getThemeColour(4) + l.contributors() + hp.getThemeColour(3) + "Toldi, DariusTK, AlansS53, Gneiwny, steve4744, Niestrat99");
        } catch (Exception e) {
		    new DebugPrint(e, "Subcommand (info)", true, sender);
        }
		return true;
	}
}
