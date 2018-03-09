package io.github.thatsmusic99.headsplus.commands;

import org.bukkit.command.CommandSender;

public interface IHeadsPlusCommand {

    String getCmdName();

    String getPermission();

    String getCmdDescription();

    String getSubCommand();

    String getUsage();

    boolean isMainCommand();

    boolean fire(String[] args, CommandSender sender);
}
