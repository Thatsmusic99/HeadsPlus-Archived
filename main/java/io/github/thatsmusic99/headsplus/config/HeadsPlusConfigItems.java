package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.Icon;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Challenge;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Glass;
import io.github.thatsmusic99.headsplus.config.headsx.icons.Head;
import io.github.thatsmusic99.headsplus.nms.NewNMSManager;

import java.util.ArrayList;
import java.util.List;

public class HeadsPlusConfigItems extends ConfigSettings {

    public HeadsPlusConfigItems() {
        conName = "inventories";
    }

    @Override
    protected void load(boolean nullp) {
        for (Icon i : Icon.getIcons()) {
            getConfig().addDefault("icons." + i.getIconName() + ".material", i.getDefaultMaterial());
            getConfig().addDefault("icons." + i.getIconName() + ".display-name", i.getDefaultDisplayName());
            getConfig().addDefault("icons." + i.getIconName() + ".lore", i.getDefaultLore());
            getConfig().addDefault("icons." + i.getIconName() + ".replacement", i.getReplacementIcon().getIconName());
            if (i instanceof Challenge) {
                getConfig().addDefault("icons." + i.getIconName() + ".complete-material", ((Challenge) i).getCompleteMaterial());
            }
            if (!(HeadsPlus.getInstance().getNMS() instanceof NewNMSManager)) {
                if (i instanceof Challenge) {
                    getConfig().addDefault("icons." + i.getIconName() + ".complete-data-value", 5);
                    getConfig().addDefault("icons." + i.getIconName() + ".data-value", 14);
                } else if (i instanceof Glass) {
                    getConfig().addDefault("icons." + i.getIconName() + ".data-value", 8);
                } else if (i instanceof Head){
                    getConfig().addDefault("icons." + i.getIconName() + ".data-value", 3);
                } else {
                    getConfig().addDefault("icons." + i.getIconName() + ".data-value", 0);
                }
            }
        }
        for (HeadInventory inv : HeadInventory.getInventories()) {
             getConfig().addDefault("inventories." + inv.getName() + ".title", inv.getDefaultTitle());
             List<String> icons = new ArrayList<>();
             for (Icon i : inv.getDefaultItems()) {
                 icons.add(i.getIconName());
             }
             getConfig().addDefault("inventories." + inv.getName() + ".icons", icons);
        }
    }
}
