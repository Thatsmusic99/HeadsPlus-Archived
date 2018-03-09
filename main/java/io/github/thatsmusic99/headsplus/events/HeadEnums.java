package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(HeadsPlus.getInstance().hpch.getConfig().getString("blaze.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("blaze.name")),
	CAVE_SPIDER(HeadsPlus.getInstance().hpch.getConfig().getString("cavespider.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("cavespider.name")),
	CHICKEN(HeadsPlus.getInstance().hpch.getConfig().getString("chicken.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("chicken.name")),
	COW(HeadsPlus.getInstance().hpch.getConfig().getString("cow.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("cow.name")),
	CREEPER(HeadsPlus.getInstance().hpch.getConfig().getString("creeper.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("creeper.name")),
	ENDERMAN(HeadsPlus.getInstance().hpch.getConfig().getString("enderman.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("enderman.name")),
	GHAST(HeadsPlus.getInstance().hpch.getConfig().getString("ghast.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("ghast.name")),
	GUARDIAN(HeadsPlus.getInstance().hpch.getConfig().getString("guardian.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("guardian.name")),
	IRONGOLEM(HeadsPlus.getInstance().hpch.getConfig().getString("irongolem.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("irongolem.name")),
	MUSHROOMCOW(HeadsPlus.getInstance().hpch.getConfig().getString("mushroomcow.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("mushroomcow.name")),
	PIG(HeadsPlus.getInstance().hpch.getConfig().getString("pig.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("pig.name")),
	SHEEP(HeadsPlus.getInstance().hpch.getConfig().getString("sheep.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("sheep.name")),
	SKELETON(HeadsPlus.getInstance().hpch.getConfig().getString("skeleton.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("skeleton.name")),
	SLIME(HeadsPlus.getInstance().hpch.getConfig().getString("slime.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("slime.name")),
	SQUID(HeadsPlus.getInstance().hpch.getConfig().getString("squid.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("squid.name")),
	VILLAGER(HeadsPlus.getInstance().hpch.getConfig().getString("villager.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("villager.name")),
	WITCH(HeadsPlus.getInstance().hpch.getConfig().getString("witch.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("witch.name")),
	ZOMBIE(HeadsPlus.getInstance().hpch.getConfig().getString("zombie.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("zombie.name")),
	BAT(HeadsPlus.getInstance().hpch.getConfig().getString("bat.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("bat.name")),
	DONKEY(HeadsPlus.getInstance().hpch.getConfig().getString("donkey.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("donkey.name")),
	ENDERDRAGON(HeadsPlus.getInstance().hpch.getConfig().getString("enderdragon.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("enderdragon.name")),
	ELDERGUARDIAN(HeadsPlus.getInstance().hpch.getConfig().getString("elderguardian.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("elderguardian.name")),
	ENDERMITE(HeadsPlus.getInstance().hpch.getConfig().getString("endermite.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("endermite.name")),
	EVOKER(HeadsPlus.getInstance().hpch.getConfig().getString("evoker.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("evoker.name")),
	HORSE(HeadsPlus.getInstance().hpch.getConfig().getString("horse.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("horse.name")),
	LLAMA(HeadsPlus.getInstance().hpch.getConfig().getString("llama.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("llama.name")),
	MAGMACUBE(HeadsPlus.getInstance().hpch.getConfig().getString("magmacube.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("magmacube.name")),
	MULE(HeadsPlus.getInstance().hpch.getConfig().getString("mule.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("mule.name")),
	POLARBEAR(HeadsPlus.getInstance().hpch.getConfig().getString("polarbear.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("polarbear.name")),
	RABBIT(HeadsPlus.getInstance().hpch.getConfig().getString("rabbit.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("rabbit.name")),
	SHULKER(HeadsPlus.getInstance().hpch.getConfig().getString("shulker.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("shulker.name")),
	SILVERFISH(HeadsPlus.getInstance().hpch.getConfig().getString("silverfish.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("silverfish.name")),
	SKELETONHORSE(HeadsPlus.getInstance().hpch.getConfig().getString("skeletonhorse.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("skeletonhorse.name")),
	SNOWMAN(HeadsPlus.getInstance().hpch.getConfig().getString("snowman.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("snowman.name")),
	STRAY(HeadsPlus.getInstance().hpch.getConfig().getString("stray.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("stray.name")),
	VEX(HeadsPlus.getInstance().hpch.getConfig().getString("vex.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("vex.name")),
	VINDICATOR(HeadsPlus.getInstance().hpch.getConfig().getString("vindicator.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("vindicator.name")),
	WITHER(HeadsPlus.getInstance().hpch.getConfig().getString("wither.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("wither.name")),
	WITHERSKELETON(HeadsPlus.getInstance().hpch.getConfig().getString("witherskeleton.interact-name"), HeadsPlus.getInstance().hpch.getConfig().getStringList("witherskeleton.name")),
	APPLE(HeadsPlus.getInstance().hpch.getConfig().getString("appleHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("appleHeadN")),
	CAKE(HeadsPlus.getInstance().hpch.getConfig().getString("cakeHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("cakeHeadN")),
    CHEST(HeadsPlus.getInstance().hpch.getConfig().getString("chestHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("chestHeadN")),
    CACTUS(HeadsPlus.getInstance().hpch.getConfig().getString("cactusHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("cactusHeadN")),
    MELON(HeadsPlus.getInstance().hpch.getConfig().getString("melonHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("melonHeadN")),
    PUMPKIN(HeadsPlus.getInstance().hpch.getConfig().getString("pumpkinHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("pumpkinHeadN")),
    COCONUTB(HeadsPlus.getInstance().hpch.getConfig().getString("brownCoconutHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("brownCoconutHeadN")),
    COCONUTG(HeadsPlus.getInstance().hpch.getConfig().getString("greenCoconutHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("greenCoconutHeadN")),
    OAKLOG(HeadsPlus.getInstance().hpch.getConfig().getString("oaklogHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("oaklogHeadN")),
    PRESENT1(HeadsPlus.getInstance().hpch.getConfig().getString("present1HeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("present1HeadN")),
    PRESENT2(HeadsPlus.getInstance().hpch.getConfig().getString("present2HeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("present2HeadN")),
    TNT(HeadsPlus.getInstance().hpch.getConfig().getString("tntHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("tntHeadN")),
    TNT2(HeadsPlus.getInstance().hpch.getConfig().getString("tnt2HeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("tnt2HeadN")),
    ARROWUP(HeadsPlus.getInstance().hpch.getConfig().getString("arrowUpHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("arrowUpHeadN")),
    ARROWDOWN(HeadsPlus.getInstance().hpch.getConfig().getString("arrowDownHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("arrowDownHeadN")),
    ARROWLEFT(HeadsPlus.getInstance().hpch.getConfig().getString("arrowLeftHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("arrowLeftHeadN")),
    ARROWRIGHT(HeadsPlus.getInstance().hpch.getConfig().getString("arrowRightHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("arrowRightHeadN")),
    EXCLAMATION(HeadsPlus.getInstance().hpch.getConfig().getString("exclamationHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("exclamationHeadN")),
    QUESTION(HeadsPlus.getInstance().hpch.getConfig().getString("questionHeadEN"), HeadsPlus.getInstance().hpch.getConfig().getStringList("questionHeadN"));

	final String displayname;
	final List<String> name;
	
	HeadEnums(String dn, List<String> n) {
		this.displayname = dn;
		this.name = n;
	}

}
