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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
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
        FileConfiguration configuration = getConfig();

        config.setCountCherry(configuration.getInt("count-cherry", 0));
        config.setCountEndrod(configuration.getInt("count-endrod", 0));
        config.setCountTotem(configuration.getInt("count-totem", 0));
        config.setCountHeart(configuration.getInt("count-heart", 0));

        String method = configuration.getString("method", "TXT");
        switch (method.toLowerCase()){
            case "txt" -> config.setMethod("TXT");
            case "mysql" -> {
                config.setMethod("MYSQL");

                config.setDbUrl(configuration.getString("db.url", "localhost:3306/effects"));
                config.setDbUser(configuration.getString("db.username", "root"));
                config.setDbPassword(configuration.getString("db.password", "root"));
            }
            default -> config.setMethod("TXT");
        }

        String lang = configuration.getString("lang", "en");
        switch (lang.toLowerCase()) {
            case "en":
                config.setMsgEnable(configuration.getString("enable-effect", "Effect enabled"));
                config.setMsgDisable(configuration.getString("disable-effect", "Effect disabled"));

                config.setErrConsole(configuration.getString("err-console", "You can't send this command"));
                config.setErrArgs(configuration.getString("err-args", "Using /eff <cherry | endrod | totem | heart | pale>"));
                config.setErrCommand(configuration.getString("err-command", "Invalid command. Use /eff <cherry | endrod | totem | heart | pale>"));
                break;

            case "ru":
                config.setMsgEnable(configuration.getString("enable-effect-ru", "Эффект включен"));
                config.setMsgDisable(configuration.getString("disable-effect-ru", "Эффект выключен"));

                config.setErrConsole(configuration.getString("err-console-ru", "Эту команду может отправлять только игрок"));
                config.setErrArgs(configuration.getString("err-args-ru", "Используйте /eff <cherry | endrod | totem | heart | pale>"));
                config.setErrCommand(configuration.getString("err-command-ru", "Неизвестная команда. Используйте /eff <cherry | endrod | totem | heart | pale>"));
                break;

            default:
                getLogger().info(ChatColor.RED + "Language " + lang + " not found in plugin base. Defaulting to English.");
                loadDefaultEnglishMessages();
                break;
        }
    }

    private void loadDefaultEnglishMessages() {
        config.setMsgEnable("Effect enabled");
        config.setMsgDisable("Effect disabled");

        config.setErrConsole("You can't send this command");
        config.setErrArgs("Using /eff <cherry | endrod | totem | heart | pale>");
        config.setErrCommand("Invalid command. Use /eff <cherry | endrod | totem | heart | pale>");
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
