package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.Effects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

public class MainListener implements Listener {

    private Effects effects;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        effects.cherryPlayers.remove(player);
        effects.endRodPlayers.remove(player);
        effects.totemPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        effects.cherryPlayers.remove(player);
        effects.totemPlayers.remove(player);
        effects.endRodPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        double oldX = event.getFrom().getX();
        double oldY = event.getFrom().getY();
        double oldZ = event.getFrom().getZ();

        double newX = event.getTo().getX();
        double newY = event.getTo().getY();
        double newZ = event.getTo().getZ();

        if (oldX != newX || oldY != newY || oldZ != newZ){
            if (effects.cherryPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.CHERRY_LEAVES);
            }
            if (effects.endRodPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.END_ROD);
            }
            if (effects.totemPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.TOTEM_OF_UNDYING);
            }
        }
    }

}
