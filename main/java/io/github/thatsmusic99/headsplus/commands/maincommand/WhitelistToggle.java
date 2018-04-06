package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class WhitelistToggle implements IHeadsPlusCommand{

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    public void toggleNoArgs(CommandSender sender) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.toggle")) {
            try {
                if (config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-off"))));
                } else if (!config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-on"))));
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
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
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-on"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-a-on"))));
                    }

                } else if (str.equalsIgnoreCase("off")) {
                    if (config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-a-off"))));
                    }
                } else if (!(str.equalsIgnoreCase("on"))) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelist [On|Off]");
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }
    }

    @Override
    public String getCmdName() {
        return "whitelist";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.toggle";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistToggle();
    }

    @Override
    public String getSubCommand() {
        return "Whitelist";
    }

    @Override
    public String getUsage() {
        return "/hp whitelist [On|Off]";
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        if (args.length == 1) {
            try {
                if (config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", false);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-off"))));
                } else if (!config.getBoolean("whitelistOn")) {
                    config.set("whitelistOn", true);
                    config.options().copyDefaults(true);
                    HeadsPlus.getInstance().saveConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-on"))));
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
            }
        } else {
            String str = args[1];
            try {
                if (str.equalsIgnoreCase("on")) {
                    if (!config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", true);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-on"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-a-on"))));
                    }

                } else if (str.equalsIgnoreCase("off")) {
                    if (config.getBoolean("whitelistOn")) {
                        config.set("whitelistOn", false);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-off"))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-a-off"))));
                    }
                } else if (!(str.equalsIgnoreCase("on"))) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelist [On|Off]");
                }
            } catch (Exception e) {
                HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle whitelist!");
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));
            }
        }
        return false;
    }
}
