package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Effects {

    private final SimpleEffects plugin;
    private final File datafile;

    public Effects(SimpleEffects plugin) {
        this.plugin = plugin;
        this.datafile = new File(plugin.getDataFolder(), "players.txt");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public final Set<Player> cherryPlayers = new HashSet<>();
    public final Set<Player> endRodPlayers = new HashSet<>();
    public final Set<Player> totemPlayers = new HashSet<>();

    public void savePlayers(Set<Player> players, String playerType){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(datafile, true))){
            for (Player player : players){
                writer.write(player.getUniqueId().toString() + "," + playerType);
                writer.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Set<Player> loadPlayers(){
        Set<Player> players = new HashSet<>();

        if (!datafile.exists()){
            return players;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(datafile))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 2){
                    String uuidString = parts[0];
                    String playerType = parts[1];

                    Player player = plugin.getServer().getPlayer(UUID.fromString(uuidString));
                    if (player != null){
                        switch (playerType){
                            case "cherry":
                                cherryPlayers.add(player);
                                break;
                            case "endrod":
                                endRodPlayers.add(player);
                                break;
                            case "totem":
                                totemPlayers.add(player);
                                break;
                        }
                        players.add(player);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return players;
    }

    public void clearData(){
        if (datafile.exists()){
            datafile.delete();
        }
    }

    public void spawnEffect(Location location, Particle particle){
        Config config = plugin.getConfigObject();
        location.getWorld().spawnParticle(particle, location, config.getCount(), 0.5, 0.5, 0.5);
    }
}
