package io.github.thatsmusic99.headsplus.nms.v1_14_R1_NMS;

import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SearchGUI1_14_R1 extends SearchGUI {
    public SearchGUI1_14_R1(Player player, AnvilClickEventHandler handler) {
        super(player, handler);
    }

    private class AnvilContainer extends ContainerAnvil {
        public AnvilContainer(EntityHuman entity) {
            super(((CraftPlayer)((Player) entity)).getHandle().nextContainerCounter(), entity.inventory);
        }

        @Override
        public boolean canUse(EntityHuman entityhuman) {
            return true;
        }
    }


    public void open() {
        EntityPlayer p = ((CraftPlayer) getPlayer()).getHandle();

        AnvilContainer container = new AnvilContainer(p);

        //Set the items to the items from the inventory given
        inv = container.getBukkitView().getTopInventory();

        for (AnvilSlot slot : items.keySet()) {
            inv.setItem(slot.getSlot(), items.get(slot));
        }

        //Counter stuff that the game uses to keep track of inventories
        int c = p.nextContainerCounter();

        //Send the packet
      //  p.playerConnection.sendPacket(new PacketPlayOutOpenWindow(0, container, new ChatMessage("Repairing")));
        //Set their active container to the container
        p.activeContainer = container;

        //Set their active container window id to that counter stuff

        //Add the slot listener
        p.activeContainer.addSlotListener(p);
    }
}
