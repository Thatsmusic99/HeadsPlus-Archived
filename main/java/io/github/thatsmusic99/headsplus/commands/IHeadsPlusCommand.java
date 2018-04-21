package io.github.thatsmusic99.headsplus.commands;

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
}
