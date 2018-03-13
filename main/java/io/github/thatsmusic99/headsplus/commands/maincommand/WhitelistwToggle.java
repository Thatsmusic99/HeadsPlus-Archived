package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class WhitelistwToggle implements IHeadsPlusCommand{

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistw";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.toggle";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwToggle();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistw";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistw [On|Off]";
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        if (args.length == 1) {
            try {
                if (config.getBoolean("whitelistwOn")) {
                    config.set("whitelistwOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-off"))));
                } else if (!config.getBoolean("whitelistwOn")) {
                    config.set("whitelistwOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-on"))));
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
            }
        } else {
            String str = args[1];
            try {
                if (str.equalsIgnoreCase("on")) {
                    if (!config.getBoolean("whitelistwOn")) {
                        config.set("whitelistwOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-on"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-a-on"))));
                    }

                } else if (str.equalsIgnoreCase("off")) {
                    if (config.getBoolean("whitelistwOn")) {
                        config.set("whitelistwOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-a-off"))));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelistw [On|Off]");
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle world whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
            }
        }
        return false;
    }
}
