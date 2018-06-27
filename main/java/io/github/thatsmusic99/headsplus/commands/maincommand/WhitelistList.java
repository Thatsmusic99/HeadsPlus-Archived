package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class WhitelistList implements IHeadsPlusCommand {

    private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelistl";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.list";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistList();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistl";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistl [Page No.]";
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
            List<String> wl = hp.getConfiguration().getWhitelist("default").getStringList("list");
            if (args.length == 1) {
                if (wl.size() < 1) {
                    sender.sendMessage(hpc.getString("empty-wl"));
                    return true;
                }
                PagedLists<String> pl = new PagedLists<>(wl, 8);
                sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "Whitelist: " + hp.getThemeColour(3) + "1/" + pl.getTotalPages() + hp.getThemeColour(1) + " ==========" );
                for (String key : pl.getContentsInPage(1)) {
                    sender.sendMessage(hp.getThemeColour(4) + key);
                }
            } else {
                if (args[1].matches("^[0-9]+$")) {
                    int page = Integer.parseInt(args[1]);
                    PagedLists<String> pl = new PagedLists<>(wl, 8);
                    if ((page > pl.getTotalPages()) || (0 >= page)) {
                        sender.sendMessage(hpc.getString("invalid-pg-no"));
                    } else {
                        sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "Whitelist: " + hp.getThemeColour(3) + page + "/" + pl.getTotalPages() + hp.getThemeColour(1) + " ==========");

                        for (String key : pl.getContentsInPage(page)) {
                            sender.sendMessage(hp.getThemeColour(4) + key);
                        }
                    }
                } else {
                    sender.sendMessage(hpc.getString("invalid-input-int"));
                }
            }
        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelistl)", true, sender);
        }
        return true;
    }
}
