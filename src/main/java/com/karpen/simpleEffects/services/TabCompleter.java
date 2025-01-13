package com.karpen.simpleEffects.services;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("eff")){
            if (args.length == 1){
                suggestions.add("cherry");
                suggestions.add("endrod");
                suggestions.add("totem");
            }
        }

        return suggestions;
    }
}
