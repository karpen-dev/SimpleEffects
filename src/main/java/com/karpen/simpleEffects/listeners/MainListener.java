package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.services.Effects;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MainListener implements Listener {

    private final Effects effects;

    public MainListener(Effects effects){
        this.effects = effects;
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
