package jp.karpen.simpleEffects.listeners.base;

import jp.karpen.simpleEffects.listeners.AbstractEffectListener;
import jp.karpen.simpleEffects.model.Type;
import jp.karpen.simpleEffects.utils.FileManager;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public final class PurpleListener extends AbstractEffectListener {
    public PurpleListener (FileManager fileManager) {
        super(Type.PURPLE, fileManager);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (enabledPlayers.contains(player.getUniqueId())) {
            if (hasPlayerMovedSignificantly(event, player)) return;

            spawnParticleTrail(player, Particle.WITCH);
        }
    }

    @Override
    protected void runSpiralParticle(Player player) {
    }
}
