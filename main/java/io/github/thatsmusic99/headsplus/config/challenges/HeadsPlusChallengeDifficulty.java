package io.github.thatsmusic99.headsplus.config.challenges;

import io.github.thatsmusic99.headsplus.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum HeadsPlusChallengeDifficulty {
    
    EASY("easy", ChatColor.GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD + LocaleManager.getLocale().easy() + ChatColor.GRAY + "]", DyeColor.GREEN, 20),
    EASY_MEDIUM("easy_medium", ChatColor.GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD + LocaleManager.getLocale().easy() + ChatColor.GRAY + "-" + ChatColor.GOLD + ChatColor.BOLD + LocaleManager.getLocale().medium() + ChatColor.GRAY + "]", DyeColor.LIME, 22),
    MEDIUM("medium", ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + LocaleManager.getLocale().medium() + ChatColor.GRAY + "]", DyeColor.YELLOW, 24),
    MEDIUM_HARD("medium_hard", ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + LocaleManager.getLocale().medium() + ChatColor.GRAY + "-" + ChatColor.RED + ChatColor.BOLD + LocaleManager.getLocale().hard() + ChatColor.GRAY + "]", DyeColor.ORANGE, 30),
    HARD("hard", ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.BOLD + LocaleManager.getLocale().hard() + ChatColor.GRAY + "]", DyeColor.RED, 32);

    public String key;
    public String dn;
    public DyeColor color;
    public int i;
    public int p;

    HeadsPlusChallengeDifficulty(String key, String dn, DyeColor color, int i) {
        this.key = key;
        this.dn = dn;
        this.color = color;
        this.i = i;
    }
}
