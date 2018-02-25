package io.github.thatsmusic99.headsplus.commands.maincommand;

import java.io.File;

import io.github.thatsmusic99.headsplus.config.*;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallenges;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;

public class MCReload {
	
	private final File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

	private static final File messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");

	private static final File headsF = new File(HeadsPlus.getInstance().getDataFolder(), "heads.yml");

	private static final File craftingF = new File(HeadsPlus.getInstance().getDataFolder(), "crafting.yml");

	private  static final File headsXF = new File(HeadsPlus.getInstance().getDataFolder(), "headsx.yml");

	private static final File lbF = new File(HeadsPlus.getInstance().getDataFolder(), "leaderboards.yml");

	private final File chalF = new File(HeadsPlus.getInstance().getDataFolder(), "challenges.yml");
	
	public void reload(CommandSender sender) {
		if (sender.hasPermission("headsplus.maincommand.reload")) {
		    HeadsPlusConfig m = HeadsPlus.getInstance().hpc;
			   String reloadM = ChatColor.translateAlternateColorCodes('&', m.getMessages().getString("reload-message"));
			   String reloadingM = ChatColor.translateAlternateColorCodes('&', m.getMessages().getString("reloading-message"));
			   String reloadF = ChatColor.translateAlternateColorCodes('&', m.getMessages().getString("reload-fail"));
			   reloadM = HeadsPlus.getInstance().translateMessages(reloadM);
			   reloadingM = HeadsPlus.getInstance().translateMessages(reloadingM);
			   reloadF = HeadsPlus.getInstance().translateMessages(reloadF);
		       sender.sendMessage(reloadingM);
		       try {

			       if  (!(configF.exists())) {
				       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
				       HeadsPlus.getInstance().saveConfig();
			       } else {
				       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
				       HeadsPlus.getInstance().reloadConfig();

                       HeadsPlus.getInstance().drops = HeadsPlus.getInstance().getConfig().getBoolean("dropHeads");
                       HeadsPlus.getInstance().arofj = HeadsPlus.getInstance().getConfig().getBoolean("autoReloadOnFirstJoin");
                       HeadsPlus.getInstance().sellable = HeadsPlus.getInstance().econ() && HeadsPlus.getInstance().getConfig().getBoolean("sellHeads");
                       HeadsPlus.getInstance().stopP = HeadsPlus.getInstance().getConfig().getBoolean("stop-placement-of-sellable-heads");
                       HeadsPlus.getInstance().lb = HeadsPlus.getInstance().getConfig().getBoolean("leaderboards");
                       HeadsPlus.getInstance().db = HeadsPlus.getInstance().getConfig().getBoolean("headsDatabase");
                       HeadsPlus.getInstance().dm = HeadsPlus.getInstance().getConfig().getBoolean("player-death-messages");
                       HeadsPlus.getInstance().checkTheme();

					   HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
				   }

			      if (!(messagesF.exists())) {
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages not found, creating!");
			    	  m.reloadMessages(false);
			    	  m.messages = YamlConfiguration.loadConfiguration(messagesF);
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Messages created!");
			      } else {
			    	  m.reloadMessages(false);
			      }
			      HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().hpch;
			      if (!(headsF.exists())) {
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads not found, creating!");
			    	  hpch.reloadHeads();
			    	  hpch.heads = YamlConfiguration.loadConfiguration(headsF);
			    	  HeadsPlus.getInstance().log.info("[HeadsPlus] Heads created!");
			      } else {
			    	  hpch.reloadHeads();
			      }
			      if (HeadsPlus.getInstance().hpcr != null) {
					  HeadsPlusCrafting hpc = HeadsPlus.getInstance().hpcr;
					  if (!(craftingF.exists())) {
						  if (HeadsPlus.getInstance().getConfig().getBoolean("craftHeads")) {
							  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting not found, creating!");

							  hpc.craftingEnable();
							  hpc.crafting = YamlConfiguration.loadConfiguration(craftingF);
							  HeadsPlus.getInstance().log.info("[HeadsPlus] Crafting created!");
							  sender.sendMessage(reloadM);
						  }
					  } else {
						  hpc.reloadCrafting();
						  sender.sendMessage(reloadM);
					  }
				  }
			      HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().hpchx;
				   if (!headsXF.exists()) {
					   if (HeadsPlus.getInstance().getConfig().getBoolean("headsDatabase")) {
						   HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX not found, creating!");
						   hpchx.reloadHeadsX();
						   hpchx.headsx = YamlConfiguration.loadConfiguration(headsXF);
						   HeadsPlus.getInstance().log.info("[HeadsPlus] HeadsX created!");
					   }
				   } else {
					   hpchx.reloadHeadsX();
				   }
				   HeadsPlusLeaderboards hpl = HeadsPlus.getInstance().hplb;
				   if (!lbF.exists()) {
			           if (HeadsPlus.getInstance().getConfig().getBoolean("leaderboards") && !HeadsPlus.getInstance().getConfig().getBoolean("leaderboards-mysql")) {
                           HeadsPlus.getInstance().log.info("[HeadsPlus] Leaderboards not found, creating!");
                           hpl.reloadLeaderboards();
                           hpl.lb = YamlConfiguration.loadConfiguration(lbF);
                           HeadsPlus.getInstance().log.info("[HeadsPlus] Leaderboards created!");
                       }
                   } else {
                       hpl.reloadLeaderboards();
                   }
                   HeadsPlusChallenges hpchal = HeadsPlus.getInstance().hpchl;
				   if (!chalF.exists()) {
				       HeadsPlus.getInstance().getLogger().info("Challenges not found, creating!");
				       hpchal.reloadChlnges();
				       hpchal.challenges = YamlConfiguration.loadConfiguration(chalF);
				       HeadsPlus.getInstance().getLogger().info("Challenges created!");
                   } else {
				       hpchal.reloadChlnges();
                   }
			      
		       } catch (Exception e) {
			       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config!");
			       e.printStackTrace();
			       sender.sendMessage(reloadF);
		       }
		   } else {
		       sender.sendMessage(new HeadsPlusCommand().noPerms);
		   }
	   }
	
}


