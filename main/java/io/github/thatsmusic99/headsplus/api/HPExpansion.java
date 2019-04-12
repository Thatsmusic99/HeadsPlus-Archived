package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.SQLException;

public class HPExpansion extends PlaceholderExpansion {

    private HeadsPlus hp;

    public HPExpansion(HeadsPlus headsPlus) {
        hp = headsPlus;
    }
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "headsplus";
    }

    @Override
    public String getAuthor() {
        return "Thatsmusic99";
    }

    @Override
    public String getVersion() {
        return hp.getVersion();
    }

    @Override
    public boolean register(){
        if(!canRegister()){
            return false;
        }
        hp = (HeadsPlus) Bukkit.getPluginManager().getPlugin("HeadsPlus");

        if(hp == null){
            return false;
        }
        return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        HPPlayer pl = HPPlayer.getHPPlayer(player);
        if (pl == null) {
            pl = new HPPlayer(player);
        }
        // %example_placeholder1%
        if(identifier.equals("xp")){
            return String.valueOf(pl.getXp());
        }

        // %example_placeholder2%
        if(identifier.equals("completed_challenges_total")){
            return String.valueOf(pl.getCompleteChallenges().size());
        }

        if (identifier.equals("level")) {
            return pl.getLevel().getDisplayName();
        }

        if (identifier.startsWith("hunting")) {
            try {
                return String.valueOf(hp.getAPI().getPlayerInLeaderboards(player, identifier.split("_")[1], "hunting"));
            } catch (SQLException e) {
                e.printStackTrace();
                return "0";
            }
        }

        if (identifier.startsWith("crafting")) {
            try {
                return String.valueOf(HeadsPlus.getInstance().getAPI().getPlayerInLeaderboards(player, identifier.split("_")[1], "crafting"));
            } catch (SQLException e) {
                e.printStackTrace();
                return "0";
            }
        }

        if (identifier.startsWith("selling")) {
            try {
                return String.valueOf(HeadsPlus.getInstance().getAPI().getPlayerInLeaderboards(player, identifier.split("_")[1], "selling"));
            } catch (SQLException e) {
                e.printStackTrace();
                return "0";
            }
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
