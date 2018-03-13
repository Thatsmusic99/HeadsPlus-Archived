package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class WhitelistwAdd implements IHeadsPlusCommand {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistwadd";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelistw.add";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistwAdd();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistwadd";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistwadd <World Name>";
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
                    if (!(configF.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        @SuppressWarnings("unused")
                        File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    }
                    List<String> blacklist = config.getStringList("whitelistw");
                    String aWorld = args[1].toLowerCase();
                    if (blacklist.contains(aWorld)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-a-add"))));
                    } else {
                        blacklist.add(aWorld);
                        config.set("whitelistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("world-added-wl").replaceAll("%w", args[1]))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add world to whitelist!");
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

