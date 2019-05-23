package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.Challenge;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.headsx.HeadInventory;
import io.github.thatsmusic99.headsplus.config.headsx.HeadsPlusConfigHeadsX;
import io.github.thatsmusic99.headsplus.config.headsx.inventories.*;
import io.github.thatsmusic99.headsplus.nms.NMSManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.logging.Level;

public class InventoryManager {

    public static enum Type {

        SELL, LIST_MENU, LIST_CATEGORY, LIST_SEARCH, LIST_FAVORITES, CHALLENGES_MENU, CHALLENGES_LIST
    }

    public static enum Page {

        MENU('M'), START('<'), BACK('B'), NEXT('N'), BACK_2('['), BACK_3('{'), NEXT_2(']'), NEXT_3('}'), LAST('>');
        public final char shortHand;

        private Page(char shortHand) {
            this.shortHand = shortHand;
        }
    }
    public static final HashMap<Player, InventoryManager> pls = new HashMap<>();

    private final Player player;
    private int pages;
    private String menuSection;
    private List<String> searchResults = null;
    private int headsInSection;
    private int currentPage = 0;
    private HeadInventory inventory;
    private Type type;
    private final HeadsPlus plugin;
    private final boolean largerMenu;
    private final HeadsPlusConfigHeadsX hpchx;

    public int getPages() {
        return pages;
    }

    public int getPage() {
        return currentPage;
    }

    public int getHeads() {
        return headsInSection;
    }

    public String getSection() {
        return menuSection;
    }

    public Type getType() {
        return type;
    }

    public HeadInventory getInventory() {
        return inventory;
    }

    protected InventoryManager(Player p) {
        this.player = p;
        plugin = HeadsPlus.getInstance();
        hpchx = plugin.getHeadsXConfig();
        largerMenu = plugin.getConfig().getBoolean("plugin.larger-menus", false);
    }

    public static InventoryManager get(Player p) {
        return pls.get(p);
    }

    public static InventoryManager getOrCreate(Player p) {
        InventoryManager im = pls.get(p);
        if (im == null) {
            pls.put(p, im = new InventoryManager(p));
        }
        return im;
    }

    public static void close(Player p) {
        InventoryManager im = pls.get(p);
        if (im != null) {
            im.inventory = null;
        }
    }

    public void showScreen(Type type) {
        this.type = type;
        currentPage = 0;
        player.closeInventory();
        if (type == Type.LIST_FAVORITES) {
            searchResults = loadFavoriteHeads();
        }
        player.openInventory(getPageInventory());
    }

    public void showSearch(String search) {
        searchResults = searchHeads(menuSection = search);
        showScreen(Type.LIST_SEARCH);
    }

    public void showSection(String sec) {
        menuSection = sec;
        showScreen(Type.LIST_CATEGORY);
    }

    public void showChallengeSection(String sec) {
        menuSection = sec;
        showScreen(Type.CHALLENGES_LIST);
    }

    public void showPage(Page page) {
        if (page == Page.MENU) {
            switch (type) {
                case CHALLENGES_LIST:
                    // change to CHALLENGES_MENU
                    currentPage = 0;
                    type = Type.CHALLENGES_MENU;
                    break;
                case LIST_CATEGORY:
                case LIST_FAVORITES:
                case LIST_SEARCH:
                default:
                    // change to LIST_MENU
                    currentPage = 0;
                    type = Type.LIST_MENU;
                    break;
				//default:
                //    inventory = null;
                //    player.closeInventory();
                //	  return;
            }
        } else if (page == Page.START) {
            currentPage = 0;
        } else if (page == Page.LAST) {
            currentPage = pages - 1;
        } else if (page == Page.BACK) {
            currentPage = Math.min(0, currentPage - 1);
        } else if (page == Page.BACK_2) {
            currentPage = Math.min(0, currentPage - 2);
        } else if (page == Page.BACK_3) {
            currentPage = Math.min(0, currentPage - 3);
        } else if (page == Page.NEXT) {
            currentPage = Math.max(pages - 1, currentPage + 1);
        } else if (page == Page.NEXT_2) {
            currentPage = Math.max(pages - 1, currentPage + 2);
        } else if (page == Page.NEXT_3) {
            currentPage = Math.max(pages - 1, currentPage + 3);
        } else {
            return;
        }
        // change page to currentPage
        player.closeInventory();
        // somewhat legacy compatibility
        switch (type) {
            case LIST_MENU:
                menuSection = "menu";
                break;
            case LIST_FAVORITES:
                menuSection = "favourites";
                break;
            case CHALLENGES_MENU:
                menuSection = "chal";
                break;
        }
        player.openInventory(getPageInventory());
    }

    public Inventory getPageInventory() {
        switch (type) {
            case LIST_MENU:
                return getListMainMenu();
            case SELL:
                return getSellMenu();
            case LIST_SEARCH:
                return getSearchMenu();
            case LIST_FAVORITES:
                return getFavorites();
            case LIST_CATEGORY:
                return getCategory();
            case CHALLENGES_MENU:
                return getChallengeMain();
            case CHALLENGES_LIST:
                return getChallenge();
        }
        return null;
    }

    private Inventory getListMainMenu() {
        HeadMenu headmenu = new HeadMenu();
        inventory = headmenu;
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.headmenu.icons"), "L"); //4 * 7;
        boolean wide = false;
        if (largerMenu && hpchx.sections.size() > max) {
            max = 4 * 9;
            wide = true;
        }
        List<ItemStack> heads = new ArrayList<>();
        int start = currentPage * max;
        String[] sections = hpchx.sections.keySet().toArray(new String[0]);
        int categories = sections.length;
        if (hpchx.isAdvent()) {
            ++categories;
        }
        pages = (int) Math.min(1, Math.ceil(categories / max));
        headsInSection = 0;
        for (int i = start, c = 0; i < categories && c < max; ++i, ++c) {
            final int count = hpchx.sections.get(sections[i]).size();
            headsInSection += count;
            try {
                ItemStack is = hpchx.getSkull(hpchx.getConfig().getString("sections." + sections[i] + ".texture"));
                SkullMeta im = (SkullMeta) is.getItemMeta();
                final String disp = hpchx.getConfig().getString("sections." + sections[i] + ".display-name");
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', disp != null ? disp : sections[i]));
                im.setLore(Arrays.asList(ChatColor.GRAY.toString() + count + " heads"));
                is.setItemMeta(im);
                is = plugin.getNMS().addSection(is, sections[i]);
                heads.add(is);
            } catch (Exception ex) {
                plugin.getLogger().log(Level.WARNING, "Unexpected Error processing section " + sections[i], ex);
            }
        }
        if (hpchx.isAdvent()) {
            try {
                final String text = hpchx.getConfig().getString("options.advent-texture");
                ItemStack is;

                if (text.startsWith("HP#")) {
                    is = hpchx.getSkull(text);
                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("options.advent-display-name")));
                    is.setItemMeta(sm);
                } else {
                    is = hpchx.getSkullFromTexture(text, false, hpchx.getConfig().getString("options.advent-display-name"));
                }
                is = plugin.getNMS().addSection(is, "advent-calendar");
                heads.add(is);
            } catch (Exception ex) {
                plugin.getLogger().log(Level.WARNING, "Unexpected Error processing section options.advent-texture", ex);
            }
        }
        return headmenu.build(player, heads, "Main Menu", currentPage, pages, true, wide);
    }

    private Inventory getSellMenu() {
        HeadsPlusConfigHeads hpch = plugin.getHeadsConfig();
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.sellheadmenu.icons"), "H"); //4 * 7;
        boolean wide = false;
        if (largerMenu && hpch.mHeads.size() > max) {
            max = 4 * 9;
            wide = true;
        }

        HashMap<String, String> s = new HashMap<>();
        for (String o : hpch.mHeads) {
            if (!hpch.getConfig().getStringList(o + ".name").isEmpty()) {
                s.put(o, o + ".name");
            } else {
                try {
                    for (String str : hpch.getConfig().getConfigurationSection(o + ".name").getKeys(false)) {
                        if (!hpch.getConfig().getStringList(o + ".name." + str).isEmpty()) {
                            s.put(o, o + ".name." + str);
                            break;
                        }
                    }
                } catch (NullPointerException ignored) {
                }

            }
        }
        for (String o : hpch.uHeads) {
            if (!hpch.getConfig().getStringList(o + ".name").isEmpty()) {
                s.put(o, o + ".name");
            } else {
                try {
                    for (String str : hpch.getConfig().getConfigurationSection(o + ".name").getKeys(false)) {
                        if (!hpch.getConfig().getStringList(o + ".name." + str).isEmpty()) {
                            s.put(o, o + ".name." + str);
                            break;
                        }
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }

        List<ItemStack> items = new ArrayList<>();
        NMSManager nms = plugin.getNMS();
        for (String o : s.keySet()) {

            ItemStack it = nms.getSkullMaterial(1);
            SkullMeta sm = (SkullMeta) it.getItemMeta();
            sm = nms.setSkullOwner(hpch.getConfig().getStringList(s.get(o)).get(0), sm);
            sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpch.getConfig().getString(o + ".display-name")));
            List<String> d = new ArrayList<>();
            for (String a : hpch.getConfig().getStringList(o + ".lore")) {
                d.add(ChatColor.translateAlternateColorCodes('&', a).replaceAll("\\{price}", String.valueOf(hpch.getConfig().getDouble(o + ".price"))).replaceAll("\\{type}", o));
            }
            sm.setLore(d);
            it.setItemMeta(sm);
            it = nms.setType(o, it);
            items.add(it);
        }
        headsInSection = items.size();
        PagedLists<ItemStack> ps = new PagedLists<>(items, max);
        pages = ps.getTotalPages();
        inventory = new SellheadMenu();
        return new SellheadMenu().build(player, ps.getContentsInPage(currentPage), "Sell", currentPage, pages, false, wide);
    }

    private List<String> searchHeads(String term) {
        term = term.toLowerCase();
        List<String> c = new ArrayList<>();
        for (String k : hpchx.headsCache.keySet()) {
            final String name = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', hpchx.getConfig().getString("heads." + k + ".displayname"))).toLowerCase().replaceAll("[^a-z]", "");
            if (name.contains(term)) {
                c.add(k);
            }
        }
        return c;
    }

    private List<String> loadFavoriteHeads() {
        List<String> c = new ArrayList<>();
        HPPlayer hps = HPPlayer.getHPPlayer(player);
        for (String str : hps.getFavouriteHeads()) {
            if (hpchx.headsCache.containsKey(str)) {
                c.add(str);
            }
        }
        return c;
    }

    private Inventory getSearchMenu() {
        List<ItemStack> heads = new ArrayList<>();
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.headsection.icons"), "H"); //4 * 7;
        boolean wide = false;
        if (searchResults != null) {
            if (largerMenu && searchResults.size() > max) {
                max = 4 * 9;
                wide = true;
            }
            headsInSection = searchResults.size();
            pages = (int) Math.min(1, Math.ceil(headsInSection / max));
            int start = currentPage * max;
            for (int i = start, c = 0; i < searchResults.size() && c < max; ++i, ++c) {
                heads.add(skull(searchResults.get(i)));
            }
        }

        inventory = new HeadSection();
        return inventory.build(player, heads, menuSection, currentPage, pages, true, wide);
    }

    public Inventory getFavorites() {
        List<ItemStack> heads = new ArrayList<>();
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.favourites.icons"), "H"); //4 * 7;
        boolean wide = false;

        if (searchResults != null) {
            if (largerMenu && searchResults.size() > max) {
                max = 4 * 9;
                wide = true;
            }
            headsInSection = searchResults.size();
            pages = (int) Math.min(1, Math.ceil(headsInSection / max));
            int start = currentPage * max;
            for (int i = start, c = 0; i < searchResults.size() && c < max; ++i, ++c) {
                heads.add(skull(searchResults.get(i)));
            }
        }

        inventory = new FavouritesMenu();
        return inventory.build(player, heads, menuSection, currentPage, pages, true, wide);
    }

    public Inventory getCategory() {
        List<String> allHeads = hpchx.sections.get(menuSection);
        List<ItemStack> heads = new ArrayList<>();
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.headsection.icons"), "H"); //4 * 7;
        boolean wide = false;

        if (allHeads != null) {
            headsInSection = allHeads.size();
            if (largerMenu && headsInSection > max) {
                max = 4 * 9;
                wide = true;
            }
            pages = (int) Math.min(1, Math.ceil(headsInSection / max));
            int start = currentPage * max;
            for (int i = start, c = 0; i < headsInSection && c < max; ++i, ++c) {
                heads.add(skull(allHeads.get(i)));
            }
        } else if (menuSection.equalsIgnoreCase("advent-calendar")) {
            try {
                for (AdventCManager acm : AdventCManager.values()) {
                    if (hpchx.getConfig().getStringList("advent." + acm.name()).contains(player.getUniqueId().toString())) {
                        ItemStack is = hpchx.setTexture(acm.texture, HeadsPlus.getInstance().getNMS().getSkullMaterial(1));
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.name));
                        is.setItemMeta(im);
                        is = HeadsPlus.getInstance().getNMS().setCalendarValue(is, acm.name());
                        is = HeadsPlus.getInstance().getNMS().setOpen(is, true);
                        heads.add(is);
                    } else {
                        ItemStack is = hpchx.setTexture(acm.wTexture, HeadsPlus.getInstance().getNMS().getSkullMaterial(1));
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.wName));
                        is.setItemMeta(im);
                        is = HeadsPlus.getInstance().getNMS().setCalendarValue(is, acm.name());
                        is = HeadsPlus.getInstance().getNMS().setOpen(is, false);
                        heads.add(is);
                    }
                }
            } catch (Exception e) {
            }
            headsInSection = heads.size();
            if (largerMenu && headsInSection > max) {
                max = 4 * 9;
                wide = true;
            }
            pages = (int) Math.min(1, Math.ceil(headsInSection / max));

            PagedLists<ItemStack> paged = new PagedLists<>(heads, max);
            heads = paged.getContentsInPage(currentPage);
        }
        inventory = new HeadSection();
        return inventory.build(player, heads, menuSection, currentPage, pages, false, wide);
    }

    public Inventory getChallengeMain() {
        pages = HeadsPlus.getInstance().getItems().getConfig().getString("inventories.challenges-menu.icons").split(":").length;
        inventory = new ChallengesMenu();
        return inventory.build(player, null, "Challenges", currentPage, pages, true, false);
    }

    public Inventory getChallenge() {
        // menuSection
        List<Challenge> challenges = new ArrayList<>();
        for (Challenge c : plugin.getChallenges()) {
            if (c.getDifficulty().name().equalsIgnoreCase(menuSection)) {
                challenges.add(c);
            }
        }
        List<ItemStack> heads = new ArrayList<>();
        int max = charOccurance(plugin.getItems().getConfig().getString("inventories.challenge-section.icons"), "C");
        headsInSection = challenges.size();
        pages = (int) Math.min(1, Math.ceil(headsInSection / max));

        int start = currentPage * max;
        pages = (int) Math.min(1, Math.ceil(headsInSection / max));
        headsInSection = 0;
        for (int i = start, c = 0; i < headsInSection && c < max; ++i, ++c) {
            Challenge ch = challenges.get(i);
            ItemStack is;
            if (ch.isComplete(player)) {
                is = new ItemStack(new io.github.thatsmusic99.headsplus.config.headsx.icons.Challenge().getCompleteMaterial(), 1, (byte) plugin.getItems().getConfig().getInt("icons.challenge.complete-data-value"));
            } else {
                is = new ItemStack(new io.github.thatsmusic99.headsplus.config.headsx.icons.Challenge().getMaterial(), 1, (byte) plugin.getItems().getConfig().getInt("icons.challenge.data-value"));
            }
            is = plugin.getNMS().setChallenge(is, ch);
            heads.add(is);
        }

        inventory = new ChallengeSection();
        return inventory.build(player, heads, menuSection, currentPage, pages, true, false);
    }

    private ItemStack skull(String str) {
        ItemStack s = hpchx.getSkull(str);
        List<String> price = new ArrayList<>();
        double pr = 0.0;
        if (HeadsPlus.getInstance().econ()) {
            if (hpchx.getConfig().get("options.price-per-world." + player.getWorld().getName()) instanceof String) {
                if (((String) hpchx.getConfig().get("options.price-per-world." + player.getWorld().getName())).equalsIgnoreCase("default")) {
                    if (!hpchx.getConfig().get("options.default-price").equals("free")) {
                        pr = hpchx.getConfig().getDouble("options.default-price");
                    }
                } else {
                    pr = hpchx.getConfig().getDouble("options.price-per-world." + player.getWorld().getName());
                }
            } else if (hpchx.getConfig().get("options.price-per-world." + player.getWorld().getName()) instanceof Double) {
                pr = hpchx.getConfig().getDouble("options.price-per-world." + player.getWorld().getName());
            } else if (hpchx.getConfig().get("heads." + str + ".price") instanceof String) {
                if (!((String) hpchx.getConfig().get("heads." + str + ".price")).equalsIgnoreCase("free")) {
                    if (((String) hpchx.getConfig().get("heads." + str + ".price")).equalsIgnoreCase("default")) {
                        if (!hpchx.getConfig().get("options.default-price").equals("free")) {
                            pr = hpchx.getConfig().getDouble("options.default-price");
                        }
                    } else {
                        pr = hpchx.getConfig().getDouble("heads." + str + ".price");
                    }
                }
            } else {
                if (!(((Double) hpchx.getConfig().get("heads." + str + ".price")) == 0.0)) {
                    pr = hpchx.getConfig().getDouble("heads." + str + ".price");
                }
            }
        }
        price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + pr));

        HPPlayer hps = HPPlayer.getHPPlayer(player);
        if (hps.hasHeadFavourited(str)) {
            price.add(ChatColor.GOLD + "Favourite!");
        }
        ItemMeta sm = s.getItemMeta();
        sm.setLore(price);
        s.setItemMeta(sm);
        s = HeadsPlus.getInstance().getNMS().addDatabaseHead(s, str, pr);
        return s;
    }

    private int charOccurance(String s, String c) {
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (String.valueOf(s.charAt(i)).equalsIgnoreCase(c)) {
                ++count;
            }
        }
        return count;
    }
}
