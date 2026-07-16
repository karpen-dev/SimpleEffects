package jp.karpen.simpleEffects.listeners;

import jp.karpen.simpleEffects.SimpleEffects;
import jp.karpen.simpleEffects.model.Type;
import jp.karpen.simpleEffects.utils.FileManager;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractEffectListener implements Listener {

    @Getter
    protected final Type effectType;

    protected final Set<UUID> enabledPlayers;
    protected final Configuration config;
    protected final FileManager fileManager;
    protected final SimpleEffects plugin;

    public AbstractEffectListener(Type effectType, FileManager fileManager) {
        this.effectType = effectType;
        this.plugin = SimpleEffects.getInstance();
        this.config = plugin.getConfig();
        this.fileManager = fileManager;

        this.enabledPlayers = new HashSet<>();
    }

    public void toggleEffect(Player player) {
        if (!isAllowed(player)) return;

        if (enabledPlayers.contains(player.getUniqueId())) {
            enabledPlayers.remove(player.getUniqueId());
            fileManager.removePlayer(effectType, player.getUniqueId());

            player.sendMessage(SimpleEffects.getLanguageManager().getMessage("disable-effect"));
        } else {
            enabledPlayers.add(player.getUniqueId());
            fileManager.savePlayer(effectType, player.getUniqueId());

            player.sendMessage(SimpleEffects.getLanguageManager().getMessage("enable-effect"));

            runSpiralParticle(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        fileManager.containsPlayer(effectType, player.getUniqueId())
                .thenAccept(exists -> {
                    if (exists) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            enabledPlayers.add(player.getUniqueId());
                        });
                    }
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
    }

    protected boolean hasPlayerMovedSignificantly(PlayerMoveEvent event, Player player) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return true;

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return true;
        }

        float yaw = player.getLocation().getYaw();
        return from.getX() == to.getX() && from.getZ() == to.getZ()
                && (yaw <= 90.0 || yaw >= 0.0 || from.getY() != to.getY());
    }

    protected void spawnParticleTrail(Player player, Particle particle) {
        spawnRandomLocParticle(player, particle);
    }

    private boolean isAllowed(Player player) {
        return player.hasPermission(Objects.requireNonNull(
                config.getString(String.format("rights.%s", effectType.toString().toLowerCase()))));
    }

    private void spawnRandomLocParticle(Player player, Particle particle) {
        player.getWorld().spawnParticle(
                particle,
                player.getLocation().add(Math.random() * 1, +0.5, Math.random() * 1),
                1
        );
    }

    protected void spawnParticleSpiral(Player player, Particle particle) {
        Location center = player.getLocation().add(0, 1, 0);
        World world = center.getWorld();
        if (world == null) return;

        double radius = 1.5;
        double height = 3.0;
        int points = 30;
        double speed = 0.3;
        long time = System.currentTimeMillis() / 50;

        for (int i = 0; i < points; i++) {
            double progress = (double) i / points;
            double angle = progress * Math.PI * 6 + time * speed;
            double y = progress * height;
            double x = Math.cos(angle) * radius * (1 - progress * 0.3);
            double z = Math.sin(angle) * radius * (1 - progress * 0.3);

            Location particleLoc = center.clone().add(x, y, z);

            world.spawnParticle(
                    particle,
                    particleLoc,
                    1,
                    0, 0, 0,
                    0
            );
        }

        for (int i = 0; i < points; i++) {
            double progress = (double) i / points;
            double angle = -progress * Math.PI * 6 + time * speed * 0.8;
            double y = progress * height;
            double x = Math.cos(angle) * radius * 0.8 * (1 - progress * 0.3);
            double z = Math.sin(angle) * radius * 0.8 * (1 - progress * 0.3);

            Location particleLoc = center.clone().add(x, y, z);

            world.spawnParticle(
                    particle,
                    particleLoc,
                    1,
                    0, 0, 0,
                    0
            );
        }
    }

    protected abstract void runSpiralParticle(Player player);
}
