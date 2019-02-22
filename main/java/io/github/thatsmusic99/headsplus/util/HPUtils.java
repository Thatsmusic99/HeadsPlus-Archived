package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HPUtils {

    public static String getHeader(int page, int pages) {
        HeadsPlus hp = HeadsPlus.getInstance();
        return hp.getThemeColour(1) + "===============" + hp.getThemeColour(2) + " HeadsPlus " + hp.getThemeColour(3) + page + "/" + pages + " " + hp.getThemeColour(1) + "===============\n";
    }

    public static boolean switchBoolean(boolean b) {
        return !b;
    }

    public static void addBossBar(OfflinePlayer pl) {
        HPPlayer p = HPPlayer.getHPPlayer(pl);
        ConfigurationSection c = HeadsPlus.getInstance().getConfiguration().getMechanics();
        if (c.getBoolean("boss-bar.enabled")) {
            if (p.getNextLevel() != null) {
                String s = ChatColor.translateAlternateColorCodes('&', c.getString("boss-bar.title"));
                BossBar b = HeadsPlus.getInstance().getServer().createBossBar(s, BarColor.valueOf(c.getString("boss-bar.color")), BarStyle.SEGMENTED_6);
                b.addPlayer((Player) p.getPlayer());
                Double d = ((double) p.getNextLevel().getRequiredXP() - p.getXp()) / (double) p.getNextLevel().getRequiredXP();
                b.setProgress(d);
                b.setVisible(true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.setVisible(false);
                    }
                }.runTaskLater(HeadsPlus.getInstance(), c.getInt("boss-bar.lifetime") * 20);
            }
        }
    }
}
