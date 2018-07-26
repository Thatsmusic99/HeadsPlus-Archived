package io.github.thatsmusic99.headsplus.util;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatListenerUtil {

    public static List<Player> players = new ArrayList<>();

    public class StringInputEvent {

        private final String input;
        private final Player p;

        public StringInputEvent(String name, Player p) {
            this.input = name;
            this.p = p;
        }

        public String getInput() {
            return input;
        }

        public Player getPlayer() {
            return p;
        }

    }

    public interface ChatEventHandler {
        void onChat(ChatListenerUtil.StringInputEvent event) throws NoSuchFieldException, IllegalAccessException;
    }

    private Player player;

    private ChatListenerUtil.ChatEventHandler handler;

    protected HashMap<SearchGUI.AnvilSlot, ItemStack> items = new HashMap<>();

    protected Inventory inv;

    private Listener listener;

    public ChatListenerUtil(Player player, final ChatEventHandler handler) {
        this.player = player;
        this.handler = handler;
        players.add(player);

        this.listener = new Listener() {
            @EventHandler
            public void onPlayerChat(AsyncPlayerChatEvent event) throws NoSuchFieldException, IllegalAccessException {
                if (event.getPlayer() != null) {

                    if (players.contains(event.getPlayer())) {
                        event.setCancelled(true);

                        String name = event.getMessage();

                        ChatListenerUtil.StringInputEvent clickEvent = new ChatListenerUtil.StringInputEvent(name, event.getPlayer());

                        handler.onChat(clickEvent);
                        players.remove(event.getPlayer());
                    }
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (event.getPlayer().equals(getPlayer())) {
                    players.remove(player);
                }
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, HeadsPlus.getInstance()); //Replace with instance of main class
    }

    public Player getPlayer() {
        return player;
    }
}
