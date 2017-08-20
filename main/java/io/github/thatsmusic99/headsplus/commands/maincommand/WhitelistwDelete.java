package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistwDelete {
    private static final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    @SuppressWarnings("unused")
    private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

    public static void wlDel(CommandSender sender, String world) {
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
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("world-removed-wl").replaceAll("%w", world))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("world-a-removed-wl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wlw-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove world from whitelist!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wlw-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(HeadsPlusCommand.noPerms);
        }
    }
}
