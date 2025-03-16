package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.services.Effects;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;

public class MainListener implements Listener {

    private final Effects effects;

    public MainListener(Effects effects){
        this.effects = effects;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (effects.cherryPlayers.contains(player)){
            effects.savePlayers(Set.of(player), "cherry");
        }

        if (effects.endRodPlayers.contains(player)){
            effects.savePlayers(Set.of(player), "endrod");
        }

        if (effects.totemPlayers.contains(player)){
            effects.savePlayers(Set.of(player), "totem");
        }

        if (effects.heartPlayers.contains(player)){
            effects.savePlayers(Set.of(player), "heart");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        effects.loadPlayers();
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

        float yaw = player.getLocation().getYaw();

        if (oldX != newX || oldY != newY || oldZ != newZ){
            if (oldX == newX && oldZ == newZ && ((yaw <= 90.0 || yaw >= 0.0) || (oldY != newY))){
                return;
            }

            if (effects.cherryPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.CHERRY_LEAVES);
            }
            if (effects.endRodPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.END_ROD);
            }
            if (effects.totemPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.TOTEM_OF_UNDYING);
            }
            if (effects.heartPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.HEART);
            }
        }
    }

    @EventHandler
    public void onSnowBallThrow(ProjectileLaunchEvent event){
        if (event.getEntity() instanceof Snowball){
            Snowball snowball = (Snowball)  event.getEntity();

            if (!(snowball.getShooter() instanceof Player)){
                return;
            }

            Player player = (Player) snowball.getShooter();

            if (effects.cherryPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.CHERRY_LEAVES);
            }
            if (effects.endRodPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.END_ROD);
            }
            if (effects.totemPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.TOTEM_OF_UNDYING);
            }
            if (effects.heartPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.HEART);
            }
        }

        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();

            if (!(arrow.getShooter() instanceof Player)){
                return;
            }

            Player player = (Player) arrow.getShooter();

            if (effects.cherryPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.CHERRY_LEAVES);
            }
            if (effects.endRodPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.END_ROD);
            }
            if (effects.totemPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.TOTEM_OF_UNDYING);
            }
            if (effects.heartPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.HEART);
            }
        }
    }

}
