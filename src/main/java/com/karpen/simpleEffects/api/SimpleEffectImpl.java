package com.karpen.simpleEffects.api;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.FileManager;
import org.bukkit.entity.Player;

public class SimpleEffectImpl implements SimpleEffectsApi {

    private final SimpleEffects plugin;
    private Types types;
    private Config config;
    private FileManager fileManager;
    private DBManager dbManager;

    public SimpleEffectImpl(SimpleEffects plugin, Types types, Config config, FileManager fileManager, DBManager dbManager){
        this.plugin = plugin;
        this.types = types;
        this.config = config;
        this.fileManager = fileManager;
        this.dbManager = dbManager;
    }

    @Override
    public void active(Type type, Player player) {
        if (plugin.isOldVer() && type.equals(Type.PALE)) return;
        types.players.put(player.getUniqueId(), type);
    }

    @Override
    public void disable(Type type, Player player) {
        if (plugin.isOldVer() && type.equals(Type.PALE)) return;
        types.players.remove(player.getUniqueId());
    }

    @Override
    public Type getEffect(Player player) {
        return types.players.get(player.getUniqueId());
    }

    @Override
    public void loadPlayers() {
        switch (config.getMethod().toUpperCase()) {
            case "TXT":
                types.players = fileManager.loadPlayers();
                break;
            case "MYSQL":
                types.players = dbManager.loadPlayers();
                break;
        }
    }

    @Override
    public void savePlayers() {
        switch (config.getMethod().toUpperCase()) {
            case "TXT":
                fileManager.savePlayers(types.players);
                break;
            case "MYSQL":
                dbManager.savePlayers(types.players);
                break;
        }
    }
}
