package com.karpen.simpleEffects.services;

import com.karpen.simpleEffects.SimpleEffects;
import com.karpen.simpleEffects.model.Types;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileManager {

    private final SimpleEffects plugin;
    private final File dataFile;

    private Types types;

    public FileManager(SimpleEffects plugin, Types types){
        this.plugin = plugin;
        this.types = types;

        this.dataFile = new File(plugin.getDataFolder(), "players.txt");

        if (!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }
    }

    public void savePlayers(Set<Player> players, String type){
        for (Player player : players){
            if (!isPlayerInFile(player.getName(), type)){
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true))) {
                    writer.write(player.getName() + "," + type);
                    writer.newLine();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isPlayerInFile(String name, String type){
        try(BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");

                if (parts.length == 2){
                    String playerName = parts[0];
                    String playerType = parts[1];

                    if (playerName.equals(name) && playerType.equals(type)){
                        return true;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public Set<Player> loadPlayers(){
        Set<Player> players = new HashSet<>();

        if (!dataFile.exists()){
            return players;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");

                if (parts.length == 2){
                    String playerName = parts[0];
                    String playerType = parts[1];

                    Player player = plugin.getServer().getPlayer(playerName);
                    if (player != null){
                        switch (playerType){
                            case "cherry":
                                types.cherryPlayers.add(player);
                                break;
                            case "endrod":
                                types.endRodPlayers.add(player);
                                break;
                            case "totem":
                                types.totemPlayers.add(player);
                                break;
                            case "heart":
                                types.heartPlayers.add(player);
                                break;
//                            case "pale":
//                                types.palePlayers.add(player);
//                                break;
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

    private void clearData(){
        if (dataFile.exists()){
            dataFile.delete();
        }
    }

    public void removePlayer(Player player){
        Set<Player> allPlayers = loadPlayers();
        clearData();

        for (Player p : allPlayers){
            if (!p.equals(player)){
                if (types.cherryPlayers.contains(p)) savePlayers(types.cherryPlayers, "cherry");
                if (types.endRodPlayers.contains(p)) savePlayers(types.endRodPlayers, "endrod");
                if (types.totemPlayers.contains(p)) savePlayers(types.totemPlayers, "totem");
                if (types.heartPlayers.contains(p)) savePlayers(types.heartPlayers, "heart");
//                if (types.palePlayers.contains(p)) savePlayers(types.palePlayers, "pale");
            }
        }
    }
}
