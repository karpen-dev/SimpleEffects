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
        this.dataFile = new File(plugin.getDataFolder(), "players.tmp");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void savePlayers(Map<UUID, Type> playerTypes) {
        File tempFile = new File(plugin.getDataFolder(), "players.tmp");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            Map<String, String> saveData = playerTypes.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().name()
                    ));
            oos.writeObject(saveData);

            if (dataFile.exists()) {
                dataFile.delete();
            }
            tempFile.renameTo(dataFile);

        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player data: " + e.getMessage());
            tempFile.delete();
        }
    }

    public Map<UUID, Type> loadPlayers() {
        Map<UUID, Type> result = new HashMap<>();

        if (!dataFile.exists()) {
            return result;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            @SuppressWarnings("unchecked")
            Map<String, String> loadedData = (Map<String, String>) ois.readObject();

            for (Map.Entry<String, String> entry : loadedData.entrySet()) {
                try {
                    UUID uuid = UUID.fromString(entry.getKey());
                    Type type = Type.valueOf(entry.getValue());
                    result.put(uuid, type);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid data entry: " + entry.getKey() +
                            "=" + entry.getValue());
                }
            }
        } catch (InvalidClassException | StreamCorruptedException e) {
            plugin.getLogger().warning("Corrupted data file, creating backup...");
            renameCorruptedFile();
        } catch (IOException | ClassNotFoundException e) {
            plugin.getLogger().warning("Failed to load player data: " + e.getMessage());
        }

        return result;
    }

    public void removePlayer(UUID playerId) {
        Map<UUID, Type> currentData = loadPlayers();
        currentData.remove(playerId);
        savePlayers(currentData);
    }

    private void renameCorruptedFile() {
        File backupFile = new File(plugin.getDataFolder(),
                "players_corrupted_" + System.currentTimeMillis() + ".dat");
        if (dataFile.exists()) {
            dataFile.renameTo(backupFile);
        }
    }
}