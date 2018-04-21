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

public class ChallengeCommand implements CommandExecutor, IHeadsPlusCommand {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String l, String[] args) {
        try {
            if (HeadsPlus.getInstance().hasChallengesEnabled()) {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    if (cs.hasPermission("headsplus.challenges")) {
                        InventoryManager im2 = new InventoryManager("chal");
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
            new DebugPrint(e, "Command (Challenges/HPC)", true, cs);
        }

        return true;
    }

    @Override
    public String getCmdName() {
        return "hpc";
    }

    @Override
    public String getPermission() {
        return "headsplus.challenges";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descChallenges();
    }

    @Override
    public String getSubCommand() {
        return "Hpc";
    }

    @Override
    public String getUsage() {
        return "/hpc";
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
