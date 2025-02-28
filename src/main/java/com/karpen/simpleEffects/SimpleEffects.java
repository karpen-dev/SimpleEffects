package com.karpen.simpleEffects;

import com.karpen.simpleEffects.commands.Eff;
import com.karpen.simpleEffects.commands.EffReload;
import com.karpen.simpleEffects.listeners.MainListener;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.services.Effects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleEffects extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    Eff eff;
    EffReload effReload;
    Effects effects;
    Config config;

    @Override
    public void onEnable() {
        effects = new Effects(this);
        config = new Config();
        eff = new Eff(config, effects);
        effReload = new EffReload(this);

        loadConfig();

        if (getCommand("eff") != null) {
            getCommand("eff-reload").setExecutor(effReload);
            getCommand("eff").setExecutor(eff);
            getCommand("eff").setTabCompleter(new com.karpen.simpleEffects.services.TabCompleter());
        } else {
            getLogger().warning("Command 'eff' is not registered in plugin.yml");
        }

        Bukkit.getPluginManager().registerEvents(new MainListener(effects), this);
        getLogger().info("SimpleEffects by karpen");
    }

    public void loadConfig() {
        saveDefaultConfig();
        FileConfiguration configuration = getConfig();

        config.setCountCherry(configuration.getInt("count-cherry", 0));
        config.setCountEndrod(configuration.getInt("count-endrod", 0));
        config.setCountTotem(configuration.getInt("count-totem", 0));
        config.setCountHeart(configuration.getInt("count-heart", 0));

        String lang = configuration.getString("lang", "en");
        switch (lang.toLowerCase()) {
            case "en":
                config.setMsgCherry(configuration.getString("selected-cherry", "Cherry effect enabled"));
                config.setMsgEndRod(configuration.getString("selected-endRod", "Endrod effect enabled"));
                config.setMsgTotem(configuration.getString("selected-totem", "Totem effect enabled"));
                config.setMsgHeart(configuration.getString("selected-heart", "Heart effect enabled"));

                config.setMsgDisCherry(configuration.getString("disable-cherry", "Cherry effect disabled"));
                config.setMsgDisEndRod(configuration.getString("disable-endRod", "Endrod effect disabled"));
                config.setMsgDisTotem(configuration.getString("disable-totem", "Totem effect disabled"));
                config.setMsgDisHeart(configuration.getString("disable-heart", "Heart effect disabled"));

                config.setErrConsole(configuration.getString("err-console", "You can't send this command"));
                config.setErrArgs(configuration.getString("err-args", "Using /eff <cherry | endrod | totem | heart>"));
                config.setErrCommand(configuration.getString("err-command", "Invalid command. Use /eff <cherry | endrod | totem | heart>"));
                break;

            case "ru":
                config.setMsgCherry(configuration.getString("selected-cherry-ru", "Эффект вишни активирован"));
                config.setMsgEndRod(configuration.getString("selected-endRod-ru", "Эффект энд род активирован"));
                config.setMsgTotem(configuration.getString("selected-totem-ru", "Эффект тотема активирован"));
                config.setMsgHeart(configuration.getString("selected-heart-ru", "Эффект сердец активирован"));

                config.setMsgDisCherry(configuration.getString("disable-cherry-ru", "Эффект вишни выключен"));
                config.setMsgDisEndRod(configuration.getString("disable-endRod-ru", "Эффект энд род выключен"));
                config.setMsgDisTotem(configuration.getString("disable-totem-ru", "Эффект тотем выключен"));
                config.setMsgDisHeart(configuration.getString("disable-heart-ru", "Эффект сердец выключен"));

                config.setErrConsole(configuration.getString("err-console-ru", "Эту команду может отправлять только игрок"));
                config.setErrArgs(configuration.getString("err-args-ru", "Используйте /eff <cherry | endrod | totem | heart>"));
                config.setErrCommand(configuration.getString("err-command-ru", "Неизвестная команда. Используйте /eff <cherry | endrod | totem | heart>"));
                break;

            default:
                getLogger().info(ChatColor.RED + "Language " + lang + " not found in plugin base. Defaulting to English.");
                loadDefaultEnglishMessages();
                break;
        }
    }

    private void loadDefaultEnglishMessages() {
        config.setMsgCherry("Default Cherry Message");
        config.setMsgEndRod("Default End Rod Message");
        config.setMsgTotem("Default Totem Message");
        config.setMsgHeart("Default Heart Message");

        config.setMsgDisCherry("Default Disable Cherry Message");
        config.setMsgDisEndRod("Default Disable End Rod Message");
        config.setMsgDisTotem("Default Disable Totem Message");
        config.setMsgDisHeart("Default Disable Heart Message");

        config.setErrConsole("Default Console Error");
        config.setErrArgs("Default Arguments Error");
        config.setErrCommand("Default Command Error");
    }

    public Config getConfigObject() {
        return config;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
