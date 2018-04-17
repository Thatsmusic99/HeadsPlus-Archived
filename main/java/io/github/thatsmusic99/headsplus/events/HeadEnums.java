package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;

import java.util.List;

public enum HeadEnums {
	
	BLAZE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("blaze.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("blaze.name")),
	CAVE_SPIDER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("cavespider.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("cavespider.name")),
	CHICKEN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("chicken.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("chicken.name")),
	COW(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("cow.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("cow.name")),
	CREEPER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("creeper.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("creeper.name")),
	ENDERMAN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("enderman.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("enderman.name")),
	GHAST(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("ghast.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("ghast.name")),
	GUARDIAN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("guardian.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("guardian.name")),
	IRONGOLEM(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("irongolem.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("irongolem.name")),
	MUSHROOMCOW(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("mushroomcow.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("mushroomcow.name")),
	PIG(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("pig.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("pig.name")),
	SHEEP(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("sheep.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("sheep.name")),
	SKELETON(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("skeleton.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("skeleton.name")),
	SLIME(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("slime.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("slime.name")),
	SQUID(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("squid.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("squid.name")),
	VILLAGER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("villager.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("villager.name")),
	WITCH(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("witch.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("witch.name")),
	ZOMBIE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("zombie.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("zombie.name")),
	BAT(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("bat.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("bat.name")),
	DONKEY(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("donkey.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("donkey.name")),
	ENDERDRAGON(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("enderdragon.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("enderdragon.name")),
	ELDERGUARDIAN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("elderguardian.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("elderguardian.name")),
	ENDERMITE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("endermite.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("endermite.name")),
	EVOKER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("evoker.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("evoker.name")),
	HORSE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("horse.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("horse.name")),
	LLAMA(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("llama.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("llama.name")),
	MAGMACUBE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("magmacube.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("magmacube.name")),
	MULE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("mule.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("mule.name")),
	POLARBEAR(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("polarbear.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("polarbear.name")),
	RABBIT(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("rabbit.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("rabbit.name")),
	SHULKER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("shulker.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("shulker.name")),
	SILVERFISH(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("silverfish.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("silverfish.name")),
	SKELETONHORSE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("skeletonhorse.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("skeletonhorse.name")),
	SNOWMAN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("snowman.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("snowman.name")),
	STRAY(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("stray.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("stray.name")),
	VEX(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("vex.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("vex.name")),
	VINDICATOR(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("vindicator.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("vindicator.name")),
	WITHER(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("wither.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("wither.name")),
	WITHERSKELETON(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("witherskeleton.interact-name"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("witherskeleton.name")),
	APPLE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("appleHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("appleHeadN")),
	CAKE(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("cakeHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("cakeHeadN")),
    CHEST(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("chestHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("chestHeadN")),
    CACTUS(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("cactusHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("cactusHeadN")),
    MELON(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("melonHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("melonHeadN")),
    PUMPKIN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("pumpkinHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("pumpkinHeadN")),
    COCONUTB(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("brownCoconutHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("brownCoconutHeadN")),
    COCONUTG(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("greenCoconutHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("greenCoconutHeadN")),
    OAKLOG(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("oaklogHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("oaklogHeadN")),
    PRESENT1(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("present1HeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("present1HeadN")),
    PRESENT2(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("present2HeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("present2HeadN")),
    TNT(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("tntHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("tntHeadN")),
    TNT2(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("tnt2HeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("tnt2HeadN")),
    ARROWUP(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("arrowUpHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("arrowUpHeadN")),
    ARROWDOWN(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("arrowDownHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("arrowDownHeadN")),
    ARROWLEFT(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("arrowLeftHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("arrowLeftHeadN")),
    ARROWRIGHT(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("arrowRightHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("arrowRightHeadN")),
    EXCLAMATION(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("exclamationHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("exclamationHeadN")),
    QUESTION(HeadsPlus.getInstance().getHeadsConfig().getConfig().getString("questionHeadEN"), HeadsPlus.getInstance().getHeadsConfig().getConfig().getStringList("questionHeadN"));

	final String displayname;
	final List<String> name;
	
	HeadEnums(String dn, List<String> n) {
		this.displayname = dn;
		this.name = n;
	}

}
