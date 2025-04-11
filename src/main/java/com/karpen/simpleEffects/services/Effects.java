package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class Effects {

    private final SimpleEffects plugin;
    private Config config;
    private FileManager manager;
    private DBManager dbManager;

    public Effects(SimpleEffects plugin, Config config, FileManager manager, DBManager dbManager) {
        this.plugin = plugin;
        this.config = config;
        this.manager = manager;
        this.dbManager = dbManager;
    }

    public void removePlayer(Player player){
        if (config.getMethod().equals("TXT")){
            manager.removePlayer(player);
        } else {
            dbManager.removePlayer(player);
        }
    }

    public void spawnEffect(Location location, Particle particle) {
        Config config = plugin.getConfigObject();

        if (particle.equals(Particle.CHERRY_LEAVES)) {
            location.getWorld().spawnParticle(particle, location, config.getCountCherry(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.END_ROD)) {
            for (int i = 0; i < config.getCountEndrod(); i++) {
                Location particleLoc = location.clone().add(
                        (Math.random() - 0.5) * 0.2,
                        Math.random() * 0.5,
                        (Math.random() - 0.5) * 0.2
                );
                location.getWorld().spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        1,
                        0,
                        -0.02,
                        0,
                        0.1
                );
            }
        }
        if (particle.equals(Particle.TOTEM_OF_UNDYING)) {
            for (int i = 0; i < config.getCountTotem(); i++) {
                Location particleLoc = location.clone().add(
                        (Math.random() - 0.5) * 0.3,
                        Math.random() * 0.7,
                        (Math.random() - 0.5) * 0.3
                );
                location.getWorld().spawnParticle(
                        Particle.TOTEM_OF_UNDYING,
                        particleLoc,
                        1,
                        0,
                        -0.01,
                        0,
                        0.05
                );
            }
        }
        if (particle.equals(Particle.HEART)){
            location.getWorld().spawnParticle(particle, location, config.getCountHeart(), 0.2, 0.2, 0.2);
        }
        if (particle.equals(Particle.PALE_OAK_LEAVES)){
            location.getWorld().spawnParticle(particle, location, config.getCountPale(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.WITCH)){
            location.getWorld().spawnParticle(particle, location, config.getCountPurple(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.NOTE)){
            location.getWorld().spawnParticle(particle, location, config.getCountNotes(), 0.5, 0.5, 0.5);
        }
    }

    public void spawnEffectSnowball(Snowball snowball, Particle particle){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (snowball.isValid()){
                Location location = snowball.getLocation();

                spawnEffect(location, particle);
            }
        }, 0L, 5L);
    }

    public void spawnEffectArrow(Arrow arrow, Particle particle){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (arrow.isValid()){
                Location location = arrow.getLocation();

                spawnEffect(location, particle);
            }
        }, 0L, 5L);
    }
}
