package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import io.github.thatsmusic99.headsplus.util.InventoryManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class Heads implements CommandExecutor, IHeadsPlusCommand {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String l, String[] args) {
        try {
            if (HeadsPlus.getInstance().isUsingHeadDatabase()) {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    if (cs.hasPermission("headsplus.heads")) {
                        InventoryManager im2 = new InventoryManager("heads");
                        InventoryManager.pls.put(p, im2);
                        InventoryManager im = InventoryManager.getIM(p);
                        im.setSection("Menu");
                        p.openInventory(im.changePage(true, true, (Player) cs, "Menu"));
                    } else {
                        cs.sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("no-perms"));
                    }
                } else {
                    cs.sendMessage("[HeadsPlus] You have to be a player to run this command!");
                }
            } else {
                cs.sendMessage(HeadsPlus.getInstance().getMessagesConfig().getString("disabled"));
            }
        } catch (Exception e) {
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
            if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
                Logger log = HeadsPlus.getInstance().getLogger();
                log.severe("HeadsPlus has failed to execute this command. An error report has been made in /plugins/HeadsPlus/debug");
                try {
                    String s = new DebugFileCreator().createReport(e, "Command (heads)");
                    log.severe("Report name: " + s);
                    log.severe("Please submit this report to the developer at one of the following links:");
                    log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
                    log.severe("https://discord.gg/nbT7wC2");
                    log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
                } catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String getCmdName() {
        return "heads";
    }

    @Override
    public String getPermission() {
        return "headsplus.heads";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descHeads();
    }

    @Override
    public String getSubCommand() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/heads";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        h.put(true, "");
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return false;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        return false;
    }
}
