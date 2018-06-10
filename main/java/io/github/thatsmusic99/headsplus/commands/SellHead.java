package io.github.thatsmusic99.headsplus.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.SellHeadEvent;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.util.SellheadInventory;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;

public class SellHead implements CommandExecutor, IHeadsPlusCommand {

	private final HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
	private final HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().getHeadsXConfig();
	private final List<String> soldHeads = new ArrayList<>();
	private final HashMap<String, Integer> hm = new HashMap<>();
    private final String disabled = hpc.getString("disabled");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (sender instanceof Player) {
			    if (HeadsPlus.getInstance().canSellHeads()) {
			        Player p = (Player) sender;
			        soldHeads.clear();
			        hm.clear();
                    ItemStack invi = checkHand(p);
                    if (args.length == 0 && (sender.hasPermission("headsplus.sellhead"))) { // If sold via hand
                        SellheadInventory si = new SellheadInventory();
                        SellheadInventory.setSI(p, si);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                p.openInventory(si.changePage(false, true, p));
                            }
                        }.runTaskAsynchronously(HeadsPlus.getInstance());

                        /*if (nms().isSellable(invi)) {
                            String s = nms().getType(invi).toLowerCase();
                            if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
                                Double price;
                                if (invi.getAmount() > 0) {
                                    price = invi.getAmount() * hpch.getConfig().getDouble(s + ".price");
                                    soldHeads.add(s);
                                    hm.put(s, invi.getAmount());
                                    SellHeadEvent she = new SellHeadEvent(price, soldHeads, (Player) sender, HeadsPlus.getInstance().getEconomy().getBalance((Player) sender), HeadsPlus.getInstance().getEconomy().getBalance((Player) sender) + price, hm);
                                    Bukkit.getServer().getPluginManager().callEvent(she);
                                    if (!she.isCancelled()) {
                                        EconomyResponse zr = HeadsPlus.getInstance().getEconomy().depositPlayer((Player) sender, price);
                                            String success = hpc.getString("sell-success").replaceAll("\\{price}", Double.toString(zr.amount)).replaceAll("\\{balance}", Double.toString(zr.balance));
                                            if (zr.transactionSuccess()) {
                                                if (price > 0) {
                                                    itemRemoval((Player) sender, args, invi);
                                                    sender.sendMessage(success);

                                                }
                                            } else {
                                                sender.sendMessage(hpc.getString("cmd-fail"));
                                            }
                                        }
                                    }
                                }

                        } else {
                            sender.sendMessage(hpc.getString("false-head"));
                            return true;
                        } */
                    } else {
                        if (!sender.hasPermission("headsplus.sellhead")) {
                            sender.sendMessage(hpc.getString("no-perm"));
                        } else if (args.length > 0) {
                            if (args[0].equalsIgnoreCase("all")) {
                                sellAll(p, args, invi);
                            } else {
                                double price = 0.0;
                                for (ItemStack i : p.getInventory()) {
                                    if (i != null) {
                                    //    boolean found = false;
                                        if (i.getType() == Material.SKULL_ITEM && i.getDurability() == 3) {
                                            if (nms().isSellable(i)) {
                                                price = setPrice(price, args, i, p);
                                            }
                                        }
                                    }
                                } if (price == 0.0) {
                                    sender.sendMessage(hpc.getString("no-heads"));
                                    return true;
                                }
                                pay(p, args, new ItemStack(Material.AIR), price);
                            }
                        } else {
                            String falseItem = hpc.getString("false-item");
                            sender.sendMessage(falseItem);
                        }
                    }
                } else {
                    sender.sendMessage(disabled);
                }
            } else {
                sender.sendMessage("[HeadsPlus] You must be a player to run this command!");
            }
        } catch (Exception e) {
		    new DebugPrint(e, "Command (sellhead)", true, sender);
		}
        return false;
	}

	@SuppressWarnings("deprecation")
    private static ItemStack checkHand(Player p) {
		if (Bukkit.getVersion().contains("1.8")) {
			return p.getInventory().getItemInHand();
		} else {
			return p.getInventory().getItemInMainHand();
		}
	}
	@SuppressWarnings("deprecation")
	private void setHand(Player p, ItemStack i) {
		if (Bukkit.getVersion().contains("1.8")) {
			p.getInventory().setItemInHand(i);
		} else {
			p.getInventory().setItemInMainHand(i);
		}
	}
	private void itemRemoval(Player p, String[] a, ItemStack i) throws NoSuchFieldException, IllegalAccessException {
		if (a.length > 0) { 
			if (a[0].equalsIgnoreCase("all")) {
				for (ItemStack is : p.getInventory()) {
					if (is != null) {
						if (is.getType() == Material.SKULL_ITEM) {
                            if (nms().isSellable(is)) {
                                p.getInventory().remove(is);
                            }
					    }
					}
				}
			} else if (a[0].matches("^[0-9]+$")) { 
				if (i.getAmount() > Integer.parseInt(a[0])) {
					checkHand(p).setAmount(checkHand(p).getAmount() - Integer.parseInt(a[0]));
			    } else {
			    	setHand(p, new ItemStack(Material.AIR));
		        }
		    } else {
		    	for (ItemStack is : p.getInventory()) {
		    		if (is != null) {
		    			if (is.getType() == Material.SKULL_ITEM) {
		    				SkullMeta sm = (SkullMeta) is.getItemMeta();
                            if (nms().isSellable(is)) {
								boolean found = false;
								for (String s : hpch.mHeads) {
								    if (s.equalsIgnoreCase("sheep")) {
                                        for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                            found = c(s, a, is, hpch.getConfig().getStringList(s + ".name." + st), p);
                                        }
                                    } else {
                                        found = c(s, a, is, hpch.getConfig().getStringList(s + ".name"), p);
                                    }
							    }
							    for (String s : hpch.uHeads) {
                                    found = c(s, a, is, hpch.getConfig().getStringList(s + ".name"), p);
                                }
                                if (!found && a[0].equalsIgnoreCase("player")) {
                                    boolean player = true;
                                    SkullMeta sms = (SkullMeta) is.getItemMeta();
                                    for (String s : hpch.uHeads) {
                                        if (d(hpch.getConfig().getStringList(s + ".name"), sms)) {
                                            player = false;
                                            break;
                                        }
                                    }
                                    for (String s : hpch.mHeads) {
                                        if (s.equalsIgnoreCase("sheep")) {
                                            for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                                if (d(hpch.getConfig().getStringList(s + ".name." + st), sms)) {
                                                    player = false;
                                                    break;
                                                }
                                            }
                                        } else {
                                            if (d(hpch.getConfig().getStringList(s + ".name"), sms)) {
                                                player = false;
                                                break;
                                            }
                                        }
                                    }
                                    if (player) {

                                        if (nms().isSellable(is)){
                                            p.getInventory().remove(is);
                                        }
                                    }
							    } else if (nms().getSkullOwnerName(sm).equalsIgnoreCase("HPXHead")) {
                                    for (String key : hpch.mHeads) {
                                        if (key.equalsIgnoreCase("sheep")) {
                                            for (String s : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                                b(0.0, key, hpch.getConfig().getStringList(key + ".name." + s), is, p, a, false, false);
                                            }
                                        } else {
                                            b(0.0, key, hpch.getConfig().getStringList(key + ".name"), is, p, a, false, false);
                                        }

                                    }
                                    for (String key : hpch.uHeads) {
                                        b(0.0, key, hpch.getConfig().getStringList(key + ".name"), is, p, a, false, false);
                                    }
                                }
							}
		    			}
		    		}
		    	}
		    }
		} else {
			setHand(p, new ItemStack(Material.AIR));
		}
	}
	private double setPrice(Double p, String[] a, ItemStack i, Player pl) {
		if (a.length > 0) { // More than one argument
			if (!a[0].matches("^[0-9]+$")) { // More than one head
				if (a[0].equalsIgnoreCase("all")) { // Sell everything
					if (i.getType().equals(Material.SKULL_ITEM)) {
                        if (nms().isSellable(i)) {
							String s = nms().getType(i).toLowerCase();
							if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
							    soldHeads.add(s);
							    i(s, i.getAmount());
							    p += hpch.getConfig().getDouble(s + ".price");
                            }
						}
					}
				} else { // Selected mob
				    p = f(i, p, a[0]);
				}
			} else {
			    if (Integer.parseInt(a[0]) <= i.getAmount()) {
					if (nms().isSellable(i)) {
					    String s = nms().getType(i);
					    if (hpch.mHeads.contains(s) || hpch.uHeads.contains(s) || s.equalsIgnoreCase("player")) {
                            p = hpch.getConfig().getDouble(s + ".price") * Integer.parseInt(a[0]);
                            soldHeads.add(s);
                            i(s, i.getAmount());
                        }
                    }

				} else {
					pl.sendMessage(hpc.getString("not-enough-heads"));
				}
			}
		}
		return p;
	}
	private void sellAll(Player p, String[] a, ItemStack i) throws NoSuchFieldException, IllegalAccessException {
		Double price = 0.0;
		for (ItemStack is : p.getInventory()) {
            if (is != null) {
                if (is.getType().equals(Material.SKULL_ITEM)) {
                    price = setPrice(price, a, is, p);
                }
            }
        }
        try {
            if (hm.get("sheep") != 0 || hm.get("sheep") != null) {
                if (hm.get("sheep") % 3 == 0) {
                    hm.put("sheep", hm.get("sheep") / 3);
                }
            }
        } catch (Exception ignored) {

        }

        if (price == 0) {
            p.sendMessage(hpc.getString("no-heads"));
            return;
        }

		pay(p, a, i, price);
	}
	private void pay(Player p, String[] a, ItemStack i, double pr) throws NoSuchFieldException, IllegalAccessException {
		Economy econ = HeadsPlus.getInstance().getEconomy();
		SellHeadEvent she = new SellHeadEvent(pr, soldHeads, p, econ.getBalance(p), econ.getBalance(p) + pr, hm);
		Bukkit.getServer().getPluginManager().callEvent(she);
		if (!she.isCancelled()) {
            EconomyResponse zr = econ.depositPlayer(p, pr);
            String success = hpc.getString("sell-success").replaceAll("\\{price}", Double.toString(zr.amount)).replaceAll("\\{balance}", Double.toString(zr.balance));
            if (zr.transactionSuccess()) {
                itemRemoval(p, a, i);
                p.sendMessage(success);
            } else {
                p.sendMessage(hpc.getString("cmd-fail"));
            }
        }
	}

    private Double b(Double price, String key, List<String> ls, ItemStack i, Player p, String[] args, boolean e, boolean f) throws NoSuchFieldException, IllegalAccessException {
        for (String l : ls) {
            if (hpchx.isHPXSkull(l)) {
                GameProfile gm = h(i);
                for (Property pr : gm.getProperties().get("textures")) {
                    if (pr.getValue().equals(hpchx.getTextures(l))) {
                        if (i.getAmount() > 0) {
                            if (e) {
                                price = setPrice(price, args, i, p);
                                return price;
                            } else if (f) {
                                soldHeads.add(key);
                                i(key, i.getAmount());
                                price = price + (i.getAmount() * hpch.getConfig().getDouble(key + ".price"));
                                return price;
                            } else {
                                p.getInventory().remove(i);
                            }
                        }
                    }
                }
            }
        }
        return price;
    }

    private boolean c(String s, String[] a, ItemStack is, List<String> ls, Player p) {
        for (String l : ls) {
            if (a[0].equalsIgnoreCase(s)) {
                if (nms().getSkullOwnerName((SkullMeta) is.getItemMeta()).equals(l)) {
                    p.getInventory().remove(is);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d(List<String> ls, SkullMeta sms) {
        for (String l : ls) {
            if (nms().getSkullOwnerName(sms).equalsIgnoreCase(l)) {
                return true;
            }
        }
        return false;
    }

    private Double f(ItemStack i, Double p, String s) {
	    String st = nms().getType(i).toLowerCase();
	    if (nms().isSellable(i)) {
	        if (st.equalsIgnoreCase(s)) {
	            soldHeads.add(s);
	            i(s, i.getAmount());
                p = p + (i.getAmount() * hpch.getConfig().getDouble(s + ".price"));
            }
        }
        return p;
    }


    private GameProfile h(ItemStack i) throws NoSuchFieldException, IllegalAccessException {
        Field pro;
        pro = ((SkullMeta) i.getItemMeta()).getClass().getDeclaredField("profile");
        pro.setAccessible(true);
        GameProfile gm;
        gm = (GameProfile) pro.get(i.getItemMeta());
        return gm;
    }

    private void i(String s, int amount) {
	    if (hm.get(s) == null) {
            hm.put(s, amount);
            return;
        }
	    if (hm.get(s) > 0) {
	        int i = hm.get(s);
	        i += amount;
	        hm.put(s, i);
        } else {
	        hm.put(s, amount);
        }
    }

    @Override
    public String getCmdName() {
        return "sellhead";
    }

    @Override
    public String getPermission() {
        return "headsplus.sellhead";
    }

    @Override
    public String getCmdDescription() {
        return LocaleManager.getLocale().descSellhead();
    }

    @Override
    public String getSubCommand() {
        return "Sellhead";
    }

    @Override
    public String getUsage() {
        return "/sellhead [All|Entity|#]";
    }

    @Override
    public HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender) {
        HashMap<Boolean, String> h = new HashMap<>();
        if (args.length > 1) {
            if (args[1].matches("^[A-Za-z0-9_]+$")) {
                h.put(true, "");
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
        return false;
    }

    private NMSManager nms() {
	    return HeadsPlus.getInstance().getNMS();
    }
}