package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.thatsmusic99.headsplus.HeadsPlus;

public class HeadsPlusConfig {
	private static FileConfiguration messages;
	private static File messagesF;
	
	public static FileConfiguration getMessages() {
		return messages;
	}
	public static void msgEnable() {
		reloadMessages();
		loadMessages();
	}
	
	private static void loadMessages() {
		getMessages().options().header("HeadsPlus by Thatsmusic99");
		getMessages().addDefault("prefix", "&1[&6HeadsPlus&1]");
		getMessages().addDefault("reloading-message", "%h &3Reloading config...");
		getMessages().addDefault("reload-message", "%h &3Config has reloaded!");
		getMessages().addDefault("reload-fail", "%h &cConfig failed to reload.");
		getMessages().addDefault("head-interact-message", "&3That is &b%p&3''s &3head!");
		getMessages().addDefault("head-mhf-interact-message", "&3That is a &b%p&3''s head!");
		getMessages().addDefault("head-mhf-interact-message-2", "&3That is an &b%p&3''s &3head!");
		getMessages().addDefault("sell-success", "&3You successfully sold the head(s) for &b%l &3and now have &b%b!");
		getMessages().addDefault("sell-fail", "&cCouldn''t sell head!");
		getMessages().addDefault("false-head", "&cThis head cannot be sold!");
		getMessages().addDefault("false-item", "&cThis is not a head!");
		getMessages().addDefault("blacklist-head", "&cThis head is blacklisted and cannot be used!");
		getMessages().addDefault("full-inv", "&cYour inventory is full!");
		getMessages().addDefault("alpha-names", "&cThis command only handles alphanumeric names!");
		getMessages().addDefault("too-many-args", "&cToo many arguments!");
		getMessages().addDefault("head-too-long", "&cIGN is too long to be valid! Please use an IGN between 3 and 16 characters.");
		getMessages().addDefault("too-short-head", "&cIGN is too short to be valid! Please use an IGN between 3 and 16 characters.");
		getMessages().addDefault("invalid-pg-no", "%h &cInvalid page number!");
		getMessages().addDefault("invalid-input-int", "&h &cYou can only use integers in this command!");
		getMessages().addDefault("no-perm", "&cYou do not have permission to use this command.");
		getMessages().addDefault("head-a-add", "%h &3This head is already added!");
		getMessages().addDefault("head-added", "%h &3%p has been added to the blacklist!");
		getMessages().addDefault("head-a-removed", "%h &3This head is not on the blacklist!");
		getMessages().addDefault("head-removed", "%h &3%p has been removed from the blacklist!");
		getMessages().addDefault("world-a-add", "%h &3This world is already added!");
		getMessages().addDefault("world-added", "%h &3%w has been added to the world blacklist!");
		getMessages().addDefault("world-a-removed", "%h &3This world is not on the blacklist!");
		getMessages().addDefault("world-removed", "%h &3%w has been removed from the blacklist!");
		getMessages().addDefault("bl-on", "%h &3The blacklist has been enabled!");
		getMessages().addDefault("bl-a-on", "%h &3The blacklist is already enabled!");
		getMessages().addDefault("bl-off", "%h &3The blacklist has been disabled!");
		getMessages().addDefault("bl-a-off", "%h &3The blacklist is already disabled!");
		getMessages().addDefault("blw-on", "%h &3The world blacklist has been enabled!");
		getMessages().addDefault("blw-a-on", "%h &3The world blacklist is already enabled!");
		getMessages().addDefault("blw-off", "%h &3The world blacklist has been disabled!");
		getMessages().addDefault("blw-a-off", "%h &3The world blacklist is already disabled!");
		getMessages().addDefault("bl-fail", "%h &cFailed to modify blacklist.");
		getMessages().addDefault("blw-fail", "%h &cFailed to modify world blacklist.");
		getMessages().addDefault("disabled", "&cThis command is disabled.");
		getMessages().addDefault("empty-bl", "%h &cThe blacklist is empty!");
		getMessages().addDefault("empty-blw", "%h &cThe world blacklist is empty!");
		getMessages().options().copyDefaults(true);
		saveMessages();
		
	}
	public static void reloadMessages() {
		if (messagesF == null) {
			messagesF = new File(HeadsPlus.getInstance().getDataFolder(), "messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesF);
		loadMessages();
		saveMessages();
	}
    private static void saveMessages() {
    	if (messages == null || messagesF == null) {
    		return;
    	}
    	try {
    		messages.save(messagesF);
    	} catch (IOException e) {
    		HeadsPlus.getInstance().log.severe("[HeadsPlus] Couldn't save messages.yml!");
    		e.printStackTrace();
    	}
    }

}
