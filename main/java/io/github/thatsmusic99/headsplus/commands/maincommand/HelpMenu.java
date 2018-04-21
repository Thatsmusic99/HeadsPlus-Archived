package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HelpMenu implements IHeadsPlusCommand {

	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

	private void helpNoArgs(CommandSender sender) {
        List<IHeadsPlusCommand> headPerms = new ArrayList<>();
        for (IHeadsPlusCommand key : HeadsPlus.getInstance().getCommands()) {
            if (sender.hasPermission(key.getPermission())) {
                headPerms.add(key);
            }
        }
        PagedLists<IHeadsPlusCommand> pl = new PagedLists<>(headPerms, 8);
        sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "1/" + pl.getTotalPages() + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
        for (IHeadsPlusCommand key2 : pl.getContentsInPage(1)) {
            new FancyMessage()
                    .text(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key2.getUsage() + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key2.getCmdDescription())
                    .command("/hp help " + key2.getSubCommand())
                    .send(sender);
        }
	}
	private void helpNo(CommandSender sender, String str) {
        List<IHeadsPlusCommand> headPerms = new ArrayList<>();
        for (IHeadsPlusCommand key : HeadsPlus.getInstance().getCommands()) {
            if (sender.hasPermission(key.getPermission())) {
                headPerms.add(key);
            }
        }
        int page = Integer.parseInt(str);
        PagedLists<IHeadsPlusCommand> pl = new PagedLists<>(headPerms, 8);
				
        if ((page > pl.getTotalPages()) || (0 >= page)) {
            sender.sendMessage(hpc.getString("invalid-pg-no"));
        } else {
            sender.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + String.valueOf(page) + "/" + String.valueOf(pl.getTotalPages()) + " " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
            for (IHeadsPlusCommand key : pl.getContentsInPage(page)) {
                new FancyMessage()
                        .text(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + key.getUsage() + " - " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + key.getCmdDescription())
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
            if (pe != null) {
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============" + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor1")) + "===============");
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Usage: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.getUsage());
                cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3")) + "Description: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.getCmdDescription());
                if (cs.hasPermission("headsplus.help.viewperms")) {
                    cs.sendMessage(ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor3"))+ "Permission: " + ChatColor.valueOf(HeadsPlus.getInstance().getConfig().getString("themeColor4")) + pe.getPermission());
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
	        if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console"))
	        e.printStackTrace();
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Subcommand (help)");
                    log.severe("Report name: " + s);
                    log.severe("Please submit this report to the developer at one of the following links:");
                    log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    log.severe("https://discord.gg/nbT7wC2");
                    log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        return true;
	}
}
