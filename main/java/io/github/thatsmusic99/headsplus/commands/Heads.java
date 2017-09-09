package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heads implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String l, String[] args) {
        if (HeadsPlus.getInstance().db) {
            if (cs instanceof Player) {
                if (cs.hasPermission("headsplus.heads")) {
                    Player p = (Player) cs;
                    p.openInventory(InventoryManager.changePage(true, true));
                } else {
                    cs.sendMessage(HeadsPlusCommand.noPerms);
                }
            } else {
                cs.sendMessage("[HeadsPlus] You have to be a player to run this command!");
            }
        } else {
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("disabled")));
        }
        return true;
    }
}
