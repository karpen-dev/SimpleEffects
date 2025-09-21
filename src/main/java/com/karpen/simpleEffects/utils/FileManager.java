package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Type;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.*;

public class FileManager {

    private final SimpleEffects plugin;

    public FileManager(SimpleEffects plugin) {
        this.plugin = plugin;
    }

    public void savePlayers(Map<UUID, Type> data) {
        File file = new File(plugin.getDataFolder(), "players.dat");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<UUID, Type> loadPlayers() {
        File file = new File(plugin.getDataFolder(), "players.dat");
        Map<UUID, Type> data = new HashMap<>();

        if (!file.exists()) {
            return data;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                data = (Map<UUID, Type>) obj;
            }
        } catch (EOFException e) {
            savePlayers(new HashMap<>());
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
        }

        return data;
    }

    public void removePlayer(UUID playerId) {
        Map<UUID, Type> currentData = loadPlayers();
        currentData.remove(playerId);
        savePlayers(currentData);
    }
}