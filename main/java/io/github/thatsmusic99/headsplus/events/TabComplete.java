package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.commands.maincommand.PermissionEnums;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class TabComplete implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender cs, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> c = new ArrayList<>();
            for (PermissionEnums key : PermissionEnums.values()) {
                if (cs.hasPermission(key.str)) {
                    c.add(key.scmd);
                }
            }
            List<String> f = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], c, f);
            Collections.sort(f);
            return f;
        }
        List<String> p = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            p.add(pl.getName());
        }
        return p;
    }
}
