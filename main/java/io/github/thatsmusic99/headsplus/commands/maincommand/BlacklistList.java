package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class BlacklistList implements IHeadsPlusCommand {

	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

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
            if (args.length == 1) {
                int headsN = 1;
                List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
                int bls = bl.size();
                if (bls < 1) {
                    sender.sendMessage(hpc.getString("empty-bl"));
                    return true;
                }
                while (bls > 8) {
                    headsN++;
                    bls = bls - 8;
                }
                sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Blacklist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + headsN + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========" );
                int TimesSent = 0;
                for (String key : bl) {
                    if (TimesSent <= 7) {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                        TimesSent++;
                    }
                }
            } else {
                if (args[1].matches("^[0-9]+$")) {
                    List<String> bl = HeadsPlus.getInstance().getConfig().getStringList("blacklist");
                    int page = Integer.parseInt(args[1]);

                    PagedLists<String> pl = new PagedLists<>(bl, 8);

                    if ((page > pl.getTotalPages()) || (0 >= page)) {
                        sender.sendMessage(hpc.getString("invalid-pg-no"));
                    } else {
                        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "============ " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + "Blacklist: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + page + "/" + pl.getTotalPages() + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + " ==========");

                        for (Object keyz : pl.getContentsInPage(page)) {
                            String key = (String) keyz;
                            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key);
                        }
                    }
                } else {
                    sender.sendMessage(hpc.getString("invalid-input-int"));
                }
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Subcommand (blacklistl)", true, sender);
        }
        return true;
	}
}
