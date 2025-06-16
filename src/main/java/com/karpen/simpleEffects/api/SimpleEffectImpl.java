package com.karpen.simpleEffects.api;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.entity.Player;

public class SimpleEffectImpl implements SimpleEffectsApi {

    private final SimpleEffects plugin;
    private Types types;

    public SimpleEffectImpl(SimpleEffects plugin, Types types){
        this.plugin = plugin;
        this.types = types;
    }

    @Override
    public void active(Type type, Player player) {
        if (plugin.isOldVer() && type.equals(Type.PALE)) return;
        types.players.put(player, type);
    }

    @Override
    public void disable(Type type, Player player) {
        if (plugin.isOldVer() && type.equals(Type.PALE)) return;
        types.players.remove(player);
    }

    @Override
    public Type getEffect(Player player) {
        return types.players.get(player);
    }
}
