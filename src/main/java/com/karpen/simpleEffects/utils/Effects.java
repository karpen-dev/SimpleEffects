package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Effects {
    private final SimpleEffects plugin;
    private final Config config;
    private final FileManager manager;
    private final DBManager dbManager;
    private final Types types;
    private final Map<Particle, ParticleEffectHandler> particleHandlers;
    private final Map<Player, BukkitTask> activeClouds = new HashMap<>();

    public Effects(SimpleEffects plugin, Config config, FileManager manager, DBManager dbManager, Types types) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null");
        this.config = Objects.requireNonNull(config, "Config cannot be null");
        this.manager = Objects.requireNonNull(manager, "FileManager cannot be null");
        this.dbManager = Objects.requireNonNull(dbManager, "DBManager cannot be null");
        this.types = Objects.requireNonNull(types, "Types cannot be null");
        this.particleHandlers = createParticleHandlers();
    }

    private Map<Particle, ParticleEffectHandler> createParticleHandlers() {
        Map<Particle, ParticleEffectHandler> handlers = new HashMap<>();

        handlers.put(Particle.CHERRY_LEAVES, (location, cfg) ->
                spawnSimpleParticle(location, cfg.getCountCherry(), 0.5, Particle.CHERRY_LEAVES));

        handlers.put(Particle.END_ROD, (location, cfg) -> {
            for (int i = 0; i < cfg.getCountEndrod(); i++) {
                Location particleLoc = location.clone().add(
                        (Math.random() - 0.5) * 0.2,
                        Math.random() * 0.5,
                        (Math.random() - 0.5) * 0.2
                );
                spawnParticleWithVelocity(particleLoc, Particle.END_ROD, 0, -0.02, 0, 0.1);
            }
        });

        handlers.put(Particle.TOTEM_OF_UNDYING, (location, cfg) -> {
            for (int i = 0; i < cfg.getCountTotem(); i++) {
                Location particleLoc = location.clone().add(
                        (Math.random() - 0.5) * 0.3,
                        Math.random() * 0.7,
                        (Math.random() - 0.5) * 0.3
                );
                spawnParticleWithVelocity(particleLoc, Particle.TOTEM_OF_UNDYING, 0, -0.01, 0, 0.05);
            }
        });

        handlers.put(Particle.HEART, (location, cfg) ->
                spawnSimpleParticle(location, cfg.getCountHeart(), 0.2, Particle.HEART));

        handlers.put(Particle.WITCH, (location, cfg) ->
                spawnSimpleParticle(location, cfg.getCountPurple(), 0.5, Particle.WITCH));

        handlers.put(Particle.NOTE, (location, cfg) ->
                spawnSimpleParticle(location, cfg.getCountNotes(), 0.5, Particle.NOTE));

        if (!config.isOldVer()) {
            handlers.put(Particle.PALE_OAK_LEAVES, (location, cfg) ->
                    spawnSimpleParticle(location, cfg.getCountPale(), 0.5, Particle.PALE_OAK_LEAVES));
        }

        return handlers;
    }

    public void removePlayer(Player player) {
        if (player == null) return;

        if (config.getMethod().equals("TXT")) {
            manager.removePlayer(player);
        } else {
            dbManager.removePlayer(player);
        }
    }

    public void spawnEffect(Particle particle, Player player) {
        if (player == null || particle == null || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        ParticleEffectHandler handler = particleHandlers.get(particle);
        if (handler != null) {
            try {
                handler.handle(player.getLocation(), config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void spawnEffectSnowball(Snowball snowball, Particle particle, Player player) {
        if (snowball == null || particle == null || player == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!snowball.isValid()) {
                    this.cancel();
                    return;
                }
                spawnEffect(particle, player);
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 5L);
    }

    public void spawnEffectArrow(Arrow arrow, Particle particle, Player player) {
        if (arrow == null || particle == null || player == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!arrow.isValid()) {
                    this.cancel();
                    return;
                }
                spawnEffect(particle, player);
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 5L);
    }

    public void startCloudEffect(Player player) {
        if (activeClouds.containsKey(player)) return;

        BukkitTask task = new CloudEffectRunnable(player).runTaskTimerAsynchronously(plugin, 0, 1);
        activeClouds.put(player, task);
    }

    private class CloudEffectRunnable extends BukkitRunnable {
        private final Player player;
        private Location cloudLocation = null;
        private int animationStep = 0;

        public CloudEffectRunnable(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (!Type.CLOUD.equals(types.players.get(player))) {
                this.cancel();
                activeClouds.remove(player);
                return;
            }

            if (player.getGameMode() == GameMode.SPECTATOR) {
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

            player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, cloudLocation, 1, 0, 0, 0, 0);

            if (player.getVelocity().lengthSquared() < 0.01) {
                if (animationStep % 25 == 0) {
                    for (int i = 0; i < 3; i++) {
                        Location rainLocation = cloudLocation.clone().add(
                                Math.random() * 0.8 - 0.4,
                                -1,
                                Math.random() * 0.8 - 0.4
                        );
                        player.getWorld().spawnParticle(Particle.DRIPPING_WATER, rainLocation, 1, 0, -0.3, 0);
                    }
                }
            }

            animationStep++;
            if (animationStep > 1000) animationStep = 0;
        }
    }

    public void stopCloudEffect(Player player) {
        BukkitTask task = activeClouds.remove(player);
        if (task != null) {
            task.cancel();
        }
    }

    private void spawnSimpleParticle(Location location, int count, double spread, Particle particle) {
        World world = location.getWorld();
        if (world != null) {
            world.spawnParticle(particle, location, count, spread, spread, spread);
        }
    }

    private void spawnParticleWithVelocity(Location location, Particle particle,
                                           double xVel, double yVel, double zVel, double speed) {
        World world = location.getWorld();
        if (world != null) {
            world.spawnParticle(particle, location, 1, xVel, yVel, zVel, speed);
        }
    }

    private boolean hasSpaceAbove(Player player, int blocksAbove) {
        Location playerLoc = player.getLocation();
        World world = player.getWorld();

        for (int y = 1; y <= blocksAbove; y++) {
            Location checkLoc = playerLoc.clone().add(0, y, 0);
            if (!world.getBlockAt(checkLoc).getType().isAir()) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    private interface ParticleEffectHandler {
        void handle(Location location, Config config) throws Exception;
    }
}