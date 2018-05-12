package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class BlacklistwAdd implements IHeadsPlusCommand {

	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

	@Override
	public String getCmdName() {
		return "blacklistwadd";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.blacklistw.add";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descBlacklistwAdd();
	}

	@Override
	public String getSubCommand() {
		return "Blacklistwadd";
	}

	@Override
	public String getUsage() {
		return "/hp blacklistwadd <World Name>";
	}

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                h.put(true, "");
            } else {
                h.put(false, hpc.getString("alpha-names"));
            }
        } else {
            h.put(false, hpc.getString("invalid-args"));
        }

        return h;
    }

    @Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                try {

                    FileConfiguration config = HeadsPlus.getInstance().getConfig();
                    List<String> blacklist = config.getStringList("blacklistw");
                    String aWorld = args[1].toLowerCase();
                    if (blacklist.contains(aWorld)) {
                        sender.sendMessage(hpc.getString("world-a-add"));
                    } else {
                        blacklist.add(aWorld);
                        config.set("blacklistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(hpc.getString("world-added-bl").replaceAll("\\{name}", args[1]));
                    }
                } catch (Exception e) {
                    new DebugPrint(e, "Subcommand (blacklistwadd)", true, sender);
                }
            } else {
                sender.sendMessage(hpc.getString("alpha-names"));
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
        }
		return false;
	}
}
