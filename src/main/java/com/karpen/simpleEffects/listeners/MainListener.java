package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
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

public class MainListener implements Listener {

    private final Effects effects;

    private Config config;
    private DBManager dbManager;
    private Types types;
    private FileManager manager;

    public MainListener(Config config, DBManager dbManager, Types types, FileManager manager, Effects effects){
        this.config = config;
        this.dbManager = dbManager;
        this.types = types;
        this.manager = manager;
        this.effects = effects;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String storageMethod = config.getMethod();

        Map<Set<Player>, String> playerTypes = new HashMap<>();
        playerTypes.put(types.cherryPlayers, "cherry");
        playerTypes.put(types.endRodPlayers, "endrod");
        playerTypes.put(types.totemPlayers, "totem");
        playerTypes.put(types.heartPlayers, "heart");
        playerTypes.put(types.palePlayers, "pale");
        playerTypes.put(types.purplePlayers, "purple");
        playerTypes.put(types.notePlayers, "note");
        playerTypes.put(types.cloudPlayers, "cloud");

        playerTypes.forEach((players, type) -> {
            if (players.contains(player)) {
                if ("MYSQL".equals(storageMethod)) {
                    dbManager.savePlayers(players, type);
                } else if ("TXT".equals(storageMethod)) {
                    manager.savePlayers(Set.of(player), type);
                }
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if (config.getMethod().equals("TXT")){
            manager.loadPlayers();
        } else if (config.getMethod().equals("MYSQL")) {
            types.cherryPlayers = dbManager.loadPlayersByType("cherry");
            types.endRodPlayers = dbManager.loadPlayersByType("endrod");
            types.totemPlayers = dbManager.loadPlayersByType("totem");
            types.heartPlayers = dbManager.loadPlayersByType("heart");
            types.palePlayers = dbManager.loadPlayersByType("pale");
            types.notePlayers = dbManager.loadPlayersByType("note");
            types.purplePlayers = dbManager.loadPlayersByType("purple");
            types.cloudPlayers = dbManager.loadPlayersByType("cloud");
        }

        if (types.cloudPlayers.contains(event.getPlayer())){
            effects.startCloudEffect(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!hasPlayerMovedSignificantly(event, player)) {
            return;
        }

        Map<Set<Player>, Particle> particleEffects = createParticleMap();

        applyParticleEffects(player, particleEffects);
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

    private Map<Set<Player>, Particle> createParticleMap() {
        Map<Set<Player>, Particle> map = new HashMap<>();
        map.put(types.cherryPlayers, Particle.CHERRY_LEAVES);
        map.put(types.endRodPlayers, Particle.END_ROD);
        map.put(types.totemPlayers, Particle.TOTEM_OF_UNDYING);
        map.put(types.heartPlayers, Particle.HEART);
        map.put(types.purplePlayers, Particle.WITCH);
        map.put(types.notePlayers, Particle.NOTE);

        if (!config.isOldVer()) {
            map.put(types.palePlayers, Particle.PALE_OAK_LEAVES);
        }

        return map;
    }

    private void applyParticleEffects(Player player, Map<Set<Player>, Particle> particleEffects) {
        particleEffects.forEach((players, particle) -> {
            if (players.contains(player)) {
                try {
                    effects.spawnEffect(particle, player);
                } catch (Exception e) {
                    if (particle == Particle.PALE_OAK_LEAVES) {
                        return;
                    }
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
        if (!(projectile.getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) projectile.getShooter();
        Map<Set<Player>, Particle> effectMap = createEffectMap();

        effectMap.forEach((players, particle) -> {
            if (players.contains(player)) {
                try {
                    effectApplier.apply(player, particle);
                } catch (Exception e) {
                    if (particle != Particle.PALE_OAK_LEAVES) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Map<Set<Player>, Particle> createEffectMap() {
        Map<Set<Player>, Particle> map = new HashMap<>();
        map.put(types.cherryPlayers, Particle.CHERRY_LEAVES);
        map.put(types.endRodPlayers, Particle.END_ROD);
        map.put(types.totemPlayers, Particle.TOTEM_OF_UNDYING);
        map.put(types.heartPlayers, Particle.HEART);
        map.put(types.purplePlayers, Particle.WITCH);
        map.put(types.notePlayers, Particle.NOTE);

        if (!config.isOldVer()) {
            map.put(types.palePlayers, Particle.PALE_OAK_LEAVES);
        }

        return map;
    }

    @FunctionalInterface
    private interface ProjectileEffectApplier {
        void apply(Player player, Particle particle) throws Exception;
    }
}
