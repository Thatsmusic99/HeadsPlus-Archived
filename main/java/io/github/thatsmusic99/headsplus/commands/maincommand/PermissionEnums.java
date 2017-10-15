package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;

public enum PermissionEnums {
	
	RELOAD("headsplus.maincommand.reload", "/hp reload", LocaleManager.getLocale().descMCReload(), "Reload"),
	BLACKLIST_ADD("headsplus.maincommand.blacklist.add", "/hp blacklistadd <IGN>", LocaleManager.getLocale().descBlacklistAdd(), "Blacklistadd"),
	BLACKLIST_DELETE("headsplus.maincommand.blacklist.delete", "/hp blacklistdel <IGN>", "Removes a head from the blacklist.", "Blacklistdel"),
	BLACKLIST_TOGGLE("headsplus.maincommand.blacklist.toggle", "/hp blacklist [On|Off]", "Toggles the blacklist.", "Blacklist"),
	INFO("headsplus.maincommand.info", "/hp info", "Displays plugin information.", "Info"),
	HEAD("headsplus.head", "/head <IGN>", "Spawns in a head.", ""),
	BLACKLIST_LIST("headsplus.maincommand.blacklist.list", "/hp blacklistl [Page no.]", "Lists all heads in the blacklist.", "Blacklistl"),
	SELLHEAD("headsplus.sellhead", "/sellhead [Entity name|#|all]", "Sells the head(s) in your hand, use number parameter to sell a specific number, entity name to sell a specific mob's head, and all to sell every head.", ""),
	BLACKLISTW_ADD("headsplus.maincommand.blacklistw.add", "/hp blacklistwadd <World>", "Adds a world to the crafting recipe blacklist.", "Blacklistwadd"),
	BLACKLISTW_DEL("headsplus.maincommand.blacklistw.delete", "/hp blacklistwdel <World>", "Removes a world to the crafting recipe blacklist.", "Blacklistwdel"),
	BLACKLISTW_TOGGLE("headsplus.maincommand.blacklistw.toggle", "/hp blacklistw [On|Off]", "Toggles the crafting recipe blacklist on/off.", "Blacklistw"),
	BLACKLISTW_LIST("headsplus.maincommand.blacklistw.list", "/hp blacklistwl", "Lists blacklisted worlds.", "Blacklistwl"),
	WHITELIST_ADD("headsplus.maincommand.whitelist.add", "/hp whitelistadd <IGN>", "Adds a head to the whitelist.", "Whitelistadd"),
	WHITELIST_DELETE("headsplus.maincommand.whitelist.delete", "/hp whitelistdel <IGN>", "Removes a head from the whitelist.", "Whitelistdel"),
	WHITELIST_TOGGLE("headsplus.maincommand.whitelist.toggle", "/hp whitelist [On|Off]", "Toggles the whitelist.", "Whitelist"),
    WHITELIST_LIST("headsplus.maincommand.whitelist.list", "/hp whitelistl [Page no.]", "Lists all heads in the whitelist.", "Whitelistl"),
	WHITELISTW_ADD("headsplus.maincommand.whitelistw.add", "/hp whitelistwadd <World>", "Adds a world to the crafting recipe whitelist.", "Whitelistwadd"),
	WHITELISTW_DEL("headsplus.maincommand.whitelistw.delete", "/hp whitelistwdel <World>", "Removes a world to the crafting recipe whitelist.", "Whitelistwdel"),
	WHITELISTW_TOGGLE("headsplus.maincommand.whitelistw.toggle", "/hp whitelistw [On|Off]", "Toggles the crafting recipe whitelist on/off.", "Whitelistw"),
	WHITELISTW_LIST("headsplus.maincommand.whitelistw.list", "/hp whitelistwl", "Lists whitelisted worlds.", "Whitelistwl");
	
	public final String str;
	final String cmd;
	final String dsc;
	public final String scmd;

	PermissionEnums(String str, String cmd, String dsc, String scmd) {
		this.str = str;
		this.cmd = cmd;
		this.dsc = dsc;
		this.scmd = scmd;
	}

}
