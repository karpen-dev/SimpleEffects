package jp.karpen.simpleEffects.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private static final List<String> FIRST = List.of(
            "cherry", "endrod", "totem", "heart", "pale",
            "purple", "note", "cloud", "spiral"
    );

    private static final List<String> SECOND = List.of(
            "cherry", "endrod", "totem", "pale", "purple",
            "note"
    );

    private static final List<String> EMPTY = List.of();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("eff")){
            if (args.length == 1) return FIRST;
            if (args.length == 2) return SECOND;
        }

        return EMPTY;
    }
}
