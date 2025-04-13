package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.model.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private Config config;

    public TabCompleter(Config config){
        this.config = config;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("eff")){
            if (args.length == 1){
                suggestions.add("cherry");
                suggestions.add("endrod");
                suggestions.add("totem");
                suggestions.add("heart");

                if (!config.isOldVer()){
                    suggestions.add("pale");
                }

                suggestions.add("purple");
                suggestions.add("note");
                suggestions.add("cloud");
            }
        }

        return suggestions;
    }
}
