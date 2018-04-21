package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistDelete implements IHeadsPlusCommand {
	
	private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
	    HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                h.put(true, "");
            } else {
                h.put(false, hpc.getString("alpha-names"));
            }
        } else {
            h.put(false, hpc.getString("invalid-args"));
        }
        return h;
    }

    @Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
	    try {
	        List<String> blacklist = config.getStringList("blacklist");
	        String rHead = args[1].toLowerCase();
	        if (blacklist.contains(rHead)) {
	            blacklist.remove(rHead);
	            config.set("blacklist", blacklist);
	            config.options().copyDefaults(true);
	            HeadsPlus.getInstance().saveConfig();
	            sender.sendMessage(hpc.getString("head-removed-bl").replaceAll("%p", args[1]));
	        } else {
	            sender.sendMessage(hpc.getString("head-a-removed-bl"));
	        }
	    } catch (Exception e) {
	        new DebugPrint(e, "Subcommand (blacklistdel)", true, sender);
	    }
        return true;
	}
}
