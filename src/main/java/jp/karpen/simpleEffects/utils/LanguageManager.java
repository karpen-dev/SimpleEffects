package jp.karpen.simpleEffects.utils;

import jp.karpen.simpleEffects.SimpleEffects;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    private final SimpleEffects plugin;
    private final Map<String, FileConfiguration> languages = new HashMap<>();
    private FileConfiguration currentLanguage;
    private final String[] resourceLangs = {
            "en_US.yml", "ja_JP.yml"
    };

    public LanguageManager(SimpleEffects plugin) {
        this.plugin = plugin;
        loadLanguages();
    }

    public void loadLanguages() {
        File langDir = new File(plugin.getDataFolder(), "langs");
        if (!langDir.exists()) {
            langDir.mkdirs();
        }

        for (String langFileName : resourceLangs) {
            File langFile = new File(langDir, langFileName);
            if (!langFile.exists()) {
                plugin.saveResource("langs/" + langFileName, false);
            }
        }

        languages.clear();
        File[] files = langDir.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                String langName = file.getName().replace(".yml", "");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                InputStream defStream = plugin.getResource("langs/" + file.getName());
                if (defStream != null) {
                    config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defStream, StandardCharsets.UTF_8)));
                }
                
                languages.put(langName, config);
            }
        }

        setLanguage(plugin.getConfig().getString("lang", "en_US"));
    }

    public void setLanguage(String langName) {
        currentLanguage = languages.get(langName);
        if (currentLanguage == null) {
            currentLanguage = languages.get("en_US");
            if (currentLanguage == null && !languages.isEmpty()) {
                currentLanguage = languages.values().iterator().next();
            }
        }
    }

    public String getMessage(String path) {
        if (currentLanguage == null) return "Missing Lang: " + path;
        String message = currentLanguage.getString(path);
        if (message == null) return "Missing Key: " + path;
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
