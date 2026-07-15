package jp.karpen.simpleEffects.utils;

import jp.karpen.simpleEffects.SimpleEffects;
import jp.karpen.simpleEffects.model.Type;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileManager {

    private final SimpleEffects plugin;
    private final Map<Type, ReentrantReadWriteLock> locks = new ConcurrentHashMap<>();
    private final String FILE_EXTENSION = ".dat";

    public FileManager(SimpleEffects plugin) {
        this.plugin = plugin;

        for (Type type : Type.values()) {
            locks.put(type, new ReentrantReadWriteLock());
        }
    }

    public CompletableFuture<Void> savePlayer(Type type, UUID playerId) {
        return CompletableFuture.runAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.writeLock().lock();

            try {
                Set<UUID> players = loadPlayersSync(type);
                players.add(playerId);
                savePlayersSync(type, players);
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public CompletableFuture<Void> removePlayer(Type type, UUID playerId) {
        return CompletableFuture.runAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.writeLock().lock();

            try {
                Set<UUID> players = loadPlayersSync(type);
                players.remove(playerId);
                savePlayersSync(type, players);
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public CompletableFuture<Optional<UUID>> loadPlayer(Type type, UUID playerId) {
        return CompletableFuture.supplyAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.readLock().lock();

            try {
                Set<UUID> players = loadPlayersSync(type);
                return players.contains(playerId) ? Optional.of(playerId) : Optional.empty();
            } finally {
                lock.readLock().unlock();
            }
        });
    }

    public CompletableFuture<Boolean> containsPlayer(Type type, UUID playerId) {
        return loadPlayer(type, playerId).thenApply(Optional::isPresent);
    }

    public CompletableFuture<Set<UUID>> loadAllPlayers(Type type) {
        return CompletableFuture.supplyAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.readLock().lock();

            try {
                return loadPlayersSync(type);
            } finally {
                lock.readLock().unlock();
            }
        });
    }

    public CompletableFuture<Void> saveAllPlayers(Type type, Set<UUID> players) {
        return CompletableFuture.runAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.writeLock().lock();

            try {
                savePlayersSync(type, players);
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    private Set<UUID> loadPlayersSync(Type type) {
        File file = getFile(type);
        Set<UUID> data = new HashSet<>();

        if (!file.exists()) {
            return data;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Set) {
                data = (Set<UUID>) obj;
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            plugin.getLogger().severe("Failed to load players for type " + type + ": " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    private void savePlayersSync(Type type, Set<UUID> players) {
        File file = getFile(type);

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(players);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player for type " + type + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public CompletableFuture<Void> clearPlayers(Type type) {
        return CompletableFuture.runAsync(() -> {
            ReentrantReadWriteLock lock = getLock(type);
            lock.writeLock().lock();

            try {
                File file = getFile(type);
                if (file.exists()) {
                    file.delete();
                }
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    private File getFile(Type type) {
        return new File(plugin.getDataFolder(), type.toString() + FILE_EXTENSION);
    }

    private ReentrantReadWriteLock getLock(Type type) {
        return locks.computeIfAbsent(type, k -> new ReentrantReadWriteLock());
    }

    @Deprecated
    public void savePlayers(Type type, Set<UUID> players) {
        saveAllPlayers(type, players);
    }

    @Deprecated
    public CompletableFuture<Set<UUID>> loadPlayers(Type type) {
        return loadAllPlayers(type);
    }
}