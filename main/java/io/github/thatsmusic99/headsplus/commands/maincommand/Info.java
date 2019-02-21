package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigTextMenu;
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
			sender.sendMessage(HeadsPlusConfigTextMenu.InfoTranslator.translate());
        } catch (Exception e) {
		    new DebugPrint(e, "Subcommand (info)", true, sender);
        }
		return true;
	}
}
