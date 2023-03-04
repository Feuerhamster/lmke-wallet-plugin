package dev.lmke.mc.wallet.utils;

import dev.lmke.mc.wallet.LMKEWallet;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class MsgLocaleManager {
    private static final Plugin plugin = LMKEWallet.getPlugin(LMKEWallet.class);
    private static FileConfiguration content;

    private static FileConfiguration fallbackContent;

    private static final String[] locales = { "de", "en" };

    public static void setup() {
        for (String locale : locales) {

            File f = new File(plugin.getDataFolder().getAbsolutePath(), locale + ".messages.yml");

            if (f.exists()) continue;

            InputStream stream = plugin.getResource(locale + ".messages.yml");

            if (stream != null) {
                try {
                    Files.copy(stream, f.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void loadMessageFile(String locale) {
        File file = new File(plugin.getDataFolder().getAbsolutePath(), locale + ".messages.yml");
        content = YamlConfiguration.loadConfiguration(file);

        InputStreamReader fallback = new InputStreamReader(plugin.getResource(locale + ".messages.yml"), StandardCharsets.UTF_8);
        fallbackContent = YamlConfiguration.loadConfiguration(fallback);
    }

    public static FileConfiguration getConfig() {
        return content;
    }

    public static String getChatText(String key) {
        return content.getString("common.prefix") + getTextRaw(key);
    }

    public static String getChatText(String key, Object ...args) {
        return content.getString("common.prefix") + String.format(getTextRaw(key), args);
    }

    public static String getTextRaw(String key) {
        String text = content.getString(key);

        if (text == null) {
            return fallbackContent.getString(key);
        }

        return text;
    }

    public static List<String> getTextListRaw(String key) {
        List<String> text = content.getStringList(key);

        if (text.isEmpty()) {
            return fallbackContent.getStringList(key);
        }

        return text;
    }
}
