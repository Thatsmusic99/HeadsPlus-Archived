package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.util.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Nav extends ItemStack implements Icon {

	final InventoryManager.Page direction;
	final String title;
	final Material mat;
	
	public Nav(InventoryManager.Page direction, String title, Material material) {
		this.direction = direction;
		this.title = title;
		this.mat = material;
	}

    @Override
    public String getIconName() {
        return direction.name().toLowerCase();
    }

    @Override
    public void onClick(Player p, InventoryManager im, InventoryClickEvent e) {
        e.setCancelled(true);
        p.closeInventory();
        try {
			im.showPage(direction);
        } catch (Exception e1) {
            new DebugPrint(e1, "Changing page (next)", false, p);
        }
    }

    @Override
    public Material getDefaultMaterial() {
        return mat;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultDisplayName() {
        return "&a&l" + title;
    }

    @Override
    public List<String> getLore() {
        return HeadsPlus.getInstance().getItems().getConfig().getStringList("icons." + getIconName() + ".lore");
    }

    @Override
    public String getSingleLetter() {
        return String.valueOf(direction.shortHand);
    }


}
