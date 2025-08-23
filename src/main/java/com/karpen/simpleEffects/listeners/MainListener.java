package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.api.SimpleEffectsApi;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.EffectAppler;
import com.karpen.simpleEffects.utils.Effects;
import com.karpen.simpleEffects.utils.FileManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainListener implements Listener {

    private final Effects effects;

    private Config config;
    private DBManager dbManager;
    private Types types;
    private FileManager manager;
    private SimpleEffectsApi api;

    public MainListener(Config config, DBManager dbManager, Types types, FileManager manager, Effects effects, SimpleEffectsApi api){
        this.config = config;
        this.dbManager = dbManager;
        this.types = types;
        this.manager = manager;
        this.effects = effects;
        this.api = api;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        
        if (types.players != null && types.players.containsKey(playerId)) {
            switch (config.getMethod().toLowerCase()) {
                case "mysql" -> dbManager.savePlayers(types.players);
                case "txt" -> manager.savePlayers(types.players);
            }

            if (types.players.get(playerId) != null && types.players.get(playerId).equals(Type.CLOUD)) {
                EffectAppler.stopCloudEffect(player);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (config.getMethod().toLowerCase()) {
            case "mysql" -> types.players = dbManager.loadPlayers();
            case "txt" -> types.players = manager.loadPlayers();
            default -> throw new IllegalStateException("Unknown method: " + config.getMethod());
        }

        UUID playerId = event.getPlayer().getUniqueId();
        if (types.players.get(playerId) != null && types.players.get(playerId).equals(Type.CLOUD)) {
            EffectAppler.startCloudEffect(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!hasPlayerMovedSignificantly(event, player)) {
            return;
        }

        applyParticleEffects(player);
    }

    private boolean hasPlayerMovedSignificantly(PlayerMoveEvent event, Player player) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return false;
        }

        float yaw = player.getLocation().getYaw();
        if (from.getX() == to.getX() && from.getZ() == to.getZ()
                && (yaw <= 90.0 || yaw >= 0.0 || from.getY() != to.getY())) {
            return false;
        }

        return true;
    }

    private void applyParticleEffects(Player player) {
        types.players.forEach((players, type) -> {
            if (players.equals(player.getUniqueId())) {
                try {
                    switch (type) {
                        case CHERRY -> effects.spawnEffect(Particle.CHERRY_LEAVES, player);
                        case NOTE -> effects.spawnEffect(Particle.NOTE, player);
                        case PALE -> {
                            if (!config.isOldVer()){
                                effects.spawnEffect(Particle.PALE_OAK_LEAVES, player);
                            }
                        }
                        case ENDROD -> effects.spawnEffect(Particle.END_ROD, player);
                        case TOTEM -> effects.spawnEffect(Particle.TOTEM_OF_UNDYING, player);
                        case PURPLE -> effects.spawnEffect(Particle.WITCH, player);
                        case HEART -> effects.spawnEffect(Particle.HEART, player);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Snowball) {
            handleSnowballEffect((Snowball) event.getEntity());
        } else if (event.getEntity() instanceof Arrow) {
            handleArrowEffect((Arrow) event.getEntity());
        }
    }

    private void handleSnowballEffect(Snowball snowball) {
        applyProjectileEffects(snowball, (player, particle) ->
                effects.spawnEffectSnowball(snowball, particle, player));
    }

    private void handleArrowEffect(Arrow arrow) {
        applyProjectileEffects(arrow, (player, particle) ->
                effects.spawnEffectArrow(arrow, particle, player));
    }

    private void applyProjectileEffects(Projectile projectile, ProjectileEffectApplier effectApplier) {
        if (!(projectile.getShooter() instanceof Player player)) {
            return;
        }

        types.players.forEach((players, type) -> {
            try {
                switch (type) {
                    case CHERRY -> effectApplier.apply(player, Particle.CHERRY_LEAVES);
                    case ENDROD -> effectApplier.apply(player, Particle.END_ROD);
                    case TOTEM -> effectApplier.apply(player, Particle.TOTEM_OF_UNDYING);
                    case PALE -> {
                        if (!config.isOldVer()){
                            effectApplier.apply(player, Particle.PALE_OAK_LEAVES);
                        }
                    }
                    case HEART -> effectApplier.apply(player, Particle.HEART);
                    case NOTE -> effectApplier.apply(player, Particle.NOTE);
                    case PURPLE -> effectApplier.apply(player, Particle.WITCH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FunctionalInterface
    private interface ProjectileEffectApplier {
        void apply(Player player, Particle particle) throws Exception;
    }
}
