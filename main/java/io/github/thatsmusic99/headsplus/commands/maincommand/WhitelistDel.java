package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistDel implements IHeadsPlusCommand {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistdel";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.delete";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistDelete();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistdel";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistdel";
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
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    if (!(cfile.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                    }
                    try {
                        List<String> wl = config.getStringList("whitelist");
                        String rHead = args[1].toLowerCase();
                        if (wl.contains(rHead)) {
                            wl.remove(rHead);
                            config.set("whitelist", wl);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-removed-wl").replaceAll("%p", args[1]))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-a-removed-wl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("alpha-names"))));
            }

        } else {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
        }
        return true;
    }
}
