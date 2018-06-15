package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.locale.Locale;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.util.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeadInfoCommand implements IHeadsPlusCommand {

    private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "head";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.head";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descHeadView();
    }

    @Override
    public String getSubCommand() {
        return "head";
    }

    @Override
    public String getUsage() {
        return "/hp head <view> <Entity Type> [Name|Mask|Lore] [Page]";
    }

    @Override
    public String[] advancedUsages() {
        String[] s = new String[4];
        s[0] = "/hp head view <Entity Type> [Name|Mask|Lore] [Page]";
        s[1] = "/hp head set <Entity Type> <Chance|Price|Display-name|Interact-name> <Value>";
        s[2] = "/hp head add <Entity Type> <Name|Mask|Lore> <Value|Effect> [Colour|Amplifier]";
        s[3] = "/hp head remove <Entity Type> <Name|Mask|Lore> <Index> [Colour]";
        return s;
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> g = new HashMap<>();
        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("view")) {
                if (args.length > 2) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args.length > 3) {
                            if (args[3].equalsIgnoreCase("name") || args[3].equalsIgnoreCase("lore") || args[3].equalsIgnoreCase("mask")) {
                                if (args.length > 4) {
                                    if (args[4].matches("^[0-9]+$")) {
                                        g.put(true, "");
                                    } else {
                                        g.put(false, hpc.getString("invalid-input-int"));
                                    }
                                } else {
                                    g.put(true, "");
                                }
                            } else {
                                g.put(false, hpc.getString("invalid-args"));
                            }
                        } else {
                            g.put(true, "");
                        }
                    } else {
                        g.put(false, hpc.getString("invalid-args"));
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("chance")
                                || args[3].equalsIgnoreCase("price")
                                || args[3].equalsIgnoreCase("display-name")
                                || args[3].equalsIgnoreCase("interact-name")) {
                            if (isValueValid(args[3], args[4])) {
                                g.put(true, "");
                            } else {
                                g.put(false, hpc.getString("invalid-args"));
                            }
                        } else {
                            g.put(false, hpc.getString("invalid-args"));
                        }
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("name")
                                || args[3].equalsIgnoreCase("lore")
                                || args[3].equalsIgnoreCase("mask")) {
                            if (args[3].equalsIgnoreCase("mask")) {
                                if (PotionEffectType.getByName(args[4]) != null) {
                                    if (args.length > 5) {
                                        if (args[5].matches("^[0-9]+$")) {
                                            g.put(true, "");
                                        } else {
                                            g.put(false, hpc.getString("invalid-args"));
                                        }
                                    } else {
                                        g.put(true, "");
                                    }
                                } else {
                                    g.put(false, hpc.getString("invalid-args"));
                                }
                            } else if (args[2].equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                                if (args.length > 5) {
                                    try {
                                        DyeColor.valueOf(args[5]);
                                        g.put(true, "");
                                    } catch (Exception e) {
                                        g.put(false, hpc.getString("invalid-args"));
                                    }
                                } else {
                                    g.put(true, "");
                                }
                            } else {
                                g.put(true, "");
                            }
                        } else {
                            g.put(false, hpc.getString("invalid-args"));
                        }
                    } else {
                        g.put(false, hpc.getString("invalid-args"));
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("name")
                                || args[3].equalsIgnoreCase("lore")
                                || args[3].equalsIgnoreCase("mask")) {
                            if (args[4].matches("^[0-9]+$")) {
                                if (args[2].equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                                    if (args.length > 6) {
                                        try {
                                            DyeColor.valueOf(args[6]);
                                            g.put(true, "");
                                        } catch (Exception e) {
                                            g.put(false, hpc.getString("invalid-args"));
                                        }
                                    } else {
                                        g.put(true, "");
                                    }
                                } else {
                                    g.put(true, "");
                                }
                            } else {
                                g.put(false, hpc.getString("invalid-args"));
                            }
                        } else {
                            g.put(false, hpc.getString("invalid-args"));
                        }
                    } else {
                        g.put(false, hpc.getString("invalid-args"));
                    }
                } else {
                    g.put(false, hpc.getString("invalid-args"));
                }
            } else {
                g.put(false, hpc.getString("invalid-args"));
            }
        } else {
            g.put(false, hpc.getString("invalid-args"));
        }
        return g;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            if (args.length > 3) {
                String type = args[2];
                HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
                if (args[1].equalsIgnoreCase("view")) {
                    if (args[3].equalsIgnoreCase("name")) {
                        if (args.length > 4) {
                            sender.sendMessage(printNameInfo(type, Integer.parseInt(args[4])));
                        } else {
                            sender.sendMessage(printNameInfo(type, 1));
                        }
                    } else if (args[3].equalsIgnoreCase("mask")) {
                        if (args.length > 4) {
                            sender.sendMessage(printMaskInfo(type, Integer.parseInt(args[4])));
                        } else {
                            sender.sendMessage(printMaskInfo(type, 1));
                        }
                    } else if (args[3].equalsIgnoreCase("lore")) {
                        if (args.length > 4) {
                            sender.sendMessage(printLoreInfo(type, Integer.parseInt(args[4])));
                        } else {
                            sender.sendMessage(printLoreInfo(type, 1));
                        }
                    } else {
                        sender.sendMessage(printInfo(type));
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("set")) {
                    hpch.getConfig().set(type + "." + args[3], args[4]);
                    sender.sendMessage(hpc.getString("set-value")
                            .replaceAll("\\{value}", args[4])
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));
                } else if (args[1].equalsIgnoreCase("add")) {
                    if (type.equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                        if (args.length > 5) {
                            List<String> s = hpch.getConfig().getStringList(type + ".name." + args[5].toUpperCase());
                            s.add(args[4]);

                        } else {
                            List<String> s = hpch.getConfig().getStringList(type + ".name.default");
                            s.add(args[4]);
                        }
                    } else {
                        if (args[3].equalsIgnoreCase("mask")) {
                            if (args.length > 5) {
                                List<Integer> st = hpch.getConfig().getIntegerList(type + ".mask-amplifiers");
                                st.add(Integer.parseInt(args[5]));
                            } else {
                                List<Integer> st = hpch.getConfig().getIntegerList(type + ".mask-amplifiers");
                                st.add(1);
                            }
                        }
                        List<String> s = hpch.getConfig().getStringList(type + "." + args[3]);
                        s.add(args[4]);
                    }
                    sender.sendMessage(hpc.getString("add-value")
                            .replaceAll("\\{value}", args[4])
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));

                } else if (args[1].equalsIgnoreCase("remove")) {
                    List<String> s;
                    int p = Integer.parseInt(args[4]);
                    if (type.equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                        if (args.length > 5) {
                            s = hpch.getConfig().getStringList(type + ".name." + args[5].toUpperCase());
                        } else {
                            s = hpch.getConfig().getStringList(type + ".name.default");
                        }
                    } else {
                        s = hpch.getConfig().getStringList(type + "." + args[3]);
                        if (args[3].equalsIgnoreCase("mask")) {
                            List<Integer> st = hpch.getConfig().getIntegerList(type + ".mask-amplifiers");
                            st.remove(p);
                        }
                    }
                    String value = s.get(p);
                    s.remove(p);
                    sender.sendMessage(hpc.getString("remove-value")
                            .replaceAll("\\{value}", value)
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));
                }
                hpch.getConfig().options().copyDefaults(true);
                hpch.save();
                return true;
            }
        } catch (Exception e) {
            new DebugPrint(e, "Head Information command", true, sender);
        }

        return false;
    }

    private boolean isValueValid(String option, String value) {
        if (option.equalsIgnoreCase("chance") || option.equalsIgnoreCase("price")) {
            try {
                Double.valueOf(value);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        } else {
            return true;
        }
    }

    private String printInfo(String type) {
        StringBuilder sb = new StringBuilder();
        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
        Locale l = LocaleManager.getLocale();
        FileConfiguration fc = HeadsPlus.getInstance().getConfig();
        sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus ").append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============");
        sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append("\n").append(l.type()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(type);
        sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append("\n").append(l.displayName()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(hpch.getConfig().getString(type + ".display-name"));
        sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append("\n").append(l.price()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(hpch.getConfig().getDouble(type + ".price"));
        sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append("\n").append(l.interactName()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(hpch.getConfig().getString(type + ".interact-name"));
        sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append("\n").append(l.chance()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(hpch.getConfig().getDouble(type + ".chance"));
        return sb.toString();
    }

    private String printNameInfo(String type, int page) {
        try {
            Locale l = LocaleManager.getLocale();
            HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
            FileConfiguration fc = HeadsPlus.getInstance().getConfig();
            if (type.equalsIgnoreCase("sheep")) {
                List<Head> h = new ArrayList<>();
                for (String t : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                    for (String r : hpch.getConfig().getStringList("sheep.name." + t)) {
                        h.add(new Head(r, t));
                    }
                }
                PagedLists<Head> hs = new PagedLists<>(h, 8);
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(String.valueOf(page)).append("/").append(String.valueOf(hs.getTotalPages())).append(" ").append(fc.getString("themeColor1")).append("===============\n");
                sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append(l.type()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(type).append("\n");
                for (Head o : hs.getContentsInPage(page)) {
                    sb.append(ChatColor.valueOf(fc.getString("themeColor3"))).append(o.type).append(" (").append(o.colour).append(")\n");
                }
                return sb.toString();
            } else {
                PagedLists<String> names = new PagedLists<>(hpch.getConfig().getStringList(type + ".name"), 8);
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(String.valueOf(page)).append("/").append(String.valueOf(names.getTotalPages())).append(" ").append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============\n");
                sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append(l.type()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(type).append("\n");
                for (String s : names.getContentsInPage(page)) {
                    sb.append(ChatColor.valueOf(fc.getString("themeColor3"))).append(s).append("\n");
                }
                return sb.toString();
            }
        } catch (IllegalArgumentException ex) {
            return hpc.getString("invalid-pg-no");
        }

    }

    private String printMaskInfo(String type, int page) {
        try {
            HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
            List<Mask> m = new ArrayList<>();
            Locale l = LocaleManager.getLocale();
            StringBuilder sb = new StringBuilder();
            FileConfiguration fc = HeadsPlus.getInstance().getConfig();
            if (hpch.getConfig().getStringList(type + ".mask-effects").size() < 1) {
                return hpc.getString("no-mask-data");
            }
            for (int i = 0; i < hpch.getConfig().getStringList(type + ".mask-effects").size(); i++) {
                String s = hpch.getConfig().getStringList(type + ".mask-effects").get(i);
                int a;
                try {
                    a = hpch.getConfig().getIntegerList(type + ".mask-amplifiers").get(i) + 1;
                } catch (IndexOutOfBoundsException ex) {
                    a = 1;
                }
                m.add(new Mask(type, a, s));

            }
            PagedLists<Mask> s = new PagedLists<>(m, 8);
            sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(String.valueOf(page)).append("/").append(String.valueOf(s.getTotalPages())).append(" ").append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============\n");
            sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append(l.type()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(type).append("\n");
            for (Mask sm : s.getContentsInPage(page)) {
                sb.append(ChatColor.valueOf(fc.getString("themeColor3"))).append(sm.effect).append(" (").append(sm.amplifier).append(")\n");
            }
            return sb.toString();
        } catch (IllegalArgumentException ex) {
            return hpc.getString("invalid-pg-no");
        }
    }

    private String printLoreInfo(String type, int page) {
        try {
            FileConfiguration fc = HeadsPlus.getInstance().getConfig();
            HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
            Locale l = LocaleManager.getLocale();
            PagedLists<String> lore = new PagedLists<>(hpch.getConfig().getStringList(type + ".lore"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============").append(ChatColor.valueOf(fc.getString("themeColor2"))).append(" HeadsPlus ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(String.valueOf(page)).append("/").append(String.valueOf(lore.getTotalPages())).append(" ").append(ChatColor.valueOf(fc.getString("themeColor1"))).append("===============\n");
            sb.append(ChatColor.valueOf(fc.getString("themeColor4"))).append(l.type()).append(" ").append(ChatColor.valueOf(fc.getString("themeColor3"))).append(type).append("\n");
            for (String s : lore.getContentsInPage(page)) {
                sb.append(ChatColor.valueOf(fc.getString("themeColor3"))).append(ChatColor.translateAlternateColorCodes('&', s));
            }
            return sb.toString();
        } catch (IllegalArgumentException ex) {
            return hpc.getString("invalid-pg-no");
        }
    }

    private class Head {
        String type;
        String colour;

        Head(String t, String c) {
            type = t;
            colour = c;
        }
    }

    private class Mask {
        String type;
        int amplifier;
        String effect;

        Mask(String t, int a, String e) {
            type = t;
            amplifier = a;
            effect = e;
        }
    }
}
