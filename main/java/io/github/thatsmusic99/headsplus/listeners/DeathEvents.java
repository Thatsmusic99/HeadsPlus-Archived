package io.github.thatsmusic99.headsplus.listeners;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.api.events.EntityHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.events.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import io.github.thatsmusic99.headsplus.reflection.NBTManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class DeathEvents implements Listener {

    public DeathEvents() {
        createList();
        setupHeads();
    }
	
	public final List<EntityType> ableEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HORSE, EntityType.IRON_GOLEM, EntityType.MAGMA_CUBE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.PIG_ZOMBIE, EntityType.RABBIT, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER, EntityType.WITCH, EntityType.WITHER, EntityType.ZOMBIE, EntityType.WOLF));
    private final HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().getHeadsXConfig();
    private final HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
    public static HashMap<EntityType, HashMap<String, List<ItemStack>>> heads = new HashMap<>();

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
	    try {
	        HeadsPlus hp = HeadsPlus.getInstance();
	        if (!hp.isDropsEnabled()) return;
	        if (checkForMythicMob(e.getEntity())) return;
            HeadsPlusMainConfig c = hp.getConfiguration();
            if (runAcceptTests(e.getEntity())) {
                String entity = e.getEntityType().toString().toLowerCase().replaceAll("_", "");
                Random rand = new Random();
                double chance1 = hpch.getConfig().getDouble(entity + ".chance");
                double chance2 = (double) rand.nextInt(100);
                int amount = 1;
                if (e.getEntity().getKiller() != null) {
                    if (HeadsPlus.getInstance().getNMS().getItemInHand(e.getEntity().getKiller()).containsEnchantment(Enchantment.LOOT_BONUS_MOBS)
                            && HeadsPlus.getInstance().getConfiguration().getMechanics().getBoolean("allow-looting-enchantment")
                            && !(HeadsPlus.getInstance().getConfiguration().getMechanics().getStringList("looting.ignored-entities").contains(e.getEntityType().name().replaceAll("_", "").toLowerCase()))) {
                        if (c.getMechanics().getBoolean("looting.use-old-system")) {
                            amount += HeadsPlus.getInstance().getNMS().getItemInHand(e.getEntity().getKiller()).getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) + 1;
                        } else {
                            chance1 *= (HeadsPlus.getInstance().getNMS().getItemInHand(e.getEntity().getKiller()).getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) + 1);
                            amount = ((int) chance1 / 100) == 0 ? 1 : (int) (chance1 / 100);
                        }

                    }
                }
                if (chance1 == 0.0) return;
                if (chance2 <= chance1) {

                    if (entity.equalsIgnoreCase("sheep") ||
                            entity.equalsIgnoreCase("parrot") ||
                            entity.equalsIgnoreCase("horse") ||
                            entity.equalsIgnoreCase("llama")) {
                        dropHead(e.getEntity(), e.getEntity().getKiller(), amount);
                    } else {
                        if (hpch.getConfig().getStringList(entity + ".name").isEmpty()) return;
                        dropHead(e.getEntity(), e.getEntity().getKiller(), amount);
                    }
                }
            }
        } catch (Exception ex) {
	        new DebugPrint(ex, "Event (DeathEvents)", false, null);
        }

	} 
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ep) {
	    try {
            HeadsPlus hp = HeadsPlus.getInstance();
            if (!hp.isDropsEnabled()) return;
            HeadsPlusMainConfig c = hp.getConfiguration();
            if (runAcceptTests(ep.getEntity())) {
                Random rand = new Random();
                double chance1 = hpch.getConfig().getDouble("player.chance");
                double chance2 = (double) rand.nextInt(100);
                NMSManager nms = hp.getNMS();
                int a = 1;
                if (ep.getEntity().getKiller() != null) {
                    if (nms.getItemInHand(ep.getEntity().getKiller()).containsEnchantment(Enchantment.LOOT_BONUS_MOBS)
                            && c.getMechanics().getBoolean("allow-looting-enchantment")
                            && !(c.getMechanics().getStringList("looting.ignored-entities").contains("player"))) {
                        if (c.getMechanics().getBoolean("looting.use-old-system")) {
                            a = HeadsPlus.getInstance().getNMS().getItemInHand(ep.getEntity().getKiller()).getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                        } else {
                            chance1 *= HeadsPlus.getInstance().getNMS().getItemInHand(ep.getEntity().getKiller()).getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                            a = ((int) chance1 / 100) == 0 ? 1 : (int) (chance1 / 100);
                        }
                    }
                }

                if (chance1 == 0.0) return;
                if (chance2 <= chance1) {


                    ItemStack head = nms.getSkullMaterial(a);
                    SkullMeta headM = (SkullMeta) head.getItemMeta();
                    headM = nms.setSkullOwner(ep.getEntity().getName(), headM);
                    headM.setDisplayName(hpch.getDisplayName("player").replaceAll("\\{player}", ep.getEntity().getName()));


                    Location entityLoc = ep.getEntity().getLocation();
                    double entityLocY = entityLoc.getY() + 1;
                    entityLoc.setY(entityLocY);
                    World world = ep.getEntity().getWorld();

                    double price = hpch.getPrice("player");
                    boolean b = hp.getConfiguration().getPerks().getBoolean("pvp.player-balance-competition");
                    double lostprice = 0.0;
                    if (b && HeadsPlus.getInstance().getEconomy().getBalance(ep.getEntity()) > 0.0) {
                        double playerprice = HeadsPlus.getInstance().getEconomy().getBalance(ep.getEntity());
                        price = playerprice * hp.getConfiguration().getPerks().getDouble("pvp.percentage-balance-for-head");
                        lostprice = HeadsPlus.getInstance().getEconomy().getBalance(ep.getEntity()) * hp.getConfiguration().getPerks().getDouble("pvp.percentage-lost");
                    }

                    List<String> strs = new ArrayList<>();
                    for (String str : hpch.getLore("player")) {
                        strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{player}", ep.getEntity().getName()).replaceAll("\\{price}", String.valueOf(HeadsPlus.getInstance().getConfiguration().fixBalanceStr(price)))));
                    }
                    headM.setLore(strs);
                    head.setItemMeta(headM);
                    NBTManager nbt = HeadsPlus.getInstance().getNBTManager();
                    head = nbt.makeSellable(head);
                    head = nbt.setType(head, "player");
                    head = nbt.setPrice(head, price);
                    PlayerHeadDropEvent event = new PlayerHeadDropEvent(ep.getEntity(), ep.getEntity().getKiller(), head, world, entityLoc);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        if (b && ep.getEntity().getKiller() != null) {
                            hp.getEconomy().withdrawPlayer(ep.getEntity(), lostprice);
                            ep.getEntity().sendMessage(hp.getMessagesConfig().getString("lost-money").replaceAll("\\{player}", ep.getEntity().getKiller().getName()).replaceAll("\\{price}", String.valueOf(HeadsPlus.getInstance().getConfiguration().fixBalanceStr(price))));
                        }
                        world.dropItem(event.getLocation(), event.getSkull());
                    }
                }
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Event (DeathEvents)", false, null);
        }
	}

	private void createList() {
        String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (bukkitVersion) {
            case "v1_14_R1":
                ableEntities.addAll(Arrays.asList(EntityType.CAT, EntityType.FOX, EntityType.PANDA, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.TRADER_LLAMA, EntityType.WANDERING_TRADER));
            case "v1_13_R1":
            case "v1_13_R2":
                ableEntities.addAll(Arrays.asList(EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.PHANTOM, EntityType.TURTLE, EntityType.DOLPHIN, EntityType.DROWNED, EntityType.ZOMBIE_VILLAGER));
            case "v1_12_R1":
                ableEntities.addAll(Collections.singletonList(EntityType.PARROT));
            case "v1_11_R1":
                ableEntities.addAll(Arrays.asList(EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.EVOKER, EntityType.HUSK, EntityType.LLAMA, EntityType.MULE, EntityType.POLAR_BEAR, EntityType.SKELETON_HORSE, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON, EntityType.ZOMBIE_HORSE));
            case "v1_10_R1":
            case "v1_9_R1":
            case "v1_9_R2":
                ableEntities.addAll(Collections.singletonList(EntityType.SHULKER));
                break;
        }
	}

	private void setupHeads() {
	    NMSManager nms = HeadsPlus.getInstance().getNMS();
	    for (EntityType e : ableEntities) {
	        try {
                HeadsPlus.getInstance().debug("Creating head for " + e.name() + "...", 3);
                HashMap<String, List<ItemStack>> keys = new HashMap<>();
                List<ItemStack> heads = new ArrayList<>();
                if (e == EntityType.SHEEP) {
                    keys = a("sheep", keys);
                    DeathEvents.heads.put(e, keys);
                    continue;
                }
                if (HeadsPlus.getInstance().getNMSVersion().getOrder() > 7) {
                    if (e == EntityType.LLAMA) {
                        keys = a("llama", keys);
                        DeathEvents.heads.put(e, keys);
                        continue;
                    }
                    if (e == EntityType.PARROT) {
                        keys = a("parrot", keys);
                        DeathEvents.heads.put(e, keys);
                        continue;
                    }
                }
                if (e == EntityType.HORSE) {
                    keys = a("horse", keys);
                    DeathEvents.heads.put(e, keys);
                    continue;
                }
                String fancyName = e.name().toLowerCase().replaceAll("_", "");
                for (String name : hpch.getConfig().getStringList(fancyName + ".name")) {

                    ItemStack is;
                    if (hpchx.isHPXSkull(name)) {
                        is = hpchx.getSkull(name);
                    } else if (name.equalsIgnoreCase("{mob-default}")) {
                        try {
                            if (e == EntityType.SKELETON) {
                                is = nms.getSkull(0);
                            } else if (e == EntityType.WITHER_SKELETON) {
                                is = nms.getSkull(1);
                            } else if (e == EntityType.ZOMBIE) {
                                is = nms.getSkull(2);
                            } else if (e == EntityType.CREEPER) {
                                is = nms.getSkull(4);
                            } else if (e == EntityType.ENDER_DRAGON) {
                                is = nms.getSkull(5);
                            } else {
                                is = nms.getSkull(3);
                            }
                        } catch (NoSuchFieldError ex) {
                            HeadsPlus.getInstance().getLogger().warning("Error thrown when trying to add a mob-default head. Setting to player skull...");
                            is = nms.getSkull(3);
                        }


                    } else {

                        is = nms.getSkullMaterial(1);
                        double price = hpch.getPrice(fancyName);
                        SkullMeta sm = (SkullMeta) is.getItemMeta();
                        sm = nms.setSkullOwner(name, sm);
                        sm.setDisplayName(hpch.getDisplayName(fancyName));
                        List<String> strs = new ArrayList<>();
                        List<String> lore = hpch.getLore(fancyName);
                        for (String str2 : lore) {
                            strs.add(ChatColor.translateAlternateColorCodes('&', str2.replaceAll("\\{type}", fancyName).replaceAll("\\{price}", String.valueOf(HeadsPlus.getInstance().getConfiguration().fixBalanceStr(price)))));
                        }
                        sm.setLore(strs);
                        is.setItemMeta(sm);
                        NBTManager nbt = HeadsPlus.getInstance().getNBTManager();
                        is = nbt.makeSellable(is);
                        is = nbt.setType(is, fancyName);
                        is = nbt.setPrice(is, price);
                    }
                    heads.add(is);

                }
                keys.put("default", heads);
                DeathEvents.heads.put(e, keys);
            } catch (Exception ex) {
	            HeadsPlus.getInstance().getLogger().severe("Error thrown when creating the head for " + e.name() + ". If it's a custom head, please double check the name.");
	            ex.printStackTrace();
            }

        }
    }

    private HashMap<String, List<ItemStack>> a(String en,  HashMap<String, List<ItemStack>> keys) {

        for (String str : hpch.getConfig().getConfigurationSection(en + ".name").getKeys(false)) {
            List<ItemStack> heads = new ArrayList<>();
            for (String name : hpch.getConfig().getStringList(en + ".name." + str)) {
                ItemStack is = null;
                NMSManager nms = HeadsPlus.getInstance().getNMS();
                if (HeadsPlus.getInstance().getHeadsXConfig().isHPXSkull(name)) {
                    try {
                        is = HeadsPlus.getInstance().getHeadsXConfig().getSkull(name);
                    } catch (NullPointerException ex) {
                        HeadsPlus.getInstance().getLogger().warning("WARNING: NPE thrown at " + str + ", " + name + ". If this is light_gray, please change HP#light_gray_sheep to HP#silver_sheep. If not, make sure your HP# head is valid.");
                    }
                }  else {
                    is = nms.getSkullMaterial(1);
                }


                double price = hpch.getPrice(en);
                SkullMeta sm = (SkullMeta) is.getItemMeta();
                sm = nms.setSkullOwner(name, sm);
                sm.setDisplayName(hpch.getDisplayName(en));
                List<String> strs = new ArrayList<>();
                List<String> lore = hpch.getLore(en);
                for (String str2 : lore) {
                    strs.add(ChatColor.translateAlternateColorCodes('&', str2.replaceAll("\\{type}", en).replaceAll("\\{price}", String.valueOf(HeadsPlus.getInstance().getConfiguration().fixBalanceStr(price)))));
                }
                sm.setLore(strs);
                is.setItemMeta(sm);
                NBTManager nbt = HeadsPlus.getInstance().getNBTManager();
                is = nbt.makeSellable(is);
                is = nbt.setType(is, en);
                is = nbt.setPrice(is, price);
                heads.add(is);

            }
            keys.put(str, heads);
        }
        return keys;
    }

	private List<ItemStack> hasColor(Entity e) {
        if (e instanceof Sheep) {
            Sheep sheep = (Sheep) e;
            DyeColor dc = sheep.getColor();
            for (String str : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                if (!str.equalsIgnoreCase("default")) {
                    if (dc.equals(DyeColor.valueOf(str))) {
                        return heads.get(e.getType()).get(str);
                    }
                }
            }
        }
        if (HeadsPlus.getInstance().getNMSVersion().getOrder() > 7) {
            if (e instanceof Llama) {
                Llama llama = (Llama) e;
                Llama.Color color = llama.getColor();
                for (String str : hpch.getConfig().getConfigurationSection("llama.name").getKeys(false)) {
                    if (!str.equalsIgnoreCase("default")) {
                        if (color.equals(Llama.Color.valueOf(str))) {
                            return heads.get(e.getType()).get(str);
                        }
                    }
                }
            } else if (e instanceof Parrot) {
                Parrot parrot = (Parrot) e;
                Parrot.Variant va = parrot.getVariant();
                for (String str : hpch.getConfig().getConfigurationSection("parrot.name").getKeys(false)) {
                    if (!str.equalsIgnoreCase("default")) {
                        if (va.equals(Parrot.Variant.valueOf(str))) {
                            return heads.get(e.getType()).get(str);
                        }
                    }
                }
            }
        }
         if (e instanceof Horse) {
            Horse horse = (Horse) e;
            Horse.Color c = horse.getColor();
            for (String str : hpch.getConfig().getConfigurationSection("horse.name").getKeys(false)) {
                if (!str.equalsIgnoreCase("default")) {
                    if (c.equals(Horse.Color.valueOf(str))) {
                        return heads.get(e.getType()).get(str);
                    }
                }
            }
        }
        return null;
    }

    private void dropHead(Entity e, Player k, int a) {
	    Random r = new Random();
	    int thing;
	    ItemStack i;
	    List<ItemStack> af = hasColor(e);

	    String mobName = e.getType().name().replaceAll("_", "").toLowerCase();
	    try {
            if (af != null && !af.isEmpty()) {
                thing = r.nextInt(af.size());
                i = af.get(thing);
            } else {
                if (heads.get(e.getType()) == null) return;
                if (heads.get(e.getType()).get("default") == null) return;
                if (heads.get(e.getType()).get("default").size() < 1) return;
                if (e instanceof Sheep) {
                    thing = r.nextInt(hpch.getConfig().getStringList("sheep.name.default").size());
                } else if (e instanceof Horse) {
                    thing = r.nextInt(hpch.getConfig().getStringList("horse.name.default").size());
                } else if (HeadsPlus.getInstance().getNMSVersion().getOrder() > 7) {
                    if (e instanceof Parrot) {
                        thing = r.nextInt(hpch.getConfig().getStringList("parrot.name.default").size());
                    } else if (e instanceof Llama) {
                        thing = r.nextInt(hpch.getConfig().getStringList("llama.name.default").size());
                    } else {
                        thing = r.nextInt(hpch.getConfig().getStringList(mobName + ".name").size());
                    }
                } else {
                    thing = r.nextInt(hpch.getConfig().getStringList(mobName + ".name").size());
                }
                i = heads.get(e.getType()).get("default").get(thing);
            }
        } catch (IndexOutOfBoundsException ex) {
	        return;
        }
        i.setAmount(a);
        Location entityLoc = e.getLocation();
        double entityLocY = entityLoc.getY() + 1;
        entityLoc.setY(entityLocY);
        World world = e.getWorld();
        EntityHeadDropEvent event = new EntityHeadDropEvent(k, i, world, entityLoc, e.getType());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            world.dropItem(event.getLocation(), event.getSkull());
            if (k != null) {
                HPPlayer.getHPPlayer(k).addXp(10);
            }
        }
    }

    private boolean checkForMythicMob(Entity e) {
	    HeadsPlus hp = HeadsPlus.getInstance();
	    if (hp.getConfiguration().getMechanics().getBoolean("mythicmobs.no-hp-drops")) {
            if (hp.getServer().getPluginManager().getPlugin("MythicMobs") != null) {
                return MythicMobs.inst().getMobManager().isActiveMob(e.getUniqueId());
            }
        }

        return false;
    }

    public EntityType prettyStringToEntity(String s) {
	    for (EntityType e : ableEntities) {
	        if (e.name().replaceAll("_", "").equalsIgnoreCase(s)) {
	            return e;
            }
        }
        return null;
    }

    public void reload() {
	    heads = new HashMap<>();
        createList();
        setupHeads();
    }

    private boolean runAcceptTests(LivingEntity e) {
        HeadsPlusMainConfig c = HeadsPlus.getInstance().getConfiguration();
        // Killer checks
	    if (e.getKiller() == null) {
	        if (c.getPerks().getStringList("drops.entities-requiring-killer").contains(e.getName().replaceAll("_", "").toLowerCase())) {
	            return false;
            } else if (e instanceof Player) {
                if (c.getPerks().getStringList("drops.entities-requiring-killer").contains("player")) {
                    return false;
                }
            } else if (c.getPerks().getBoolean("drops.needs-killer")) {
	            return false;
            }
        }
        // Whitelist checks
        if (c.getWhitelist("world").getBoolean("enabled")) {
	        if (!c.getWhitelist("world").getStringList("list").contains(e.getWorld().getName())) {
	            if (e.getKiller() != null) {
	                return false;
                } else if (!e.getKiller().hasPermission("headsplus.bypass.whitelistw")) {
                    return false;
                }
            }
        }
        // Blacklist checks
        if (c.getBlacklist("world").getBoolean("enabled")) {
            if (c.getBlacklist("world").getStringList("list").contains(e.getWorld().getName())) {
                if (e.getKiller() != null) {
                    if (!e.getKiller().hasPermission("headsplus.bypass.blacklistw")) {
                        return false;
                    }
                }
            }
        }
        if (e instanceof Player) {
	        return !(c.getPerks().getStringList("drops.ignore-players").contains(e.getUniqueId().toString())
                    || c.getPerks().getStringList("drops.ignore-players").contains(e.getName()));
        } else {
            return ableEntities.contains(e.getType());
        }

    }
}