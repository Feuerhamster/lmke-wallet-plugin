package dev.lmke.mc.wallet;

import dev.lmke.mc.wallet.commands.WalletCommand;
import dev.lmke.mc.wallet.database.Database;
import dev.lmke.mc.wallet.economy.EconomyImpl;
import dev.lmke.mc.wallet.events.PlayerJoin;
import dev.lmke.mc.wallet.utils.MsgLocaleManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class LMKEWallet extends JavaPlugin {

    private static EconomyImpl economy = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        MsgLocaleManager.setup();
        MsgLocaleManager.loadMessageFile(getConfig().getString("locale"));

        Database.init();

        if (!setupEconomy()) {
            getLogger().severe("Vault not found!");
        }

        getCommand("wallet").setExecutor(new WalletCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        getLogger().info("Plugin loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Database.close();
        getLogger().info("Plugin disabled");
    }

    private boolean setupEconomy() {
        economy = new EconomyImpl();
        getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.High);
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }
}
