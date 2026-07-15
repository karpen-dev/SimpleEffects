package jp.karpen.simpleEffects.api;

import jp.karpen.simpleEffects.model.Type;
import org.bukkit.entity.Player;

public interface SimpleEffectsApi {
    void toggleEffect(Player player, Type type);
}