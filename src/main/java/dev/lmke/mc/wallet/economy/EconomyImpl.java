package dev.lmke.mc.wallet.economy;

import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class EconomyImpl implements Economy {
    private final FileConfiguration config = ConfigManager.getConfig();

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "lmke-wallet";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public String format(double amount) {
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
        return String.valueOf(bd.doubleValue());

    }

    @Override
    public String currencyNamePlural() {
        return config.getString("currency_name.plural");
    }

    @Override
    public String currencyNameSingular() {
        return config.getString("currency_name.singular");
    }

    @Override
    public boolean hasAccount(String playerName) {
        return DAL.playerHasWalletByName(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return DAL.playerHasWallet(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        WalletObject wallet = DAL.getWalletByPlayerName(playerName);

        if (wallet == null) return 0;

        return wallet.balance;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        WalletObject wallet = DAL.getWalletByPlayerId(player.getUniqueId());

        if (wallet == null) return 0;

        return wallet.balance;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        double bal = getBalance(playerName);

        return bal >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        double bal = getBalance(player);

        return bal >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        WalletObject wallet = DAL.getWalletByPlayerName(playerName);

        if (wallet == null) {
            return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "not found");
        }

        wallet.balance -= amount;

        DAL.updateWallet(wallet);

        return new EconomyResponse(amount, wallet.balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        WalletObject wallet = DAL.getWalletByPlayerId(player.getUniqueId());

        if (wallet == null) {
            return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "not found");
        }

        wallet.balance -= amount;

        DAL.updateWallet(wallet);

        return new EconomyResponse(amount, wallet.balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        WalletObject wallet = DAL.getWalletByPlayerName(playerName);

        if (wallet == null) {
            return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "not found");
        }

        wallet.balance += amount;

        DAL.updateWallet(wallet);

        return new EconomyResponse(amount, wallet.balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        WalletObject wallet = DAL.getWalletByPlayerId(player.getUniqueId());

        if (wallet == null) {
            return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "not found");
        }

        wallet.balance += amount;

        DAL.updateWallet(wallet);

        return new EconomyResponse(amount, wallet.balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not implemented");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        WalletObject wallet = DAL.getWalletByPlayerName(playerName);

        if (wallet != null) return false;

        WalletObject newWallet = new WalletObject();
        newWallet.player = Bukkit.getPlayer(playerName).getUniqueId();
        newWallet.playerName = playerName;
        newWallet.balance = ConfigManager.getConfig().getDouble("initial_balance");

        return DAL.createWallet(newWallet);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        WalletObject wallet = DAL.getWalletByPlayerId(player.getUniqueId());

        if (wallet != null) return false;

        WalletObject newWallet = new WalletObject();
        newWallet.player = player.getUniqueId();
        newWallet.playerName = player.getName();
        newWallet.balance = ConfigManager.getConfig().getDouble("defaults.initial_balance");

        return DAL.createWallet(newWallet);
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }
}
