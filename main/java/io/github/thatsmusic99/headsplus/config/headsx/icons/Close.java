package io.github.thatsmusic99.headsplus.config.headsx.icons;

import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Close extends ItemStack implements Icon {
    @Override
    public String getIconName() {
        return "close";
    }

    @Override
    public void onClick(Player p) {

    }
}
