package io.github.thatsmusic99.headsplus.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.api.SellHeadEvent;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SellHead implements CommandExecutor {
	
	private boolean sold;
	private HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().hpch;
	private HeadsPlusConfig hpc = HeadsPlus.getInstance().hpc;
	private HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().hpchx;
	private List<String> soldHeads = new ArrayList<>();
	private HashMap<String, Integer> hm = new HashMap<>();
    private final String disabled = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("disabled")));

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (sender instanceof Player) {
			    if (HeadsPlus.getInstance().sellable && HeadsPlus.getInstance().getConfig().getBoolean("sellHeads")) {

			        soldHeads.clear();
			        hm.clear();
                    ItemStack invi = checkHand((Player) sender);
                    if (args.length == 0 && (checkHand((Player) sender).getType() == Material.SKULL_ITEM) && (sender.hasPermission("headsplus.sellhead"))) { // If sold via hand
                        if (((SkullMeta)invi.getItemMeta()).getOwner() == null || ((SkullMeta)invi.getItemMeta()).getOwner().equalsIgnoreCase("HPXHead")) {
                            if (HeadsPlus.getInstance().nms.isSellable(invi)) {
                                for (String key : hpch.mHeads) {
                                    if (!key.equalsIgnoreCase("sheep")) {
                                        if (checkSkullForTexture((Player) sender, invi, hpch.getConfig().getStringList(key + ".name"), key, args)) {
                                            return true; // Ends because head is already sold
                                        }
                                    } else {
                                        if (checkColorSheep((Player) sender, args, invi)) {
                                            return true;
                                        }
                                    }
                                }
                                for (String key : hpch.uHeads) {
                                    if (checkSkullForTexture((Player) sender, invi, hpch.getConfig().getStringList(key + ".name"), key, args)) {
                                        return true;
                                    }
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("false-head")));
                                return true;
                            }
                        }
                        SkullMeta skullM = (SkullMeta) invi.getItemMeta();
                        String owner = skullM.getOwner();
		                if (HeadsPlus.getInstance().nms.isSellable(invi)) {
                            Economy econ = HeadsPlus.getInstance().econ;
                            List<String> mHeads = hpch.mHeads;
                            List<String> uHeads = hpch.uHeads;
                            for (String key : mHeads) {
                                if (key.equalsIgnoreCase("sheep")) {
                                    for (String s : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                        for (int i = 0; i < hpch.getConfig().getStringList("sheep.name." + s).size(); i++) {
                                            if (owner.matches(hpch.getConfig().getStringList("sheep.name." + s).get(i))) {
                                                a(invi, key, args, (Player) sender);
                                            }
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < hpch.getConfig().getStringList(key + ".name").size(); i++) {
                                        if (owner.matches(hpch.getConfig().getStringList(key + ".name").get(i))) {
                                            a(invi, key, args, (Player) sender);
                                        } else if ((owner.matches(hpch.getConfig().getStringList("irongolem.name").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                                            a(invi, key, args, (Player) sender);
                                        }
                                    }
                                }
                            }
                            for (String key : uHeads) {
                                if (owner.equalsIgnoreCase(hpch.getConfig().getString(key + ".name"))) {
                                    Double price = hpch.getConfig().getDouble(key + ".price");
                                    if (invi.getAmount() > 0) {
                                        price = setPrice(price, args, invi, (Player) sender);
                                        SellHeadEvent she = new SellHeadEvent(price, soldHeads, (Player) sender, econ.getBalance((Player) sender), econ.getBalance((Player) sender) + price, hm);
                                        Bukkit.getServer().getPluginManager().callEvent(she);
                                        if (!she.isCancelled()) {
                                            EconomyResponse zr = econ.depositPlayer((Player) sender, price);
                                            String success = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
                                            success = ChatColor.translateAlternateColorCodes('&', success);
                                            String fail = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-fail"));
                                            fail = ChatColor.translateAlternateColorCodes('&', fail);
                                            if (zr.transactionSuccess()) {
                                                if (price > 0) {
                                                    itemRemoval((Player) sender, args, invi);
                                                    sender.sendMessage(success);
                                                    sold = true;
                                                }
                                            } else {
                                                sender.sendMessage(fail + ": " + zr.errorMessage);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!sold) {
                                Double price = hpch.getConfig().getDouble("player.price");
                                if (invi.getAmount() > 0) {
                                    price = setPrice(price, args, invi, (Player) sender);
                                }
                                SellHeadEvent she = new SellHeadEvent(price, soldHeads, (Player) sender, econ.getBalance((Player) sender), econ.getBalance((Player) sender) + price, hm);
                                Bukkit.getServer().getPluginManager().callEvent(she);
                                if (!she.isCancelled()) {
                                    EconomyResponse zr = econ.depositPlayer((Player) sender, price);
                                    String success = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
                                    success = ChatColor.translateAlternateColorCodes('&', success);
                                    String fail = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-fail"));
                                    fail = ChatColor.translateAlternateColorCodes('&', fail);
                                    if (zr.transactionSuccess()) {
                                        itemRemoval((Player) sender, args, invi);
                                        sender.sendMessage(success);
                                        sold = true;
                                    } else {
                                        sender.sendMessage(fail + ": " + zr.errorMessage);
                                    }
                                }
                            }
                            if (!sold) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("false-head")));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("false-head")));
                        }
                    } else {
                        if (!sender.hasPermission("headsplus.sellhead")) {
                            sender.sendMessage(new HeadsPlusCommand().noPerms);
                        } else if (args.length > 0) {
                            if (args[0].equalsIgnoreCase("all")) {
                                sellAll((Player) sender, args, invi);
                            } else if (args[0] != null) {
                                Player p = (Player) sender;
                                double price = 0.0;
                                for (ItemStack i : p.getInventory()) {
                                    if (i != null) {
                                        boolean found = false;
                                        if (i.getType() == Material.SKULL_ITEM) {
                                            SkullMeta sm = (SkullMeta) i.getItemMeta();
                                            for (String str : hpch.mHeads) {
                                                try {
                                                    if (sm.getOwner() != null) {
                                                        if (str.equalsIgnoreCase("sheep")) {
                                                            for (String s : hpch.getConfig().getConfigurationSection(str + ".name").getKeys(false)) {
                                                                for (int in = 0; in < hpch.getConfig().getStringList(str + ".name." + s).size(); in++) {
                                                                    if (sm.getOwner().equalsIgnoreCase(hpch.getConfig().getStringList(str + ".name." + s).get(in))) {
                                                                        found = true;
                                                                        price = setPrice(price, args, i, p);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            for (int in = 0; in < hpch.getConfig().getStringList(str + ".name").size(); in++) {
                                                                if (sm.getOwner().equalsIgnoreCase(hpch.getConfig().getStringList(str + ".name").get(in))) {
                                                                    found = true;
                                                                    price = setPrice(price, args, i, p);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (NullPointerException ex) {
                                                    //
                                                }
                                            }
                                            for (String str : hpch.uHeads) {
                                                try {
                                                    if (sm.getOwner() != null) {
                                                        if (sm.getOwner().equalsIgnoreCase(hpch.getConfig().getString(str + ".name"))) {
                                                            found = true;
                                                            price = setPrice(price, args, i, p);
                                                        }
                                                    }
                                                } catch (NullPointerException ex) {
                                                    //
                                                }
                                            }
                                            if (!found) {
                                                if (args[0].equalsIgnoreCase("player")) {
                                                    price = setPrice(price, args, i, p);
                                                } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                                    for (String key : hpch.mHeads) {
                                                        if (!key.equalsIgnoreCase("sheep")) {
                                                            price = b(price, key, hpch.getConfig().getStringList(key + ".name"), i, (Player) sender, args, true, false);
                                                        } else {
                                                            for (String s : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                                                price = b(price, key, hpch.getConfig().getStringList(key + ".name." + s), i, (Player) sender, args, true, false);
                                                            }
                                                        }
                                                    }
                                                    for (String key : hpch.uHeads) {
                                                        price = b(price, key, hpch.getConfig().getStringList(key + ".name"), i, (Player) sender, args, true, false);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } if (price == 0.0) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("no-heads")));
                                    return true;
                                }
                                pay(p, args, new ItemStack(Material.AIR), price);
                            }
                        } else {
                            String falseItem = hpc.getConfig().getString("false-item");
                            falseItem = HeadsPlus.getInstance().translateMessages(falseItem);
                            falseItem = ChatColor.translateAlternateColorCodes('&', falseItem);
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
			e.printStackTrace();
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
	private void itemRemoval(Player p, String[] a, ItemStack i) {
		if (a.length > 0) { 
			if (a[0].equalsIgnoreCase("all")) {
				for (ItemStack is : p.getInventory()) {
					if (is != null) {
						if (is.getType() == Material.SKULL_ITEM) {
                            if (HeadsPlus.getInstance().nms.isSellable(is)) {
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
		    } else if (a[0] != null) {
		    	for (ItemStack is : p.getInventory()) {
		    		if (is != null) {
		    			if (is.getType() == Material.SKULL_ITEM) {
		    				SkullMeta sm = (SkullMeta) is.getItemMeta();
                            if (HeadsPlus.getInstance().nms.isSellable(is)) {
								boolean found = false;
								for (String s : hpch.mHeads) {
								    if (s.equalsIgnoreCase("sheep")) {
                                        for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                            found = c(s, a, is, hpch.getConfig().getStringList(s + ".name." + st), p, true);
                                        }
                                    } else {
                                        found = c(s, a, is, hpch.getConfig().getStringList(s + ".name"), p, true);
                                    }
							    }
							    for (String s : hpch.uHeads) {
                                    found = c(s, a, is, hpch.getConfig().getStringList(s + ".name"), p, true);
                                }
                                if (!found && a[0].equalsIgnoreCase("player")) {
                                    boolean player = true;
                                    SkullMeta sms = (SkullMeta) is.getItemMeta();
                                    for (String s : hpch.uHeads) {
                                        player = d(hpch.getConfig().getStringList(s + ".name"), sms);
                                    }
                                    for (String s : hpch.mHeads) {
                                        if (s.equalsIgnoreCase("sheep")) {
                                            for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                                player = d(hpch.getConfig().getStringList(s + ".name." + st), sms);
                                            }
                                        } else {
                                            player = d(hpch.getConfig().getStringList(s + ".name"), sms);
                                        }
                                    }
                                    if (player) {

                                        List<String> lst = new ArrayList<>();
                                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                            lst.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                        }
                                        if ((sms.getLore() != null) && (sms.getLore().size() == 2) && (sms.getLore().equals(lst))) {
                                            p.getInventory().remove(is);
                                        }
                                    }
							    } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
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
		    } else {
		    	setHand(p, new ItemStack(Material.AIR));
		    }
		} else {
			setHand(p, new ItemStack(Material.AIR));
		}
	}
	private double setPrice(Double p, String[] a, ItemStack i, Player pl) {
		if (a.length > 0) { 
			if (!a[0].matches("^[0-9]+$")) {
				if (a[0].equalsIgnoreCase("all")) {
					if (i.getType().equals(Material.SKULL_ITEM)) {
						SkullMeta sm = (SkullMeta) i.getItemMeta();
                        List<String> ls = new ArrayList<>();
                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                        }
						// if ((sm.getLore() != null) && (sm.getLore().equals(ls))) {
                        if (HeadsPlus.getInstance().nms.isSellable(i)) {
							boolean found = false;
							for (String s : hpch.mHeads) {
							    if (s.equalsIgnoreCase("sheep")) {
							        for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
							            if (!Objects.equals(e(hpch.getConfig().getStringList(s + ".name." + st), p, s, sm, i), p)) {
                                            p = e(hpch.getConfig().getStringList(s + ".name." + st), p, s, sm, i);
                                            soldHeads.add(s);
                                            i(s);
                                            found = true;
                                            break;
                                        }
                                    }
                                } else {
                                    if (!Objects.equals(e(hpch.getConfig().getStringList(s + ".name"), p, s, sm, i), p)) {
                                        p = e(hpch.getConfig().getStringList(s + ".name"), p, s, sm, i);
                                        soldHeads.add(s);
                                        i(s);
                                        found = true;
                                        break;
                                    }
                                }
						    }
						    for (String s : hpch.uHeads) {
                                if (!Objects.equals(e(hpch.getConfig().getStringList(s + ".name"), p, s, sm, i), p)) {
                                    p = e(hpch.getConfig().getStringList(s + ".name"), p, s, sm, i);
                                    soldHeads.add(s);
                                    i(s);
                                    found = true;
                                    break;
                                }
						    }
                            if (!Objects.equals(e(hpch.getConfig().getStringList("irongolem.name"), p, "irongolem", sm, i), p)) {
                                p = e(hpch.getConfig().getStringList("irongolem.name"), p, "irongolem", sm, i);
                                soldHeads.add("irongolem");
                                i("irongolem");
                                found = true;
                            }

						    if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                for (String key : hpch.mHeads) {
                                    if (key.equalsIgnoreCase("sheep")) {
                                        for (String s : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                            if (!b(p, key, hpch.getConfig().getStringList(key + ".name." + s), i, pl, a, false, true).equals(p)) {
                                                p = b(p, key, hpch.getConfig().getStringList(key + ".name." + s), i, pl, a, false, true);
                                                soldHeads.add(key);
                                                i(key);
                                                found = true;
                                                break;
                                            }
                                        }
                                    } else {
                                        if (!b(p, key, hpch.getConfig().getStringList(key + ".name"), i, pl, a, false, true).equals(p)) {
                                            p = b(p, key, hpch.getConfig().getStringList(key + ".name"), i, pl, a, false, true);
                                            soldHeads.add(key);
                                            i(key);
                                            found = true;
                                            break;
                                        }
                                    }

                                }
                                for (String key : hpch.uHeads) {
                                    if (!b(p, key, hpch.getConfig().getStringList(key + ".name"), i, pl, a, false, true).equals(p)) {
                                        p = b(p, key, hpch.getConfig().getStringList(key + ".name"), i, pl, a, false, true);
                                        soldHeads.add(key);
                                        i(key);
                                        found = true;
                                        break;
                                    }
                                }
                            }
						    if (!found) {
						    	p = p + (i.getAmount() * hpch.getConfig().getDouble("player.price"));
                                soldHeads.add("player");
                                i("player");
						    }
						}
					}
				} else if (a[0] != null) {
					for (String s : hpch.mHeads) {
						if (a[0].equalsIgnoreCase(s)) {
						    SkullMeta sm = (SkullMeta) i.getItemMeta();
						    if (s.equalsIgnoreCase("sheep")) {
						        for (String st : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                                    p = f(i, sm, p, s, a, hpch.getConfig().getStringList(s + ".name." + st));
                                }
                            } else {
                                p = f(i, sm, p, s, a, hpch.getConfig().getStringList(s + ".name"));
                            }
						}
					}
					for (String s : hpch.uHeads) {
                        SkullMeta sm = (SkullMeta) i.getItemMeta();
						if (a[0].equalsIgnoreCase(s)) {
                            p = f(i, sm, p, s, a, hpch.getConfig().getStringList(s + ".name"));
                        }
					}
					if (a[0].equalsIgnoreCase("player")) {
					    boolean player = true;
					    SkullMeta sm = (SkullMeta) i.getItemMeta();
					    for (String s : hpch.uHeads) {
					        player = d(hpch.getConfig().getStringList(s + ".name"), sm);
                        }
                        for (String s : hpch.mHeads) {
					        if (s.equalsIgnoreCase("sheep")) {
					            for (String st : hpch.getConfig().getConfigurationSection(s + ".name").getKeys(false)) {
                                    player = d(hpch.getConfig().getStringList(s + ".name." + st), sm);
                                }
                            } else {
                                player = d(hpch.getConfig().getStringList(s + ".name"), sm);
                            }
                        }
                        if (player) {

                            List<String> ls = new ArrayList<>();
                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                            }
                            if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
                                p = p + (i.getAmount() * hpch.getConfig().getDouble("player.price"));
                                soldHeads.add("player");
                                i("player");
                            }
                        }
                    }
				}	
			} else {
				if (Integer.parseInt(a[0]) <= i.getAmount()) {
					SkullMeta sm = (SkullMeta) i.getItemMeta();
					String s = null;
					for (String str : hpch.mHeads) {
                        if (str.equalsIgnoreCase("sheep")) {
                            for (String st : hpch.getConfig().getConfigurationSection(s + ".name").getKeys(false)) {
                                s = g(hpch.getConfig().getStringList(str + ".name." + st), sm, str);
                            }
                        } else {
                            s = g(hpch.getConfig().getStringList(str + ".name"), sm, str);
                        }
					}
					p = hpch.getConfig().getDouble(s + ".price") * Integer.parseInt(a[0]);
                    soldHeads.add(s);
                    i(s);
				} else {
					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("not-enough-heads")));
				}
			}
		} else {
			SkullMeta sm = (SkullMeta) i.getItemMeta();
			String s = null;
			for (String str : hpch.mHeads) {
                if (str.equalsIgnoreCase("sheep")) {
                    for (String st : hpch.getConfig().getConfigurationSection(str + ".name").getKeys(false)) {
                        s = g(hpch.getConfig().getStringList(str + ".name." + st), sm, str);

                    }
                } else {
                    s = g(hpch.getConfig().getStringList(str + ".name"), sm, str);

                }
			}
            for (String str : hpch.uHeads) {
                s = g(hpch.getConfig().getStringList(str + ".name"), sm, str);

            }
            if (s == null) {
			    s = "player";
            }
			p = hpch.getConfig().getDouble(s + ".price") * i.getAmount();
            soldHeads.add(s);
            i(s);
	    }
		return p;
	}
	private void sellAll(Player p, String[] a, ItemStack i) {
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
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', hpc.getConfig().getString("no-heads")));
            return;
        }

		pay(p, a, i, price);
	}
	private void pay(Player p, String[] a, ItemStack i, double pr) {
		Economy econ = HeadsPlus.getInstance().econ;
		SellHeadEvent she = new SellHeadEvent(pr, soldHeads, p, econ.getBalance(p), econ.getBalance(p) + pr, hm);
		Bukkit.getServer().getPluginManager().callEvent(she);
		if (!she.isCancelled()) {
            EconomyResponse zr = econ.depositPlayer(p, pr);
            String success = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
            success = ChatColor.translateAlternateColorCodes('&', success);
            String fail = HeadsPlus.getInstance().translateMessages(hpc.getConfig().getString("sell-fail"));
            fail = ChatColor.translateAlternateColorCodes('&', fail);

            if (zr.transactionSuccess()) {
                itemRemoval(p, a, i);
                p.sendMessage(success);
                sold = true;
            } else {
                p.sendMessage(fail + ": " + zr.errorMessage);
            }
        }
	}

	private boolean checkColorSheep(Player sender, String[] args, ItemStack invi) throws NoSuchFieldException, IllegalAccessException {
	    for (String s : hpch.getConfig().getConfigurationSection( "sheep.name").getKeys(false)) {
	        for (int i = 0; i < hpch.getConfig().getStringList("sheep.name." + s).size(); i++) {
                if (hpchx.isHPXSkull(hpch.getConfig().getStringList("sheep.name." + s).get(i))) {
                    Field pro = ((SkullMeta) invi.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(invi.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(hpchx.getTextures(hpch.getConfig().getStringList("sheep.name." + s).get(i)))) {
                            Double price = hpch.getConfig().getDouble("sheep.price");
                            if (invi.getAmount() > 0) {
                                price *= invi.getAmount();
                            }
                            pay(sender, args, invi, price);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean checkSkullForTexture(Player pl, ItemStack is, List<String> list, String key, String[] args) throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < list.size(); i++) {
            if (hpchx.isHPXSkull(list.get(i))) {
                Field pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                pro.setAccessible(true);
                GameProfile gm = (GameProfile) pro.get(is.getItemMeta());
                for (Property p : gm.getProperties().get("textures")) {
                    if (p.getValue().equals(hpchx.getTextures(hpch.getConfig().getStringList(key + ".name").get(i)))) {
                        Double price = hpch.getConfig().getDouble(key + ".price");
                        if (is.getAmount() > 0) {
                            price *= is.getAmount();
                        }
                        pay(pl, args, is, price);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void a(ItemStack is, String key, String[] args, Player p) {
        Double price = hpch.getConfig().getDouble(key + ".price");
        soldHeads.add(key);
        i(key);
        if (is.getAmount() > 0) {
            price = setPrice(price, args, is, p);
        }
        pay(p, args, is, price);
    }

    private Double b(Double price, String key, List<String> ls, ItemStack i, Player p, String[] args, boolean e, boolean f) {
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
                                i(key);
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

    private boolean c(String s, String[] a, ItemStack is, List<String> ls, Player p, boolean f) {
        for (String l : ls) {
            if (a[0].equalsIgnoreCase(s)) {
                if (((SkullMeta) is.getItemMeta()).getOwner().equals(l)) {
                    if (f) {
                        p.getInventory().remove(is);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d(List<String> ls, SkullMeta sms) {
        for (String l : ls) {
            if (sms.getOwner().equalsIgnoreCase(l)) {
                return false;
            }
        }
        return true;
    }
    private Double e(List<String> ls, Double p, String s, SkullMeta sm, ItemStack i) {
        for (String l : ls) {
            if (sm.getOwner().equals(l)) {
                p = p + (i.getAmount() * hpch.getConfig().getDouble(s + ".price"));
                return p;
            }
        }
        return p;
    }

    private Double f(ItemStack i, SkullMeta sm, Double p, String s, String[] a, List<String> ls2) {
        for (String aLs2 : ls2) {

            if (sm.getOwner().equalsIgnoreCase(aLs2)) {

                if (HeadsPlus.getInstance().nms.isSellable(i)) {
                    soldHeads.add(s);
                    i(s);
                    p = p + (i.getAmount() * hpch.getConfig().getDouble(s + ".price"));
                }
            } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                if (hpchx.isHPXSkull(aLs2)) {

                    GameProfile gm = h(i);
                    for (Property pr : gm.getProperties().get("textures")) {
                        if (pr.getValue().equals(hpchx.getTextures(aLs2))) {
                            if (i.getAmount() > 0) {
                                if (HeadsPlus.getInstance().nms.isSellable(i)) {
                                    soldHeads.add(s);
                                    i(s);
                                    p = p + (i.getAmount() * hpch.getConfig().getDouble(s + ".price"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return p;
    }

    private String g(List<String> ls, SkullMeta sm, String str) {
        for (String l : ls) {
            if (sm.getOwner().equalsIgnoreCase(l)) {
                return str;
            }
        }
        return null;
    }

    private GameProfile h(ItemStack i) {
        Field pro = null;
        try {
            pro = ((SkullMeta) i.getItemMeta()).getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        pro.setAccessible(true);
        GameProfile gm = null;
        try {
            gm = (GameProfile) pro.get(i.getItemMeta());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return gm;
    }

    private void i(String s) {
	    if (hm.get(s) != null) {
	        int i = hm.get(s);
	        i++;
	        hm.put(s, i);
        } else {
	        hm.put(s, 1);
        }
    }
}