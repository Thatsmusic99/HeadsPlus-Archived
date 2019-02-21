package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigTextMenu;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;

public class BlacklistwList implements IHeadsPlusCommand {

    // M
    private final HeadsPlus hp = HeadsPlus.getInstance();
	private final HeadsPlusMessagesConfig hpc = hp.getMessagesConfig();

	@Override
	public String getCmdName() {
		return "blacklistwl";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklistw.list";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistwList();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistwl";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistwl [Page No.]";
	}

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 1) {
            if (args[1].matches("^[0-9]+$")) {
                h.put(true, "");
            } else {
                h.put(false, hpc.getString("invalid-input-int"));
            }
        } else {
            h.put(true, "");
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
		    List<String> bl = hp.getConfiguration().getBlacklist("world").getStringList("list");
		    int page;
		    if (args.length == 1) {
		        page = 1;
            } else {
		        page = Integer.parseInt(args[1]);
            }
            if (bl.size() == 0) {
                sender.sendMessage(hpc.getString("empty-blw"));
                return true;
            }
            sender.sendMessage(HeadsPlusConfigTextMenu.BlacklistTranslator.translate("blacklist", "world", bl, page));

        } catch (Exception e) {
		    new DebugPrint(e, "Subcommand (blacklistwl)", true, sender);
        }

		return false;
	}
}
