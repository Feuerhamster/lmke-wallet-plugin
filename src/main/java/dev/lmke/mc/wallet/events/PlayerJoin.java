package dev.lmke.mc.wallet.events;

import dev.lmke.mc.wallet.LMKEWallet;
import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.LogManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    Economy eco = LMKEWallet.getEconomy();

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!eco.hasAccount(p)) {
            eco.createPlayerAccount(p);
        } else {
            WalletObject w = DAL.getWalletByPlayerId(p.getUniqueId());

            if (!w.playerName.equals(p.getName())) {
                LogManager.getLogger().info(String.format("Player changed name. Changed \"%s\" to \"%s\"", w.playerName, p.getName()));

                w.playerName = p.getName();
                DAL.updateWallet(w);
            }
        }
    }
}
