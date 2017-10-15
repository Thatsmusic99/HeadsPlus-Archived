package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;

public enum PermissionEnums {
	
	RELOAD("headsplus.maincommand.reload", "/hp reload", LocaleManager.getLocale().descMCReload(), "Reload"),
	BLACKLIST_ADD("headsplus.maincommand.blacklist.add", "/hp blacklistadd <IGN>", LocaleManager.getLocale().descBlacklistAdd(), "Blacklistadd"),
	BLACKLIST_DELETE("headsplus.maincommand.blacklist.delete", "/hp blacklistdel <IGN>", LocaleManager.getLocale().descBlacklistDelete(), "Blacklistdel"),
	BLACKLIST_TOGGLE("headsplus.maincommand.blacklist.toggle", "/hp blacklist [On|Off]", LocaleManager.getLocale().descBlacklistToggle(), "Blacklist"),
	INFO("headsplus.maincommand.info", "/hp info", LocaleManager.getLocale().descInfo(), "Info"),
	HEAD("headsplus.head", "/head <IGN>", LocaleManager.getLocale().descHead(), ""),
	BLACKLIST_LIST("headsplus.maincommand.blacklist.list", "/hp blacklistl [Page no.]", LocaleManager.getLocale().descBlacklistList(), "Blacklistl"),
	SELLHEAD("headsplus.sellhead", "/sellhead [Entity name|#|all]", LocaleManager.getLocale().descSellhead(), ""),
	BLACKLISTW_ADD("headsplus.maincommand.blacklistw.add", "/hp blacklistwadd <World>", LocaleManager.getLocale().descBlacklistwAdd(), "Blacklistwadd"),
	BLACKLISTW_DEL("headsplus.maincommand.blacklistw.delete", "/hp blacklistwdel <World>", LocaleManager.getLocale().descBlacklistwDelete(), "Blacklistwdel"),
	BLACKLISTW_TOGGLE("headsplus.maincommand.blacklistw.toggle", "/hp blacklistw [On|Off]", LocaleManager.getLocale().descBlacklistwToggle(), "Blacklistw"),
	BLACKLISTW_LIST("headsplus.maincommand.blacklistw.list", "/hp blacklistwl", LocaleManager.getLocale().descBlacklistwList(), "Blacklistwl"),
	WHITELIST_ADD("headsplus.maincommand.whitelist.add", "/hp whitelistadd <IGN>", LocaleManager.getLocale().descWhitelistAdd(), "Whitelistadd"),
	WHITELIST_DELETE("headsplus.maincommand.whitelist.delete", "/hp whitelistdel <IGN>", LocaleManager.getLocale().descWhitelistDelete(), "Whitelistdel"),
	WHITELIST_TOGGLE("headsplus.maincommand.whitelist.toggle", "/hp whitelist [On|Off]", LocaleManager.getLocale().descWhitelistToggle(), "Whitelist"),
    WHITELIST_LIST("headsplus.maincommand.whitelist.list", "/hp whitelistl [Page no.]", LocaleManager.getLocale().descWhitelistList(), "Whitelistl"),
	WHITELISTW_ADD("headsplus.maincommand.whitelistw.add", "/hp whitelistwadd <World>", LocaleManager.getLocale().descWhitelistwAdd(), "Whitelistwadd"),
	WHITELISTW_DEL("headsplus.maincommand.whitelistw.delete", "/hp whitelistwdel <World>", LocaleManager.getLocale().descWhitelistwDelete(), "Whitelistwdel"),
	WHITELISTW_TOGGLE("headsplus.maincommand.whitelistw.toggle", "/hp whitelistw [On|Off]", LocaleManager.getLocale().descWhitelistwToggle(), "Whitelistw"),
	WHITELISTW_LIST("headsplus.maincommand.whitelistw.list", "/hp whitelistwl", LocaleManager.getLocale().descWhitelistwList(), "Whitelistwl"),
	HEADS("headsplus.heads", "/heads", LocaleManager.getLocale().descHeads(), "");
	
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
