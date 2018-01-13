package io.github.thatsmusic99.headsplus.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;
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
	
	private static boolean sold;
	private static final String disabled = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("disabled")));
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (sender instanceof Player) {
			if (HeadsPlus.getInstance().sellable && HeadsPlus.getInstance().getConfig().getBoolean("sellHeads")) {
		    ItemStack invi = checkHand((Player) sender);
		    
		    if (args.length == 0 && (checkHand((Player) sender).getType() == Material.SKULL_ITEM) && (sender.hasPermission("headsplus.sellhead"))) {
		    	if (((SkullMeta)invi.getItemMeta()).getOwner() == null || ((SkullMeta)invi.getItemMeta()).getOwner().equalsIgnoreCase("HPXHead")) {
		    	    for (String key : HeadsPlusConfigHeads.mHeads) {
		    	        if (!key.equalsIgnoreCase("sheep")) {
                            for (int i = 0; i < HeadsPlusConfigHeads.getHeads().getStringList(key + "headN").size(); i++) {
                                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                                    Field pro = ((SkullMeta) invi.getItemMeta()).getClass().getDeclaredField("profile");
                                    pro.setAccessible(true);
                                    GameProfile gm = (GameProfile) pro.get(invi.getItemMeta());
                                    for (Property p : gm.getProperties().get("textures")) {
                                        if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(i)))) {
                                            Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
                                            if (invi.getAmount() > 0) {
                                                price *= invi.getAmount();
                                            }
                                            pay((Player) sender, args, invi, price);
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            checkColorSheep((Player) sender, args, invi);
                            return true;
                        }
                    }
                    for (String key : HeadsPlusConfigHeads.uHeads) {
                        if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                            Field pro = ((SkullMeta) invi.getItemMeta()).getClass().getDeclaredField("profile");
                            pro.setAccessible(true);
                            GameProfile gm = (GameProfile) pro.get(invi.getItemMeta());
                            for (Property p : gm.getProperties().get("textures")) {
                                if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN")))) {
                                    Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
                                    if (invi.getAmount() > 0) {
                                        price *= invi.getAmount();
                                    }
                                    pay((Player) sender, args, invi, price);
                                    return true;
                                }
                            }
                        }
                    }
                }
		    	
		    	SkullMeta skullM = (SkullMeta) invi.getItemMeta();
		        String owner = skullM.getOwner();
		        List<String> ls = new ArrayList<>();
		        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
		            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                }

		    	if ((skullM.getLore() != null) &&  (skullM.getLore().equals(ls))) {
		  
		    		Economy econ = HeadsPlus.getInstance().econ;
		    		List<String> mHeads = HeadsPlusConfigHeads.mHeads;
		    		List<String> uHeads = HeadsPlusConfigHeads.uHeads; 
		    		for (String key : mHeads) {
		    		    if (key.equalsIgnoreCase("sheep")) {
		    		        for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
		    		            for (int i = 0; i < HeadsPlusConfigHeads.getHeads().getStringList("sheepHeadN." + s).size(); i++) {
                                    if (owner.matches(HeadsPlusConfigHeads.getHeads().getStringList("sheepHeadN." + s).get(i))) {
                                        Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
                                        if (invi.getAmount() > 0) {
                                            price = setPrice(price, args, invi, (Player) sender);
                                        }
                                        pay((Player) sender, args, invi, price);

                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); i++) {
                                if (owner.matches(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(i))) {
                                    Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
                                    if (invi.getAmount() > 0) {
                                        price = setPrice(price, args, invi, (Player) sender);
                                    }
                                    pay((Player) sender, args, invi, price);

                                } else if ((owner.matches(HeadsPlusConfigHeads.getHeads().getStringList("irongolemHeadN").get(i))) && (key.equalsIgnoreCase("irongolem"))) {
                                    Double price = HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP");
                                    if (invi.getAmount() > 0) {
                                        price = setPrice(price, args, invi, (Player) sender);

                                    }
                                    pay((Player) sender, args, invi, price);
                                }
                            }
                        }


		    		}
		    		for (String key : uHeads) {
						if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
							Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
							if (invi.getAmount() > 0) {
								price = setPrice(price, args, invi, (Player) sender);
								EconomyResponse zr = econ.depositPlayer((Player) sender, price);
								String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
								success = ChatColor.translateAlternateColorCodes('&', success);
								String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
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
		    		if (!sold) {
			    		Double price = HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP");
			    		if (invi.getAmount() > 0) {
			    			price = setPrice(price, args, invi, (Player) sender);
	    				}
						EconomyResponse zr = econ.depositPlayer((Player) sender, price);
			    		String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
			    		success = ChatColor.translateAlternateColorCodes('&', success);
			    		String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
						fail = ChatColor.translateAlternateColorCodes('&', fail);
						if (zr.transactionSuccess()) {
							itemRemoval((Player) sender, args, invi);
							sender.sendMessage(success);
							sold = true;
						} else {
							sender.sendMessage(fail + ": " + zr.errorMessage);
						}
		    		}
		    		if (!sold) {
		    			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("false-head")));
		    		}
		    		
		    		
		    	} else {
		            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("false-head")));
                }
		    } else {
		    	if (!sender.hasPermission("headsplus.sellhead")) {
		    		sender.sendMessage(HeadsPlusCommand.noPerms);
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
                                    for (String str : HeadsPlusConfigHeads.mHeads) {
                                        try {
                                            if (sm.getOwner() != null) {
                                                if (str.equalsIgnoreCase("sheep")) {
                                                    for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection(str + "HeadN").getKeys(false)) {
                                                        for (int in = 0; in > HeadsPlusConfigHeads.getHeads().getStringList(str + "HeadN." + s).size(); in++) {
                                                            if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(str + "HeadN").get(in))) {
                                                                found = true;
                                                                price = setPrice(price, args, i, p);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    for (int in = 0; in > HeadsPlusConfigHeads.getHeads().getStringList(str + "HeadN").size(); in++) {
                                                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(str + "HeadN").get(in))) {
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
                                    for (String str : HeadsPlusConfigHeads.uHeads) {
                                        try {
                                            if (sm.getOwner() != null) {
                                                if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(str + "HeadN"))) {
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
                                            for (String key : HeadsPlusConfigHeads.mHeads) {
                                                if (!key.equalsIgnoreCase("sheep")) {
                                                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); in++) {
                                                        if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(in))) {
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
                                                            for (Property pr : gm.getProperties().get("textures")) {
                                                                if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                                    if (i.getAmount() > 0) {
                                                                        price = setPrice(price, args, i, p);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN." + s).size(); in++) {
                                                            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN." + s).get(in))) {
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
                                                                for (Property pr : gm.getProperties().get("textures")) {
                                                                    if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                                        if (i.getAmount() > 0) {
                                                                            price = setPrice(price, args, i, p);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            for (String key : HeadsPlusConfigHeads.uHeads) {
                                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); in++) {
                                                    if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
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
                                                        for (Property pr : gm.getProperties().get("textures")) {
                                                            if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                                if (i.getAmount() > 0) {
                                                                    price = setPrice(price, args, i, p);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (price == 0.0) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("no-heads")));
                            return true;
                        }
                        pay(p, args, new ItemStack(Material.AIR), price);
                    }
                } else {
		    		
		    		String falseItem = HeadsPlusConfig.getMessages().getString("false-item");
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
	public static ItemStack checkHand(Player p) {
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
							if (is.getItemMeta().getLore() != null) {
                                List<String> ls = new ArrayList<>();
                                for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                    ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                }
								if ((is.getItemMeta().getLore().size() == 2) && ((is.getItemMeta().getLore().equals(ls)))) {
									p.getInventory().remove(is);
								}
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
                            List<String> ls = new ArrayList<>();
                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                            }
							if ((sm.getLore() != null) && (sm.getLore().equals(ls))) {
								boolean found = false;
								for (String s : HeadsPlusConfigHeads.mHeads) {
								    if (s.equalsIgnoreCase("sheep")) {
                                        for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                                if (a[0].equalsIgnoreCase(s)) {
                                                    if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                                        p.getInventory().remove(is);
                                                        found = true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                            if (a[0].equalsIgnoreCase(s)) {
                                                if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                                    p.getInventory().remove(is);
                                                    found = true;
                                                }
                                            }
                                        }
                                    }
							    }
							    for (String s : HeadsPlusConfigHeads.uHeads) {
                                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                        if (a[0].equalsIgnoreCase(s)) {
                                            if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                                p.getInventory().remove(is);
                                                found = true;
                                            }
                                        }
                                    }
							    }
							    if (!found && a[0].equalsIgnoreCase("player")) {
                                    boolean player = true;
                                    SkullMeta sms = (SkullMeta) is.getItemMeta();
                                    for (String s : HeadsPlusConfigHeads.uHeads) {
                                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                            if (sms.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                                player = false;
                                            }
                                        }

                                    }
                                    for (String s : HeadsPlusConfigHeads.mHeads) {
                                        if (s.equalsIgnoreCase("sheep")) {
                                            for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                                    if (sms.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                                        player = false;
                                                    }
                                                }
                                            }
                                        } else {
                                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                                if (sms.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                                    player = false;
                                                }
                                            }
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
                                    for (String key : HeadsPlusConfigHeads.mHeads) {
                                        if (key.equalsIgnoreCase("sheep")) {
                                            for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN." + s).size(); in++) {
                                                    if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN." + s).get(in))) {
                                                        Field pro = null;
                                                        try {
                                                            pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                                        } catch (NoSuchFieldException e) {
                                                            e.printStackTrace();
                                                        }
                                                        pro.setAccessible(true);
                                                        GameProfile gm = null;
                                                        try {
                                                            gm = (GameProfile) pro.get(is.getItemMeta());
                                                        } catch (IllegalAccessException e) {
                                                            e.printStackTrace();
                                                        }
                                                        for (Property pr : gm.getProperties().get("textures")) {
                                                            if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                                p.getInventory().remove(is);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); in++) {
                                                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(in))) {
                                                    Field pro = null;
                                                    try {
                                                        pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                                    } catch (NoSuchFieldException e) {
                                                        e.printStackTrace();
                                                    }
                                                    pro.setAccessible(true);
                                                    GameProfile gm = null;
                                                    try {
                                                        gm = (GameProfile) pro.get(is.getItemMeta());
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    }
                                                    for (Property pr : gm.getProperties().get("textures")) {
                                                        if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                            p.getInventory().remove(is);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    for (String key : HeadsPlusConfigHeads.uHeads) {
                                        if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
                                            Field pro = null;
                                            try {
                                                pro = ((SkullMeta) is.getItemMeta()).getClass().getDeclaredField("profile");
                                            } catch (NoSuchFieldException e) {
                                                e.printStackTrace();
                                            }
                                            pro.setAccessible(true);
                                            GameProfile gm = null;
                                            try {
                                                gm = (GameProfile) pro.get(is.getItemMeta());
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                            for (Property pr : gm.getProperties().get("textures")) {
                                                if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                    p.getInventory().remove(is);
                                                }
                                            }
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
						if ((sm.getLore() != null) && (sm.getLore().equals(ls))) {
							boolean found = false;
							for (String s : HeadsPlusConfigHeads.mHeads) {
							    if (s.equalsIgnoreCase("sheep")) {
							        for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                            if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                                p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                                found = true;
                                            }
                                        }
                                    }
                                } else {
							        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                        if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                            p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                            found = true;
                                        }
                                    }
                                }

						    }
						    for (String s : HeadsPlusConfigHeads.uHeads) {
                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                    if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                        p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                        found = true;
                                    }
                                }

						    }
                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList("irongolemHeadN").size(); in++) {
                                if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getStringList("irongolemHeadN").get(in))) {
                                    p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP"));
                                    found = true;
                                }
                            }

						    if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                for (String key : HeadsPlusConfigHeads.mHeads) {
                                    if (key.equalsIgnoreCase("sheep")) {
                                        for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN." + s).size(); in++) {
                                                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(in))) {
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
                                                    for (Property pr : gm.getProperties().get("textures")) {
                                                        if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                            if (i.getAmount() > 0) {
                                                                p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP"));
                                                                found = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); in++) {
                                            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(in))) {
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
                                                for (Property pr : gm.getProperties().get("textures")) {
                                                    if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                        if (i.getAmount() > 0) {
                                                            p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP"));
                                                            found = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                for (String key : HeadsPlusConfigHeads.uHeads) {
                                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").size(); in++) {
                                        if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(key + "HeadN").get(in))) {
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
                                            for (Property pr : gm.getProperties().get("textures")) {
                                                if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(key))) {
                                                    if (i.getAmount() > 0) {
                                                        p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP"));
                                                        found = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
						    if (!found) {
						    	p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP"));
						    }
						}
					}
				} else if (a[0] != null) {
					for (String s : HeadsPlusConfigHeads.mHeads) {
						if (a[0].equalsIgnoreCase(s)) {
						    if (s.equalsIgnoreCase("sheep")) {
						        for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection("sheepHeadN").getKeys(false)) {
                                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                        SkullMeta sm = (SkullMeta) i.getItemMeta();
                                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                            List<String> ls = new ArrayList<>();
                                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                            }
                                            if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
                                                p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                            }
                                        } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                            if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(a[0] + "HeadN." + st).get(in))) {
                                                List<String> ls = new ArrayList<>();
                                                for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                                    ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                                }
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
                                                for (Property pr : gm.getProperties().get("textures")) {
                                                    if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getStringList(a[0] + "HeadN." + st).get(in)))) {
                                                        if (i.getAmount() > 0) {
                                                            if (i.getItemMeta().getLore() != null) {
                                                                if (i.getItemMeta().getLore().equals(ls)) {
                                                                    p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                    SkullMeta sm = (SkullMeta) i.getItemMeta();
                                    if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                        List<String> ls = new ArrayList<>();
                                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                        }
                                        if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
                                            p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                        }
                                    } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                        if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(a[0] + "HeadN").get(in))) {
                                            List<String> ls = new ArrayList<>();
                                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                            }
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
                                            for (Property pr : gm.getProperties().get("textures")) {
                                                if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getStringList(a[0] + "HeadN").get(in)))) {
                                                    if (i.getAmount() > 0) {
                                                        if (i.getItemMeta().getLore() != null) {
                                                            if (i.getItemMeta().getLore().equals(ls)) {
                                                                p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
						}
					}
					for (String s : HeadsPlusConfigHeads.uHeads) {
                        SkullMeta sm = (SkullMeta) i.getItemMeta();
						if (a[0].equalsIgnoreCase(s)) {
						    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                    List<String> ls = new ArrayList<>();
                                    for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                        ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                    }
                                    if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
                                        p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                    }
                                } else if (sm.getOwner().equalsIgnoreCase("HPXHead")) {
                                    if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                        List<String> ls = new ArrayList<>();
                                        for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                            ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                        }
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
                                        for (Property pr : gm.getProperties().get("textures")) {
                                            if (pr.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in)))) {
                                                if (i.getAmount() > 0) {
                                                    if (i.getItemMeta().getLore() != null) {
                                                        if (i.getItemMeta().getLore().equals(ls)) {
                                                            p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

						}
					}
					if (a[0].equalsIgnoreCase("player")) {
					    boolean player = true;
					    SkullMeta sm = (SkullMeta) i.getItemMeta();
					    for (String s : HeadsPlusConfigHeads.uHeads) {
					        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                    player = false;
                                }
                            }

                        }
                        for (String s : HeadsPlusConfigHeads.mHeads) {
					        if (s.equalsIgnoreCase("sheep")) {
					            for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection(s + "HeadN").getKeys(false)) {
                                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                            player = false;
                                        }
                                    }
                                }
                            } else {
                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                    if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                        player = false;
                                    }
                                }
                            }
                        }
                        if (player) {

                            List<String> ls = new ArrayList<>();
                            for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                            }
                            if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
                                p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP"));
                            }
                        }
                    }
				}	
			} else {
				if (Integer.parseInt(a[0]) <= i.getAmount()) {
					SkullMeta sm = (SkullMeta) i.getItemMeta();
					String s = null;
					for (String str : HeadsPlusConfigHeads.mHeads) {
                        if (str.equalsIgnoreCase("sheep")) {
                            for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection(s + "HeadN").getKeys(false)) {
                                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                                    if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                        s = str;
                                    }
                                }
                            }
                        } else {
                            for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                                if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                                    s = str;
                                }
                            }
                        }
					}
					p = HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP") * Integer.parseInt(a[0]);
				} else {
					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("not-enough-heads")));
				}
			}
		} else {
			SkullMeta sm = (SkullMeta) i.getItemMeta();
			String s = null;
			for (String str : HeadsPlusConfigHeads.mHeads) {
                if (str.equalsIgnoreCase("sheep")) {
                    for (String st : HeadsPlusConfigHeads.getHeads().getConfigurationSection(s + "HeadN").getKeys(false)) {
                        for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).size(); in++) {
                            if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN." + st).get(in))) {
                                s = str;
                            }
                        }
                    }
                } else {
                    for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                            s = str;
                        }
                    }
                }
			}
            for (String str : HeadsPlusConfigHeads.uHeads) {
                for (int in = 0; in < HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").size(); in++) {
                    if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getStringList(s + "HeadN").get(in))) {
                        s = str;
                    }
                }
            }
            if (s == null) {
			    s = "player";
            }
			p = HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP") * i.getAmount();
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
		if (price == 0) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("no-heads")));
			return;
		}
		pay(p, a, i, price);
	}
	private void pay(Player p, String[] a, ItemStack i, double pr) {
		Economy econ = HeadsPlus.getInstance().econ;
		EconomyResponse zr = econ.depositPlayer(p, pr);
	    String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
	    success = ChatColor.translateAlternateColorCodes('&', success);
	    String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
		fail = ChatColor.translateAlternateColorCodes('&', fail);
		if (zr.transactionSuccess()) {
			itemRemoval(p, a, i);
			p.sendMessage(success);
			sold = true;
		} else {
			p.sendMessage(fail + ": " + zr.errorMessage);
		}
	}

	private void checkColorSheep(Player sender, String[] args, ItemStack invi) throws NoSuchFieldException, IllegalAccessException {
	    for (String s : HeadsPlusConfigHeads.getHeads().getConfigurationSection( "sheepHeadN").getKeys(false)) {
	        for (int i = 0; i < HeadsPlusConfigHeads.getHeads().getStringList("sheepHeadN." + s).size(); i++) {
                if (HeadsPlusConfigHeadsX.isHPXSkull(HeadsPlusConfigHeads.getHeads().getStringList("sheepHeadN." + s).get(i))) {
                    Field pro = ((SkullMeta) invi.getItemMeta()).getClass().getDeclaredField("profile");
                    pro.setAccessible(true);
                    GameProfile gm = (GameProfile) pro.get(invi.getItemMeta());
                    for (Property p : gm.getProperties().get("textures")) {
                        if (p.getValue().equals(HeadsPlusConfigHeadsX.getTextures(HeadsPlusConfigHeads.getHeads().getStringList("sheeoHeadN." + s).get(i)))) {
                            Double price = HeadsPlusConfigHeads.getHeads().getDouble("sheepHeadP");
                            if (invi.getAmount() > 0) {
                                price *= invi.getAmount();
                            }
                            pay(sender, args, invi, price);
                            return;
                        }
                    }
                }
            }
        }
    }
}