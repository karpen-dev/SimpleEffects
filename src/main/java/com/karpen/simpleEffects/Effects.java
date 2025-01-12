package com.karpen.simpleEffects;

import com.karpen.simpleEffects.model.Config;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Effects {

    private Config config;

    public final Set<Player> cherryPlayers = new HashSet<>();
    public final Set<Player> endRodPlayers = new HashSet<>();
    public final Set<Player> totemPlayers = new HashSet<>();

    public void spawnEffect(Location location, Particle particle){
        location.getWorld().spawnParticle(particle, location, config.getCount(), 0.5, 0.5, 0.5);
    }
}
