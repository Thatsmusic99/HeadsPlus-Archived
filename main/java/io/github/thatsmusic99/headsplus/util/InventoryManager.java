package io.github.thatsmusic99.headsplus.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusChallengeDifficulty;
import io.github.thatsmusic99.headsplus.config.HeadsPlusChallengeEnums;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeadsX;

import org.apache.commons.codec.binary.Base64;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

public class InventoryManager {

    public InventoryManager(String t) {
        this.type = t;
    }

    private String type;
    private int pages;
    private int heads;
    private int timesSent = 0;
    private int cPage = 0;
    private int sections = 0;
    private String cSection = "menu";
    private int chSections = 0;
    private HeadsPlusConfigHeadsX hpchx = HeadsPlus.getInstance().hpchx;
    public static HashMap<Player, InventoryManager> pls = new HashMap<>();
    private int[] pos() {
        int[] a = new int[28];
        a[0] = 10;
        a[1] = 11;
        a[2] = 12;
        a[3] = 13;
        a[4] = 14;
        a[5] = 15;
        a[6] = 16;
        a[7] = 19;
        a[8] = 20;
        a[9] = 21;
        a[10] = 22;
        a[11] = 23;
        a[12] = 24;
        a[13] = 25;
        a[14] = 28;
        a[15] = 29;
        a[16] = 30;
        a[17] = 31;
        a[18] = 32;
        a[19] = 33;
        a[20] = 34;
        a[21] = 37;
        a[22] = 38;
        a[23] = 39;
        a[24] = 40;
        a[25] = 41;
        a[26] = 42;
        a[27] = 43;
        return a;
    }
    private int[] glass() {
        int[] a = new int[24];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        a[4] = 5;
        a[5] = 6;
        a[6] = 7;
        a[7] = 9;
        a[8] = 17;
        a[9] = 18;
        a[10] = 26;
        a[11] = 27;
        a[12] = 35;
        a[13] = 36;
        a[14] = 44;
        a[15] = 45;
        a[16] = 46;
        a[17] = 47;
        a[18] = 48;
        a[19] = 49;
        a[20] = 50;
        a[21] = 51;
        a[22] = 52;
        a[23] = 53;
        return a;
    }

    private int[] cColouredGlass() {
        int[] a = new int[24];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        a[4] = 5;
        a[5] = 6;
        a[6] = 7;
        a[7] = 9;
        a[8] = 17;
        a[9] = 18;
        a[10] = 26;
        a[11] = 27;
        a[12] = 35;
        a[13] = 36;
        a[14] = 44;
        a[15] = 45;
        a[16] = 46;
        a[17] = 47;
        a[18] = 48;
        a[19] = 49;
        a[20] = 50;
        a[21] = 51;
        a[22] = 52;
        a[23] = 53;
        return a;
    }

    private Inventory create(String name) {
        return Bukkit.createInventory(null, 54, name);
    }

    public int getPages() { return pages; }
    public int getPage() { return cPage; }
    public int getHeads() { return heads; }
    public String getSection() { return cSection; }
    public int getSections() { return sections; }
    public void setSection(String s) { cSection = s; }
    public String getType() {
        return type;
    }

    public Inventory changePage(boolean next, boolean start, Player p, String section) {
        Inventory i;
        if (next) {
            cPage++;
        } else {
            cPage--;
        }
        if (start) {
            cPage = 1;
        }
        if (type.equalsIgnoreCase("heads")) {
            PagedLists ls;
            sections = hpchx.getHeadsX().getConfigurationSection("sections").getKeys(false).size();
            heads = hpchx.getHeadsX().getConfigurationSection("heads").getKeys(false).size();


            if (section.equalsIgnoreCase("menu")) {
                // if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("options.advent-calender")) {
                //     sections++;
                // }

                ls = new PagedLists(new ArrayList<>(hpchx.getHeadsX().getConfigurationSection("sections").getKeys(false)), 28);
                i = create("HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
                for (Object str : ls.getContentsInPage(cPage)) {
                    String st = (String) str;
                    if (hpchx.isHPXSkull(hpchx.getHeadsX().getString("sections." + st + ".texture"))) {
                        ItemStack is = hpchx.getSkull(hpchx.getHeadsX().getString("sections." + st + ".texture"));
                        SkullMeta im = (SkullMeta) is.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getHeadsX().getString("sections." + st + ".display-name")));
                        is.setItemMeta(im);
                        i.setItem(pos()[timesSent], is);
                        timesSent++;
                    } else {
                        ItemStack is = new ItemStack(Material.SKULL_ITEM);
                        SkullMeta sm = (SkullMeta) is.getItemMeta();
                        sm.setOwner(st);
                        is.setItemMeta(sm);
                        i.setItem(pos()[timesSent], is);
                        timesSent++;
                    }
                }
                //  if (HeadsPlusConfigHeadsX.getHeadsX().getBoolean("options.advent-calender")) {
                //   ItemStack is = HeadsPlusConfigHeadsX.getSkull("HP#snowman_head");
                //   SkullMeta sm = (SkullMeta) is.getItemMeta();
                //   sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4[&a&lHeadsPlus &c&lAdvent Calender!&2]"));
                //   is.setItemMeta(sm);
                //   i.setItem(pos()[timesSent], is);
                // }
            } else if (section.startsWith("search:")) {
                String term = section.split(":")[1];
                List<String> f = new ArrayList<>();
                List<String> c = new ArrayList<>();
                HashMap<String, String> s = new HashMap<>();
                for (String str : hpchx.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                    c.add(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', hpchx.getHeadsX().getString("heads." + str + ".displayname"))).replace("[", "").replace("]", ""));
                    s.put(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', hpchx.getHeadsX().getString("heads." + str + ".displayname"))).replace("[", "").replace("]", ""), str);
                }
                StringUtil.copyPartialMatches(term, c, f);
                Collections.sort(f);
                Iterator<Map.Entry<String, String>> m = s.entrySet().iterator();
                while (m.hasNext()) {
                    if (!f.contains(m.next().getKey())) {
                        m.remove();
                    }
                }



                heads = s.size();

                ls = new PagedLists(new ArrayList<>(s.values()), 28);
                i = create("HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
                for (Object str : ls.getContentsInPage(cPage)) {
                    if (hpchx.getHeadsX().getBoolean("heads." + str + ".database")) {
                        skull(String.valueOf(str), i);
                    }
                }


            } else {
                List<String> l = new ArrayList<>();
                // if (!section.equalsIgnoreCase("advent_calender")) {
                for (String str : hpchx.getHeadsX().getConfigurationSection("heads").getKeys(false)) {
                    if (hpchx.getHeadsX().getString("heads." + str + ".section").equalsIgnoreCase(section)) {
                        l.add(str);
                    }
                }
                heads = l.size();
                ls = new PagedLists(l, 28);
                i = create("HeadsPlus Head selector: page " + cPage + "/" + ls.getTotalPages());
                for (Object str : ls.getContentsInPage(cPage)) {
                    if (hpchx.getHeadsX().getBoolean("heads." + str + ".database")) {
                        skull(String.valueOf(str), i);
                    }
                }
                // } else {
                //  i = create(54, "HeadsPlus Head selector: page " + cPage + "/" + pages);
                //  skullChristmas(i, p.getPlayer());
                //}

            }
            ItemStack isi = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
            ItemMeta ims = isi.getItemMeta();
            ims.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6"));
            isi.setItemMeta(ims);
            for (int in : glass()) {
                i.setItem(in, isi);
            }
            ItemStack name = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta nams = name.getItemMeta();
            nams.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6[&e&lSearch Heads&6]"));
            name.setItemMeta(nams);
            i.setItem(8, name);
            pages = ls.getTotalPages();
            if (pages > cPage) {
                setItem(i, "Next Page", 50);
            }
            if (cPage != 1) {
                setItem(i, "Back", 48);
            }
            if (!cSection.equalsIgnoreCase("menu")) {
                setItem(i, "Main Menu", 45);
            }
            ItemStack it = new ItemStack(Material.BARRIER);
            ItemMeta is = it.getItemMeta();
            is.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Menu");
            it.setItemMeta(is);
            i.setItem(49, it);
            ItemStack is2 = new ItemStack(Material.PAPER);
            ItemMeta im = is2.getItemMeta();
            im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Total heads: " + heads);
            lore.add(ChatColor.GREEN + "Total pages: " + pages);
            lore.add(ChatColor.GREEN + "Total sections: " + sections) ;
            lore.add(ChatColor.GREEN + "Current balance: " + HeadsPlus.getInstance().econ.getBalance(p));
            lore.add(ChatColor.GREEN + "Current section: " + section);
            im.setLore(lore);
            is2.setItemMeta(im);
            i.setItem(4, is2);
            timesSent = 0;
            return i;
        } else {
            i = create("HeadsPlus challenges: " + cSection);
            int ran = new Random().nextInt(DyeColor.values().length);
            DyeColor dc = DyeColor.values()[ran];
            ItemStack isi = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) dc.ordinal());
            ItemMeta ims = isi.getItemMeta();
            ims.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6"));
            isi.setItemMeta(ims);
            for (int in : cColouredGlass()) {
                i.setItem(in, isi);
            }
            if (cSection.equalsIgnoreCase("menu")) {
                for (HeadsPlusChallengeDifficulty hpcd : HeadsPlusChallengeDifficulty.values()) {
                    ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short) hpcd.color.ordinal());
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpcd.dn));
                    is.setItemMeta(im);
                    i.setItem(hpcd.i, is);
                }
                int cch = HeadsPlus.getInstance().hpchl.getChallenges().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").size();
                i.setItem(8, isi);
                ItemStack is2 = new ItemStack(Material.PAPER);
                ItemMeta im = is2.getItemMeta();
                im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "Total challenges: " + HeadsPlusChallengeEnums.values().length);
                lore.add(ChatColor.GREEN + "Total pages: " + pages);
                lore.add(ChatColor.GREEN + "Total sections: " + sections) ;
                lore.add(ChatColor.GREEN + "Completed challenges: " + cch);
                lore.add(ChatColor.GREEN + "Current section: " + section);
                im.setLore(lore);
                is2.setItemMeta(im);
                i.setItem(4, is2);
            } else {
                for (HeadsPlusChallengeDifficulty hpcd : HeadsPlusChallengeDifficulty.values()) {
                    if (hpcd.key.equalsIgnoreCase(cSection)) {
                        Set<String> aa = HeadsPlus.getInstance().hpchl.getChallenges().getConfigurationSection("challenges." + hpcd.name()).getKeys(false);
                        List<String> bb = new ArrayList<>();
                        bb.addAll(aa);
                        PagedLists pl = new PagedLists(bb, 27);
                        int in = 0;
                        for (Object s : pl.getContentsInPage(cPage)) {
                            String str = (String) s;
                            ItemStack is;
                            if (HeadsPlus.getInstance().hpchl.isChallengeCompleted(p, str)) {
                                is = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
                            } else {
                                is = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                            }
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s + ".header")));
                            List<String> lore = new ArrayList<>();
                            for (String st : HeadsPlus.getInstance().hpchl.getChallenges().getStringList("challenges." + hpcd.name() + "." + s + ".description")) {
                                lore.add(ChatColor.translateAlternateColorCodes('&', st));
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(ChatColor.GOLD).append("Reward: ");
                            String re = HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s + ".reward-type");
                            if (re.equalsIgnoreCase("ECO")) {
                                sb.append(ChatColor.GREEN).append("$").append(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s + ".reward-value"));
                            } else if (re.equalsIgnoreCase("GIVE_ITEM")) {
                                try {
                                    Material.valueOf(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s + ".reward-value"));
                                    sb.append(ChatColor.GREEN).append(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s +".item-amount")).append(" ").append(WordUtils.capitalize(HeadsPlus.getInstance().hpchl.getChallenges().getString("challenges." + hpcd.name() + "." + s + ".reward-value").toLowerCase().replaceAll("_", " "))).append("(s)");
                                } catch (IllegalArgumentException ignored) {

                                }
                            }
                            lore.add(sb.toString());
                            lore.add(ChatColor.GOLD + "XP: " + ChatColor.GREEN + HeadsPlus.getInstance().hpchl.getChallenges().getInt("challenges." + hpcd.name() + "." + s +".xp"));
                            if (HeadsPlus.getInstance().hpchl.isChallengeCompleted(p, str)) {
                                lore.add(ChatColor.GREEN + "Completed!");
                            }
                            im.setLore(lore);
                            is.setItemMeta(im);
                            i.setItem(pos()[in], is);
                            in++;
                        }
                    }
                    int ch = HeadsPlus.getInstance().hpchl.getChallenges().getConfigurationSection("challenges." + cSection.toUpperCase()).getKeys(false).size();
                    int cch = HeadsPlus.getInstance().hpchl.getChallenges().getStringList("player-data." + p.getUniqueId().toString() + ".completed-challenges").size();
                    setItem(i, "Back", 8);
                    ItemStack is2 = new ItemStack(Material.PAPER);
                    ItemMeta im = is2.getItemMeta();
                    im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Stats" + ChatColor.GOLD + "]");
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GREEN + "Total challenges: " + ch);
                    lore.add(ChatColor.GREEN + "Total pages: " + pages);
                    lore.add(ChatColor.GREEN + "Total sections: " + sections) ;
                    lore.add(ChatColor.GREEN + "Completed challenges: " + cch);
                    lore.add(ChatColor.GREEN + "Current section: " + section);
                    im.setLore(lore);
                    is2.setItemMeta(im);
                    i.setItem(4, is2);
                }
            }
            return i;
        }
    }

    private void skull(String str, Inventory i) {
        ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) s.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
        if (hpchx.getHeadsX().getBoolean("heads." + str + ".encode")) {
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", hpchx.getHeadsX().getString(str + ".texture")).getBytes());
            gm.getProperties().put("textures", new Property("texture", Arrays.toString(encodedData)));
        } else {
            gm.getProperties().put("textures", new Property("texture", hpchx.getHeadsX().getString("heads." + str + ".texture")));
        }

        Field profileField = null;
        try {
            profileField = sm.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(sm, gm);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', hpchx.getHeadsX().getString("heads." + str + ".displayname")));
        if (hpchx.getHeadsX().get("heads." + str + ".price") instanceof String) {
            if (!((String) hpchx.getHeadsX().get("heads." + str + ".price")).equalsIgnoreCase("free")) {
                if (((String) hpchx.getHeadsX().get("heads." + str + ".price")).equalsIgnoreCase("default")) {
                    if (!hpchx.getHeadsX().get("options.default-price").equals("free")) {
                        List<String> price = new ArrayList<>();
                        price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + hpchx.getHeadsX().get("options.default-price")));
                        sm.setLore(price);
                    }
                } else {
                    List<String> price = new ArrayList<>();
                    price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + hpchx.getHeadsX().get("heads." + str + ".price")));
                    sm.setLore(price);
                }
            }
        } else {
            if (!(((Double) hpchx.getHeadsX().get("heads." + str + ".price")) == 0.0)) {
                List<String> price = new ArrayList<>();
                price.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "[" + ChatColor.YELLOW + "Price" + ChatColor.GOLD + "] " + ChatColor.GREEN + hpchx.getHeadsX().get("heads." + str + ".price")));
                sm.setLore(price);
            }

        }
        s.setItemMeta(sm);
        i.setItem(pos()[timesSent], s);
        timesSent++;
    }



  /*  public static void skullChristmas(Inventory i, Player p) {
        for (AdventCManager acm : AdventCManager.values()) {
            if (HeadsPlusConfigHeadsX.getHeadsX().getStringList("advent." + acm.name()).contains(p.getUniqueId().toString())) {
                ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta sm = (SkullMeta) s.getItemMeta();
                GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
                gm.getProperties().put("textures", new Property("texture", acm.texture));


                Field profileField = null;
                try {
                    profileField = sm.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(sm, gm);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.name));

                s.setItemMeta(sm);
                i.setItem(acm.place, s);
                timesSent++;
            } else {
                ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta sm = (SkullMeta) s.getItemMeta();
                GameProfile gm = new GameProfile(UUID.randomUUID(), "HPXHead");
                gm.getProperties().put("textures", new Property("texture", acm.wTexture));


                Field profileField = null;
                try {
                    profileField = sm.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(sm, gm);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', acm.wName));

                s.setItemMeta(sm);
                i.setItem(acm.place, s);
                timesSent++;
            }
        }
    } */


    private void setItem(Inventory i, String s, int o) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + s);
        item.setItemMeta(im);
        i.setItem(o, item);
    }

    public static InventoryManager getIM(Player p) {
        return pls.get(p);
    }
}
