package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.events.SectionChangeEvent;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HeadSection extends ItemStack implements Icon {
    @Override
    public String getIconName() {
        return "headsection";
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {

        String section = HeadsPlus.getInstance().getNMS().getSection(e.getCurrentItem());
        SectionChangeEvent event = new SectionChangeEvent(p, im.getSection(), section);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            im.setSection(section);
            try {
                p.openInventory(im.changePage(false, true, p, section));
            } catch (NoSuchFieldException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
            e.setCancelled(true);
        }

    }

    @Override
    public Material getDefaultMaterial() {
        return HeadsPlus.getInstance().getNMS().getSkullMaterial(1).getType();
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultDisplayName() {
        return "{head-name}";
    }

    @Override
    public Icon getReplacementIcon() {
        return new Air();
    }

    @Override
    public List<String> getLore() {
        return HeadsPlus.getInstance().getItems().getConfig().getStringList("icons." + getIconName() + ".lore");
    }

    @Override
    public String getSingleLetter() {
        return "L";
    }
}
