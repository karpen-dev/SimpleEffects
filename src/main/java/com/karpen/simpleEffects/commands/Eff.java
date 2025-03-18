package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.services.Effects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Eff implements CommandExecutor {

    private final Config config;
    private final Effects effects;

    public Eff(Config config, Effects effects) {
        this.config = config;
        this.effects = effects;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED + config.getErrConsole());
            return true;
        }

        if (config == null){
            commandSender.sendMessage(ChatColor.RED + "Config not initialized");
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
            case "heart" -> activeHeart(player);
            case "pale" -> activePale(player);
            default -> errCommand(player);
        };
    }

    private boolean activeCherry(Player player){
        if (effects.cherryPlayers.contains(player)){
            effects.cherryPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else{
            effects.cherryPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }
        return true;
    }

    private boolean activeEndRod(Player player){
        if (effects.endRodPlayers.contains(player)){
            effects.endRodPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            effects.endRodPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }
        return true;
    }

    private boolean activeTotem(Player player){
        if (effects.totemPlayers.contains(player)){
            effects.totemPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            effects.totemPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }
        return true;
    }

    private boolean activeHeart(Player player){
        if (effects.heartPlayers.contains(player)){
            effects.heartPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            effects.heartPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }
        return true;
    }

    private boolean activePale(Player player){
        if (effects.palePlayers.contains(player)){
            effects.palePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            effects.palePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }
        return true;
    }

    private boolean errCommand(Player player){
        player.sendMessage(ChatColor.RED + config.getErrCommand());
        return true;
    }

}
