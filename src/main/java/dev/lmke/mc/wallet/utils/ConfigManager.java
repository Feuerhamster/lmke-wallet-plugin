package dev.lmke.mc.wallet.utils;

import dev.lmke.mc.wallet.LMKEWallet;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
    private static final Plugin plugin = LMKEWallet.getPlugin(LMKEWallet.class);

    public static FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
