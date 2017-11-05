package io.github.thatsmusic99.headsplus.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class EntityHeadDropEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private boolean isAllowed;
    private ItemStack skull;
    private Player player;
    private EntityType entityType;
    private World world;
    private Location location;

    public EntityHeadDropEvent(boolean isAllowed, Player killer, ItemStack head, World world, Location location, EntityType entityType) {
        this.isAllowed = isAllowed;
        this.player = killer;
        this.skull = head;
        this.world = world;
        this.location = location;
        this.entityType = entityType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public ItemStack getSkull() {
        return skull;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getSkullKey() {
        try {
            return HeadsPlusAPI.getSkullType(skull);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    public Location getLocation() {
        return location;
    }
    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }
}
