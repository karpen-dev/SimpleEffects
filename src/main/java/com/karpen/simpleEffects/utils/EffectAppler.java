package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.api.SimpleEffectsApi;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class EffectAppler {

    private static Config config;
    private static Effects effects;
    private static Types types;
    private static Map<Player, Inventory> playerInventors;
    private static SimpleEffectsApi api;

    public EffectAppler(Config config, Effects effects, Types types, Map<Player, Inventory> playerInventors, SimpleEffectsApi api) {
        EffectAppler.config = config;
        EffectAppler.effects = effects;
        EffectAppler.types = types;
        EffectAppler.playerInventors = playerInventors;
        EffectAppler.api = api;
    }

    public static boolean activeEff(Player player, Type type) {
        if (config.isRightsUsing()) {
            switch (type) {
                case CHERRY -> {
                    if (!player.hasPermission(config.getRightsCherry())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case ENDROD -> {
                    if (!player.hasPermission(config.getRightsEndRod())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case TOTEM -> {
                    if (!player.hasPermission(config.getRightsTotem())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case HEART -> {
                    if (!player.hasPermission(config.getRightsHeart())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case PALE -> {
                    if (!player.hasPermission(config.getRightsPale())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case PURPLE -> {
                    if (!player.hasPermission(config.getRightsPurple())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case NOTE -> {
                    if (!player.hasPermission(config.getRightsNotes())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case CLOUD -> {
                    if (!player.hasPermission(config.getRightsCloud())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
                        return true;
                    }
                }
                case TOTEM_SPIRAL -> {
                    if (!player.hasPermission(config.getRightsTotemSpiral())) {
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

        switch (type) {
            case TOTEM_SPIRAL -> {
                if (Type.TOTEM_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.TOTEM_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
            case ENDROD_SPIRAL -> {
                if (Type.ENDROD_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.ENDROD_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
            case CHERRY_SPIRAL -> {
                if (Type.CHERRY_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.CHERRY_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
            case PALE_SPIRAL -> {
                if (Type.PALE_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.PALE_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
            case PURPLE_SPIRAL -> {
                if (Type.PURPLE_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.PURPLE_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
            case NOTE_SPIRAL -> {
                if (Type.NOTE_SPIRAL.equals(types.players.get(player.getUniqueId()))) {
                    types.players.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
                } else {
                    types.players.put(player.getUniqueId(), Type.NOTE_SPIRAL);
                    effects.spawnSpiralParticle(player, type, 1.0, 3, 3, 30);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
                }

                return true;
            }
        }

        if (Type.CLOUD.equals(type)) {
            if (Type.CLOUD.equals(types.players.get(player.getUniqueId()))) {
                types.players.remove(player.getUniqueId());
                effects.stopCloudEffect(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
            } else {
                types.players.put(player.getUniqueId(), Type.CLOUD);
                effects.startCloudEffect(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
            }
        } else {
            if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(type)) {
                types.players.remove(player.getUniqueId());
                effects.removePlayer(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
            } else {
                types.players.put(player.getUniqueId(), type);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
            }
        }

        api.callEvent(player);

        return true;
    }

    public static void stopCloudEffect(Player player) {
        if (types.players.get(player.getUniqueId()).equals(Type.CLOUD)) {
            effects.stopCloudEffect(player);
        }
    }

    public static void startCloudEffect(Player player) {
        if (types.players.get(player.getUniqueId()).equals(Type.CLOUD)) {
            effects.startCloudEffect(player);
        }
    }
}
