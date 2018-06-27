package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class BlacklistDelete implements IHeadsPlusCommand {
	
	private final HeadsPlusMainConfig config = HeadsPlus.getInstance().getConfiguration();
	private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
			List<String> blacklist = config.getBlacklist("default").getStringList("list");
			String rHead = args[1].toLowerCase();
	        if (blacklist.contains(rHead)) {
	            blacklist.remove(rHead);
				config.getConfig().set("blacklist.default.list", blacklist);
				config.save();
				sender.sendMessage(hpc.getString("head-removed-bl").replaceAll("\\{name}", args[1]));
	        } else {
	            sender.sendMessage(hpc.getString("head-a-removed-bl"));
	        }
	    } catch (Exception e) {
	        new DebugPrint(e, "Subcommand (blacklistdel)", true, sender);
	    }
        return true;
	}
}
