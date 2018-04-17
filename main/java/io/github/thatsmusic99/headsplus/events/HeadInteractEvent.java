package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.util.DebugFileCreator;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import java.io.IOException;
import java.util.logging.Logger;

public final class HeadInteractEvent implements Listener {

	private final HeadsPlusConfig hpc = HeadsPlus.getInstance().getMessagesConfig();
	private int TimesSent = 0;

	@EventHandler
	public void interact(PlayerInteractEvent event) {
		try {
		    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		        Player player = event.getPlayer();
			    BlockState block = event.getClickedBlock().getState();
		    	if (block instanceof Skull) {
				
			        Skull skull = (Skull) block;
			        String owner;

			    	owner = getSkullName(skull);
			        String playerName = player.toString();
			        if (TimesSent < 1) {
			    	    for (HeadEnums key : HeadEnums.values()) {
			    	    	if (key.name.contains(owner)) {
			    		    	String iMessage1;
				    	    	if (key.displayname.startsWith("a") || key.displayname.startsWith("e") || key.displayname.startsWith("i") || key.displayname.startsWith("o") || key.displayname.startsWith("u") || key.displayname.startsWith("A") || key.displayname.startsWith("E") || key.displayname.startsWith("I") || key.displayname.startsWith("O") || key.displayname.startsWith("U")) {
				    	    		iMessage1 = hpc.getString("head-mhf-interact-message-2");
				    	    	} else {
				    		    	iMessage1 = hpc.getString("head-mhf-interact-message");
				    	    	}
				    	    	iMessage1 = iMessage1.replaceAll("%p", key.displayname).replaceAll("%m", playerName);
				    	    	player.sendMessage(iMessage1);
				    	    	TimesSent++;
				    	    	return;
			    		    }
			    	    }
			    	    String iMessage1 = hpc.getString("head-interact-message");
		                iMessage1 = iMessage1.replaceAll("%p", owner);
		    		    iMessage1 = iMessage1.replaceAll("%m", playerName);
		                player.sendMessage(iMessage1);
		                TimesSent++;
			        } else {
			            TimesSent --;
			        }
		        }
		    }
		} catch (NullPointerException ex) {
		//
	    } catch (Exception e) {
			if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                e.printStackTrace();
            }
			if (HeadsPlus.getInstance().getConfig().getBoolean("debug.create-debug-files")) {
				Logger log = HeadsPlus.getInstance().getLogger();
				log.severe("HeadsPlus has failed to fire this event. An error report has been made in /plugins/HeadsPlus/debug");
				try {
					String s = new DebugFileCreator().createReport(e, "Event (HeadInteractEvent)");
					log.severe("Report name: " + s);
					log.severe("Please submit this report to the developer at one of the following links:");
					log.severe("https://github.com/Thatsmusic99/HeadsPlus/issues");
					log.severe("https://discord.gg/nbT7wC2");
					log.severe("https://www.spigotmc.org/threads/headsplus-1-8-x-1-12-x.237088/");
				} catch (IOException e1) {
                    if (HeadsPlus.getInstance().getConfig().getBoolean("debug.print-stacktraces-in-console")) {
                        e1.printStackTrace();
                    }
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	private static String getSkullName(Skull s) {
            if (HeadsPlus.getInstance().getServer().getVersion().contains("1.8")) {
                return s.getOwner();
            } else {
                return s.getOwningPlayer().getName();
            }
    }
}