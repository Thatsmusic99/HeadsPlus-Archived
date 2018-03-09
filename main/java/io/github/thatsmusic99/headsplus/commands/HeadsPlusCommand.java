package io.github.thatsmusic99.headsplus.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.thatsmusic99.headsplus.commands.maincommand.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class HeadsPlusCommand implements CommandExecutor {

    private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	public final String noPerms = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("no-perm")));
	private final String tooManyArgs = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("too-many-args")));

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		HelpMenu hm = new HelpMenu();
		List<String> validCmds = new ArrayList<>(Arrays.asList("reload",
                "blacklistadd",
                "blacklistdel",
                "blacklist",
                "info",
                "blacklistl",
                "help",
                "blacklistw",
                "blacklistwadd",
                "blacklistwdel",
                "blacklistwl",
                "blacklistw",
                "whitelistadd",
                "whitelistdel",
                "whitelistl",
                "whitelist",
				"whitelistwadd",
                "whitelistwdel",
                "whitelistwl",
                "whitelistw",
                "profile"));

		if ((cmd.getName().equalsIgnoreCase("headsplus")) || (cmd.getName().equalsIgnoreCase("hp"))) {
			   if ((args.length == 0)) {
				   hm.helpNoArgs(sender);
			   }
			    
			   if ((args.length >= 1) && (args[0].equalsIgnoreCase("reload"))) { 
				   new MCReload().reload(sender);
			   }   
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   new BlacklistAdd().blacklistAdd(sender, args[1]);
			   } 
			   else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   } 
			   else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistadd"))) {
				new WhitelistAdd().wlAdd(sender, args[1]);
			}
			else if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelistadd"))) {
				if (sender.hasPermission("headsplus.maincommand.whitelist.add")) {
					sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelistadd [IGN]");
				} else {
					sender.sendMessage(noPerms);
				}
			}
			else if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistadd"))) {
				if (sender.hasPermission("headsplus.maincommand.whitelist.add")) {
					sender.sendMessage(tooManyArgs);
					sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus whitelistadd [IGN]");
				} else {
					sender.sendMessage(noPerms);
				}
			}
		      
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
			 	  new BlacklistDelete().blacklistDel(sender, args[1]);
			   } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistdel [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   } else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistdel [IGN]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistdel"))) {
				new WhitelistDel().whitelistDel(sender, args[1]);
			} else if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelistdel"))) {
				if (sender.hasPermission("headsplus.maincommand.whitelist.delete")) {
					sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistdel [IGN]");
				} else {
					sender.sendMessage(noPerms);
				}
			} else if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistdel"))) {
				if (sender.hasPermission("headsplus.maincommand.whitelist.delete")) {
					sender.sendMessage(tooManyArgs);
					sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistdel [IGN]");
				} else {
					sender.sendMessage(noPerms);
				}
			}
			BlacklistToggle bt = new BlacklistToggle();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklist"))) {
                bt.toggleNoArgs(sender);
            }
            if ((args.length >= 2) && (args[0].equalsIgnoreCase("blacklist"))) {
                bt.toggle(sender, args[1]);
            }
            WhitelistToggle wt = new WhitelistToggle();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelist"))) {
                wt.toggleNoArgs(sender);
            }
            if ((args.length >= 2) && (args[0].equalsIgnoreCase("whitelist"))) {
                wt.toggle(sender, args[1]);
            }
            if ((args.length >= 1) && (args[0].equalsIgnoreCase("info"))) {
                new Info().info(sender);
            }
            BlacklistList bl = new BlacklistList();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistl"))) {
                bl.blacklistListNoArgs(sender);
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistl"))) {
                bl.blacklistList(sender, args[1]);
            }
            if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistl"))) {
                if (sender.hasPermission("headsplus.maincommand.blacklist.list")) {
                    sender.sendMessage(tooManyArgs);
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistl [Page no.]");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
            WhitelistList wl = new WhitelistList();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelistl"))) {
                wl.wlListNoArgs(sender);
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistl"))) {
                wl.wlList(sender, args[1]);
            }
            if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistl"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelist.list")) {
                    sender.sendMessage(tooManyArgs);
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistl [Page no.]");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
			   if ((args.length >= 1) && (args[0].matches("^[0-9]+$"))) {
				   hm.helpNo(sender, args[0]);
			   }
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
				   hm.helpNoArgs(sender);
			   }
			   if ((args.length >= 2) && (args[0].equalsIgnoreCase("help"))) {
                   if (args[1].matches("^[0-9]+$")) {
                       hm.helpNo(sender, args[1]);
                   } else {
                       hm.helpCmd(sender, args[1]);
                   }
			   }
			   if ((args.length > 0) && !validCmds.contains(args[0].toLowerCase()) && !args[0].matches("^[0-9]+$")) {
				   hm.helpNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwadd"))) {
				   new BlacklistwAdd().blacklistAdd(sender, args[1]);
			   }
			   if ((args.length == 1) && (args[0].equals("blacklistwadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.add")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwadd <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.add")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwadd <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwdel"))) {
				   new BlacklistwDelete().blacklistDel(sender, args[1]);
			   }
			   if ((args.length == 1) && (args[0].equals("blacklistwdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.delete")) {
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwdel <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwdel"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.delete")) {
					   sender.sendMessage(tooManyArgs);
					   sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwdel <World>");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
			   BlacklistwToggle bwt = new BlacklistwToggle();
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistw"))) {
				   bwt.toggleWorldNoArgs(sender);
			   }
			   if ((args.length >= 2) && (args[0].equalsIgnoreCase("blacklistw"))) {
				   bwt.toggleWorld(sender, args[1]);
			   }
			   BlacklistwList bwl = new BlacklistwList();
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   bwl.blacklistwListNoArgs(sender);
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   bwl.blacklistwList(sender, args[1]);
			   }
			   if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistwl"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklistw.list")) {
				       sender.sendMessage(tooManyArgs);
				       sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp blacklistwl [Page no.]");
				   } else {
					   sender.sendMessage(noPerms);
				   }
			   }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistwadd"))) {
                new WhitelistwAdd().wlAdd(sender, args[1]);
            }
            if ((args.length == 1) && (args[0].equals("whitelistwadd"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelistw.add")) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistwadd <World>");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
            if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistwadd"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelistw.add")) {
                    sender.sendMessage(tooManyArgs);
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistwadd <World>");
                } else {
                    sender.sendMessage(noPerms);
                }
            }

            if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistwdel"))) {
                new WhitelistwDelete().wlDel(sender, args[1]);
            }
            if ((args.length == 1) && (args[0].equals("whitelistwdel"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelistw.delete")) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistwdel <World>");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
            if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistwdel"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelistw.delete")) {
                    sender.sendMessage(tooManyArgs);
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistwdel <World>");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
            WhitelistwToggle wwt = new WhitelistwToggle();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelistw"))) {
                wwt.togglewlwNoArgs(sender);
            }
            if ((args.length >= 2) && (args[0].equalsIgnoreCase("whitelistw"))) {
                wwt.toggleWorld(sender, args[1]);
            }
            WhitelistwList wwl = new WhitelistwList();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("whitelistwl"))) {
                wwl.wlwListNoArgs(sender);
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("whitelistwl"))) {
                wwl.wlwList(sender, args[1]);
            }
            if ((args.length > 2) && (args[0].equalsIgnoreCase("whitelistwl"))) {
                if (sender.hasPermission("headsplus.maincommand.whitelistw.list")) {
                    sender.sendMessage(tooManyArgs);
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/hp whitelistwl [Page no.]");
                } else {
                    sender.sendMessage(noPerms);
                }
            }
            ProfileCommand pc = new ProfileCommand();
            if ((args.length == 1) && (args[0].equalsIgnoreCase("profile"))) {
                pc.getProfile(Bukkit.getOfflinePlayer(sender.getName()), sender);
            } else if ((args.length >= 2) && (args[0].equalsIgnoreCase("profile"))) {
                pc.getProfile(Bukkit.getOfflinePlayer(args[1]), sender);
            }
       
	} return false;
	}

	private IHeadsPlusCommand getCommandByName(String name) {
	    for (IHeadsPlusCommand hpc : HeadsPlus.getInstance().commands) {
	        if (hpc.getCmdName().equalsIgnoreCase(name)) {
	            return hpc;
            }
        }
        return null;
    }
}