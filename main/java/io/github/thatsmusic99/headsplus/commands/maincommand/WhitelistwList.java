package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class WhitelistwList implements IHeadsPlusCommand {

    private final HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "whitelistwl";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.list";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwList();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistwl";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistwl [Page No.]";
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
            List<String> bl = hp.getConfiguration().getWhitelist("world").getStringList("list");
            if (args.length == 1) {

                if (bl.size() < 1) {
                    sender.sendMessage(hpc.getString("empty-wlw"));
                    return true;
                }
                PagedLists<String> pl = new PagedLists<>(bl, 8);
                sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "World Whitelist: " + hp.getThemeColour(3) + "1/" + pl.getTotalPages() + hp.getThemeColour(1) + " ==========" );

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
                        sender.sendMessage(hp.getThemeColour(1) + "============ " + hp.getThemeColour(2) + "World Whitelist: "
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
            new DebugPrint(e, "Subcommand (whitelistwl)", true, sender);
        }

        return true;
    }
}
