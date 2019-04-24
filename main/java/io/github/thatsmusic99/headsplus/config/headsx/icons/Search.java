package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesConfig;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import io.github.thatsmusic99.headsplus.nms.v1_12_NMS.SearchGUI1_12;
import io.github.thatsmusic99.headsplus.util.ChatPrompt;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Search extends ItemStack implements Icon {

    @Override
    public String getIconName() {
        return "search";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {
        e.setCancelled(true);
        p.closeInventory();
        final InventoryClickEvent ev = e;
        try {
            /* if (HeadsPlus.getInstance().getConfiguration().getMechanics().getBoolean("anvil-menu-search")) {
                SearchGUI s = HeadsPlus.getInstance().getNMS().getSearchGUI(p, event -> {

                    if (event.getSlot().equals(SearchGUI.AnvilSlot.OUTPUT)) {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                        im.setSection("search:" + event.getName());
                        event.getPlayer().closeInventory();
                        event.getPlayer().openInventory(im.changePage(false, true, event.getPlayer(), "search:" + event.getName()));
                    }


                    ev.setCancelled(true);
                });
                s.setSlot(SearchGUI1_12.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));
                s.open();
            } else { */
                ConversationFactory c = new ConversationFactory(HeadsPlus.getInstance());
                Conversation conv = c.withFirstPrompt(new ChatPrompt()).withLocalEcho(false).buildConversation(p);
                conv.addConversationAbandonedListener(event -> {
                    if (event.gracefulExit()) {
                        String input = String.valueOf(event.getContext().getSessionData("term"));
                        im.setSection(input);
                        p.closeInventory();
                        try {
                            p.openInventory(im.changePage(false, true, p, "search:" + input));
                        } catch (NoSuchFieldException | IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
                    }

                });
                conv.begin();
           // }

        } catch (Exception ex) {
            new DebugPrint(ex, "Event (InventoryEvent)", false, null);
        }
    }

    @Override
    public Material getDefaultMaterial() {
        return Material.NAME_TAG;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultDisplayName() {
        return "&6[&e&lSearch Heads&6]";
    }

    @Override
    public List<String> getLore() {
        return HeadsPlus.getInstance().getItems().getConfig().getStringList("icons." + getIconName() + ".lore");
    }

    @Override
    public String getSingleLetter() {
        return "K";
    }
}
