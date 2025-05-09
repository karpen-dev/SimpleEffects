package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Type;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {

    private final SimpleEffects plugin;
    private final File dataFile;

    public FileManager(SimpleEffects plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "players.dat");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void savePlayers(Map<Player, Type> playerTypes) {
        clearData();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            Map<String, String> saveData = playerTypes.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().getUniqueId().toString(),
                            e -> e.getValue().name()
                    ));

            oos.writeObject(saveData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Player, Type> loadPlayers() {
        Map<Player, Type> result = new HashMap<>();

        if (!dataFile.exists()) {
            return result;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            @SuppressWarnings("unchecked")
            Map<String, String> loadedData = (Map<String, String>) ois.readObject();

            for (Map.Entry<String, String> entry : loadedData.entrySet()) {
                Player player = plugin.getServer().getPlayer(UUID.fromString(entry.getKey()));
                if (player != null) {
                    try {
                        Type type = Type.valueOf(entry.getValue());
                        result.put(player, type);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Unknown player type: " + entry.getValue());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void removePlayer(Player player) {
        Map<Player, Type> currentData = loadPlayers();
        currentData.remove(player);
        savePlayers(currentData);
    }

    private void clearData() {
        if (dataFile.exists()) {
            dataFile.delete();
        }
    }
}