package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.commands.maincommand.PermissionEnums;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
            List<String> f = new ArrayList<>();
            List<String> c = new ArrayList<>();
            for (PermissionEnums key : PermissionEnums.values()) {
                if (cs.hasPermission(key.str)) {
                    c.add(key.scmd);
                }
            }
            StringUtil.copyPartialMatches(args[0], c, f);
            Collections.sort(f);
            return f;
        } else if (args.length >= 2) {
            List<String> f = new ArrayList<>();
            switch (args[0].toLowerCase()) {
                case "blacklistadd":
                    StringUtil.copyPartialMatches(args[1], players(), f);
                    Collections.sort(f);
                    return f;
                case "blacklistdel":
                    StringUtil.copyPartialMatches(args[1], players(), f);
                    Collections.sort(f);
                    return f;
                case "blacklistl":
                    return new ArrayList<>();
                case "blacklist":
                    StringUtil.copyPartialMatches(args[1], new ArrayList<>(Arrays.asList("on", "off")), f);
                    Collections.sort(f);
                    return f;
                case "blacklistw":
                    StringUtil.copyPartialMatches(args[1], new ArrayList<>(Arrays.asList("on", "off")), f);
                    Collections.sort(f);
                    return f;
                case "blacklistwadd":
                    StringUtil.copyPartialMatches(args[1], worlds(), f);
                    Collections.sort(f);
                    return f;
                case "blacklistwdel":
                    StringUtil.copyPartialMatches(args[1], worlds(), f);
                    Collections.sort(f);
                    return f;
                case "blacklistwl":
                    return new ArrayList<>();
                case "info":
                    return new ArrayList<>();
                case "reload":
                    return new ArrayList<>();
                case "whitelist":
                    StringUtil.copyPartialMatches(args[1], new ArrayList<>(Arrays.asList("on", "off")), f);
                    Collections.sort(f);
                    return f;
                case "whitelistadd":
                    StringUtil.copyPartialMatches(args[1], players(), f);
                    Collections.sort(f);
                    return f;
                case "whitelistdel":
                    StringUtil.copyPartialMatches(args[1], players(), f);
                    Collections.sort(f);
                    return f;
                case "whitelistl":
                    return new ArrayList<>();
                case "whitelistw":
                    StringUtil.copyPartialMatches(args[1], new ArrayList<>(Arrays.asList("on", "off")), f);
                    Collections.sort(f);
                    return f;
                case "whitelistwadd":
                    StringUtil.copyPartialMatches(args[1], worlds(), f);
                    Collections.sort(f);
                    return f;
                case "whitelistwdel":
                    StringUtil.copyPartialMatches(args[1], worlds(), f);
                    Collections.sort(f);
                    return f;
                case "whitelistwl":
                    return new ArrayList<>();
            }
        }
        return players();
    }

    private static List<String> players() {
        List<String> p = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            p.add(pl.getName());
        }
        return p;
    }
    private static List<String> worlds() {
        List<String> w = new ArrayList<>();
        for (World wo : Bukkit.getWorlds()) {
            w.add(wo.getName());
        }
        return w;
    }
}
