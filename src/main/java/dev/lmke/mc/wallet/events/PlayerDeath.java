package dev.lmke.mc.wallet.events;

import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        if (!ConfigManager.getConfig().getBoolean("clear_wallet_on_death")) {
            return;
        }

        Player p = e.getEntity();

        WalletObject w = DAL.getWalletByPlayerId(p.getUniqueId());

        w.balance = 0;

        DAL.updateWallet(w);
    }
}
