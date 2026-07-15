package jp.karpen.simpleEffects.api;

import jp.karpen.simpleEffects.SimpleEffects;
import jp.karpen.simpleEffects.model.Type;
import org.bukkit.entity.Player;

public final class SimpleEffectImpl implements SimpleEffectsApi {
    private final SimpleEffects plugin;

    public SimpleEffectImpl(SimpleEffects plugin) {
        this.plugin = plugin;
    }

    @Override
    public void toggleEffect(Player player, Type type) {
        switch (type) {
            case CHERRY -> plugin.getCherryListener().toggleEffect(player);
            case ENDROD -> plugin.getEndrodListener().toggleEffect(player);
            case TOTEM -> plugin.getTotemListener().toggleEffect(player);
            case HEART -> plugin.getHeartListener().toggleEffect(player);
            case PALE -> plugin.getPaleListener().toggleEffect(player);
            case PURPLE -> plugin.getPurpleListener().toggleEffect(player);
            case NOTE -> plugin.getNoteListener().toggleEffect(player);

            case CHERRY_SPIRAL -> plugin.getCherrySpiralListener().toggleEffect(player);
            case ENDROD_SPIRAL -> plugin.getEndrodSpiralListener().toggleEffect(player);
            case TOTEM_SPIRAL -> plugin.getTotemSpiralListener().toggleEffect(player);
            case PALE_SPIRAL -> plugin.getPaleSpiralListener().toggleEffect(player);
            case PURPLE_SPIRAL -> plugin.getPurpleSpiralListener().toggleEffect(player);
            case NOTE_SPIRAL -> plugin.getNoteSpiralListener().toggleEffect(player);
        }
    }
}
