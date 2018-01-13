package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(HeadsPlusConfigHeads.getHeads().getString("blazeHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("blazeHeadN")),
	CAVE_SPIDER(HeadsPlusConfigHeads.getHeads().getString("cavespiderHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("cavespiderHeadN")),
	CHICKEN(HeadsPlusConfigHeads.getHeads().getString("chickenHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("chickenHeadN")),
	COW(HeadsPlusConfigHeads.getHeads().getString("cowHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("cowHeadN")),
	CREEPER(HeadsPlusConfigHeads.getHeads().getString("creeperHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("creeperHeadN")),
	ENDERMAN(HeadsPlusConfigHeads.getHeads().getString("endermanHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("endermanHeadN")),
	GHAST(HeadsPlusConfigHeads.getHeads().getString("ghastHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("ghastHeadN")),
	GUARDIAN(HeadsPlusConfigHeads.getHeads().getString("guardianHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("guardianHeadN")),
	IRONGOLEM(HeadsPlusConfigHeads.getHeads().getString("irongolemHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("irongolemHeadN")),
	MUSHROOMCOW(HeadsPlusConfigHeads.getHeads().getString("mushroomcowHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("mushroomcowHeadN")),
	PIG(HeadsPlusConfigHeads.getHeads().getString("pigHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("pigHeadN")),
	SHEEP(HeadsPlusConfigHeads.getHeads().getString("sheepHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("sheepHeadN")),
	SKELETON(HeadsPlusConfigHeads.getHeads().getString("skeletonHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("skeletonHeadN")),
	SLIME(HeadsPlusConfigHeads.getHeads().getString("slimeHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("slimeHeadN")),
	SQUID(HeadsPlusConfigHeads.getHeads().getString("squidHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("squidHeadN")),
	VILLAGER(HeadsPlusConfigHeads.getHeads().getString("villagerHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("villagerHeadN")),
	WITCH(HeadsPlusConfigHeads.getHeads().getString("witchHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("witchHeadN")),
	ZOMBIE(HeadsPlusConfigHeads.getHeads().getString("zombieHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("zombieHeadN")),
	BAT(HeadsPlusConfigHeads.getHeads().getString("batHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("batHeadN")),
	DONKEY(HeadsPlusConfigHeads.getHeads().getString("donkeyHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("donkeyHeadN")),
	ENDERDRAGON(HeadsPlusConfigHeads.getHeads().getString("enderdragonHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("enderdragonHeadN")),
	ELDERGUARDIAN(HeadsPlusConfigHeads.getHeads().getString("elderguardianHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("elderguardianHeadN")),
	ENDERMITE(HeadsPlusConfigHeads.getHeads().getString("endermiteHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("endermiteHeadN")),
	EVOKER(HeadsPlusConfigHeads.getHeads().getString("evokerHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("evokerHeadN")),
	HORSE(HeadsPlusConfigHeads.getHeads().getString("horseHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("horseHeadN")),
	LLAMA(HeadsPlusConfigHeads.getHeads().getString("llamaHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("llamaHeadN")),
	MAGMACUBE(HeadsPlusConfigHeads.getHeads().getString("magmacubeHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("magmacubeHeadN")),
	MULE(HeadsPlusConfigHeads.getHeads().getString("muleHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("muleHeadN")),
	POLARBEAR(HeadsPlusConfigHeads.getHeads().getString("polarbearHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("polarbearHeadN")),
	RABBIT(HeadsPlusConfigHeads.getHeads().getString("rabbitHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("rabbitHeadN")),
	SHULKER(HeadsPlusConfigHeads.getHeads().getString("shulkerHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("shulkerHeadN")),
	SILVERFISH(HeadsPlusConfigHeads.getHeads().getString("silverfishHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("silverfishHeadN")),
	SKELETONHORSE(HeadsPlusConfigHeads.getHeads().getString("skeletonhorseHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("skeletonhorseHeadN")),
	SNOWMAN(HeadsPlusConfigHeads.getHeads().getString("snowmanHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("snowmanHeadN")),
	STRAY(HeadsPlusConfigHeads.getHeads().getString("strayHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("strayHeadN")),
	VEX(HeadsPlusConfigHeads.getHeads().getString("vexHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("vexHeadN")),
	VINDICATOR(HeadsPlusConfigHeads.getHeads().getString("vindicatorHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("vindicatorHeadN")),
	WITHER(HeadsPlusConfigHeads.getHeads().getString("witherHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("witherHeadN")),
	WITHERSKELETON(HeadsPlusConfigHeads.getHeads().getString("witherskeletonHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("witherskeletonHeadN")),
	APPLE(HeadsPlusConfigHeads.getHeads().getString("appleHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("appleHeadN")),
	CAKE(HeadsPlusConfigHeads.getHeads().getString("cakeHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("cakeHeadN")),
    CHEST(HeadsPlusConfigHeads.getHeads().getString("chestHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("chestHeadN")),
    CACTUS(HeadsPlusConfigHeads.getHeads().getString("cactusHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("cactusHeadN")),
    MELON(HeadsPlusConfigHeads.getHeads().getString("melonHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("melonHeadN")),
    PUMPKIN(HeadsPlusConfigHeads.getHeads().getString("pumpkinHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("pumpkinHeadN")),
    COCONUTB(HeadsPlusConfigHeads.getHeads().getString("brownCoconutHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("brownCoconutHeadN")),
    COCONUTG(HeadsPlusConfigHeads.getHeads().getString("greenCoconutHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("greenCoconutHeadN")),
    OAKLOG(HeadsPlusConfigHeads.getHeads().getString("oaklogHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("oaklogHeadN")),
    PRESENT1(HeadsPlusConfigHeads.getHeads().getString("present1HeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("present1HeadN")),
    PRESENT2(HeadsPlusConfigHeads.getHeads().getString("present2HeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("present2HeadN")),
    TNT(HeadsPlusConfigHeads.getHeads().getString("tntHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("tntHeadN")),
    TNT2(HeadsPlusConfigHeads.getHeads().getString("tnt2HeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("tnt2HeadN")),
    ARROWUP(HeadsPlusConfigHeads.getHeads().getString("arrowUpHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("arrowUpHeadN")),
    ARROWDOWN(HeadsPlusConfigHeads.getHeads().getString("arrowDownHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("arrowDownHeadN")),
    ARROWLEFT(HeadsPlusConfigHeads.getHeads().getString("arrowLeftHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("arrowLeftHeadN")),
    ARROWRIGHT(HeadsPlusConfigHeads.getHeads().getString("arrowRightHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("arrowRightHeadN")),
    EXCLAMATION(HeadsPlusConfigHeads.getHeads().getString("exclamationHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("exclamationHeadN")),
    QUESTION(HeadsPlusConfigHeads.getHeads().getString("questionHeadEN"), HeadsPlusConfigHeads.getHeads().getStringList("questionHeadN"));

	final String displayname;
	final List<String> name;
	
	HeadEnums(String dn, List<String> n) {
		this.displayname = dn;
		this.name = n;
	}

}
