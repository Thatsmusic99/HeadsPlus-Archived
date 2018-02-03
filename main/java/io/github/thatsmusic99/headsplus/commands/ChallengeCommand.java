package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChallengeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
        if (HeadsPlus.getInstance().chal) {
            if (cs instanceof Player) {
                Player p = (Player) cs;
                if (cs.hasPermission("headsplus.challenges.display")) {
                    InventoryManager im2 = new InventoryManager("chal");
                    InventoryManager.pls.put(p, im2);
                    InventoryManager im = InventoryManager.getIM(p);
                    im.setSection("Menu");
                    p.openInventory(im.changePage(true, true, (Player) cs, "Menu"));
                } else {
                    cs.sendMessage(new HeadsPlusCommand().noPerms);
                }
            } else {
                cs.sendMessage("[HeadsPlus] You have to be a player to run this command!");
            }
        } else {
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().hpc.getMessages().getString("disabled")));
        }
        return true;
    }
}
