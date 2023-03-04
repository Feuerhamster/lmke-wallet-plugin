package dev.lmke.mc.wallet.utils;

import dev.lmke.mc.wallet.LMKEWallet;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class LogManager {
    private static final Plugin plugin = LMKEWallet.getPlugin(LMKEWallet.class);

    public static Logger getLogger() {
        return plugin.getLogger();
    }
}
