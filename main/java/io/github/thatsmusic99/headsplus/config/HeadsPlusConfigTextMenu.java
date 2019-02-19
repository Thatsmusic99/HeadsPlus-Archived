package io.github.thatsmusic99.headsplus.config;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.util.PagedLists;

import java.util.ArrayList;
import java.util.Arrays;

public class HeadsPlusConfigTextMenu extends ConfigSettings {

    public HeadsPlusConfigTextMenu() {
        this.conName = "textmenus";
        enable(false);
    }

    @Override
    protected void load(boolean nullp) {
        getConfig().addDefault("default-header", "{1}=============== {2}HeadsPlus {1}===============");
        getConfig().addDefault("default-header-paged", "{1}=============== {2}HeadsPlus {3}{page}/{pages} {1}===============");
        getConfig().addDefault("help.header", "{default}");
        getConfig().addDefault("help.for-each-line", "{3}{usage} - {4}{description}");
        getConfig().addDefault("help.lines-per-page", 8);
        getConfig().addDefault("help.command-help", new ArrayList<>(Arrays.asList("{header}",
                "{3}Usage - {4}{usage}",
                "{3}Description - {4}{description}",
                "{3}Permission - {4}{permission}",
                "{3}Further usages - {4}{further-usage}")));
        getConfig().addDefault("head-info.normal-layout", new ArrayList<>(Arrays.asList("{header}",
                "{4}Type: {3}{type}",
                "{4}Display name: {3}{display-name}",
                "{4}Price: {3}{price}",
                "{4}Interact name: {3}{interact-name}",
                "{4}Chance: {3}{chance}")));
        getConfig().addDefault("head-info.mask-info.header", "{default}");
        getConfig().addDefault("head-info.mask-info.first-line", "{4}Type: {3}{type}");
        getConfig().addDefault("head-info.mask-info.for-each-line", "{3}{effect} ({amplifier})");
        getConfig().addDefault("head-info.mask-info.lines-per-page", 8);
        getConfig().addDefault("head-info.lore-info.header", "{default}");
        getConfig().addDefault("head-info.lore-info.first-line", "{4}Type: {3}{type}");
        getConfig().addDefault("head-info.lore-info.for-each-line", "{3}{lore}");
        getConfig().addDefault("head-info.name-info.colored.header", "{default}");
        getConfig().addDefault("head-info.name-info.colored.first-line", "{4}Type: {3}{type}");
        getConfig().addDefault("head-info.name-info.colored.for-each-line", "{3}{name} ({color})");
        getConfig().addDefault("head-info.name-info.colored.lines-per-page", 8);
        getConfig().addDefault("head-info.name-info.default.header", "{default}");
        getConfig().addDefault("head-info.name-info.default.first-line", "{4}Type: {3}{type}");
        getConfig().addDefault("head-info.name-info.default.for-each-line", "{3}{name}");
        getConfig().addDefault("head-info.name-info.default.lines-per-page", 8);
        getConfig().addDefault("profile.header", "{default}");
        getConfig().addDefault("profile.layout", new ArrayList<>(Arrays.asList("{header}",
                "{4}Player: {2}{player}",
                "{4}XP: {2}{xp}",
                "{4}Completed challenges: {2}{completed-challenges}",
                "{4}Total heads dropped: {2}{hunter-counter}",
                "{4}Total heads sold: {2}{sellhead-counter}",
                "{4}Total heads crafted: {2}{crafting-counter}",
                "{4}Current level: {2}{level}",
                "{4}XP until next level: {2}{next-level}")));
        getConfig().addDefault("blacklist.default.header", "{1}============ {2}Blacklist: {3}{page}/{pages} {1}============");
        getConfig().addDefault("blacklist.default.for-each-line", "{4}{name}");
        getConfig().addDefault("blacklist.default.lines-per-page", 8);
        getConfig().addDefault("blacklist.world.header", "{1}============ {2}World Blacklist: {3}{page}/{pages} {1}============");
        getConfig().addDefault("blacklist.world.for-each-line", "{4}{name}");
        getConfig().addDefault("blacklist.world.lines-per-page", 8);
        getConfig().addDefault("whitelist.default.header", "{1}============ {2}Whitelist: {3}{page}/{pages} {1}============");
        getConfig().addDefault("whitelist.default.for-each-line", "{4}{name}");
        getConfig().addDefault("whitelist.default.lines-per-page", 8);
        getConfig().addDefault("whitelist.world.header", "{1}============ {2}World Whitelist: {3}{page}/{pages} {1}============");
        getConfig().addDefault("whitelist.world.for-each-line", "{4}{name}");
        getConfig().addDefault("whitelist.world.lines-per-page", 8);
        getConfig().addDefault("leaderboard.header", "{1}======= {2}HeadsPlus Leaderboards: {section} {3}{page}/{pages} {1}=======");
        getConfig().addDefault("leaderboard.for-each-line", "{4}{pos}. {2}{name} {3}- {2}{score}");
        getConfig().addDefault("leaderboard.lines-per-page", 8);
        getConfig().options().copyDefaults(true);
        save();
    }

    private static String translateColors(String s) {
        HeadsPlus hp = HeadsPlus.getInstance();
        return translateHeader(s.replaceAll("\\{1}", hp.getThemeColour(1).toString())
                .replaceAll("\\{2}", hp.getThemeColour(2).toString())
                .replaceAll("\\{3}", hp.getThemeColour(3).toString())
                .replaceAll("\\{4}", hp.getThemeColour(4).toString()));
    }

    private static String translateHeader(String s) {
        return s.replaceAll("\\{header}", HeadsPlus.getInstance().getMenus().getConfig().getString("default-header"));
    }
    public static class BlacklistTranslator {

        public static String translate(String type, String type2, PagedLists<String> list, int page) {
            StringBuilder sb = new StringBuilder();
            HeadsPlusConfigTextMenu h = HeadsPlus.getInstance().getMenus();
            sb.append(translateColors(h.getConfig().getString(type + "." + type2 + ".header")
                    .replaceAll("\\{page}", String.valueOf(page))
            .replaceAll("\\{pages}", String.valueOf(list.getTotalPages())))).append("\n");
            for (String str : list.getContentsInPage(page)) {
                sb.append(translateColors(str.replaceAll("\\{name}", str))).append("\n");
            }
            return sb.toString();
        }
    }

    public static class ProfileTranslator {
        public static String translate(HPPlayer p) {
            StringBuilder sb = new StringBuilder();
            return sb.toString();
        }
    }
}
