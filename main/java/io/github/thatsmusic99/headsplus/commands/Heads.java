package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heads implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String l, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            p.openInventory(InventoryManager.setupInvHeadsX());
        }
        return true;
    }
}
