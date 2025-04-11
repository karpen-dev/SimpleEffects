package com.karpen.simpleEffects.listeners;

import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.services.Effects;
import com.karpen.simpleEffects.services.FileManager;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (config.getMethod().equals("MYSQL")){
            if (types.cherryPlayers.contains(player)){
                dbManager.savePlayers(types.cherryPlayers, "cherry");
            }

            if (types.endRodPlayers.contains(player)){
                dbManager.savePlayers(types.endRodPlayers, "endrod");
            }

            if (types.totemPlayers.contains(player)){
                dbManager.savePlayers(types.totemPlayers, "totem");
            }

            if (types.heartPlayers.contains(player)){
                dbManager.savePlayers(types.heartPlayers, "heart");
            }

            if (types.palePlayers.contains(player)){
                dbManager.savePlayers(types.palePlayers, "pale");
            }

            if (types.purplePlayers.contains(player)){
                dbManager.savePlayers(types.purplePlayers, "purple");
            }

            if (types.notePlayers.contains(player)){
                dbManager.savePlayers(types.notePlayers, "note");
            }

        } else if (config.getMethod().equals("TXT")){
            if (types.cherryPlayers.contains(player)){
                manager.savePlayers(Set.of(player), "cherry");
            }

            if (types.endRodPlayers.contains(player)){
                manager.savePlayers(Set.of(player), "endrod");
            }

            if (types.totemPlayers.contains(player)){
                manager.savePlayers(Set.of(player), "totem");
            }

            if (types.heartPlayers.contains(player)){
                manager.savePlayers(Set.of(player), "heart");
            }

            if (types.palePlayers.contains(player)){
                manager.savePlayers(Set.of(player), "pale");
            }

            if (types.purplePlayers.contains(player)){
                manager.savePlayers(Set.of(player), "purple");
            }

            if (types.notePlayers.contains(player)){
                manager.savePlayers(Set.of(player), "note");
            }
        }
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
        }
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

        float yaw = player.getLocation().getYaw();

        if (oldX != newX || oldY != newY || oldZ != newZ){
            if (oldX == newX && oldZ == newZ && ((yaw <= 90.0 || yaw >= 0.0) || (oldY != newY))){
                return;
            }

            if (types.cherryPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.CHERRY_LEAVES);
            }
            if (types.endRodPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.END_ROD);
            }
            if (types.totemPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.TOTEM_OF_UNDYING);
            }
            if (types.heartPlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.HEART);
            }
            if (types.palePlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.PALE_OAK_LEAVES);
            }
            if (types.purplePlayers.contains(player)){
                effects.spawnEffect(player.getLocation(), Particle.WITCH);
            }
            if (types.notePlayers.contains(player)){
                effects.spawnEffect(player.getLocation(),Particle.NOTE);
            }
        }
    }

    @EventHandler
    public void onSnowBallThrow(ProjectileLaunchEvent event){
        if (event.getEntity() instanceof Snowball){
            Snowball snowball = (Snowball)  event.getEntity();

            if (!(snowball.getShooter() instanceof Player)){
                return;
            }

            Player player = (Player) snowball.getShooter();

            if (types.cherryPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.CHERRY_LEAVES);
            }
            if (types.endRodPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.END_ROD);
            }
            if (types.totemPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.TOTEM_OF_UNDYING);
            }
            if (types.heartPlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.HEART);
            }
            if (types.palePlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.PALE_OAK_LEAVES);
            }
            if (types.purplePlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.WITCH);
            }
            if (types.notePlayers.contains(player)){
                effects.spawnEffectSnowball(snowball, Particle.NOTE);
            }
        }

        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();

            if (!(arrow.getShooter() instanceof Player)){
                return;
            }

            Player player = (Player) arrow.getShooter();

            if (types.cherryPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.CHERRY_LEAVES);
            }
            if (types.endRodPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.END_ROD);
            }
            if (types.totemPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.TOTEM_OF_UNDYING);
            }
            if (types.heartPlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.HEART);
            }
            if (types.palePlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.PALE_OAK_LEAVES);
            }
            if (types.purplePlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.WITCH);
            }
            if (types.notePlayers.contains(player)){
                effects.spawnEffectArrow(arrow, Particle.NOTE);
            }
        }
    }

}
