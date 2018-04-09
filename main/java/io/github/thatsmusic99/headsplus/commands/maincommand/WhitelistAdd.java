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

public class WhitelistAdd implements IHeadsPlusCommand {
    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;

    @Override
    public String getCmdName() {
        return "whitelistadd";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.whitelist.add";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descWhitelistAdd();
    }

    @Override
    public String getSubCommand() {
        return "Whitelistadd";
    }

    @Override
    public String getUsage() {
        return "/hp whitelistadd <Username>";
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

                    if  (!(configF.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        @SuppressWarnings("unused")
                        File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    }
                    List<String> wl = config.getStringList("whitelist");
                    String aHead = args[1].toLowerCase();
                    if (wl.contains(aHead)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-a-add"))));
                    } else {
                        wl.add(aHead);
                        config.set("whitelist", wl);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("head-added-wl").replaceAll("%p", args[1]))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add head!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("wl-fail"))));


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
