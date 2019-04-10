package io.github.thatsmusic99.headsplus.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class HPExpansion extends PlaceholderExpansion {

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
        return "1.0.0";
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
        if(identifier.equals("placeholder2")){
            return "placeholder2 works";
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
