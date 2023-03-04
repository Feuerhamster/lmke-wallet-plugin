package dev.lmke.mc.wallet.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "lmke_wallet_wallets")
public class WalletObject {
    @DatabaseField(id = true)
    public UUID player;

    @DatabaseField(uniqueIndex = true)
    public String playerName;

    @DatabaseField
    public double balance;
}
