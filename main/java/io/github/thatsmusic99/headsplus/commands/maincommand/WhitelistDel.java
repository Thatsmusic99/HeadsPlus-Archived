package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistDel {

    private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
    @SuppressWarnings("unused")
    private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

    public static void whitelistDel(CommandSender sender, String name) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.delete")) {
            if (name.matches("^[A-Za-z0-9_]+$")) {
                try {
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    if (!(cfile.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                    }
                    try {
                        List<String> wl = config.getStringList("whitelist");
                        String rHead = name.toLowerCase();
                        if (wl.contains(rHead)) {
                            wl.remove(rHead);
                            config.set("whitelist", wl);
                            config.options().copyDefaults(true);
                            HeadsPlus.getInstance().saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-removed-wl").replaceAll("%p", name))));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-a-removed-wl"))));

                        }} catch (Exception e) {
                        HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(HeadsPlusCommand.noPerms);
        }
    }
}
