package io.github.thatsmusic99.headsplus.events;


import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import org.bukkit.ChatColor;

public final class HeadInteractEvent implements Listener {
	private int TimesSent = 0;
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
		    Player player = event.getPlayer();
			BlockState block = event.getClickedBlock().getState();
			if (block instanceof Skull) {
				
			    Skull skull = (Skull) block;
			    @SuppressWarnings("deprecation")
			    String owner = skull.getOwner();
			    String playerName = player.toString();
			    if (TimesSent < 1) {
			    	for (HeadEnums key : HeadEnums.values()) {
			    		if (owner.equalsIgnoreCase(key.name)) {
			    			String iMessage1;
				    		if (key.displayname.startsWith("a") || key.displayname.startsWith("e") || key.displayname.startsWith("i") || key.displayname.startsWith("o") || key.displayname.startsWith("u") || key.displayname.startsWith("A") || key.displayname.startsWith("E") || key.displayname.startsWith("I") || key.displayname.startsWith("O") || key.displayname.startsWith("U")) {
				    			iMessage1 = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("head-mhf-interact-message-2"));
				    		} else {
				    			iMessage1 = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("head-mhf-interact-message"));
				    		}
				    		iMessage1 = HeadsPlus.getInstance().translateMessages(iMessage1);
				    		iMessage1 = iMessage1.replaceAll("%p", key.displayname);
				    		iMessage1 = iMessage1.replaceAll("%m", playerName);
				    		player.sendMessage(iMessage1);
				    		TimesSent++;
				    		return;
			    		}
			    	}
			    	String iMessage1 = ChatColor.translateAlternateColorCodes('&', HeadsPlusConfig.getMessages().getString("head-interact-message"));
		            iMessage1 = HeadsPlus.getInstance().translateMessages(iMessage1);
		            iMessage1 = iMessage1.replaceAll("%p", owner);
		    		iMessage1 = iMessage1.replaceAll("%m", playerName);
		            player.sendMessage(iMessage1);
		            TimesSent++;
		            return;
			    } else {
			        TimesSent --;
			    }
		    } 
		}
	}
}