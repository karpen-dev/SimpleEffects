package com.karpen.simpleEffects.database;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBManager {

    Config config;
    JavaPlugin plugin;
    com.karpen.simpleEffects.model.Types types;

    @Setter
    private Connection connection;

    public DBManager(Config config, JavaPlugin plugin, Types types){
        this.config = config;
        this.plugin = plugin;
        this.types = types;

        if (config.getMethod().equals("MYSQL")){
            final String url = config.getDbUrl();
            final String user = config.getDbUser();
            final String password = config.getDbPassword();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(url, user, password);
                createTableIfNotExists();
            } catch (SQLException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
    }

    private void createTableIfNotExists(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_types (" +
                "playerName VARCHAR(100) PRIMARY KEY, " +
                "type VARCHAR(50) NOT NULL)";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removePlayer(Player player){
        String removeSQL = "DELETE FROM player_types WHERE playerName = ?";

        try(PreparedStatement stmt = connection.prepareStatement(removeSQL)) {
            stmt.setString(1, player.getName());
            stmt.addBatch();

            stmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void savePlayers(Set<Player> players, String type) {
        String insertSQL = "INSERT INTO player_types (playerName, type) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (Player player : players) {
                stmt.setString(1, player.getName());
                stmt.setString(2, type);
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Player> loadPlayersByType(String type) {
        Set<Player> players = new HashSet<>();
        String selectSQL = "SELECT playerName FROM player_types WHERE type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, type);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String playerName = resultSet.getString("playerName");
                Player player = plugin.getServer().getPlayer(playerName);
                if (player != null) {
                    players.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public Set<Player> loadPlayers() {
        Set<Player> players = new HashSet<>();

        String query = "SELECT playerName, type FROM player_effects";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("playerName");
                String type = resultSet.getString("type");

                Player player = plugin.getServer().getPlayer(name);
                if (player != null) {
                    switch (type) {
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
//                        case "pale":
//                            types.palePlayers.add(player);
//                            break;
                    }

                    players.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public void close(){
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
