package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigTextMenu;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
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
            List<String> bl = hp.getConfiguration().getWhitelist("world").getStringList("list");
            int page;
            if (args.length == 1) {
                page = 1;
            } else {
                page = Integer.parseInt(args[1]);
            }
            if (bl.size() == 0) {
                sender.sendMessage(hpc.getString("empty-wlw"));
                return true;
            }
            sender.sendMessage(HeadsPlusConfigTextMenu.BlacklistTranslator.translate("whitelist", "world", bl, page));

        } catch (Exception e) {
            new DebugPrint(e, "Subcommand (whitelistwl)", true, sender);
        }

        return true;

    }
}
