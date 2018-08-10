package io.github.thatsmusic99.headsplus.commands;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public interface IHeadsPlusCommand {

    String getCmdName();

    String getPermission();

    String getCmdDescription();

    String getSubCommand();

    String getUsage();

    HashMap<Boolean, String> isCorrectUsage(String[] args, CommandSender sender);

    boolean isMainCommand();

    boolean fire(String[] args, CommandSender sender);

    default String[] advancedUsages() {
        return new String[0];
    }

    default void printDebugResults(HashMap<String, Boolean> results, boolean success) {
        HeadsPlus hp = HeadsPlus.getInstance();
        hp.debug("- Tests for " + getCmdName() + " were " + (success ? "" : "not ") + "passed!", 1);
        for (String r : results.keySet()) {
            hp.debug("- " + r + ": " + results.get(r), 3);
        }
    }
}
