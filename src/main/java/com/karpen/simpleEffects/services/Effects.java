package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Effects {

    private final SimpleEffects plugin;
    private final File datafile;

    private Config config;
    private DBManager dbManager;

    public Effects(SimpleEffects plugin, Config config, DBManager dbManager) {
        this.plugin = plugin;
        this.config = config;
        this.dbManager = dbManager;

        this.datafile = new File(plugin.getDataFolder(), "players.txt");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public Set<Player> cherryPlayers = new HashSet<>();
    public Set<Player> endRodPlayers = new HashSet<>();
    public Set<Player> totemPlayers = new HashSet<>();
    public Set<Player> heartPlayers = new HashSet<>();
    public Set<Player> palePlayers = new HashSet<>();

    public void savePlayers(Set<Player> players, String playerType) {
        for (Player player : players) {
            if (!isPlayerInFile(player.getUniqueId(), playerType)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(datafile, true))) {
                    writer.write(player.getUniqueId().toString() + "," + playerType);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isPlayerInFile(UUID playerUUID, String playerType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(datafile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String uuidString = parts[0];
                    String type = parts[1];
                    if (uuidString.equals(playerUUID.toString()) && type.equals(playerType)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<Player> loadPlayers() {
        Set<Player> players = new HashSet<>();

        if (!datafile.exists()) {
            return players;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(datafile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String uuidString = parts[0];
                    String playerType = parts[1];

                    Player player = plugin.getServer().getPlayer(UUID.fromString(uuidString));
                    if (player != null) {
                        switch (playerType) {
                            case "cherry":
                                cherryPlayers.add(player);
                                break;
                            case "endrod":
                                endRodPlayers.add(player);
                                break;
                            case "totem":
                                totemPlayers.add(player);
                                break;
                            case "heart":
                                heartPlayers.add(player);
                                break;
                            case "pale":
                                palePlayers.add(player);
                                break;
                        }
                        players.add(player);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    public void clearData() {
        if (datafile.exists()) {
            datafile.delete();
        }
    }

    public void removePlayer(Player player) {
        cherryPlayers.remove(player);
        endRodPlayers.remove(player);
        totemPlayers.remove(player);
        heartPlayers.remove(player);
        palePlayers.remove(player);

        dbManager.removePlayerByName(player.getName());

        Set<Player> allPlayers;

        if (config.getMethod().equals("TXT")) {
            allPlayers = loadPlayers();
            clearData();
        } else if (config.getMethod().equals("MYSQL")) {
            allPlayers = dbManager.loadPlayers();
        } else {
            allPlayers = new HashSet<>();
        }

        for (Player p : allPlayers) {
            if (!p.equals(player)) {
                if (config.getMethod().equals("TXT")) {
                    if (cherryPlayers.contains(p)) {
                        savePlayers(cherryPlayers, "cherry");
                    }
                    if (endRodPlayers.contains(p)) {
                        savePlayers(endRodPlayers, "endrod");
                    }
                    if (totemPlayers.contains(p)) {
                        savePlayers(totemPlayers, "totem");
                    }
                    if (heartPlayers.contains(p)) {
                        savePlayers(heartPlayers, "heart");
                    }
                    if (palePlayers.contains(p)) {
                        savePlayers(palePlayers, "pale");
                    }
                }
            }
        }
    }

    public void spawnEffect(Location location, Particle particle) {
        Config config = plugin.getConfigObject();

        if (particle.equals(Particle.CHERRY_LEAVES)) {
            location.getWorld().spawnParticle(particle, location, config.getCountCherry(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.END_ROD)) {
            location.getWorld().spawnParticle(particle, location, config.getCountEndrod(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.TOTEM_OF_UNDYING)) {
            location.getWorld().spawnParticle(particle, location, config.getCountTotem(), 0.5, 0.5, 0.5);
        }
        if (particle.equals(Particle.HEART)){
            location.getWorld().spawnParticle(particle, location, config.getCountHeart(), 0.2, 0.2, 0.2);
        }
        if (particle.equals(Particle.PALE_OAK_LEAVES)){
            location.getWorld().spawnParticle(particle, location, 10);
        }
    }

    public void spawnEffectSnowball(Snowball snowball, Particle particle){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (snowball.isValid()){
                Location location = snowball.getLocation();

                spawnEffect(location, particle);
            }
        }, 0L, 5L);
    }

    public void spawnEffectArrow(Arrow arrow, Particle particle){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (arrow.isValid()){
                Location location = arrow.getLocation();

                spawnEffect(location, particle);
            }
        }, 0L, 5L);
    }
}
