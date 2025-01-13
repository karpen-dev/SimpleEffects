package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Effects {

    private final SimpleEffects plugin;

    public Effects(SimpleEffects plugin){
        this.plugin = plugin;
    }

    public final Set<Player> cherryPlayers = new HashSet<>();
    public final Set<Player> endRodPlayers = new HashSet<>();
    public final Set<Player> totemPlayers = new HashSet<>();

    public void spawnEffect(Location location, Particle particle){
        Config config = plugin.getConfigObject();
        location.getWorld().spawnParticle(particle, location, config.getCount(), 0.5, 0.5, 0.5);
    }
}
