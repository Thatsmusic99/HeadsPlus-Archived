package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(HeadsPlus.getInstance().hpch.getHeads().getString("blazeHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("blazeHeadN")),
	CAVE_SPIDER(HeadsPlus.getInstance().hpch.getHeads().getString("cavespiderHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cavespiderHeadN")),
	CHICKEN(HeadsPlus.getInstance().hpch.getHeads().getString("chickenHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("chickenHeadN")),
	COW(HeadsPlus.getInstance().hpch.getHeads().getString("cowHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cowHeadN")),
	CREEPER(HeadsPlus.getInstance().hpch.getHeads().getString("creeperHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("creeperHeadN")),
	ENDERMAN(HeadsPlus.getInstance().hpch.getHeads().getString("endermanHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("endermanHeadN")),
	GHAST(HeadsPlus.getInstance().hpch.getHeads().getString("ghastHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("ghastHeadN")),
	GUARDIAN(HeadsPlus.getInstance().hpch.getHeads().getString("guardianHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("guardianHeadN")),
	IRONGOLEM(HeadsPlus.getInstance().hpch.getHeads().getString("irongolemHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("irongolemHeadN")),
	MUSHROOMCOW(HeadsPlus.getInstance().hpch.getHeads().getString("mushroomcowHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("mushroomcowHeadN")),
	PIG(HeadsPlus.getInstance().hpch.getHeads().getString("pigHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("pigHeadN")),
	SHEEP(HeadsPlus.getInstance().hpch.getHeads().getString("sheepHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("sheepHeadN")),
	SKELETON(HeadsPlus.getInstance().hpch.getHeads().getString("skeletonHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("skeletonHeadN")),
	SLIME(HeadsPlus.getInstance().hpch.getHeads().getString("slimeHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("slimeHeadN")),
	SQUID(HeadsPlus.getInstance().hpch.getHeads().getString("squidHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("squidHeadN")),
	VILLAGER(HeadsPlus.getInstance().hpch.getHeads().getString("villagerHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("villagerHeadN")),
	WITCH(HeadsPlus.getInstance().hpch.getHeads().getString("witchHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("witchHeadN")),
	ZOMBIE(HeadsPlus.getInstance().hpch.getHeads().getString("zombieHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("zombieHeadN")),
	BAT(HeadsPlus.getInstance().hpch.getHeads().getString("batHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("batHeadN")),
	DONKEY(HeadsPlus.getInstance().hpch.getHeads().getString("donkeyHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("donkeyHeadN")),
	ENDERDRAGON(HeadsPlus.getInstance().hpch.getHeads().getString("enderdragonHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("enderdragonHeadN")),
	ELDERGUARDIAN(HeadsPlus.getInstance().hpch.getHeads().getString("elderguardianHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("elderguardianHeadN")),
	ENDERMITE(HeadsPlus.getInstance().hpch.getHeads().getString("endermiteHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("endermiteHeadN")),
	EVOKER(HeadsPlus.getInstance().hpch.getHeads().getString("evokerHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("evokerHeadN")),
	HORSE(HeadsPlus.getInstance().hpch.getHeads().getString("horseHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("horseHeadN")),
	LLAMA(HeadsPlus.getInstance().hpch.getHeads().getString("llamaHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("llamaHeadN")),
	MAGMACUBE(HeadsPlus.getInstance().hpch.getHeads().getString("magmacubeHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("magmacubeHeadN")),
	MULE(HeadsPlus.getInstance().hpch.getHeads().getString("muleHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("muleHeadN")),
	POLARBEAR(HeadsPlus.getInstance().hpch.getHeads().getString("polarbearHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("polarbearHeadN")),
	RABBIT(HeadsPlus.getInstance().hpch.getHeads().getString("rabbitHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("rabbitHeadN")),
	SHULKER(HeadsPlus.getInstance().hpch.getHeads().getString("shulkerHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("shulkerHeadN")),
	SILVERFISH(HeadsPlus.getInstance().hpch.getHeads().getString("silverfishHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("silverfishHeadN")),
	SKELETONHORSE(HeadsPlus.getInstance().hpch.getHeads().getString("skeletonhorseHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("skeletonhorseHeadN")),
	SNOWMAN(HeadsPlus.getInstance().hpch.getHeads().getString("snowmanHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("snowmanHeadN")),
	STRAY(HeadsPlus.getInstance().hpch.getHeads().getString("strayHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("strayHeadN")),
	VEX(HeadsPlus.getInstance().hpch.getHeads().getString("vexHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("vexHeadN")),
	VINDICATOR(HeadsPlus.getInstance().hpch.getHeads().getString("vindicatorHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("vindicatorHeadN")),
	WITHER(HeadsPlus.getInstance().hpch.getHeads().getString("witherHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("witherHeadN")),
	WITHERSKELETON(HeadsPlus.getInstance().hpch.getHeads().getString("witherskeletonHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("witherskeletonHeadN")),
	APPLE(HeadsPlus.getInstance().hpch.getHeads().getString("appleHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("appleHeadN")),
	CAKE(HeadsPlus.getInstance().hpch.getHeads().getString("cakeHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cakeHeadN")),
    CHEST(HeadsPlus.getInstance().hpch.getHeads().getString("chestHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("chestHeadN")),
    CACTUS(HeadsPlus.getInstance().hpch.getHeads().getString("cactusHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cactusHeadN")),
    MELON(HeadsPlus.getInstance().hpch.getHeads().getString("melonHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("melonHeadN")),
    PUMPKIN(HeadsPlus.getInstance().hpch.getHeads().getString("pumpkinHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("pumpkinHeadN")),
    COCONUTB(HeadsPlus.getInstance().hpch.getHeads().getString("brownCoconutHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("brownCoconutHeadN")),
    COCONUTG(HeadsPlus.getInstance().hpch.getHeads().getString("greenCoconutHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("greenCoconutHeadN")),
    OAKLOG(HeadsPlus.getInstance().hpch.getHeads().getString("oaklogHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("oaklogHeadN")),
    PRESENT1(HeadsPlus.getInstance().hpch.getHeads().getString("present1HeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("present1HeadN")),
    PRESENT2(HeadsPlus.getInstance().hpch.getHeads().getString("present2HeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("present2HeadN")),
    TNT(HeadsPlus.getInstance().hpch.getHeads().getString("tntHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("tntHeadN")),
    TNT2(HeadsPlus.getInstance().hpch.getHeads().getString("tnt2HeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("tnt2HeadN")),
    ARROWUP(HeadsPlus.getInstance().hpch.getHeads().getString("arrowUpHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("arrowUpHeadN")),
    ARROWDOWN(HeadsPlus.getInstance().hpch.getHeads().getString("arrowDownHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("arrowDownHeadN")),
    ARROWLEFT(HeadsPlus.getInstance().hpch.getHeads().getString("arrowLeftHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("arrowLeftHeadN")),
    ARROWRIGHT(HeadsPlus.getInstance().hpch.getHeads().getString("arrowRightHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("arrowRightHeadN")),
    EXCLAMATION(HeadsPlus.getInstance().hpch.getHeads().getString("exclamationHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("exclamationHeadN")),
    QUESTION(HeadsPlus.getInstance().hpch.getHeads().getString("questionHeadEN"), HeadsPlus.getInstance().hpch.getHeads().getStringList("questionHeadN"));

	final String displayname;
	final List<String> name;
	
	HeadEnums(String dn, List<String> n) {
		this.displayname = dn;
		this.name = n;
	}

}
