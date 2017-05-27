package io.github.thatsmusic99.headsplus.commands;

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
	
	private static String lore;
	private static String lore2;
	private static boolean sold;
	private static String disabled = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("disabled")));
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (HeadsPlus.getInstance().sellable && HeadsPlus.getInstance().getConfig().getBoolean("sellHeads")) {
		    ItemStack invi = checkHand((Player) sender);
		    
		    if ((checkHand((Player) sender).getType() == Material.SKULL_ITEM) && (sender.hasPermission("headsplus.sellhead"))) {
		    	
		    	SkullMeta skullM = (SkullMeta) invi.getItemMeta();
		        String owner = skullM.getOwner();
		        if (skullM.getLore() != null) {
		            lore = skullM.getLore().get(0);
		            lore2 = skullM.getLore().get(1);
		        }
		    	if ((skullM.getLore() != null) && ((lore.equals("" + ChatColor.GOLD + ChatColor.BOLD + "This head can be sold!")) && (lore2.equals("" + ChatColor.GOLD + "Do /sellhead to sell!")))) {
		  
		    		Economy econ = HeadsPlus.getInstance().econ;
		    		List<String> mHeads = HeadsPlusConfigHeads.mHeads;
		    		List<String> uHeads = HeadsPlusConfigHeads.uHeads; 
		    		for (String key : mHeads) {
		    			
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				if (invi.getAmount() > 0 && args.length >= 0) { 
		    					if (args.length > 0) { 
		    						if (!args[0].matches("^[0-9]+$")) { 
		    							price = price * invi.getAmount(); 
		    						} else {
		    							if (invi.getAmount() >= Integer.parseInt(args[1]) + 1) {
											checkHand((Player) sender).setAmount(checkHand((Player) sender).getAmount() - Integer.parseInt(args[1]));
									    } else {
									    	
									    }
		    						}
		    					} else { 
		    					    price = price * invi.getAmount(); 
		    					} 
		    				}
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								if (args.length > 0) { 
									if (args[1].matches("^[0-9]+$")) { 
										if (invi.getAmount() >= Integer.parseInt(args[1]) + 1) {
											checkHand((Player) sender).setAmount(checkHand((Player) sender).getAmount() - Integer.parseInt(args[1]));
									    } else {
									    	setHand((Player) sender, new ItemStack(Material.AIR));
								        }
								    } else {
								    	setHand((Player) sender, new ItemStack(Material.AIR));
								    } 
								} else {
									setHand((Player) sender, new ItemStack(Material.AIR));
								}
								sender.sendMessage(success);
								sold = true;
							} else { sender.sendMessage(fail + ": " + zr.errorMessage); }
		    				
		    			} else if ((owner.matches("MHF_Golem")) && (key.equalsIgnoreCase("irongolem"))) {
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble("irongolemHeadP");
		    				String senderName = sender.getName();
		    				if (invi.getAmount() > 0 && args.length >= 0) { if (args.length > 0) { if (!args[0].equalsIgnoreCase("one")) { price = price * invi.getAmount(); } } else { price = price * invi.getAmount(); } }
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								if (args.length > 0) { 
									if (args[0].equalsIgnoreCase("one")) { 
										if (invi.getAmount() >= 2) {
											checkHand((Player) sender).setAmount(checkHand((Player) sender).getAmount() - 1);
									    } else {
									    	setHand((Player) sender, new ItemStack(Material.AIR));
								        }
								    } else {
								    	setHand((Player) sender, new ItemStack(Material.AIR));
								    } 
								} else {
									setHand((Player) sender, new ItemStack(Material.AIR));
								}
								sender.sendMessage(success);
								sold = true;
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    			} 
		    		}
		    		for (String key : uHeads) {
		    			if (owner.matches(HeadsPlusConfigHeads.getHeads().getString(key + "HeadN"))) {
		    				String senderName = sender.getName();
		    				Double price = HeadsPlusConfigHeads.getHeads().getDouble(key + "HeadP");
		    				if (invi.getAmount() > 0 && args.length >= 0) { if (args.length > 0) { if (!args[0].equalsIgnoreCase("one")) { price = price * invi.getAmount(); } } else { price = price * invi.getAmount(); } }
		    				@SuppressWarnings({ "deprecation" })
							EconomyResponse zr = econ.depositPlayer(senderName, price);
		    				String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
							success = ChatColor.translateAlternateColorCodes('&', success);
							String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
							fail = ChatColor.translateAlternateColorCodes('&', fail);
							if (zr.transactionSuccess()) {
								if (args.length > 0) { 
									if (args[0].equalsIgnoreCase("one")) { 
										if (invi.getAmount() >= 2) {
											checkHand((Player) sender).setAmount(checkHand((Player) sender).getAmount() - 1);
									    } else {
									    	setHand((Player) sender, new ItemStack(Material.AIR));
								        }
								    } else {
								    	setHand((Player) sender, new ItemStack(Material.AIR));
								    } 
								} else {
									setHand((Player) sender, new ItemStack(Material.AIR));
								}
								sender.sendMessage(success);
								sold = true;
							} else {
								sender.sendMessage(fail + ": " + zr.errorMessage);
							}
		    				
		    			} 
		    		}
		    		if (!sold) {
			    		Double price = HeadsPlusConfigHeads.getHeads().getDouble("playerHeadP");
			    		String senderName = sender.getName();
			    		if (invi.getAmount() > 0 && args.length >= 0) { if (args.length > 0) { if (!args[0].equalsIgnoreCase("one")) { price = price * invi.getAmount(); } } else { price = price * invi.getAmount(); } }
			    		@SuppressWarnings("deprecation")
						EconomyResponse zr = econ.depositPlayer(senderName, price);
			    		String success = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-success")).replaceAll("%l", Double.toString(zr.amount)).replaceAll("%b", Double.toString(zr.balance));
			    		success = ChatColor.translateAlternateColorCodes('&', success);
			    		String fail = HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("sell-fail"));
						fail = ChatColor.translateAlternateColorCodes('&', fail);
						if (zr.transactionSuccess()) {
							if (args.length > 0) { 
								if (args[0].equalsIgnoreCase("one")) { 
									if (invi.getAmount() >= 2) {
										checkHand((Player) sender).setAmount(checkHand((Player) sender).getAmount() - 1);
								    } else {
								    	setHand((Player) sender, new ItemStack(Material.AIR));
							        }
							    } else {
							    	setHand((Player) sender, new ItemStack(Material.AIR));
							    } 
							} else {
								setHand((Player) sender, new ItemStack(Material.AIR));
							}
							
							sender.sendMessage(success);
							sold = true;
						} else {
							sender.sendMessage(fail + ": " + zr.errorMessage);
						}
			    			
		    		}
		    	} else {
		    		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("false-head"))));
		    	}
		    	} else {
		    		if (!sender.hasPermission("headsplus.sellhead")) {
		    		sender.sendMessage(HeadsPlusCommand.noPerms);
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
		     
	return false;
	}
	@SuppressWarnings("deprecation")
	public ItemStack checkHand(Player p) {
		if (Bukkit.getVersion().contains("1.8")) {
			ItemStack i = p.getInventory().getItemInHand();
			return i;
		} else {
			ItemStack i = p.getInventory().getItemInMainHand();
			return i;
		}
	}
	@SuppressWarnings("deprecation")
	public void setHand(Player p, ItemStack i) {
		if (Bukkit.getVersion().contains("1.8")) {
			p.getInventory().setItemInHand(i);
		} else {
			p.getInventory().setItemInMainHand(i);
		}
	}
}
