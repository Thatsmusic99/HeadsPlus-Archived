package io.github.thatsmusic99.headsplus.events;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

public enum HeadEnums {
	
	BLAZE(HeadsPlusConfigHeads.getHeads().getString("blazeHeadEN"), HeadsPlusConfigHeads.getHeads().getString("blazeHeadN")), CAVE_SPIDER(HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadEN"), HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadN"));
	String displayname;
	String name;
	
	HeadEnums(String dn, String n) {
		this.displayname = dn;
		this.name = n;
	}

}
