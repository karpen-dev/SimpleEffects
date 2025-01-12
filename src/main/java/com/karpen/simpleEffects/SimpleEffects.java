package com.karpen.simpleEffects;

import com.karpen.simpleEffects.commands.Eff;
import com.karpen.simpleEffects.listeners.MainListener;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleEffects extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    Eff eff;
    private Config config = new Config();

    @Override
    public void onEnable() {

        getCommand("eff").setExecutor(eff);
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);

        loadConfig();

        getLogger().info("SimpleEffects by karpen");
    }

    private void loadConfig(){
        saveDefaultConfig();
        FileConfiguration configuration = getConfig();

        config.setCount(configuration.getInt("count"));
        config.setMsgCherry(configuration.getString("selected-cherry"));
        config.setErrArgs(configuration.getString("err-args"));
        config.setErrCommand(configuration.getString("err-command"));
        config.setErrConsole(configuration.getString("err-console"));
        config.setMsgDisCherry(configuration.getString("disable-cherry"));
        config.setMsgDisEndRod(configuration.getString("disable-endRod"));
        config.setMsgDisTotem(configuration.getString("disable-totem"));
        config.setMsgTotem(configuration.getString("selected-totem"));
        config.setMsgEndRod(configuration.getString("selected-endRod"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
