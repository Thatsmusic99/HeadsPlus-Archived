package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(HeadsPlus.getInstance().hpch.getHeads().getString("blaze.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("blaze.name")),
	CAVE_SPIDER(HeadsPlus.getInstance().hpch.getHeads().getString("cavespider.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cavespider.name")),
	CHICKEN(HeadsPlus.getInstance().hpch.getHeads().getString("chicken.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("chicken.name")),
	COW(HeadsPlus.getInstance().hpch.getHeads().getString("cow.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("cow.name")),
	CREEPER(HeadsPlus.getInstance().hpch.getHeads().getString("creeper.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("creeper.name")),
	ENDERMAN(HeadsPlus.getInstance().hpch.getHeads().getString("enderman.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("enderman.name")),
	GHAST(HeadsPlus.getInstance().hpch.getHeads().getString("ghast.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("ghast.name")),
	GUARDIAN(HeadsPlus.getInstance().hpch.getHeads().getString("guardian.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("guardian.name")),
	IRONGOLEM(HeadsPlus.getInstance().hpch.getHeads().getString("irongolem.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("irongolem.name")),
	MUSHROOMCOW(HeadsPlus.getInstance().hpch.getHeads().getString("mushroomcow.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("mushroomcow.name")),
	PIG(HeadsPlus.getInstance().hpch.getHeads().getString("pig.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("pig.name")),
	SHEEP(HeadsPlus.getInstance().hpch.getHeads().getString("sheep.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("sheep.name")),
	SKELETON(HeadsPlus.getInstance().hpch.getHeads().getString("skeleton.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("skeleton.name")),
	SLIME(HeadsPlus.getInstance().hpch.getHeads().getString("slime.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("slime.name")),
	SQUID(HeadsPlus.getInstance().hpch.getHeads().getString("squid.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("squid.name")),
	VILLAGER(HeadsPlus.getInstance().hpch.getHeads().getString("villager.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("villager.name")),
	WITCH(HeadsPlus.getInstance().hpch.getHeads().getString("witch.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("witch.name")),
	ZOMBIE(HeadsPlus.getInstance().hpch.getHeads().getString("zombie.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("zombie.name")),
	BAT(HeadsPlus.getInstance().hpch.getHeads().getString("bat.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("bat.name")),
	DONKEY(HeadsPlus.getInstance().hpch.getHeads().getString("donkey.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("donkey.name")),
	ENDERDRAGON(HeadsPlus.getInstance().hpch.getHeads().getString("enderdragon.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("enderdragon.name")),
	ELDERGUARDIAN(HeadsPlus.getInstance().hpch.getHeads().getString("elderguardian.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("elderguardian.name")),
	ENDERMITE(HeadsPlus.getInstance().hpch.getHeads().getString("endermite.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("endermite.name")),
	EVOKER(HeadsPlus.getInstance().hpch.getHeads().getString("evoker.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("evoker.name")),
	HORSE(HeadsPlus.getInstance().hpch.getHeads().getString("horse.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("horse.name")),
	LLAMA(HeadsPlus.getInstance().hpch.getHeads().getString("llama.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("llama.name")),
	MAGMACUBE(HeadsPlus.getInstance().hpch.getHeads().getString("magmacube.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("magmacube.name")),
	MULE(HeadsPlus.getInstance().hpch.getHeads().getString("mule.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("mule.name")),
	POLARBEAR(HeadsPlus.getInstance().hpch.getHeads().getString("polarbear.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("polarbear.name")),
	RABBIT(HeadsPlus.getInstance().hpch.getHeads().getString("rabbit.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("rabbit.name")),
	SHULKER(HeadsPlus.getInstance().hpch.getHeads().getString("shulker.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("shulker.name")),
	SILVERFISH(HeadsPlus.getInstance().hpch.getHeads().getString("silverfish.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("silverfish.name")),
	SKELETONHORSE(HeadsPlus.getInstance().hpch.getHeads().getString("skeletonhorse.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("skeletonhorse.name")),
	SNOWMAN(HeadsPlus.getInstance().hpch.getHeads().getString("snowman.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("snowman.name")),
	STRAY(HeadsPlus.getInstance().hpch.getHeads().getString("stray.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("stray.name")),
	VEX(HeadsPlus.getInstance().hpch.getHeads().getString("vex.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("vex.name")),
	VINDICATOR(HeadsPlus.getInstance().hpch.getHeads().getString("vindicator.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("vindicator.name")),
	WITHER(HeadsPlus.getInstance().hpch.getHeads().getString("wither.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("wither.name")),
	WITHERSKELETON(HeadsPlus.getInstance().hpch.getHeads().getString("witherskeleton.interact-name"), HeadsPlus.getInstance().hpch.getHeads().getStringList("witherskeleton.name")),
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
