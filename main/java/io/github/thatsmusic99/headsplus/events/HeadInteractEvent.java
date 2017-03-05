package io.github.thatsmusic99.headsplus.events;


import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public final class HeadInteractEvent implements Listener {
	// Global variable which prevents a duplicate message bug I couldn't find a proper solution for. This method is NOT recommended in any cases and may be removed in the future, but I simply got too frustrated and decided to slip it in. I'm terrible, sorry...
	int TimesSent = 0;
	
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
		    Player player = event.getPlayer();
			BlockState block = event.getClickedBlock().getState();
			if (block instanceof Skull) {
				
			    Skull skull = (Skull) block;
			    @SuppressWarnings("deprecation")
			    String owner = skull.getOwner();
			    if (TimesSent < 1) {
			    	Pattern mhf = Pattern.compile("MHF_");
			    	Matcher match = mhf.matcher(owner);
			    	if (match.find() && !(match.matches())) {
			    		String newMatch = owner.replace("MHF_", "");
			    		String iMessage1 = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getString("head-mhf-interact-message"));
			    		String iMessage2 = iMessage1.replaceAll("%p", newMatch);
			    		player.sendMessage(iMessage2);
			    		TimesSent++;
			    	} else {
			            String iMessage1 = ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().getConfig().getString("head-interact-message"));
			            String iMessage2 = iMessage1.replaceAll("%p", owner);
			            player.sendMessage(iMessage2);
			            TimesSent++;
			    	}
			 } else {
			    TimesSent --;
			 }
		}
		}
	}	
}