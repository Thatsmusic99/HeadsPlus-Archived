package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.listeners.DeathEvents;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Conjure implements IHeadsPlusCommand {

    // F
    private ItemStack head = null;
    HeadsPlusMessagesConfig hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdName() {
        return "conjure";
    }

    @Override
    public String getPermission() {
        return "headsplus.maincommand.conjure";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descConjure();
    }

    @Override
    public String getSubCommand() {
        return "conjure";
    }

    @Override
    public String getUsage() {
        return "/hp conjure <Entity> [Amount] [Index] [Colour]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        if (sender instanceof Player) {
            if (args.length > 1) {
                HeadsPlusConfigHeads heads = HeadsPlus.getInstance().getHeadsConfig();
                List<String> mHeads = heads.mHeads;
                List<String> uHeads = heads.uHeads;
                if (mHeads.contains(args[1]) || uHeads.contains(args[1])) {
                    int amount = 1;
                    if (args.length > 2) {
                        if (args[2].matches("^[0-9]+$")) {
                            amount = Integer.parseInt(args[2]);
                        } else {
                            h.put(false, hpc.getString("invalid-input-int"));
                        }
                    }
                    int index = 0;
                    String type = "default";
                    if (args.length > 3) {
                        if (args[3].matches("^[0-9]+$")) {
                            index = Integer.parseInt(args[3]);
                        } else {
                            h.put(false, hpc.getString("invalid-input-int"));
                        }
                    }
                    if (args.length > 4) {
                        type = args[4];
                    }
                    DeathEvents de = HeadsPlus.getInstance().getDeathEvents();
                    try {
                        ItemStack i = de.heads.get(de.prettyStringToEntity(args[1])).get(type).get(index);
                        double price = heads.getPrice(args[1]);
                        SkullMeta sm = (SkullMeta) i.getItemMeta();
                        String displayname = heads.getDisplayName(args[1]);
                        sm.setDisplayName(displayname);
                        List<String> strs = new ArrayList<>();
                        List<String> lore = heads.getLore(args[1]);
                        for (String str : lore) {
                            strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{type}", args[1]).replaceAll("\\{price}", String.valueOf(price))));
                        }
                        sm.setLore(strs);
                        i.setItemMeta(sm);
                        NMSManager nms = HeadsPlus.getInstance().getNMS();
                        i = nms.addNBTTag(i);
                        i = nms.setType(args[1], i);
                        i = nms.setPrice(i, price);
                        i.setAmount(amount);
                        this.head = i;

                        h.put(true, "");
                    } catch (NullPointerException ex) {
                        h.put(false,  hpc.getString("invalid-args"));
                    } catch (IndexOutOfBoundsException e) {
                        h.put(false, hpc.getString("invalid-pg-no"));
                    }
                } else {
                    h.put(false, hpc.getString("invalid-args"));
                }
            } else {
                h.put(false, hpc.getString("invalid-args"));
            }
        } else {
            h.put(false, ChatColor.RED + "You must be a player to run this command!");
        }
        return h;
    }

    @Override
    public boolean isMainCommand() {
        return true;
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        ((Player) sender).getWorld().dropItem(((Player) sender).getLocation(), head).setPickupDelay(0);
        return false;
    }
}
