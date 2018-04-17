package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.ConfigSettings;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.logging.Logger;

public class MCReload implements IHeadsPlusCommand{

	@Override
	public String getCmdName() {
		return "reload";
	}

	@Override
	public String getPermission() {
		return "headsplus.maincommand.reload";
	}

	@Override
	public String getCmdDescription() {
		return LocaleManager.getLocale().descMCReload();
	}

	@Override
	public String getSubCommand() {
		return "Reload";
	}

	@Override
	public String getUsage() {
		return "/hp reload";
	}

    @Override
    public boolean isCorrectUsage(String[] args, CommandSender sender) {
        return false;
    }

    @Override
	public boolean isMainCommand() {
		return true;
	}

	@Override
	public boolean fire(String[] args, CommandSender sender) {
		HeadsPlusConfig m = HeadsPlus.getInstance().getMessagesConfig();
		String reloadM = m.getString("reload-message");
		String reloadingM = m.getString("reloading-message");
		String reloadF =  m.getString("reload-fail");
		sender.sendMessage(reloadingM);
		try {
			new BukkitRunnable() {
                @Override
                public void run() {
                    for (ConfigSettings cs : HeadsPlus.getInstance().getConfigs()) {
                        cs.reloadC(false);
                    }
                    HeadsPlus.getInstance().getConfig().options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    HPPlayer.players.clear();
                }
            }.runTaskLaterAsynchronously(HeadsPlus.getInstance(), 2);

			sender.sendMessage(reloadM);


		} catch (Exception e) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
			sender.sendMessage(reloadF);
			if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
				Logger log = HeadsPlus.getInstance().getLogger();
				log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
				try {
					String s = new DebugFileCreator().createReport(e, "Subcommand (reload)");
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


