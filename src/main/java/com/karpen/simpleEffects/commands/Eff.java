package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.Effects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Eff implements CommandExecutor {

    Config config;
    Effects effects = new Effects();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED + config.getErrConsole());
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0){
            player.sendMessage(ChatColor.RED + config.getErrArgs());
            return true;
        }

        return switch (strings[0].toLowerCase()) {
            case "cherry" -> activeCherry(player);
            case "endrod" -> activeEndRod(player);
            case "totem" -> activeTotem(player);
            default -> errCommand(player);
        };
    }

    private boolean activeCherry(Player player){
        if (effects.cherryPlayers.contains(player)){
            effects.cherryPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisCherry());
        } else{
            effects.cherryPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgCherry());
        }
        return true;
    }

    private boolean activeEndRod(Player player){
        if (effects.endRodPlayers.contains(player)){
            effects.endRodPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisEndRod());
        } else {
            effects.endRodPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEndRod());
        }
        return true;
    }

    private boolean activeTotem(Player player){
        if (effects.totemPlayers.contains(player)){
            effects.totemPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisTotem());
        } else {
            effects.totemPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgTotem());
        }
        return true;
    }

    private boolean errCommand(Player player){
        player.sendMessage(ChatColor.RED + config.getErrCommand());
        return true;
    }

}
