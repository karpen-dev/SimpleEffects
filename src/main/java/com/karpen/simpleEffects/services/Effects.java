package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;

public class Effects {

    private final SimpleEffects plugin;
    private Config config;
    private FileManager manager;
    private DBManager dbManager;
    private Types types;

    public Effects(SimpleEffects plugin, Config config, FileManager manager, DBManager dbManager, Types types) {
        this.plugin = plugin;
        this.config = config;
        this.manager = manager;
        this.dbManager = dbManager;
        this.types = types;
    }

    public void removePlayer(Player player){
        if (config.getMethod().equals("TXT")){
            manager.removePlayer(player);
        } else {
            dbManager.removePlayer(player);
        }
    }

    public void spawnEffect(Particle particle, Player player) {
        Config config = plugin.getConfigObject();
        Location location = player.getLocation();

        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            return;
        }

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

    public void spawnEffectSnowball(Snowball snowball, Particle particle, Player player){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (snowball.isValid()){
                spawnEffect(particle, player);
            }
        }, 0L, 5L);
    }

    public void spawnEffectArrow(Arrow arrow, Particle particle, Player player){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (arrow.isValid()){
                spawnEffect(particle, player);
            }
        }, 0L, 5L);
    }

    public void startCloudEffect(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Location baseLocation = player.getRespawnLocation();
                if (baseLocation == null) {
                    baseLocation = player.getLocation();
                }

                if (!hasSpaceAbove(player, 3)) {
                    return;
                }

                if (!types.cloudPlayers.contains(player)){
                    return;
                }

                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    return;
                }

                Location cloudLocation = baseLocation.clone().add(0, 3, 0);

                player.spawnParticle(Particle.CLOUD, cloudLocation, 20, 1, 0.1, 1, 0.05);

                for (int i = 0; i < 10; i++) {
                    Location rainLocation = cloudLocation.clone().add(
                            Math.random() * 2 - 1,
                            0,
                            Math.random() * 2 - 1
                    );

                    player.spawnParticle(Particle.DRIPPING_WATER, rainLocation, 1, 0, -0.5, 0, 0.5);
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    private boolean hasSpaceAbove(Player player, int blocksAbove) {
        Location playerLoc = player.getLocation();
        World world = player.getWorld();

        for (int y = 1; y <= blocksAbove; y++) {
            Location checkLoc = playerLoc.clone().add(0, y, 0);
            Material blockType = world.getBlockAt(checkLoc).getType();

            if (!blockType.isAir()) {
                return false;
            }
        }

        return true;
    }
}
