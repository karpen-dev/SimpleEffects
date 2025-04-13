package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.menus.SelectEffectMenu;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.Effects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Eff implements CommandExecutor {

    private final Config config;
    private final Effects effects;
    private Types types;
    private SelectEffectMenu effectMenu;

    public Eff(Config config, Effects effects, Types types, SelectEffectMenu effectMenu) {
        this.config = config;
        this.effects = effects;
        this.types = types;
        this.effectMenu = effectMenu;
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
            effectMenu.openMenu(player);

            return true;
        }

        return switch (strings[0].toLowerCase()) {
            case "cherry" -> activeCherry(player);
            case "endrod" -> activeEndRod(player);
            case "totem" -> activeTotem(player);
            case "heart" -> activeHeart(player);
            case "pale" -> activePale(player);
            case "note" -> activeNote(player);
            case "purple" -> activePurple(player);
            case "cloud" -> activeCloud(player);
            default -> errCommand(player);
        };
    }

    private boolean activeCherry(Player player){

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCherry())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.cherryPlayers.contains(player)){
            types.cherryPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else{
            types.cherryPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeEndRod(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsEndRod())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.endRodPlayers.contains(player)){
            types.endRodPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.endRodPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeTotem(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsTotem())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.totemPlayers.contains(player)){
            types.totemPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.totemPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeHeart(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsHeart())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.heartPlayers.contains(player)){
            types.heartPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.heartPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activePale(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPale())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.palePlayers.contains(player)){
            types.palePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.palePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activePurple(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPurple())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.purplePlayers.contains(player)){
            types.purplePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.purplePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeNote(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsNotes())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.notePlayers.contains(player)){
            types.notePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.notePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeCloud(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCloud())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.cloudPlayers.contains(player)){
            types.cloudPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.cloudPlayers.add(player);
            effects.startCloudEffect(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean errCommand(Player player){
        player.sendMessage(ChatColor.RED + config.getErrCommand());

        return true;
    }
}
