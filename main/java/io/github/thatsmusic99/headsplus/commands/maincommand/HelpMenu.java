package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.configuration.file.FileConfiguration;

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
            FileConfiguration fc = HeadsPlus.getInstance().getConfig();
            if (pe != null) {
                cs.sendMessage(ChatColor.valueOf(fc.getString("themeColor1")) + "===============" + ChatColor.valueOf(fc.getString("themeColor2")) + " HeadsPlus " + ChatColor.valueOf(fc.getString("themeColor1")) + "===============");
                cs.sendMessage(ChatColor.valueOf(fc.getString("themeColor3")) + "Usage: " + ChatColor.valueOf(fc.getString("themeColor4")) + pe.getUsage());
                cs.sendMessage(ChatColor.valueOf(fc.getString("themeColor3")) + "Description: " + ChatColor.valueOf(fc.getString("themeColor4")) + pe.getCmdDescription());
                if (cs.hasPermission("headsplus.help.viewperms")) {
                    cs.sendMessage(ChatColor.valueOf(fc.getString("themeColor3"))+ "Permission: " + ChatColor.valueOf(fc.getString("themeColor4")) + pe.getPermission());
                }
                if (pe.advancedUsages().length != 0) {
                    cs.sendMessage(fc.getString("themeColor3") + "Further Usage:");
                    for (String s : pe.advancedUsages()) {
                        cs.sendMessage(ChatColor.valueOf(fc.getString("themeColor4")) + s);
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
