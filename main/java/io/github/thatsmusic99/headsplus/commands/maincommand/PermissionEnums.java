package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;

public enum PermissionEnums {
	
	RELOAD("headsplus.maincommand.reload", "/hp reload", LocaleManager.getLocale().descMCReload(), "Reload", true),
	BLACKLIST_ADD("headsplus.maincommand.blacklist.add", "/hp blacklistadd <IGN>", LocaleManager.getLocale().descBlacklistAdd(), "Blacklistadd", true),
	BLACKLIST_DELETE("headsplus.maincommand.blacklist.delete", "/hp blacklistdel <IGN>", LocaleManager.getLocale().descBlacklistDelete(), "Blacklistdel", true),
	BLACKLIST_TOGGLE("headsplus.maincommand.blacklist.toggle", "/hp blacklist [On|Off]", LocaleManager.getLocale().descBlacklistToggle(), "Blacklist", true),
	INFO("headsplus.maincommand.info", "/hp info", LocaleManager.getLocale().descInfo(), "Info", true),
	HEAD("headsplus.head", "/head <IGN|Player> [IGN]", LocaleManager.getLocale().descHead(), "Head", false),
	BLACKLIST_LIST("headsplus.maincommand.blacklist.list", "/hp blacklistl [Page no.]", LocaleManager.getLocale().descBlacklistList(), "Blacklistl", true),
	SELLHEAD("headsplus.sellhead", "/sellhead [Entity name|#|all]", LocaleManager.getLocale().descSellhead(), "Sellhead", false),
	BLACKLISTW_ADD("headsplus.maincommand.blacklistw.add", "/hp blacklistwadd <World>", LocaleManager.getLocale().descBlacklistwAdd(), "Blacklistwadd", true),
	BLACKLISTW_DEL("headsplus.maincommand.blacklistw.delete", "/hp blacklistwdel <World>", LocaleManager.getLocale().descBlacklistwDelete(), "Blacklistwdel", true),
	BLACKLISTW_TOGGLE("headsplus.maincommand.blacklistw.toggle", "/hp blacklistw [On|Off]", LocaleManager.getLocale().descBlacklistwToggle(), "Blacklistw", true),
	BLACKLISTW_LIST("headsplus.maincommand.blacklistw.list", "/hp blacklistwl", LocaleManager.getLocale().descBlacklistwList(), "Blacklistwl", true),
	WHITELIST_ADD("headsplus.maincommand.whitelist.add", "/hp whitelistadd <IGN>", LocaleManager.getLocale().descWhitelistAdd(), "Whitelistadd", true),
	WHITELIST_DELETE("headsplus.maincommand.whitelist.delete", "/hp whitelistdel <IGN>", LocaleManager.getLocale().descWhitelistDelete(), "Whitelistdel", true),
	WHITELIST_TOGGLE("headsplus.maincommand.whitelist.toggle", "/hp whitelist [On|Off]", LocaleManager.getLocale().descWhitelistToggle(), "Whitelist", true),
    WHITELIST_LIST("headsplus.maincommand.whitelist.list", "/hp whitelistl [Page no.]", LocaleManager.getLocale().descWhitelistList(), "Whitelistl", true),
	WHITELISTW_ADD("headsplus.maincommand.whitelistw.add", "/hp whitelistwadd <World>", LocaleManager.getLocale().descWhitelistwAdd(), "Whitelistwadd", true),
	WHITELISTW_DEL("headsplus.maincommand.whitelistw.delete", "/hp whitelistwdel <World>", LocaleManager.getLocale().descWhitelistwDelete(), "Whitelistwdel", true),
	WHITELISTW_TOGGLE("headsplus.maincommand.whitelistw.toggle", "/hp whitelistw [On|Off]", LocaleManager.getLocale().descWhitelistwToggle(), "Whitelistw", true),
	WHITELISTW_LIST("headsplus.maincommand.whitelistw.list", "/hp whitelistwl", LocaleManager.getLocale().descWhitelistwList(), "Whitelistwl", true),
	HEADS("headsplus.heads", "/heads", LocaleManager.getLocale().descHeads(), "Heads", false),
	MYHEAD("headsplus.myhead", "/myhead", LocaleManager.getLocale().descMyHead(), "Myhead", false),
	HPLB("headsplus.leaderboards.display", "/hplb [Entity|Total|Page #] [Page #]", LocaleManager.getLocale().descHPLeaderboards(), "Hplb", false);
	
	public final String str;
	final String cmd;
	final String dsc;
	public final String scmd;
	public final boolean mcmd;

	PermissionEnums(String str, String cmd, String dsc, String scmd, boolean b) {
		this.str = str;
		this.cmd = cmd;
		this.dsc = dsc;
		this.scmd = scmd;
		this.mcmd = b;
	}

}
