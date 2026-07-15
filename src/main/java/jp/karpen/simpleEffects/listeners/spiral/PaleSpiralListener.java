package jp.karpen.simpleEffects.listeners.spiral;

import jp.karpen.simpleEffects.listeners.AbstractEffectListener;
import jp.karpen.simpleEffects.model.Type;
import jp.karpen.simpleEffects.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PaleSpiralListener extends AbstractEffectListener {
    public PaleSpiralListener(FileManager fileManager) {
        super(Type.PALE_SPIRAL, fileManager);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (enabledPlayers.contains(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskTimer(plugin, (r) -> {
                if (!enabledPlayers.contains(player.getUniqueId())) r.cancel();
                spawnParticleSpiral(player, Particle.PALE_OAK_LEAVES);
            }, 0L, 20L);
        }
    }

    @Override
    protected void runSpiralParticle(Player player) {
        Bukkit.getScheduler().runTaskTimer(plugin, (r) -> {
            if (!enabledPlayers.contains(player.getUniqueId())) r.cancel();
            spawnParticleSpiral(player, Particle.PALE_OAK_LEAVES);
        }, 0L, 20L);
    }
}
