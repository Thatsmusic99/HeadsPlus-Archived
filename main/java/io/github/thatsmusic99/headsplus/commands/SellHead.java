package io.github.thatsmusic99.headsplus.commands;

import java.util.ArrayList;
import java.util.List;

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
		    			
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				if (invi.getAmount() > 0) {
		    					price = setPrice(price, args, invi, (Player) sender);
		    				}
		    				pay((Player) sender, args, invi, price);
		    				
		    			} else if ((owner.matches(HeadsPlusConfigHeads.getHeads().getString("irongolemHeadN"))) && (key.equalsIgnoreCase("irongolem"))) {
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP");
		    				if (invi.getAmount() > 0) {
		    					price = setPrice(price, args, invi, (Player) sender);
		    					
		    				}
		    				pay((Player) sender, args, invi, price);
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
		            for (String s : skullM.getLore()) {
		                sender.sendMessage(s);
                    }
                    for (String s : ls) {
                        sender.sendMessage(s);
                    }
                }
		    } else {
		    	if (!sender.hasPermission("headsplus.sellhead")) {
		    		sender.sendMessage(HeadsPlusCommand.noPerms);
		    	} else if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("all")) {
                        sellAll((Player) sender, args, invi);
                    } else if (args[0] != null) {
                        boolean found = false;

                        Player p = (Player) sender;
                        double price = 0.0;
                        for (ItemStack i : p.getInventory()) {
                            if (i != null) {
                                if (i.getType() == Material.SKULL_ITEM) {
                                    SkullMeta sm = (SkullMeta) i.getItemMeta();
                                    for (String str : HeadsPlusConfigHeads.mHeads) {
                                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(str + "HeadN"))) {
                                            found = true;
                                            price = setPrice(price, args, i, p);
                                        }
                                    }
                                    for (String str : HeadsPlusConfigHeads.uHeads) {
                                        if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(str + "HeadN"))) {
                                            found = true;
                                            price = setPrice(price, args, i, p);
                                        }
                                    }
                                    if (!found) {
                                        if (args[0].equalsIgnoreCase("player")) {
                                            price = setPrice(price, args, i, p);
                                            found = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (price == 0) {
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
	private ItemStack checkHand(Player p) {
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
							if ((sm.getLore() != null) && ((sm.getLore().size() == 2) && (sm.getLore().equals(ls)))) {
								boolean found = false;
								for (String s : HeadsPlusConfigHeads.mHeads) {
									if (a[0].equalsIgnoreCase(s)) {
										if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
											p.getInventory().remove(is);
									        found = true;
								        }
									}
								    
							    }
							    for (String s : HeadsPlusConfigHeads.uHeads) {
							    	if (a[0].equalsIgnoreCase(s)) {
							    		if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
								        	p.getInventory().remove(is);
								        	found = true;
								        }
							    	}
								    
							    }
							    if (!found && a[0].equalsIgnoreCase("player")) {
							    	p.getInventory().remove(is);
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
						if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
							boolean found = false;
							for (String s : HeadsPlusConfigHeads.mHeads) {
							    if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
								    p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
								    found = true;
							    }
						    }
						    for (String s : HeadsPlusConfigHeads.uHeads) {
							    if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
							    	p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
							    	found = true;
							    }
						    }
						    if (sm.getOwner().equals(HeadsPlusConfigHeads.getHeads().get("irongolemHeadN"))) {
						    	p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP"));
						    	found = true;
						    } 
						    if (!found) {
						    	p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP"));
						    }
						}
					}
				} else if (a[0] != null) {
					for (String s : HeadsPlusConfigHeads.mHeads) {
						if (a[0].equalsIgnoreCase(s)) {
							SkullMeta sm = (SkullMeta) i.getItemMeta();
							if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
                                List<String> ls = new ArrayList<>();
                                for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                    ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                }
								if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
									p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
								}
							}
						}
					}
					for (String s : HeadsPlusConfigHeads.uHeads) {
						if (a[0].equalsIgnoreCase(s)) {
							SkullMeta sm = (SkullMeta) i.getItemMeta();
							if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(s + "HeadN"))) {
                                List<String> ls = new ArrayList<>();
                                for (String str : HeadsPlus.getInstance().getConfig().getStringList("lore")) {
                                    ls.add(ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(str)));
                                }
								if ((sm.getLore() != null) && (sm.getLore().size() == 2) && (sm.getLore().equals(ls))) {
									p = p + (i.getAmount() * HeadsPlusConfigHeads.getHeads().getDouble(s + "HeadP"));
								}
							}
						}
					}
				}	
			} else {
				if (Integer.parseInt(a[0]) <= i.getAmount()) {
					SkullMeta sm = (SkullMeta) i.getItemMeta();
					String s = null;
					for (String str : HeadsPlusConfigHeads.mHeads) {
						if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(str + "HeadN"))) {
							s = str;
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
				if (sm.getOwner().equalsIgnoreCase(HeadsPlusConfigHeads.getHeads().getString(str + "HeadN"))) {
					s = str;
				}
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
	    @SuppressWarnings("deprecation")
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
}