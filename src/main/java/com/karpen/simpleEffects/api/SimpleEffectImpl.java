package com.karpen.simpleEffects.api;

import com.karpen.simpleEffects.SimpleEffects;
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
        types.cherryPlayers.add(player);
    }

    public void activeEndrodToPlayer(Player player){
        types.endRodPlayers.add(player);
    }

    public void activeTotemToPlayer(Player player){
        types.totemPlayers.add(player);
    }

    public void activePaleToPlayer(Player player){
        if (plugin.isOldVer()){
            return;
        }

        types.palePlayers.add(player);
    }

    public void activeHeartToPlayer(Player player){
        types.heartPlayers.add(player);
    }

    public void activePurpleToPlayer(Player player){
        types.purplePlayers.add(player);
    }

    public void activeNoteToPlayer(Player player){
        types.notePlayers.add(player);
    }

    public void activeCloudToPlayer(Player player){
        types.cloudPlayers.add(player);
    }

    public void disableEffectCherryToPlayer(Player player){
        types.cherryPlayers.remove(player);
    }

    public void disableEndrodToPlayer(Player player){
        types.endRodPlayers.remove(player);
    }

    public void disableTotemToPlayer(Player player){
        types.totemPlayers.remove(player);
    }

    public void disablePaleToPlayer(Player player){
        if (plugin.isOldVer()){
            return;
        }

        types.palePlayers.remove(player);
    }

    public void disableHeartToPlayer(Player player){
        types.heartPlayers.remove(player);
    }

    public void disablePurpleToPlayer(Player player){
        types.purplePlayers.remove(player);
    }

    public void disableNoteToPlayer(Player player){
        types.notePlayers.remove(player);
    }

    public void disableCloudToPlayer(Player player){
        types.cloudPlayers.remove(player);
    }
}
