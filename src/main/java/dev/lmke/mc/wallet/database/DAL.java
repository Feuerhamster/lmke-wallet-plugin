package dev.lmke.mc.wallet.database;

import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.LogManager;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class DAL {

    public static boolean playerHasWallet(UUID player) {
        try {
            return Database.getWalletDao().idExists(player);
        } catch (SQLException e) {
            logException("playerHasWallet", e);
            return false;
        }
    }

    public static boolean playerHasWalletByName(String player) {
        try {
            return !Database.getWalletDao().queryForEq("playerName", player).isEmpty();
        } catch (SQLException e) {
            logException("playerHasWallet", e);
            return false;
        }
    }

    public static WalletObject getWalletByPlayerId(UUID player) {
        try {
            return Database.getWalletDao().queryForId(player);
        } catch (SQLException e) {
            logException("getWalletByPlayerId", e);
            return null;
        }
    }

    public static WalletObject getWalletByPlayerName(String player) {
        try {
            List<WalletObject> res = Database.getWalletDao().queryForEq("playerName", player);

            if (res.isEmpty()) {
                return null;
            }

            return res.get(0);
        } catch (SQLException e) {
            logException("getWalletByPlayerId", e);
            return null;
        }
    }

    public static boolean updateWallet(WalletObject wallet) {
        try {
            int res = Database.getWalletDao().update(wallet);

            return res > 0;
        } catch (SQLException e) {
            logException("updateWallet", e);
            return false;
        }
    }

    public static boolean createWallet(WalletObject wallet) {
        try {
            int res = Database.getWalletDao().create(wallet);

            return res > 0;
        } catch (SQLException e) {
            logException("createWallet", e);
            return false;
        }
    }

    private static void logException(String methodName, Exception e) {
        LogManager.getLogger().throwing(DAL.class.getName(), methodName, e);
    }
}
