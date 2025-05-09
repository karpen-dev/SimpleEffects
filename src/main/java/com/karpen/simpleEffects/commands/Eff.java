package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.menus.SelectEffectMenu;
import com.karpen.simpleEffects.model.Type;
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
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrConsole()));

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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.CHERRY)){
            types.players.remove(player, Type.CHERRY);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else{
            types.players.put(player, Type.CHERRY);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeEndRod(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsEndRod())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.ENDROD)){
            types.players.remove(player, Type.ENDROD);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.ENDROD);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeTotem(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsTotem())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.TOTEM)){
            types.players.remove(player, Type.TOTEM);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.TOTEM);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeHeart(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsHeart())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.HEART)){
            types.players.remove(player, Type.HEART);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.HEART);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activePale(Player player){
        if (config.isOldVer()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getNotAvailableMsg()));

            return true;
        }

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPale())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PALE)){
            types.players.remove(player, Type.PALE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.PALE);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activePurple(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPurple())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PURPLE)){
            types.players.remove(player, Type.PURPLE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.PURPLE);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeNote(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsNotes())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.NOTE)){
            types.players.remove(player, Type.NOTE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.NOTE);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeCloud(Player player){
        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCloud())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.CLOUD)){
            types.players.remove(player, Type.CLOUD);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.CLOUD);
            effects.startCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean errCommand(Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrCommand()));

        return true;
    }
}
