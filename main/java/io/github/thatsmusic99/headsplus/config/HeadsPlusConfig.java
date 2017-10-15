package io.github.thatsmusic99.headsplus.config;

import java.io.File;
import java.io.IOException;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;
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

		getMessages().options().header("HeadsPlus by Thatsmusic99 \nPlease do NOT change pLocale! This will be used to change the plugin's language in the future!");

		getMessages().addDefault("prefix", "&1[&6HeadsPlus&1]");
		getMessages().addDefault("locale", "en_uk");
		getMessages().addDefault("pLocale", "en_uk");
		getMessages().addDefault("reloading-message", "%h &3Reloading config...");
		getMessages().addDefault("reload-message", "%h &3Config has reloaded!");
		getMessages().addDefault("reload-fail", "%h &cConfig failed to reload.");
		getMessages().addDefault("head-interact-message", "&3That is &b%p&3''s &3head!");
		getMessages().addDefault("head-mhf-interact-message", "&3That is a &b%p&3''s head!");
		getMessages().addDefault("head-mhf-interact-message-2", "&3That is an &b%p&3''s &3head!");
		getMessages().addDefault("sell-success", "&3You successfully sold the head(s) for &b%l &3and now have &b%b!");
		getMessages().addDefault("not-enough-heads", "&cYou don't have enough heads!");
		getMessages().addDefault("no-heads", "&cYou don't have any valid heads in your inventory!");
		getMessages().addDefault("invalid-args", "&cInvalid arguments!");
		getMessages().addDefault("sell-fail", "&cCouldn''t sell head!");
		getMessages().addDefault("false-head", "&cThis head cannot be sold!");
		getMessages().addDefault("false-item", "&cThis is not a head!");
		getMessages().addDefault("blacklist-head", "&cThis head is blacklisted and cannot be used!");
		getMessages().addDefault("whitelist-head", "&cThis head isn't whitelisted and therefore cannot be used!");
		getMessages().addDefault("full-inv", "&cYour inventory is full!");
		getMessages().addDefault("alpha-names", "&cThis command only handles alphanumeric names!");
		getMessages().addDefault("too-many-args", "&cToo many arguments!");
		getMessages().addDefault("head-too-long", "&cIGN is too long to be valid! Please use an IGN between 3 and 16 characters.");
		getMessages().addDefault("too-short-head", "&cIGN is too short to be valid! Please use an IGN between 3 and 16 characters.");
		getMessages().addDefault("invalid-pg-no", "%h &cInvalid page number!");
		getMessages().addDefault("invalid-input-int", "%h &cYou can only use integers in this command!");
		getMessages().addDefault("no-perm", "&cYou do not have permission to use this command.");
		getMessages().addDefault("head-a-add", "%h &3This head is already added!");
		getMessages().addDefault("head-added-bl", "%h &3%p has been added to the blacklist!");
		getMessages().addDefault("head-a-removed-bl", "%h &3This head is not on the blacklist!");
		getMessages().addDefault("head-removed-bl", "%h &3%p has been removed from the blacklist!");
		getMessages().addDefault("head-added-wl", "%h &3%p has been added to the whitelist!");
		getMessages().addDefault("head-a-removed-wl", "%h &3This head is not on the whitelist!");
		getMessages().addDefault("head-removed-wl", "%h &3%p has been removed from the whitelist!");
		getMessages().addDefault("world-a-add", "%h &3This world is already added!");
		getMessages().addDefault("world-added-bl", "%h &3%w has been added to the world blacklist!");
		getMessages().addDefault("world-a-removed-bl", "%h &3This world is not on the blacklist!");
		getMessages().addDefault("world-removed-bl", "%h &3%w has been removed from the blacklist!");
		getMessages().addDefault("world-added-wl", "%h &3%w has been added to the world whitelist!");
		getMessages().addDefault("world-a-removed-wl", "%h &3This world is not on the whitelist!");
		getMessages().addDefault("world-removed-wl", "%h &3%w has been removed from the whitelist!");
		getMessages().addDefault("bl-on", "%h &3The blacklist has been enabled!");
		getMessages().addDefault("bl-a-on", "%h &3The blacklist is already enabled!");
		getMessages().addDefault("bl-off", "%h &3The blacklist has been disabled!");
		getMessages().addDefault("bl-a-off", "%h &3The blacklist is already disabled!");
		getMessages().addDefault("blw-on", "%h &3The world blacklist has been enabled!");
		getMessages().addDefault("blw-a-on", "%h &3The world blacklist is already enabled!");
		getMessages().addDefault("blw-off", "%h &3The world blacklist has been disabled!");
		getMessages().addDefault("blw-a-off", "%h &3The world blacklist is already disabled!");
		getMessages().addDefault("wl-on", "%h &3The whitelist has been enabled!");
		getMessages().addDefault("wl-a-on", "%h &3The whitelist is already enabled!");
		getMessages().addDefault("wl-off", "%h &3The whitelist has been disabled!");
		getMessages().addDefault("wl-a-off", "%h &3The whitelist is already disabled!");
		getMessages().addDefault("wlw-on", "%h &3The world whitelist has been enabled!");
		getMessages().addDefault("wlw-a-on", "%h &3The world whitelist is already enabled!");
		getMessages().addDefault("wlw-off", "%h &3The world whitelist has been disabled!");
		getMessages().addDefault("wlw-a-off", "%h &3The world whitelist is already disabled!");
		getMessages().addDefault("bl-fail", "%h &cFailed to add head to blacklist!");
        getMessages().addDefault("wl-fail", "%h &cFailed to add head to whitelist!");
        getMessages().addDefault("blw-fail", "%h &cFailed to add world to blacklist!");
        getMessages().addDefault("wlw-fail", "%h &cFailed to add world to whitelist!");
		getMessages().addDefault("disabled", "&cThis command is disabled.");
		getMessages().addDefault("empty-bl", "%h &cThe blacklist is empty!");
		getMessages().addDefault("empty-blw", "%h &cThe world blacklist is empty!");
		getMessages().addDefault("empty-wl", "%h &cThe whitelist is empty!");
		getMessages().addDefault("empty-wlw", "%h &cThe world whitelist is empty!");
		getMessages().addDefault("buy-success", "&3You have bought a head for &b%l &3and now have &b%b!");
        getMessages().addDefault("buy-fail", "&cCouldn''t buy head!");

        if (!getMessages().getString("locale").equalsIgnoreCase(getMessages().getString("pLocale"))) {
            getMessages().set("pLocale", getMessages().getString("locale"));
            getMessages().set("reloading-message", LocaleManager.getLocale().getReloadingMessage());
            getMessages().set("reload-message", LocaleManager.getLocale().getReloadMessage());
            getMessages().set("reload-fail", LocaleManager.getLocale().getReloadFailMessage());
            getMessages().set("head-interact-message", LocaleManager.getLocale().getHeadInteractMessage());
            getMessages().set("head-mhf-interact-message", LocaleManager.getLocale().getHeadMhfInteractMessage());
            getMessages().set("head-mhf-interact-message-2", LocaleManager.getLocale().getHeadMhfInteractMessage2());
            getMessages().set("sell-success", LocaleManager.getLocale().getSellSuccess());
            getMessages().set("not-enough-heads", LocaleManager.getLocale().getNotEnoughHeads());
            getMessages().set("no-heads", LocaleManager.getLocale().getNoHeads());
            getMessages().set("invalid-args", LocaleManager.getLocale().getInvalidArguments());
            getMessages().set("sell-fail", LocaleManager.getLocale().getSellFail());
            getMessages().set("false-head", LocaleManager.getLocale().getFalseHead());
            getMessages().set("false-item", LocaleManager.getLocale().getFalseItem());
            getMessages().set("blacklist-head", LocaleManager.getLocale().getBlacklistHead());
            getMessages().set("whitelist-head", LocaleManager.getLocale().getWhitelistHead());
            getMessages().set("full-inv", LocaleManager.getLocale().getFullInventory());
            getMessages().set("alpha-names", LocaleManager.getLocale().getAlphaNames());
            getMessages().set("too-many-args", LocaleManager.getLocale().getTooManyArguments());
            getMessages().set("head-too-long", LocaleManager.getLocale().getHeadTooLong());
            getMessages().set("too-short-head", LocaleManager.getLocale().getHeadTooShort());
            getMessages().set("invalid-pg-no", LocaleManager.getLocale().getInvalidPageNumber());
            getMessages().set("invalid-input-int", LocaleManager.getLocale().getInvalidInputInteger());
            getMessages().set("no-perm", LocaleManager.getLocale().getNoPermissions());
            getMessages().set("head-a-add", LocaleManager.getLocale().getHeadAlreadyAdded());
            getMessages().set("head-added-bl", LocaleManager.getLocale().getHeadAddedBlacklist());
            getMessages().set("head-a-removed-bl", LocaleManager.getLocale().getHeadNotOnBlacklist());
            getMessages().set("head-removed-bl", LocaleManager.getLocale().getHeadRemovedBlacklist());
            getMessages().set("head-added-wl", LocaleManager.getLocale().getHeadAddedWhitelist());
            getMessages().set("head-a-removed-wl", LocaleManager.getLocale().getHeadNotOnWhitelist());
            getMessages().set("head-removed-wl", LocaleManager.getLocale().getHeadRemovedWhitelist());
            getMessages().set("world-a-added", LocaleManager.getLocale().getWorldAlreadyAdded());
            getMessages().set("world-added-bl", LocaleManager.getLocale().getWorldAddedBlacklist());
            getMessages().set("world-a-removed-bl", LocaleManager.getLocale().getWorldNotOnBlacklist());
            getMessages().set("world-removed-bl", LocaleManager.getLocale().getWorldRemovedBlacklist());
            getMessages().set("world-added-wl", LocaleManager.getLocale().getWorldAddedWhitelist());
            getMessages().set("world-a-removed-wl", LocaleManager.getLocale().getWorldNotOnWhitelist());
            getMessages().set("world-removed-wl", LocaleManager.getLocale().getWorldRemovedWhitelist());
            getMessages().set("bl-on", LocaleManager.getLocale().getBlacklistOn());
            getMessages().set("bl-a-on", LocaleManager.getLocale().getBlacklistAlreadyOn());
            getMessages().set("bl-off", LocaleManager.getLocale().getBlacklistOff());
            getMessages().set("bl-a-off", LocaleManager.getLocale().getBlacklistAlreadyOff());
            getMessages().set("blw-on", LocaleManager.getLocale().getWBlacklistOn());
            getMessages().set("blw-a-on", LocaleManager.getLocale().getWBlacklistAlreadyOn());
            getMessages().set("blw-off", LocaleManager.getLocale().getWBlacklistOff());
            getMessages().set("blw-a-off", LocaleManager.getLocale().getWBlacklistAlreadyOff());
            getMessages().set("wl-on", LocaleManager.getLocale().getWhitelistOn());
            getMessages().set("wl-a-on", LocaleManager.getLocale().getWhitelistAlreadyOn());
            getMessages().set("wl-off", LocaleManager.getLocale().getWhitelistOff());
            getMessages().set("wl-a-off", LocaleManager.getLocale().getWhitelistAlreadyOff());
            getMessages().set("wlw-on", LocaleManager.getLocale().getWWhitelistOn());
            getMessages().set("wlw-a-on", LocaleManager.getLocale().getWWhitelistAlreadyOn());
            getMessages().set("wlw-off", LocaleManager.getLocale().getWWhitelistOff());
            getMessages().set("wlw-a-off", LocaleManager.getLocale().getWWhitelistAlreadyOff());
            getMessages().set("bl-fail", LocaleManager.getLocale().getBlacklistFail());
            getMessages().set("wl-fail", LocaleManager.getLocale().getWhitelistFail());
            getMessages().set("blw-fail", LocaleManager.getLocale().getWBlacklistFail());
            getMessages().set("wlw-fail", LocaleManager.getLocale().getWWhitelistFail());
            getMessages().set("disabled", LocaleManager.getLocale().getDisabledCommand());
            getMessages().set("empty-bl", LocaleManager.getLocale().getEmptyBlacklist());
            getMessages().set("empty-blw", LocaleManager.getLocale().getEmptyWBlacklist());
            getMessages().set("empty-wl", LocaleManager.getLocale().getEmptyWhitelist());
            getMessages().set("empty-wlw", LocaleManager.getLocale().getEmptyWWhitelist());
            getMessages().set("buy-success", LocaleManager.getLocale().getBuySuccess());
            getMessages().set("buy-fail", LocaleManager.getLocale().getBuyFail());
        }

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
