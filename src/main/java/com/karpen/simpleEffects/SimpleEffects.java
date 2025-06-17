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
import com.karpen.simpleEffects.utils.EffectAppler;
import com.karpen.simpleEffects.utils.Effects;
import com.karpen.simpleEffects.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    private SimpleEffectImpl simpleEffectImpl;
    private volatile SimpleEffectsApi api;

    private Map<Player, Inventory> playerInventors = new HashMap<>();

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

        dbManager = new DBManager(config, this);
        manager = new FileManager(this);
        effects = new Effects(this, config, manager, dbManager, types);

        new EffectAppler(config, effects, types, playerInventors);

        effectMenu = new SelectEffectMenu(config, types, playerInventors);
        eff = new Eff(config, effectMenu);
        effReload = new EffReload(this);
        checkVersion = new CheckVersion(this);

        this.simpleEffectImpl = new SimpleEffectImpl(this, types, config, manager, dbManager);
        this.api = this.simpleEffectImpl;

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

        getLogger().info("Instance initialized: " + (instance != null));
    }

    public void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        Configuration configuration = getConfig();

        String[] ver = getDescription().getVersion().split("1.");
        double currentVersion = Double.parseDouble(ver[1]);
        double configVersion = configuration.getDouble("config-version", currentVersion);

        if (configVersion < currentVersion) {
            File configFile = new File(getDataFolder(), "config.yml");
            File oldConfig = new File(getDataFolder(), "config_old_" + configVersion + ".yml");
            configFile.renameTo(oldConfig);

            saveDefaultConfig();
            reloadConfig();

            configuration = getConfig();
            configuration.set("config-version", currentVersion);
            saveConfig();

            getLogger().info("Config successful updated to " + getDescription().getVersion() + " version");
        }

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
                config.setMsgEnable(configuration.getString("en.enable-effect", "&aEffect enabled"));
                config.setMsgDisable(configuration.getString("en.disable-effect", "&aEffect disabled"));
                config.setErrConsole(configuration.getString("en.err-console", "&cYou can't send this command"));
                config.setErrPerms(configuration.getString("en.err-perms", "&cYou can't have permission for using this"));
                config.setErrArgs(configuration.getString("en.err-args", "&cUsing /eff <cherry | endrod | totem | heart | pale>"));
                config.setErrCommand(configuration.getString("en.err-command", "&cInvalid command. Use /eff <cherry | endrod | totem | heart | pale>"));

                config.setNotAvailableMsg(configuration.getString("en.unsupported-ver", "&cYou are using a version lower than 1.21.4, the some feature not available"));
                config.setUnsupportedName(configuration.getString("en.unsupported-name", "&cDon't supported to you version"));

                config.setMenuName(configuration.getString("en.menu-name", "Select effect"));
                config.setItemsEnable(configuration.getString("en.item-enable", "&aEnable this effect"));
                config.setItemsDisable(configuration.getString("en.item-disable", "&cDisable this effect"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("en.warning", "&cWhile using this effect you may see flashes and blurry images."));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("en.cherry", "&dCherry"));
                config.setItemEndRodName(configuration.getString("en.endrod", "&fEndrod"));
                config.setItemTotemName(configuration.getString("en.totem", "&eTotem"));
                config.setItemHeartName(configuration.getString("en.heart", "&cHeart"));
                config.setItemPaleName(configuration.getString("en.pale", "&8Pale"));
                config.setItemPurpleName(configuration.getString("en.purple", "&5Purple"));
                config.setItemNotesName(configuration.getString("en.notes", "&3Notes"));
                config.setItemCloudName(configuration.getString("en.cloud", "&7Cloud"));

                break;
            case "ru":
                config.setMsgEnable(configuration.getString("ru.enable-effect", "&aЭффект включен"));
                config.setMsgDisable(configuration.getString("ru.disable-effect", "&aЭффект выключен"));
                config.setErrConsole(configuration.getString("ru.err-console", "&cЭту команду может отправлять только игрок"));
                config.setErrPerms(configuration.getString("ru.err-perms", "&cВы не имейте прав для использования этой команды"));
                config.setErrArgs(configuration.getString("ru.err-args", "&cИспользуйте /eff <cherry | endrod | totem | heart>"));
                config.setErrCommand(configuration.getString("ru.err-command", "&cНеизвестная команда. Используйте /eff <cherry | endrod | totem | heart | pale>"));

                config.setNotAvailableMsg(configuration.getString("ru.unsupported-ver", "&cВы используйте версию сервера ниже 1.21.4, эта функция недоступна"));
                config.setUnsupportedName(configuration.getString("ru.unsupported-name", "&cНедоступно на вашей версии"));

                config.setMenuName(configuration.getString("ru.menu-name", "Выбрать эффект"));
                config.setItemsEnable(configuration.getString("ru.item-enable", "&aВключить этот эффект"));
                config.setItemsDisable(configuration.getString("ru.item-disable", "&cВыключить этот эффект"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("ru.warning", "&cПри использовании этого эффекта вы можете увидеть вспышки и размытие изображения."));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("ru.cherry", "&dCherry"));
                config.setItemEndRodName(configuration.getString("ru.endrod", "&fEndrod"));
                config.setItemTotemName(configuration.getString("ru.totem", "&eTotem"));
                config.setItemHeartName(configuration.getString("ru.heart", "&cHeart"));
                config.setItemPaleName(configuration.getString("ru.pale", "&8Pale"));
                config.setItemPurpleName(configuration.getString("ru.purple", "&5Purple"));
                config.setItemNotesName(configuration.getString("ru.notes", "&3Notes"));
                config.setItemCloudName(configuration.getString("ru.cloud", "&7Cloud"));

                break;
            case "jp":
                config.setMsgEnable(configuration.getString("jp.enable-effect", "&aエフェクト有効"));
                config.setMsgDisable(configuration.getString("jp.disable-effect", "&a効果は無効です"));
                config.setErrConsole(configuration.getString("jp.err-console", "&cこのコマンドは送信できません"));
                config.setErrPerms(configuration.getString("jp.err-perms", "&cこれを使用する権限がありません"));
                config.setErrArgs(configuration.getString("jp.err-args", "&c/eff を使用する <cherry | endrod | totem | heart | pale | purple | note | cloud>"));
                config.setErrCommand(configuration.getString("jp.err-command", "&c無効なコマンドです。/eff <cherry | endrod | totem | heart | pale | purple | note | cloud> を使用してください。"));

                config.setNotAvailableMsg(configuration.getString("jp.unsupported-ver", "&c1.21.4より前のバージョンを使用しています。一部の機能はご利用いただけません。"));
                config.setUnsupportedName(configuration.getString("jp.unsupported-name", "&cあなたのバージョンではサポートされていません"));

                config.setMenuName(configuration.getString("jp.menu-name", "エフェクトを選択"));
                config.setItemsEnable(configuration.getString("jp.item-enable", "&aこの効果を有効にする"));
                config.setItemsDisable(configuration.getString("jp.item-disable", "&cこの効果を無効にする"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("jp.warning", "&cこの効果を使用すると、フラッシュやぼやけた画像が見える場合があります。"));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("jp.cherry", "&dCherry"));
                config.setItemEndRodName(configuration.getString("jp.endrod", "&fEndrod"));
                config.setItemTotemName(configuration.getString("jp.totem", "&eTotem"));
                config.setItemHeartName(configuration.getString("jp.heart", "&cHeart"));
                config.setItemPaleName(configuration.getString("jp.pale", "&8Pale"));
                config.setItemPurpleName(configuration.getString("jp.purple", "&5Purple"));
                config.setItemNotesName(configuration.getString("jp.notes", "&3Notes"));
                config.setItemCloudName(configuration.getString("jp.cloud", "&7Cloud"));

                break;
            case "be":
                config.setMsgEnable(configuration.getString("be.enable-effect", "&aЭфект уключаны"));
                config.setMsgDisable(configuration.getString("be.disable-effect", "&aЭфект выключаны"));
                config.setErrConsole(configuration.getString("be.err-console", "&cГэту каманду можа адпрауляць толькі ігрок"));
                config.setErrPerms(configuration.getString("be.err-perms", "&cВы не маеце прау для выкарыставання гэтай каманды"));
                config.setErrArgs(configuration.getString("be.err-args", "&cВыкарыстовывайце /eff <cherry | endrod | totem | heart | pale | purple | note | cloud>"));
                config.setErrCommand(configuration.getString("be.err-command", "&cНевядомая каманда. Используйте /eff <cherry | endrod | totem | heart | pale | purple | note | cloud>"));

                config.setNotAvailableMsg(configuration.getString("be.unsupported-ver", "&cВы карыстаецеся версію сервера ніжэй 1.21.4, гэтая функцыя недаступная"));
                config.setUnsupportedName(configuration.getString("be.unsupported-name", "&cНедаступна на вашай версіі"));

                config.setMenuName(configuration.getString("be.menu-name", "Выбраць эфект"));
                config.setItemsEnable(configuration.getString("be.item-enable", "&aУключыць гэты эфект"));
                config.setItemsDisable(configuration.getString("be.item-disable", "&cВыключыць гэты эфект"));

                if (configuration.getBoolean("warning")){
                    config.setWarning(configuration.getString("be.warning", "&cПры выкарыстанні гэтага эфекту вы можаце выбліскі і размыццё малюнка."));
                } else {
                    config.setWarning(null);
                }

                config.setItemCherryName(configuration.getString("be.cherry", "&dCherry"));
                config.setItemEndRodName(configuration.getString("be.endrod", "&fEndrod"));
                config.setItemTotemName(configuration.getString("be.totem", "&eTotem"));
                config.setItemHeartName(configuration.getString("be.heart", "&cHeart"));
                config.setItemPaleName(configuration.getString("be.pale", "&8Pale"));
                config.setItemPurpleName(configuration.getString("be.purple", "&5Purple"));
                config.setItemNotesName(configuration.getString("be.notes", "&3Notes"));
                config.setItemCloudName(configuration.getString("be.cloud", "&7Cloud"));

                break;
        }
    }

    public static SimpleEffectsApi getApi() {
        try {
            SimpleEffects instance = SimpleEffects.instance;
            if (instance == null) {
                throw new IllegalStateException("SimpleEffects not initialized");
            }

            if (instance.api == null) {
                synchronized (SimpleEffects.class) {
                    if (instance.api == null) {
                        instance.api = new SimpleEffectImpl(instance, instance.types, instance.config, instance.manager, instance.dbManager);
                    }
                }
            }
            return instance.api;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
