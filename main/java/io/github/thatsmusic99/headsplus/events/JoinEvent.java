package io.github.thatsmusic99.headsplus.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

public class JoinEvent implements Listener { 
	
	public static boolean reloaded;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!reloaded) {
		    if (HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnEnable")) {
	        	HeadsPlus.getInstance().reloadConfig();
	        	HeadsPlusConfig.reloadMessages();
	        	HeadsPlusConfigHeads.reloadHeads();
	        	reloaded = true;
	        }
		}
	}
}
