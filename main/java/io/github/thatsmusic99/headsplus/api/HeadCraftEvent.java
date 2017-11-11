package io.github.thatsmusic99.headsplus.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class HeadCraftEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ItemStack skull;
    private World world;
    private Location location;

    public HeadCraftEvent(Player p, ItemStack head, World world, Location location) {
        player = p;
        skull = head;
        this.world = world;
        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getSkull() {
        return skull;
    }
}
