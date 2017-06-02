package io.github.thatsmusic99.headsplus.commands.maincommand;

public enum PermissionEnums {
	
	RELOAD("headsplus.maincommand.reload", "/hp reload", "Reloads configuration files."), 
	BLACKLIST_ADD("headsplus.maincommand.blacklist.add", "/hp blacklistadd <IGN>", "Adds a head to the blacklist."), 
	BLACKLIST_DELETE("headsplus.maincommand.blacklist.delete", "/hp blacklistdel <IGN>", "Removes a head from the blacklist."), 
	BLACKLIST_TOGGLE("headsplus.maincommand.blacklist.toggle", "/hp blacklist [On|Off]", "Toggles the blacklist."), 
	INFO("headsplus.maincommand.info", "/hp info", "Displays plugin information."), 
	HEAD("headsplus.head", "/head <IGN>", "Spawns in a head."), 
	BLACKLIST_LIST("headsplus.maincommand.blacklist.list", "/hp blacklistl [Page no.]", "Lists all heads in the blacklist."), 
	SELLHEAD("headsplus.sellhead", "/sellhead [#]", "Sells the head(s) in your hand, use number parameter to sell a specific number."), 
	BLACKLISTW_ADD("headsplus.maincommand.blacklistw.add", "/hp blacklistwadd <World>", "Adds a world to the crafting recipe blacklist."), 
	BLACKLISTW_DEL("headsplus.maincommand.blacklistw.delete", "/hp blacklistwdel <World>", "Removes a world to the crafting recipe blacklist."), 
	BLACKLISTW_TOGGLE("headsplus.maincommand.blacklistw.toggle", "/hp blacklistw [On|Off]", "Toggles the crafting recipe blacklist on/off."), 
	BLACKLISTW_LIST("headsplus.maincommand.blacklistw.list", "/hp blacklistwl", "Lists blacklisted worlds.");
	
	String str;
	String cmd;
	String dsc;

	PermissionEnums(String str, String cmd, String dsc) {
		this.str = str;
		this.cmd = cmd;
		this.dsc = dsc;
	}

}
