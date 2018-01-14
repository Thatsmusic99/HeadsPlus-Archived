package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(new HeadsPlusConfigHeads().getHeads().getString("blazeHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("blazeHeadN")),
	CAVE_SPIDER(new HeadsPlusConfigHeads().getHeads().getString("cavespiderHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("cavespiderHeadN")),
	CHICKEN(new HeadsPlusConfigHeads().getHeads().getString("chickenHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("chickenHeadN")),
	COW(new HeadsPlusConfigHeads().getHeads().getString("cowHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("cowHeadN")),
	CREEPER(new HeadsPlusConfigHeads().getHeads().getString("creeperHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("creeperHeadN")),
	ENDERMAN(new HeadsPlusConfigHeads().getHeads().getString("endermanHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("endermanHeadN")),
	GHAST(new HeadsPlusConfigHeads().getHeads().getString("ghastHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("ghastHeadN")),
	GUARDIAN(new HeadsPlusConfigHeads().getHeads().getString("guardianHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("guardianHeadN")),
	IRONGOLEM(new HeadsPlusConfigHeads().getHeads().getString("irongolemHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("irongolemHeadN")),
	MUSHROOMCOW(new HeadsPlusConfigHeads().getHeads().getString("mushroomcowHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("mushroomcowHeadN")),
	PIG(new HeadsPlusConfigHeads().getHeads().getString("pigHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("pigHeadN")),
	SHEEP(new HeadsPlusConfigHeads().getHeads().getString("sheepHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("sheepHeadN")),
	SKELETON(new HeadsPlusConfigHeads().getHeads().getString("skeletonHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("skeletonHeadN")),
	SLIME(new HeadsPlusConfigHeads().getHeads().getString("slimeHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("slimeHeadN")),
	SQUID(new HeadsPlusConfigHeads().getHeads().getString("squidHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("squidHeadN")),
	VILLAGER(new HeadsPlusConfigHeads().getHeads().getString("villagerHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("villagerHeadN")),
	WITCH(new HeadsPlusConfigHeads().getHeads().getString("witchHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("witchHeadN")),
	ZOMBIE(new HeadsPlusConfigHeads().getHeads().getString("zombieHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("zombieHeadN")),
	BAT(new HeadsPlusConfigHeads().getHeads().getString("batHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("batHeadN")),
	DONKEY(new HeadsPlusConfigHeads().getHeads().getString("donkeyHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("donkeyHeadN")),
	ENDERDRAGON(new HeadsPlusConfigHeads().getHeads().getString("enderdragonHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("enderdragonHeadN")),
	ELDERGUARDIAN(new HeadsPlusConfigHeads().getHeads().getString("elderguardianHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("elderguardianHeadN")),
	ENDERMITE(new HeadsPlusConfigHeads().getHeads().getString("endermiteHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("endermiteHeadN")),
	EVOKER(new HeadsPlusConfigHeads().getHeads().getString("evokerHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("evokerHeadN")),
	HORSE(new HeadsPlusConfigHeads().getHeads().getString("horseHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("horseHeadN")),
	LLAMA(new HeadsPlusConfigHeads().getHeads().getString("llamaHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("llamaHeadN")),
	MAGMACUBE(new HeadsPlusConfigHeads().getHeads().getString("magmacubeHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("magmacubeHeadN")),
	MULE(new HeadsPlusConfigHeads().getHeads().getString("muleHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("muleHeadN")),
	POLARBEAR(new HeadsPlusConfigHeads().getHeads().getString("polarbearHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("polarbearHeadN")),
	RABBIT(new HeadsPlusConfigHeads().getHeads().getString("rabbitHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("rabbitHeadN")),
	SHULKER(new HeadsPlusConfigHeads().getHeads().getString("shulkerHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("shulkerHeadN")),
	SILVERFISH(new HeadsPlusConfigHeads().getHeads().getString("silverfishHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("silverfishHeadN")),
	SKELETONHORSE(new HeadsPlusConfigHeads().getHeads().getString("skeletonhorseHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("skeletonhorseHeadN")),
	SNOWMAN(new HeadsPlusConfigHeads().getHeads().getString("snowmanHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("snowmanHeadN")),
	STRAY(new HeadsPlusConfigHeads().getHeads().getString("strayHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("strayHeadN")),
	VEX(new HeadsPlusConfigHeads().getHeads().getString("vexHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("vexHeadN")),
	VINDICATOR(new HeadsPlusConfigHeads().getHeads().getString("vindicatorHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("vindicatorHeadN")),
	WITHER(new HeadsPlusConfigHeads().getHeads().getString("witherHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("witherHeadN")),
	WITHERSKELETON(new HeadsPlusConfigHeads().getHeads().getString("witherskeletonHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("witherskeletonHeadN")),
	APPLE(new HeadsPlusConfigHeads().getHeads().getString("appleHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("appleHeadN")),
	CAKE(new HeadsPlusConfigHeads().getHeads().getString("cakeHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("cakeHeadN")),
    CHEST(new HeadsPlusConfigHeads().getHeads().getString("chestHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("chestHeadN")),
    CACTUS(new HeadsPlusConfigHeads().getHeads().getString("cactusHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("cactusHeadN")),
    MELON(new HeadsPlusConfigHeads().getHeads().getString("melonHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("melonHeadN")),
    PUMPKIN(new HeadsPlusConfigHeads().getHeads().getString("pumpkinHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("pumpkinHeadN")),
    COCONUTB(new HeadsPlusConfigHeads().getHeads().getString("brownCoconutHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("brownCoconutHeadN")),
    COCONUTG(new HeadsPlusConfigHeads().getHeads().getString("greenCoconutHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("greenCoconutHeadN")),
    OAKLOG(new HeadsPlusConfigHeads().getHeads().getString("oaklogHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("oaklogHeadN")),
    PRESENT1(new HeadsPlusConfigHeads().getHeads().getString("present1HeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("present1HeadN")),
    PRESENT2(new HeadsPlusConfigHeads().getHeads().getString("present2HeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("present2HeadN")),
    TNT(new HeadsPlusConfigHeads().getHeads().getString("tntHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("tntHeadN")),
    TNT2(new HeadsPlusConfigHeads().getHeads().getString("tnt2HeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("tnt2HeadN")),
    ARROWUP(new HeadsPlusConfigHeads().getHeads().getString("arrowUpHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("arrowUpHeadN")),
    ARROWDOWN(new HeadsPlusConfigHeads().getHeads().getString("arrowDownHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("arrowDownHeadN")),
    ARROWLEFT(new HeadsPlusConfigHeads().getHeads().getString("arrowLeftHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("arrowLeftHeadN")),
    ARROWRIGHT(new HeadsPlusConfigHeads().getHeads().getString("arrowRightHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("arrowRightHeadN")),
    EXCLAMATION(new HeadsPlusConfigHeads().getHeads().getString("exclamationHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("exclamationHeadN")),
    QUESTION(new HeadsPlusConfigHeads().getHeads().getString("questionHeadEN"), new HeadsPlusConfigHeads().getHeads().getStringList("questionHeadN"));

	final String displayname;
	final List<String> name;
	
	HeadEnums(String dn, List<String> n) {
		this.displayname = dn;
		this.name = n;
	}

}
