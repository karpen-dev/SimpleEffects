package com.karpen.simpleEffects.database;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.PlayerTypeEntity;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import jakarta.persistence.OptimisticLockException;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DBManager {

    private final Config config;
    private final JavaPlugin plugin;

    @Setter
    private SessionFactory sessionFactory;

    public DBManager(Config config, JavaPlugin plugin){
        this.config = config;
        this.plugin = plugin;

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
            
            System.out.println("Database connected");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlayer(Player player) {
        if (player == null || player.getUniqueId() == null) {
            throw new IllegalArgumentException("Player or UUID cannot be null");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createQuery(
                        "DELETE FROM PlayerTypeEntity WHERE playerUUid = :uuid");
                query.setParameter("uuid", player.getUniqueId().toString());

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Failed to remove player: " + player.getUniqueId(), e);
            }
        }
    }

    public void savePlayers(Map<UUID, Type> playerTypeMap) {
        int retryCount = 0;
        while (retryCount < 3) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                try {
                    for (Map.Entry<UUID, Type> entry : playerTypeMap.entrySet()) {
                        PlayerTypeEntity entity = session.get(PlayerTypeEntity.class, entry.getKey().toString());
                        if (entity == null) {
                            entity = new PlayerTypeEntity();
                            entity.setPlayerUUid(entry.getKey().toString());
                        }
                        entity.setType(entry.getValue().name());
                        session.saveOrUpdate(entity);
                    }
                    transaction.commit();
                    return;
                } catch (OptimisticLockException e) {
                    if (transaction != null && transaction.isActive()) {
                        transaction.rollback();
                    }
                    retryCount++;
                    if (retryCount >= 3) {
                        throw new RuntimeException("Failed to save players after 3 retries", e);
                    }
                } catch (Exception e) {
                    if (transaction != null && transaction.isActive()) {
                        transaction.rollback();
                    }
                    throw new RuntimeException("Failed to save players", e);
                }
            }
        }
    }

    public Map<UUID, Type> loadPlayers() {
        Map<UUID, Type> playerTypeMap = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            Query<PlayerTypeEntity> query = session.createQuery("FROM PlayerTypeEntity", PlayerTypeEntity.class);
            for (PlayerTypeEntity entity : query.list()) {
                UUID uuid = UUID.fromString(entity.getPlayerUUid());
                Type type = Type.valueOf(entity.getType().toUpperCase());
                playerTypeMap.put(uuid, type);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to parse player data", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load players", e);
        }
        return playerTypeMap;
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

}
