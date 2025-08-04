package com.karpen.simpleEffects.utils;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Type;

import java.io.*;
import java.util.*;

public class FileManager {

    private final SimpleEffects plugin;

    public FileManager(SimpleEffects plugin) {
        this.plugin = plugin;
    }

    public void savePlayers(Map<UUID, Type> data) {
        File file = new File(plugin.getDataFolder(), "players.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
            }

            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<UUID, Type> loadPlayers() {
        File file = new File(plugin.getDataFolder(), "players.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();

                return new HashMap<>();
            }

             return (Map<UUID, Type>) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlayer(UUID playerId) {
        Map<UUID, Type> currentData = loadPlayers();
        currentData.remove(playerId);
        savePlayers(currentData);
    }
}