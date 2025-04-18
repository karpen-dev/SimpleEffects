package com.karpen.simpleEffects;

import com.karpen.simpleEffects.api.SimpleEffectImpl;
import com.karpen.simpleEffects.api.SimpleEffectsApi;
import com.karpen.simpleEffects.commands.Eff;
import com.karpen.simpleEffects.commands.EffReload;
import com.karpen.simpleEffects.database.DBManager;
import com.karpen.simpleEffects.listeners.MainListener;
import com.karpen.simpleEffects.menus.SelectEffectMenu;
import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.CheckVersion;
import com.karpen.simpleEffects.utils.Effects;
import com.karpen.simpleEffects.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleEffects extends JavaPlugin {

    private Eff eff;
    private EffReload effReload;
    private Effects effects;
    private Config config;
    private DBManager dbManager;
    private Types types;
    private FileManager manager;
    private SelectEffectMenu effectMenu;
    private CheckVersion checkVersion;
    private volatile SimpleEffectsApi api;

    public static SimpleEffects instance;

    @Override
    public void onEnable() {
        instance = this;

        config = new Config();

        loadConfig();

        String version = Bukkit.getVersion().split("-")[0];
        String[] parts = version.split("\\.");

        int major = Integer.parseInt(parts[1]);
        int minor = Integer.parseInt(parts[2]);

        if (!(major == 21 && (minor == 4 || minor == 5))){
            config.setOldVer(true);
        }

        types = new Types();

        dbManager = new DBManager(config, this, types);
        manager = new FileManager(this, types);
        effects = new Effects(this, config, manager, dbManager, types);
        effectMenu = new SelectEffectMenu(config, effects, types);
        eff = new Eff(config, effects, types, effectMenu);
        effReload = new EffReload(this);
        checkVersion = new CheckVersion(this);

        this.api = new SimpleEffectImpl(this, types);

        if (getCommand("eff") != null) {
            getCommand("eff-reload").setExecutor(effReload);
            getCommand("eff").setExecutor(eff);
            getCommand("eff").setTabCompleter(new com.karpen.simpleEffects.utils.TabCompleter(config));
        } else {
            getLogger().warning("Command 'eff' is not registered in plugin.yml");
        }

        Bukkit.getPluginManager().registerEvents(new MainListener(config, dbManager, types, manager, effects), this);
        Bukkit.getPluginManager().registerEvents(effectMenu, this);

        getLogger().info("SimpleEffects v" + getDescription().getVersion() + " by karpen");

        if (config.isOldVer()){
            getLogger().info("You are using a version lower than 1.21.4, the server may be unstable.");
            getLogger().info("Don't report issue.");
        }

        Configuration configuration = getConfig();
        if (configuration.getBoolean("update.using") && !checkVersion.isLatest()){
            getLogger().info(configuration.getString("update.msg"));
        }
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
        config.setRightsCloud(configuration.getString("rights.cloud", "karpen.simpleeffects.cloud"));


        switch (configuration.getString("lang", "en").toLowerCase()){
            case "en":
                config.setMsgEnable(configuration.getString("en.enable-effect", "Effect enabled"));
                config.setMsgDisable(configuration.getString("en.disable-effect", "Effect disabled"));
                config.setErrConsole(configuration.getString("en.err-console", "You can't send this command"));
                config.setErrPerms(configuration.getString("en.err-perms", "You can't have permission for using this"));
                config.setErrArgs(configuration.getString("en.err-args", "Using /eff <cherry | endrod | totem | heart | pale>"));
                config.setErrCommand(configuration.getString("en.err-command", "Invalid command. Use /eff <cherry | endrod | totem | heart | pale>"));

                config.setNotAvailableMsg(configuration.getString("en.unsupported-ver", "You are using a version lower than 1.21.4, the some feature not available"));
                config.setUnsupportedName(configuration.getString("en.unsupported-name", "Don't supported to you version"));

                config.setMenuName(configuration.getString("en.menu-name", "Select effect"));
                config.setItemsEnable(configuration.getString("en.item-enable", "Enable this effect"));
                config.setItemsDisable(configuration.getString("en.item-disable", "Disable this effect"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("en.warning", "While using this effect you may see flashes and blurry images."));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("en.cherry", "Cherry"));
                config.setItemEndRodName(configuration.getString("en.endrod", "Endrod"));
                config.setItemTotemName(configuration.getString("en.totem", "Totem"));
                config.setItemHeartName(configuration.getString("en.heart", "Heart"));
                config.setItemPaleName(configuration.getString("en.pale", "Pale"));
                config.setItemPurpleName(configuration.getString("en.purple", "Purple"));
                config.setItemNotesName(configuration.getString("en.notes", "Notes"));
                config.setItemCloudName(configuration.getString("en.cloud", "Cloud"));

                break;
            case "ru":
                config.setMsgEnable(configuration.getString("ru.enable-effect", "Эффект включен"));
                config.setMsgDisable(configuration.getString("ru.disable-effect", "Эффект выключен"));
                config.setErrConsole(configuration.getString("ru.err-console", "Эту команду может отправлять только игрок"));
                config.setErrPerms(configuration.getString("ru.err-perms", "Вы не имейте прав для использования этой команды"));
                config.setErrArgs(configuration.getString("ru.err-args", "Используйте /eff <cherry | endrod | totem | heart>"));
                config.setErrCommand(configuration.getString("ru.err-command", "Неизвестная команда. Используйте /eff <cherry | endrod | totem | heart | pale>"));

                config.setNotAvailableMsg(configuration.getString("ru.unsupported-ver", "Вы используйте версию сервера ниже 1.21.4, эта функция недоступна"));
                config.setUnsupportedName(configuration.getString("ru.unsupported-name", "Недоступно на вашей версии"));

                config.setMenuName(configuration.getString("ru.menu-name", "Выбрать эффект"));
                config.setItemsEnable(configuration.getString("ru.item-enable", "Включить этот эффект"));
                config.setItemsDisable(configuration.getString("ru.item-disable", "Выключить этот эффект"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("ru.warning", "При использовании этого эффекта вы можете увидеть вспышки и размытие изображения."));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("ru.cherry", "Cherry"));
                config.setItemEndRodName(configuration.getString("ru.endrod", "Endrod"));
                config.setItemTotemName(configuration.getString("ru.totem", "Totem"));
                config.setItemHeartName(configuration.getString("ru.heart", "Heart"));
                config.setItemPaleName(configuration.getString("ru.pale", "Pale"));
                config.setItemPurpleName(configuration.getString("ru.purple", "Purple"));
                config.setItemNotesName(configuration.getString("ru.notes", "Notes"));
                config.setItemCloudName(configuration.getString("ru.cloud", "Cloud"));

                break;
        }

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

    public boolean isOldVer(){
        return config.isOldVer();
    }

    @Override
    public void onDisable() {
        if (config.getMethod().equals("MYSQL")){
            getLogger().info("Closing db connection");
            dbManager.close();
        }
    }
}
