package io.github.thatsmusic99.headsplus.events;

import java.util.*;

import io.github.thatsmusic99.headsplus.api.EntityHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;

public class DeathEvents implements Listener {

    public DeathEvents() {
        createList();
    }
	
	public final List<EntityType> ableEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HORSE, EntityType.IRON_GOLEM, EntityType.MAGMA_CUBE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.RABBIT, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER, EntityType.WITCH, EntityType.WITHER, EntityType.ZOMBIE));
    private final HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().getHeadsXConfig();
    private final HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
	    try {
	        if (!HeadsPlus.getInstance().isDropsEnabled()) return;
            if (ableEntities.contains(e.getEntityType())) {
                if (e.getEntity().getKiller() != null) {
                    if (!HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(e.getEntity().getWorld().getName())) {
                        if (!e.getEntity().getKiller().hasPermission("headsplus.bypass.whitelistw")) {
                            if (HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
                                return;
                            }
                        }
                    }
                    if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(e.getEntity().getWorld().getName()) || e.getEntity().getKiller().hasPermission("headsplus.bypass.blacklistw") || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn")) {
                        String entity = e.getEntityType().toString().toLowerCase().replaceAll("_", "");
                        Random rand = new Random();
                        double chance1 = hpch.getConfig().getDouble(entity + ".chance");
                        double chance2 = (double) rand.nextInt(100);
                        if (chance1 == 0.0) return;
                        if (chance2 <= chance1) {
                            if (entity.equalsIgnoreCase("sheep")) {
                                dropHead(e.getEntity(), e.getEntity().getKiller());
                            } else {
                                if (hpch.getConfig().getStringList(entity + ".name").isEmpty()) return;
                                dropHead(e.getEntity(), e.getEntity().getKiller());
                            }
                        }
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
            if (!HeadsPlus.getInstance().isDropsEnabled()) return;
            if (ep.getEntity().getKiller() != null) {
                if (!HeadsPlus.getInstance().getConfig().getStringList("whitelistw").contains(ep.getEntity().getWorld().getName())) {
                    if (!ep.getEntity().getKiller().hasPermission("headsplus.bypass.whitelistw")) {
                        if (HeadsPlus.getInstance().getConfig().getBoolean("whitelistwOn")) {
                            return;
                        }
                    }
                }
                if (!HeadsPlus.getInstance().getConfig().getStringList("blacklistw").contains(ep.getEntity().getWorld().getName()) || ep.getEntity().getKiller().hasPermission("headsplus.bypass.blacklistw") || !HeadsPlus.getInstance().getConfig().getBoolean("blacklistwOn")) {
                    Random rand = new Random();
                    double chance1 = hpch.getConfig().getDouble("player.chance");
                    double chance2 = (double) rand.nextInt(100);
                    NMSManager nms = HeadsPlus.getInstance().getNMS();
                    if (chance1 == 0.0) return;
                    if (chance2 <= chance1) {
                        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        SkullMeta headM = (SkullMeta) head.getItemMeta();
                        headM = nms.setSkullOwner(ep.getEntity().getName(), headM);
                        headM.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpch.getConfig().getString("player.display-name").replaceAll("\\{player}", ep.getEntity().getName())));
                        List<String> strs = new ArrayList<>();
                        for (String str : hpch.getConfig().getStringList("player.lore")) {
                            strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{player}", ep.getEntity().getName()).replaceAll("\\{price}", String.valueOf(hpch.getConfig().getDouble("player.price")))));
                        }
                        headM.setLore(strs);
                        head.setItemMeta(headM);
                        Location entityLoc = ep.getEntity().getLocation();
                        double entityLocY = entityLoc.getY() + 1;
                        entityLoc.setY(entityLocY);
                        World world = ep.getEntity().getWorld();
                        head = nms.addNBTTag(head);
                        head = nms.setType("player", head);
                        PlayerHeadDropEvent event = new PlayerHeadDropEvent(ep.getEntity(), ep.getEntity().getKiller(), head, world, entityLoc);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            world.dropItem(event.getLocation(), event.getSkull());
                        }
                    }
                }
            }
        } catch (Exception e) {
	        new DebugPrint(e, "Event (DeathEvents)", false, null);
        }
	}

	private void createList() {
        String bukkitVersion = org.bukkit.Bukkit.getVersion();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
        if (bukkitVersion.contains("1.9")) {
            ableEntities.addAll(Collections.singletonList(EntityType.SHULKER));
        }
        if (bukkitVersion.contains("1.11")) {
			ableEntities.addAll(Arrays.asList(EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.EVOKER, EntityType.HUSK, EntityType.LLAMA, EntityType.MULE, EntityType.POLAR_BEAR, EntityType.SHULKER, EntityType.SKELETON_HORSE, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON));
		}
		if (bukkitVersion.contains("1.12")) {
			ableEntities.addAll(Arrays.asList(EntityType.DONKEY, EntityType.ELDER_GUARDIAN, EntityType.EVOKER, EntityType.HUSK, EntityType.LLAMA, EntityType.MULE, EntityType.PARROT, EntityType.POLAR_BEAR, EntityType.SHULKER, EntityType.SKELETON_HORSE, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITHER_SKELETON));
		}
	}

	private List<String> hasColor(Entity e) {
        if (e instanceof Sheep) {
            Sheep sheep = (Sheep) e;
            DyeColor dc = sheep.getColor();
            for (String str : hpch.getConfig().getConfigurationSection("sheep.name").getKeys(false)) {
                if (!str.equalsIgnoreCase("default")) {
                    if (dc.equals(DyeColor.valueOf(str))) {
                        return hpch.getConfig().getStringList("sheep.name." + str);
                    }
                }
            }
        }
        return null;
    }

    private void dropHead(Entity e, Player k) throws NoSuchFieldException, IllegalAccessException {
	    Random r = new Random();
	    int thing;
	    String s;
	    ItemStack i;
	    try {
            if (hasColor(e) != null && !hasColor(e).isEmpty()) {

                thing = r.nextInt(hasColor(e).size());
                s = hasColor(e).get(thing);
            } else {
                if (e instanceof Sheep) {
                    thing = r.nextInt(hpch.getConfig().getStringList("sheep.name.default").size());
                    s = hpch.getConfig().getStringList("sheep.name.default").get(thing);
                } else {
                    thing = r.nextInt(hpch.getConfig().getStringList(e.getType().name().replaceAll("_", "").toLowerCase() + ".name").size());
                    s = hpch.getConfig().getStringList(e.getType().name().replaceAll("_", "").toLowerCase() + ".name").get(thing);
                }
            }
        } catch (IndexOutOfBoundsException ex) {
	        return;
        }
        SkullMeta sm;
        if (hpchx.isHPXSkull(s))  {
            i = hpchx.getSkull(s);
            sm = (SkullMeta) i.getItemMeta();
        } else {
            i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

            sm = (SkullMeta) i.getItemMeta();
            sm = HeadsPlus.getInstance().getNMS().setSkullOwner(s, sm);
        }

        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpch.getConfig().getString(e.getType().name().replaceAll("_", "").toLowerCase() + ".display-name")));
        List<String> strs = new ArrayList<>();
        for (String str : hpch.getConfig().getStringList(e.getType().name().replaceAll("_", "").toLowerCase() + ".lore")) {
            strs.add(ChatColor.translateAlternateColorCodes('&', str.replaceAll("\\{type}", e.getType().name().replaceAll("_", "").toLowerCase()).replaceAll("\\{price}", String.valueOf(hpch.getConfig().getDouble(e.getType().name().replaceAll("_", "").toLowerCase() + ".price")))));
        }
        sm.setLore(strs);
        i.setItemMeta(sm);
        Location entityLoc = e.getLocation();
        double entityLocY = entityLoc.getY() + 1;
        entityLoc.setY(entityLocY);
        World world = e.getWorld();
        i = HeadsPlus.getInstance().getNMS().addNBTTag(i);
        i = HeadsPlus.getInstance().getNMS().setType(e.getType().name().replaceAll("_", "").toLowerCase(), i);
        EntityHeadDropEvent event = new EntityHeadDropEvent(k, i, world, entityLoc, e.getType());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            world.dropItem(event.getLocation(), event.getSkull());
            HPPlayer.getHPPlayer(k).addXp(10);
        }
    }
}