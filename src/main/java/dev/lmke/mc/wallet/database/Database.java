package dev.lmke.mc.wallet.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dev.lmke.mc.wallet.LMKEWallet;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.ConfigManager;
import dev.lmke.mc.wallet.utils.LogManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class Database {
    private static final FileConfiguration config = ConfigManager.getConfig();
    private static final Logger logger = LogManager.getLogger();
    private static final Plugin plugin = LMKEWallet.getPlugin(LMKEWallet.class);
    private static JdbcPooledConnectionSource conn = null;

    private static Dao<WalletObject, UUID> walletDao;

    public static void init() {
        try {
            conn = new JdbcPooledConnectionSource(getConnectionString());

            walletDao = DaoManager.createDao(conn, WalletObject.class);
            TableUtils.createTableIfNotExists(conn, WalletObject.class);
        } catch (SQLException e) {
            logger.throwing(Database.class.getName(), "init", e);
        }
    }

    public static void close() {
        try {
            conn.close();
        } catch (Exception e) {
            logger.throwing(Database.class.getName(), "close", e);
        }
    }

    private static String getConnectionString() {
        StringBuilder connStr = new StringBuilder();
        connStr.append("jdbc:");

        String driver = config.getString("database.driver");

        switch (driver) {
            case "sqlite": {
                String dataPath = Paths.get(plugin.getDataFolder().getAbsolutePath(), "data.db").toString();

                connStr.append("sqlite:" + dataPath);
                break;
            }
            case "mysql": {
                String host = config.getString("database.driver");
                String port = config.getString("database.port");
                String user = config.getString("database.user");
                String pwd = config.getString("database.passwd");
                String dbname = config.getString("database.dbname");

                connStr.append("mysql://")
                    .append(host)
                    .append(":").append(port)
                    .append("/").append(dbname)
                    .append("?user=").append(user)
                    .append("&password=").append(pwd)
                    .append("&permitMysqlScheme");
                break;
            }
            default: {
                plugin.getLogger().severe("UNKNOWN DATABASE DRIVER");
            }
        }

        return connStr.toString();
    }

    public static Dao<WalletObject, UUID> getWalletDao() {
        return walletDao;
    }
}
