package com.karpen.simpleEffects;

import com.karpen.simpleEffects.api.SimpleEffectImpl;
import com.karpen.simpleEffects.api.SimpleEffectsApi;
import com.karpen.simpleEffects.commands.Eff;
import com.karpen.simpleEffects.commands.EffReload;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.listeners.MainListener;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.services.Effects;
import com.karpen.simpleEffects.services.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleEffects extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    private Eff eff;
    private EffReload effReload;
    private Effects effects;
    private Config config;
    private DBManager dbManager;
    private Types types;
    private FileManager manager;
    private volatile SimpleEffectsApi api;

    public static SimpleEffects instance;

    @Override
    public void onEnable() {
        instance = this;

        config = new Config();

        loadConfig();

        types = new Types();

        dbManager = new DBManager(config, this, types);
        manager = new FileManager(this, types);
        effects = new Effects(this, config, manager, dbManager);
        eff = new Eff(config, effects, types);
        effReload = new EffReload(this);

        this.api = new SimpleEffectImpl(this, types);

        if (getCommand("eff") != null) {
            getCommand("eff-reload").setExecutor(effReload);
            getCommand("eff").setExecutor(eff);
            getCommand("eff").setTabCompleter(new com.karpen.simpleEffects.services.TabCompleter());
        } else {
            getLogger().warning("Command 'eff' is not registered in plugin.yml");
        }

        Bukkit.getPluginManager().registerEvents(new MainListener(config, dbManager, types, manager, effects), this);
        getLogger().info("SimpleEffects by karpen");
    }

    public void loadConfig() {
        saveDefaultConfig();
        Configuration configuration = getConfig();

        config.setMethod(configuration.getString("method", "TXT"));
        config.setDbUrl(configuration.getString("db.url"));
        config.setDbUser(configuration.getString("db.username"));
        config.setDbPassword(configuration.getString("db.password"));

        config.setCountCherry(configuration.getInt("count.cherry", 1));
        config.setCountEndrod(configuration.getInt("count.endrod", 1));
        config.setCountTotem(configuration.getInt("count.totem", 1));
        config.setCountHeart(configuration.getInt("count.heart", 1));
        config.setCountPale(configuration.getInt("count.pale", 1));
        config.setCountPurple(configuration.getInt("count.purple", 1));
        config.setCountNotes(configuration.getInt("count.notes", 1));

        config.setRightsUsing(configuration.getBoolean("rights.using"));
        config.setRightsCherry(configuration.getString("rights.cherry", "karpen.simpleeffects.cherry"));
        config.setRightsEndRod(configuration.getString("rights.endrod", "karpen.simpleeffects.endrod"));
        config.setRightsTotem(configuration.getString("rights.totem", "karpen.simpleeffects.totem"));
        config.setRightsHeart(configuration.getString("rights.heart", "karpen.simpleeffects.heart"));
        config.setRightsPale(configuration.getString("rights.pale", "karpen.simpleeffects.pale"));
        config.setRightsPurple(configuration.getString("rights.purple", "karpen.simpleeffects.purple"));
        config.setRightsNotes(configuration.getString("rights.notes", "karpen.simpleeffects.notes"));


        switch (configuration.getString("lang", "en").toLowerCase()){
            case "en":
                config.setMsgEnable(configuration.getString("en.enable-effect", "Effect enabled"));
                config.setMsgDisable(configuration.getString("en.disable-effect", "Effect disabled"));
                config.setErrConsole(configuration.getString("en.err-console", "You can't send this command"));
                config.setErrPerms(configuration.getString("en.err-perms", "You can't have permission for using this"));
                config.setErrArgs(configuration.getString("en.err-args", "Using /eff <cherry | endrod | totem | heart | pale>"));
                config.setErrCommand(configuration.getString("en.err-command", "Invalid command. Use /eff <cherry | endrod | totem | heart | pale>"));

                break;
            case "ru":
                config.setMsgEnable(configuration.getString("ru.enable-effect", "Эффект включен"));
                config.setMsgDisable(configuration.getString("ru.disable-effect", "Эффект выключен"));
                config.setErrConsole(configuration.getString("ru.err-console", "Эту команду может отправлять только игрок"));
                config.setErrPerms(configuration.getString("ru.err-perms", "Вы не имейте прав для использования этой команды"));
                config.setErrArgs(configuration.getString("ru.err-args", "Используйте /eff <cherry | endrod | totem | heart>"));
                config.setErrCommand(configuration.getString("ru.err-command", "Неизвестная команда. Используйте /eff <cherry | endrod | totem | heart | pale>"));

                break;
        }

    }

    public Config getConfigObject() {
        return config;
    }

    public static SimpleEffectsApi getApi() {
        SimpleEffects instance = SimpleEffects.instance;
        if (instance == null) {
            throw new IllegalStateException("SimpleEffects not initialized");
        }

        if (instance.api == null) {
            synchronized (SimpleEffects.class) {
                if (instance.api == null) {
                    instance.api = new SimpleEffectImpl(instance, instance.types);
                }
            }
        }
        return instance.api;
    }

    @Override
    public void onDisable() {
        if (config.getMethod().equals("MYSQL")){
            getLogger().info("Closing db connection");
            dbManager.close();
        }
    }
}
