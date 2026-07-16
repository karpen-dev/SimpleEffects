package jp.karpen.simpleEffects.commands;

import jp.karpen.simpleEffects.SimpleEffects;
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

        plugin.reloadConfig();
        SimpleEffects.getLanguageManager().loadLanguages();

        commandSender.sendMessage(ChatColor.GREEN + "Simple effects reloaded");

        return true;
    }
}
