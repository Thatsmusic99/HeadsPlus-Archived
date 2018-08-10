package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class Head implements CommandExecutor, IHeadsPlusCommand {

    private final HeadsPlus hp = HeadsPlus.getInstance();
    private final HeadsPlusMessagesConfig hpc = hp.getMessagesConfig();
    private final HashMap<String, Boolean> tests = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return fire(args, sender);
    }

	private void giveHead(Player p, String n) {
		ItemStack skull = hp.getNMS().getSkullMaterial(1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta = hp.getNMS().setSkullOwner(n, meta);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', hp.getHeadsConfig().getConfig().getString("player.display-name").replaceAll("\\{player}", n)));
        skull.setItemMeta(meta);
        Location playerLoc = (p).getLocation();
        double playerLocY = playerLoc.getY() + 1;
        playerLoc.setY(playerLocY);
        World world = (p).getWorld();
        world.dropItem(playerLoc, skull).setPickupDelay(0);
	}

	private void giveH(String[] args, Player sender, Player p) {
	    ConfigurationSection blacklist = hp.getConfiguration().getBlacklist("default");
        ConfigurationSection whitelist = hp.getConfiguration().getWhitelist("default");
        List<String> bl = new ArrayList<>();
        for (String str : blacklist.getStringList("list")) {
            bl.add(str.toLowerCase());
        }
        List<String> wl = new ArrayList<>();
        for (String str : whitelist.getStringList("list")) {
            wl.add(str.toLowerCase());
        }

        boolean blacklistOn = blacklist.getBoolean("enabled");
        boolean wlOn = whitelist.getBoolean("enabled");
        String head = args[0].toLowerCase();
        if (p.getInventory().firstEmpty() == -1) {
            sender.sendMessage(hpc.getString("full-inv"));
            return;
        }
        tests.put("Whitelist enabled", wlOn);
        tests.put("Blacklist enabled", blacklistOn);
        tests.put("Whitelist contains head", wl.contains(head));
        tests.put("Blacklist contains head", bl.contains(head));
        tests.put("Can bypass blacklist", sender.hasPermission("headsplus.bypass.blacklist"));
        tests.put("Can bypass whitelist", sender.hasPermission("headsplus.bypass.whitelist"));
        if (wlOn) {
            if (blacklistOn) {
                if (wl.contains(head)) {
                    if (!bl.contains(head)) {
                        giveHead(p, args[0]);
                    } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                        giveHead(p, args[0]);
                    } else {
                        sender.sendMessage(hpc.getString("blacklist-head"));
                    }
                } else if (sender.hasPermission("headsplus.bypass.whitelist")) {
                    if (!bl.contains(head)) {
                        giveHead(p, args[0]);
                    } else if (sender.hasPermission("headsplus.bypass.blacklist")) {
                        giveHead(p, args[0]);
                    } else {
                        sender.sendMessage(hpc.getString("blacklist-head"));
                    }
                } else {
                    sender.sendMessage(hpc.getString("whitelist-head"));
                }
            } else {
                if (wl.contains(head)) {
                    giveHead(p, args[0]);
                } else if (sender.hasPermission("headsplus.bypass.whitelist")){
                    giveHead(p, args[0]);
                } else {
                    sender.sendMessage(hpc.getString("whitelist-head"));
                }
            }
        } else {
            if (blacklistOn) {
                if (!bl.contains(head)) {
                    giveHead(p, args[0]);
                } else if (sender.hasPermission("headsplus.bypass.blacklist")){
                    giveHead(p, args[0]);
                } else {
                    sender.sendMessage(hpc.getString("blacklist-head"));
                }
            } else {
                giveHead(p, args[0]);
            }
        }
    }

    @Override
    public String getCmdName() {
        return "head";
    }

    @Override
    public String getPermission() {
        return "headsplus.head";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descHead();
    }

    @Override
    public String getSubCommand() {
        return "Head";
    }

    @Override
    public String getUsage() {
        return "/head <IGN|Player giving head to> [IGN]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
	    HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 0) {
            if ((args[0].matches("^[A-Za-z0-9_]+$"))) {
                if (args[0].length() < 17) {
                    if (args[0].length() > 2) {
                       h.put(true, "");
                    } else {
                        h.put(false, hpc.getString("too-short-head"));
                    }
                } else {
                    sender.sendMessage(hpc.getString("head-too-long"));
                }
            } else {
                h.put(false, hpc.getString("alpha-names"));
            }
        } else {
            h.put(false, hpc.getString("invalid-args"));
        }
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return false;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
	    tests.clear();
	    try {
	        tests.put("Instance of Player", sender instanceof Player);
            if (sender instanceof Player){
                tests.put("No permission", !sender.hasPermission("headsplus.head"));
                if (sender.hasPermission("headsplus.head")) {
                    tests.put("More than 1 arg", args.length >= 2);
                    if (args.length >= 2) {
                        tests.put("No Other permission", !sender.hasPermission("headsplus.head.others"));
                        if (sender.hasPermission("headsplus.head.others")) {
                            tests.put("Player Found", hp.getNMS().getPlayer(args[0]) != null);
                            if (hp.getNMS().getPlayer(args[0]) != null) {
                                boolean b = args[1].matches("^[A-Za-z0-9_]+$") && (2 < args[1].length()) && (args[1].length() < 17);
                                tests.put("Valid name", b);
                                if (b) {
                                    String[] s = new String[2];
                                    s[0] = args[1];
                                    s[1] = args[0];
                                    giveH(s, (Player) sender, hp.getNMS().getPlayer(args[0]));
                                    printDebugResults(tests, true);
                                    return true;
                                } else if (!args[1].matches("^[A-Za-z0-9_]+$")) {
                                    sender.sendMessage(hpc.getString("alpha-names"));
                                } else if (args[1].length() < 3) {
                                    sender.sendMessage(hpc.getString("too-short-head"));
                                } else {
                                    sender.sendMessage(hpc.getString("head-too-long"));
                                }
                            } else {
                                sender.sendMessage(hpc.getString("player-offline"));
                            }
                            printDebugResults(tests, false);
                            return true;
                        } else {
                            sender.sendMessage(hpc.getString("no-perms"));
                        }
                    } else if (args.length > 0) {
                        boolean b = args[0].matches("^[A-Za-z0-9_]+$") && (2 < args[0].length()) && (args[0].length() < 17);
                        tests.put("Valid name", b);
                        if (b) {
                            giveH(args, (Player) sender, (Player) sender);
                            printDebugResults(tests, true);
                            return true;
                        }
                    } else {
                        sender.sendMessage(hpc.getString("invalid-args"));
                    }

                } else {
                    sender.sendMessage(hpc.getString("no-perms"));
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Command (head)", true, sender);
        }
        printDebugResults(tests, false);
        return false;
    }
}
	
