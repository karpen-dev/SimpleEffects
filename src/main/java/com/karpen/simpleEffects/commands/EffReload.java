package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.SimpleEffects;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EffReload implements CommandExecutor {

    private final SimpleEffects plugin;
    public EffReload(SimpleEffects plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        plugin.loadConfig();
        commandSender.sendMessage(ChatColor.GREEN + "Simple effects reloaded");

        return true;
    }
}
