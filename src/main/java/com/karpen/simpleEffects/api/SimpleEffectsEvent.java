package com.karpen.simpleEffects.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SimpleEffectsEvent extends Event {

    @Getter
    private final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    public SimpleEffectsEvent(Player player) {
        this.player = player;
    }
}
