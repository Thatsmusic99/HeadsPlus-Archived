package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;

public class BlacklistList implements IHeadsPlusCommand {

    // L
    private HeadsPlus hp = HeadsPlus.getInstance();
	private final HeadsPlusMessagesConfig hpc = hp.getMessagesConfig();
	private HeadsPlusMainConfig config = hp.getConfiguration();

	@Override
	public String getCmdName() {
		return "blacklistl";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklist.list";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistList();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistl";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistl [Page No.]";
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
	        int page;
            if (args.length == 1) {
                page = 1;
            } else {
                page = Integer.parseInt(args[1]);
            }

            List<String> bl = config.getBlacklist("default").getStringList("blacklist");
            if (bl.size() == 0) {
                sender.sendMessage(hpc.getString("empty-bl"));
            }
            PagedLists<String> pl = new PagedLists<>(bl, 8);
            if ((page > pl.getTotalPages()) || (0 >= page)) {
                sender.sendMessage(hpc.getString("invalid-pg-no"));
            } else {
                sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "Blacklist: " + hp.getThemeColour(3) + page + "/" + pl.getTotalPages() + hp.getThemeColour(1) + " ==========");

                for (Object keyz : pl.getContentsInPage(page)) {
                    String key = (String) keyz;
                    sender.sendMessage(hp.getThemeColour(4) + key);

                }
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Subcommand (blacklistl)", true, sender);
        }
        return true;
	}
}
