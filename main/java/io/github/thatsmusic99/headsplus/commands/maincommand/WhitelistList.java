package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigTextMenu;
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
            HeadsPlus hp = HeadsPlus.getInstance();
            List<String> wl = hp.getConfiguration().getWhitelist("default").getStringList("list");
            int page;
            if (args.length == 1) {
                page = 1;
            } else {
                page = Integer.parseInt(args[1]);
            }
            if (wl.size() == 0) {
                sender.sendMessage(hpc.getString("empty-wl"));
                return true;
            }
            PagedLists<String> pl = new PagedLists<>(wl, 8);
            if ((page > pl.getTotalPages()) || (0 >= page)) {
                sender.sendMessage(hpc.getString("invalid-pg-no"));
            } else {
                sender.sendMessage(HeadsPlusConfigTextMenu.BlacklistTranslator.translate("whitelist", "default", pl, page));
            }

        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelistl)", true, sender);
        }
        return true;
    }
}
