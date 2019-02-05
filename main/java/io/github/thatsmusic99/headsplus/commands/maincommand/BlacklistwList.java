package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
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
		    List<String> bl = hp.getConfiguration().getBlacklist("world").getStringList("list");
            if (args.length == 1) {
                if (bl.size() < 1) {
                    sender.sendMessage(hpc.getString("empty-blw"));
                    return true;
                }
                PagedLists<String> pl = new PagedLists<>(bl, 8);
                sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "World Blacklist: " + hp.getThemeColour(3)+ "1/" + pl.getTotalPages() + hp.getThemeColour(1) + " ==========" );
                for (String key : pl.getContentsInPage(1)) {
                    sender.sendMessage(hp.getThemeColour(4) + key);
                }
            } else {
                if (args[1].matches("^[0-9]+$")) {
                    int page = Integer.parseInt(args[1]);
                    PagedLists<String> pl = new PagedLists<>(bl, 8);

                    if ((page > pl.getTotalPages()) || (0 >= page)) {
                        sender.sendMessage(hpc.getString("invalid-pg-no"));
                    } else {
                        sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "World Blacklist: "
                                + hp.getThemeColour(3) + page + "/" + pl.getTotalPages()
                                + hp.getThemeColour(1) + " ==========");
                        for (String key : pl.getContentsInPage(page)) {
                            sender.sendMessage(hp.getThemeColour(4) + key);
                        }
                    }
                } else {
                    sender.sendMessage(hpc.getString("invalid-input-int"));
                }
            }
        } catch (Exception e) {
		    new DebugPrint(e, "Subcommand (blacklistwl)", true, sender);
        }

		return false;
	}
}
