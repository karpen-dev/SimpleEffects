package com.karpen.simpleEffects.api;

import com.karpen.simpleEffects.model.Type;
import org.bukkit.entity.Player;

public interface SimpleEffectsApi {
    void active(Type type, Player player);
    void disable(Type type, Player player);
    Type getEffect(Player player);
}