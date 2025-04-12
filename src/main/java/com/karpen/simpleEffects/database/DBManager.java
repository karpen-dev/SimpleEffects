package com.karpen.simpleEffects.database;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.PlayerTypeEntity;
import com.karpen.simpleEffects.model.Types;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Set;

public class DBManager {

    private final Config config;
    private final JavaPlugin plugin;
    private final Types types;

    @Setter
    private SessionFactory sessionFactory;

    public DBManager(Config config, JavaPlugin plugin, Types types){
        this.config = config;
        this.plugin = plugin;
        this.types = types;

        if (config.getMethod().equalsIgnoreCase("MYSQL")){
            configureHibernate();
        }
    }

    private void configureHibernate() {
        try {
            Configuration hibernateConfig = new Configuration();

            hibernateConfig.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            hibernateConfig.setProperty("hibernate.connection.url", config.getDbUrl());
            hibernateConfig.setProperty("hibernate.connection.username", config.getDbUser());
            hibernateConfig.setProperty("hibernate.connection.password", config.getDbPassword());

            hibernateConfig.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            hibernateConfig.setProperty("hibernate.hbm2ddl.auto", "update");
            hibernateConfig.setProperty("hibernate.show_sql", "true");

            hibernateConfig.setProperty("hibernate.show_sql", "false");
            hibernateConfig.setProperty("hibernate.format_sql", "false");
            hibernateConfig.setProperty("hibernate.use_sql_comments", "false");

            hibernateConfig.addAnnotatedClass(PlayerTypeEntity.class);

            sessionFactory = hibernateConfig.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlayer(Player player){
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query<?> query = session.createQuery("DELETE FROM PlayerTypeEntity WHERE playerName = :name");
            query.setParameter("name", player.getName());
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void savePlayers(Set<Player> players, String type){
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (Player player : players){
                removePlayer(player);

                PlayerTypeEntity entity = new PlayerTypeEntity();
                entity.setPlayerName(player.getName());
                entity.setType(type);
                session.save(entity);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Set<Player> loadPlayersByType(String type) {
        Set<Player> players = new HashSet<>();

        try (Session session = sessionFactory.openSession()) {
            Query<PlayerTypeEntity> query = session.createQuery(
                    "FROM PlayerTypeEntity WHERE type = :type", PlayerTypeEntity.class);
            query.setParameter("type", type);

            for (PlayerTypeEntity entity : query.list()) {
                Player player = plugin.getServer().getPlayer(entity.getPlayerName());
                if (player != null) {
                    players.add(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    public Set<Player> loadPlayers() {
        Set<Player> players = new HashSet<>();

        try (Session session = sessionFactory.openSession()) {
            Query<PlayerTypeEntity> query = session.createQuery(
                    "FROM PlayerTypeEntity", PlayerTypeEntity.class);

            for (PlayerTypeEntity entity : query.list()) {
                Player player = plugin.getServer().getPlayer(entity.getPlayerName());
                if (player != null) {
                    switch (entity.getType()) {
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
                        case "pale":
                            types.palePlayers.add(player);
                            break;
                        case "purple":
                            types.purplePlayers.add(player);
                            break;
                        case "note":
                            types.notePlayers.add(player);
                            break;
                        case "cloud":
                            types.cloudPlayers.add(player);
                            break;
                    }
                    players.add(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

}
