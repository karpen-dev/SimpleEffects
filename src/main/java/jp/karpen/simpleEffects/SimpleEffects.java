package jp.karpen.simpleEffects;

import jp.karpen.simpleEffects.api.SimpleEffectImpl;
import jp.karpen.simpleEffects.api.SimpleEffectsApi;
import jp.karpen.simpleEffects.commands.Eff;
import jp.karpen.simpleEffects.commands.EffReload;
import jp.karpen.simpleEffects.listeners.base.*;
import jp.karpen.simpleEffects.listeners.spiral.*;
import jp.karpen.simpleEffects.listeners.base.*;
import jp.karpen.simpleEffects.listeners.spiral.*;
import jp.karpen.simpleEffects.menus.SelectEffectMenu;
import jp.karpen.simpleEffects.utils.CheckVersion;
import jp.karpen.simpleEffects.utils.FileManager;
import jp.karpen.simpleEffects.utils.LanguageManager;
import jp.karpen.simpleEffects.utils.TabCompleter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class SimpleEffects extends JavaPlugin {

    public static String PLUGIN_LANGUAGE = "en";

    @Getter
    private static Configuration configuration;

    @Getter
    private static LanguageManager languageManager;

    @Getter
    private static SimpleEffectsApi api;

    @Getter private CherryListener cherryListener;
    @Getter private EndrodListener endrodListener;
    @Getter private HeartListener heartListener;
    @Getter private NoteListener noteListener;
    @Getter private PaleListener paleListener;
    @Getter private PurpleListener purpleListener;
    @Getter private TotemListener totemListener;

    @Getter private CherrySpiralListener cherrySpiralListener;
    @Getter private EndrodSpiralListener endrodSpiralListener;
    @Getter private NoteSpiralListener noteSpiralListener;
    @Getter private PaleSpiralListener paleSpiralListener;
    @Getter private PurpleSpiralListener purpleSpiralListener;
    @Getter private TotemSpiralListener totemSpiralListener;

    @Getter private Map<Player, Inventory> playerInventors = new HashMap<>();

    @Getter
    public static SimpleEffects instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        configuration = getConfig();
        languageManager = new LanguageManager(this);
        PLUGIN_LANGUAGE = configuration.getString("lang");

        FileManager manager = new FileManager(this);

        // base effects
        this.cherryListener = new CherryListener(manager);
        this.endrodListener = new EndrodListener(manager);
        this.heartListener = new HeartListener(manager);
        this.noteListener = new NoteListener(manager);
        this.paleListener = new PaleListener(manager);
        this.purpleListener = new PurpleListener(manager);
        this.totemListener = new TotemListener(manager);

        // spiral effects
        this.cherrySpiralListener = new CherrySpiralListener(manager);
        this.endrodSpiralListener = new EndrodSpiralListener(manager);
        this.noteSpiralListener = new NoteSpiralListener(manager);
        this.paleSpiralListener = new PaleSpiralListener(manager);
        this.purpleSpiralListener = new PurpleSpiralListener(manager);
        this.totemSpiralListener = new TotemSpiralListener(manager);

        registerListener(cherryListener);
        registerListener(endrodListener);
        registerListener(heartListener);
        registerListener(noteListener);
        registerListener(paleListener);
        registerListener(purpleListener);
        registerListener(totemListener);

        registerListener(cherrySpiralListener);
        registerListener(endrodSpiralListener);
        registerListener(noteSpiralListener);
        registerListener(paleSpiralListener);
        registerListener(purpleSpiralListener);
        registerListener(totemSpiralListener);

        SelectEffectMenu effectMenu = new SelectEffectMenu(this);
        registerListener(effectMenu);

        CheckVersion checkVersion = new CheckVersion(this);

        registerCommand("eff", new Eff(this, effectMenu), new TabCompleter());
        registerCommand("eff-reload", new EffReload(this));

        api = new SimpleEffectImpl(this);

        getLogger().info("SimpleEffects v" + getDescription().getVersion() + " by karpen");

        Configuration configuration = getConfig();
        if (configuration.getBoolean("update.using") && !checkVersion.isLatest()){
            getLogger().info(configuration.getString("update.msg"));
        }

        getLogger().info("Instance initialized: " + (instance != null));
    }

    private <L extends Listener> void registerListener(L listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    private <E extends CommandExecutor, T extends TabCompleter> void registerCommand(String name, E executor, T tabCompleter) {
        PluginCommand command = getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
            command.setTabCompleter(tabCompleter);
        }
    }

    private <E extends CommandExecutor> void registerCommand(String name, E executor) {
        PluginCommand command = getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
