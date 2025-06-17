package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class EffectAppler {

    private static Config config;
    private static Effects effects;
    private static Types types;
    private static Map<Player, Inventory> playerInventors;

    public EffectAppler(Config config, Effects effects, Types types, Map<Player, Inventory> playerInventors) {
        EffectAppler.config = config;
        EffectAppler.effects = effects;
        EffectAppler.types = types;
        EffectAppler.playerInventors = playerInventors;
    }

    public static boolean activeEff(Player player, Type type) {
        if (config.isRightsUsing()) {
            switch (type) {
                case CHERRY -> {
                    if (player.hasPermission(config.getRightsCherry())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case ENDROD -> {
                    if (player.hasPermission(config.getRightsEndRod())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case TOTEM -> {
                    if (player.hasPermission(config.getRightsTotem())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case HEART -> {
                    if (player.hasPermission(config.getRightsHeart())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case PALE -> {
                    if (player.hasPermission(config.getRightsPale())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case PURPLE -> {
                    if (player.hasPermission(config.getRightsPurple())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case NOTE -> {
                    if (player.hasPermission(config.getRightsNotes())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case CLOUD -> {
                    if (player.hasPermission(config.getRightsCloud())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
            }
        }

        if (playerInventors.containsKey(player)) {
            playerInventors.remove(player);
            player.closeInventory();
        }

        if (type.equals(Type.PALE) && config.isOldVer()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getUnsupportedName()));
            return true;
        }

        if (Type.CLOUD.equals(types.players.get(player))) {
            types.players.remove(player);
            effects.stopCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.CLOUD);
            effects.startCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));

        }

        if (types.players.containsKey(player) && types.players.get(player).equals(type) && !type.equals(Type.CLOUD)) {
            types.players.remove(player, type);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, type);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }
}
