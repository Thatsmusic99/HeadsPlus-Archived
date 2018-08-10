package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.InventoryManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Heads implements CommandExecutor, IHeadsPlusCommand {

    private final HashMap<String, Boolean> tests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String l, String[] args) {
        tests.clear();
        try {
            tests.put("Heads enabled", HeadsPlus.getInstance().isUsingHeadDatabase());
            tests.put("Instance of Player", cs instanceof Player);
            tests.put("No permission", cs.hasPermission("headsplus.heads"));
            if (HeadsPlus.getInstance().isUsingHeadDatabase()) {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    if (cs.hasPermission("headsplus.heads")) {
                        InventoryManager im2 = new InventoryManager("heads");
                        InventoryManager.pls.put(p, im2);
                        InventoryManager im = InventoryManager.getIM(p);
                        im.setSection("Menu");
                        p.openInventory(im.changePage(true, true, (Player) cs, "Menu"));
                        printDebugResults(tests, true);
                        return true;
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
            new DebugPrint(e, "Command (heads)", true, cs);
        }
        printDebugResults(tests, false);
        return false;
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
