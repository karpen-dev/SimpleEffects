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

    public void activeEffectCherryToPlayer(Player player){
        types.players.put(player, Type.CHERRY);
    }

    public void activeEndrodToPlayer(Player player){
        types.players.put(player, Type.ENDROD);
    }

    public void activeTotemToPlayer(Player player){
        types.players.put(player, Type.TOTEM);
    }

    public void activePaleToPlayer(Player player){
        if (plugin.isOldVer()){
            return;
        }

        types.players.put(player, Type.PALE);
    }

    public void activeHeartToPlayer(Player player){
        types.players.put(player, Type.HEART);
    }

    public void activePurpleToPlayer(Player player){
        types.players.put(player, Type.PURPLE);
    }

    public void activeNoteToPlayer(Player player){
        types.players.put(player, Type.NOTE);
    }

    public void activeCloudToPlayer(Player player){
        types.players.put(player, Type.CLOUD);
    }

    public void disableEffectCherryToPlayer(Player player){
        types.players.remove(player, Type.CHERRY);
    }

    public void disableEndrodToPlayer(Player player){
        types.players.remove(player, Type.ENDROD);
    }

    public void disableTotemToPlayer(Player player){
        types.players.remove(player, Type.TOTEM);
    }

    public void disablePaleToPlayer(Player player){
        if (plugin.isOldVer()){
            return;
        }

        types.players.remove(player, Type.PALE);
    }

    public void disableHeartToPlayer(Player player){
        types.players.remove(player, Type.HEART);
    }

    public void disablePurpleToPlayer(Player player){
        types.players.remove(player, Type.PURPLE);
    }

    public void disableNoteToPlayer(Player player){
        types.players.remove(player, Type.NOTE);
    }

    public void disableCloudToPlayer(Player player){
        types.players.remove(player, Type.CLOUD);
    }
}
