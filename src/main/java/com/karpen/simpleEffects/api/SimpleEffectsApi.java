package com.karpen.simpleEffects.api;

import org.bukkit.entity.Player;

public interface SimpleEffectsApi {
    void activeEffectCherryToPlayer(Player player);
    void activeEndrodToPlayer(Player player);
    void activeTotemToPlayer(Player player);
    void activePaleToPlayer(Player player);
    void activeHeartToPlayer(Player player);
    void activePurpleToPlayer(Player player);
    void activeNoteToPlayer(Player player);
    void disableEffectCherryToPlayer(Player player);
    void disableEndrodToPlayer(Player player);
    void disableTotemToPlayer(Player player);
    void disablePaleToPlayer(Player player);
    void disableHeartToPlayer(Player player);
    void disablePurpleToPlayer(Player player);
    void disableNoteToPlayer(Player player);
}