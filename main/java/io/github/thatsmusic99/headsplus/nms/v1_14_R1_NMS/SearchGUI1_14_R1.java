package io.github.thatsmusic99.headsplus.nms.v1_14_R1_NMS;

import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class SearchGUI1_14_R1 extends SearchGUI {
    public SearchGUI1_14_R1(Player player, AnvilClickEventHandler handler) {
        super(player, handler);
    }

    private class AnvilContainer extends ContainerAnvil {
        public AnvilContainer(int id, EntityHuman entity) {
            super(id, entity.inventory, ContainerAccess.at(entity.world, new BlockPosition(0, 0, 0)));
            checkReachable = true;
        }

        @Override
        public boolean canUse(EntityHuman entityhuman) {
            return true;
        }

    }


    public void open() {
        EntityPlayer p = ((CraftPlayer) getPlayer()).getHandle();
        AnvilContainer container = new AnvilContainer(p.nextContainerCounter(), p);
        inv = container.getBukkitView().getTopInventory();
        for (AnvilSlot slot : items.keySet()) {
            inv.setItem(slot.getSlot(), items.get(slot));
        }
        p.playerConnection.sendPacket(new PacketPlayOutOpenWindow(p.nextContainerCounter(), Containers.ANVIL, new ChatMessage("Repairing")));
        p.activeContainer = container;

        try {
            Field profileField;
            profileField = Container.class.getDeclaredField("windowId");
            profileField.setAccessible(true);
            profileField.set(p.activeContainer, p.nextContainerCounter());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        p.activeContainer.addSlotListener(p);
    }
}
