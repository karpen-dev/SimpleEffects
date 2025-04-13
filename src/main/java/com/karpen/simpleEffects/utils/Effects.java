package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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

        if (!config.isOldVer()){
            if (particle.equals(Particle.PALE_OAK_LEAVES)){
                location.getWorld().spawnParticle(particle, location, config.getCountPale(), 0.5, 0.5, 0.5);
            }
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
            private Location cloudLocation = null;
            private int animationStep = 0;

            @Override
            public void run() {
                if (!types.cloudPlayers.contains(player)) {
                    this.cancel();
                    return;
                }

                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    return;
                }

                Location playerLocation = player.getLocation();

                if (cloudLocation == null || cloudLocation.distance(playerLocation) > 10) {
                    cloudLocation = playerLocation.clone().add(0, 3, 0);
                }

                Location targetLocation = playerLocation.clone().add(0, 3, 0);
                Vector direction = targetLocation.toVector().subtract(cloudLocation.toVector()).multiply(0.2);
                cloudLocation.add(direction);

                if (!hasSpaceAbove(player, 3)) {
                    return;
                }

                player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, cloudLocation, 1, 0, 0, 0, 0);

                if (player.getVelocity().lengthSquared() < 0.01) {
                    if (animationStep % 25 == 0) {
                        for (int i = 0; i < 3; i++) {
                            Location rainLocation = cloudLocation.clone().add(
                                    Math.random() * 0.8 - 0.4,
                                    -1,
                                    Math.random() * 0.8 - 0.4
                            );
                            player.spawnParticle(Particle.DRIPPING_WATER, rainLocation, 1, 0, -0.3, 0);
                        }
                    }
                }

                animationStep++;
                if (animationStep > 1000) animationStep = 0;
            }
        }.runTaskTimer(plugin, 0, 1);
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
