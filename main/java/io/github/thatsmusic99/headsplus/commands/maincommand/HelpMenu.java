package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import mkremins.fanciful.FancyMessage;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HelpMenu implements IHeadsPlusCommand {

	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

	private void helpNoArgs(CommandSender sender) {
	    HeadsPlus hp = HeadsPlus.getInstance();
        List<IHeadsPlusCommand> headPerms = new ArrayList<>();
        for (IHeadsPlusCommand key : hp.getCommands()) {
            if (sender.hasPermission(key.getPermission())) {
                headPerms.add(key);
            }
        }
        PagedLists<IHeadsPlusCommand> pl = new PagedLists<>(headPerms, 8);
        sender.sendMessage(hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(3) + "1/" + pl.getTotalPages() + " " + hp.getThemeColour(1) + "===============");
        for (IHeadsPlusCommand key2 : pl.getContentsInPage(1)) {
            new FancyMessage()
                    .text(hp.getThemeColour(3) + key2.getUsage() + " - " + hp.getThemeColour(4) + key2.getCmdDescription())
                    .command("/hp help " + key2.getSubCommand())
                    .send(sender);
        }
	}
	private void helpNo(CommandSender sender, String str) {
        HeadsPlus hp = HeadsPlus.getInstance();
        List<IHeadsPlusCommand> headPerms = new ArrayList<>();
        for (IHeadsPlusCommand key : hp.getCommands()) {
            if (sender.hasPermission(key.getPermission())) {
                headPerms.add(key);
            }
        }
        int page = Integer.parseInt(str);
        PagedLists<IHeadsPlusCommand> pl = new PagedLists<>(headPerms, 8);
				
        if ((page > pl.getTotalPages()) || (0 >= page)) {
            sender.sendMessage(hpc.getString("invalid-pg-no"));
        } else {
            sender.sendMessage(hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(3) + page + "/" + pl.getTotalPages() + " " + hp.getThemeColour(1) + "===============");
            for (IHeadsPlusCommand key : pl.getContentsInPage(page)) {
                new FancyMessage()
                        .text(hp.getThemeColour(3) + key.getUsage() + " - " + hp.getThemeColour(4) + key.getCmdDescription())
                        .command("/hp help " + key.getSubCommand())
                        .send(sender);
            }
        }
	}

	private void helpCmd(CommandSender cs, String cmdName) {
        if (cs.hasPermission("headsplus.maincommand")) {
            IHeadsPlusCommand pe = null;
            for (IHeadsPlusCommand key : HeadsPlus.getInstance().getCommands()) {
                if (key.getCmdName().equalsIgnoreCase(cmdName)) {
                    pe = key;
                    break;
                }
            }
            HeadsPlus hp = HeadsPlus.getInstance();
            if (pe != null) {
                cs.sendMessage(hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(1) + "===============");
                cs.sendMessage(hp.getThemeColour(3) + "Usage: " + hp.getThemeColour(4) + pe.getUsage());
                cs.sendMessage(hp.getThemeColour(3) + "Description: " + hp.getThemeColour(4) + pe.getCmdDescription());
                if (cs.hasPermission("headsplus.help.viewperms")) {
                    cs.sendMessage(hp.getThemeColour(3) + "Permission: " + hp.getThemeColour(4) + pe.getPermission());
                }
                if (pe.advancedUsages().length != 0) {
                    cs.sendMessage(hp.getThemeColour(3) + "Further Usage:");
                    for (String s : pe.advancedUsages()) {
                        cs.sendMessage(hp.getThemeColour(4) + s);
                    }
                }
            } else {
                helpNoArgs(cs);
            }
        }
    }

	@Override
	public String getCmdName() {
		return "help";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descHelpMenu();
	}

	@Override
	public String getSubCommand() {
		return "Help";
	}

	@Override
	public String getUsage() {
		return "/hp <help|Page No.> [Page No.]";
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
            if (args.length == 0) {
                helpNoArgs(sender);
            } else if (args.length == 1) {
                if (args[0].matches("^[0-9]+$")) {
                    helpNo(sender, args[0]);
                } else if (args[0].equalsIgnoreCase("help")) {
                    helpNoArgs(sender);
                } else {
                    helpNoArgs(sender);
                }
            } else {
                if (args[0].equalsIgnoreCase("help")) {
                    if (args[1].matches("^[0-9]+$")) {
                        helpNo(sender, args[1]);
                    } else {
                        helpCmd(sender, args[1]);
                    }
                } else {
                    helpNoArgs(sender);
                }
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Subcommand (help)", true, sender);
        }

        return true;
	}
}
