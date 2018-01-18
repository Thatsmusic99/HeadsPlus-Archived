package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class WhitelistToggle {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    public void toggleNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.toggle")) {
            try {
                if (config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-off"))));
                } else if (!config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-on"))));
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-fail"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
    public void toggle(CommandSender sender, String str) {
        if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
            try {
                if (str.equalsIgnoreCase("on")) {
                    if (!config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-on"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-a-on"))));
                    }

                } else if (str.equalsIgnoreCase("off")) {
                    if (config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-a-off"))));
                    }
                } else if (!(str.equalsIgnoreCase("on"))) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelist [On|Off]");
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wl-fail"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }
}
