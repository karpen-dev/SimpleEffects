package com.karpen.simpleEffects.database;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.PlayerTypeEntity;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlayer(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "DELETE FROM PlayerTypeEntity WHERE playerName = :playerName");
            query.setParameter("playerName", player.getName());
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePlayers(Map<Player, Type> playerTypeMap) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (Player player : playerTypeMap.keySet()) {
                removePlayer(player);
            }

            for (Map.Entry<Player, Type> entry : playerTypeMap.entrySet()) {
                PlayerTypeEntity entity = new PlayerTypeEntity();
                entity.setPlayerName(entry.getKey().getName());
                entity.setType(entry.getValue().name());
                session.save(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Player, Type> loadPlayers() {
        Map<Player, Type> playerTypeMap = new HashMap<>();

        try (Session session = sessionFactory.openSession()) {
            Query<PlayerTypeEntity> query = session.createQuery(
                    "FROM PlayerTypeEntity", PlayerTypeEntity.class);

            for (PlayerTypeEntity entity : query.list()) {
                Player player = plugin.getServer().getPlayer(entity.getPlayerName());
                if (player != null) {
                    Type type = Type.valueOf(entity.getType().toUpperCase());
                    playerTypeMap.put(player, type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerTypeMap;
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

}
