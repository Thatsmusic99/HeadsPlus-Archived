package io.github.thatsmusic99.headsplus.commands.maincommand;

public enum PermissionEnums {
	
	RELOAD("headsplus.maincommand.reload"), BLACKLIST_ADD("headsplus.maincommand.blacklist.add"), BLACKLIST_DELETE("headsplus.maincommand.blacklist.delete"), BLACKLIST_TOGGLE("headsplus.maincommand.blacklist.toggle"), INFO("headsplus.maincommand.info"), HEAD("headsplus.head"), BLACKLIST_LIST("headsplus.maincommand.blacklist.list"), PURGEDATA("headsplus.maincommand.purgedata"), SELLHEAD("headsplus.sellhead"), BLACKLISTW_ADD("headsplus.maincommand.blacklistw.add"), BLACKLISTW_DEL("headsplus.maincommand.blacklistw.del"), BLACKLISTW_TOGGLE("headsplus.maincommand.blacklistw.toggle"), BLACKLISTW_LIST("headsplus.maincommand.blacklistw.list");
	String str;
	
	private PermissionEnums(String str) {
		this.str = str;
	}

}
