package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class WhitelistwAdd {

    private final FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
    private HeadsPlusConfig hpc = new HeadsPlusConfig();

    public void wlAdd(CommandSender sender, String world) {

        if (sender.hasPermission("headsplus.maincommand.whitelistw.add")) {
            if (world.matches("^[A-Za-z0-9_]+$")) {
                try {

                    if (!(configF.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        @SuppressWarnings("unused")
                        File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    }
                    List<String> blacklist = config.getStringList("whitelistw");
                    String aWorld = world.toLowerCase();
                    if (blacklist.contains(aWorld)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("world-a-add"))));
                    } else {
                        blacklist.add(aWorld);
                        config.set("whitelistw", blacklist);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("world-added-wl").replaceAll("%w", world))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add world to whitelist!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("wlw-fail"))));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getMessages().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(new HeadsPlusCommand().noPerms);
        }

    }

}

