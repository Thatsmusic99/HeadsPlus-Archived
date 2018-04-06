package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistwDelete implements IHeadsPlusCommand {
    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    public void wlDel(CommandSender sender, String world) {
        if (sender.hasPermission("headsplus.maincommand.whitelistw.delete")) {
            if (world.matches("^[A-Za-z0-9_]+$")) {
                try {
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    if (!(cfile.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                    }
                    try {
                        List<String> blacklist = config.getStringList("whitelistw");
                        String rHead = world.toLowerCase();
                        if (blacklist.contains(rHead)) {
                            blacklist.remove(rHead);
                            config.set("whitelistw", blacklist);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-removed-wl").replaceAll("%w", world))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-a-removed-wl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }

    @Override
    public String getCmdName() {
        return "whitelistwdel";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.delete";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwDelete();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistwdel";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistwdel <World Name>";
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
                        List<String> blacklist = config.getStringList("whitelistw");
                        String rHead = args[1].toLowerCase();
                        if (blacklist.contains(rHead)) {
                            blacklist.remove(rHead);
                            config.set("whitelistw", blacklist);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-removed-wl").replaceAll("%w", args[1]))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-a-removed-wl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wlw-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getUsage());
        }
        return false;
    }
}
