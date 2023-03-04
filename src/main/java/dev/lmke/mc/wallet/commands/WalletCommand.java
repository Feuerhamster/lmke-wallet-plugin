package dev.lmke.mc.wallet.commands;

import dev.lmke.mc.wallet.LMKEWallet;
import dev.lmke.mc.wallet.annotations.HasPermission;
import dev.lmke.mc.wallet.commands.wallet.*;
import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.CommandBase;
import dev.lmke.mc.wallet.utils.MsgLocaleManager;
import dev.lmke.mc.wallet.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@HasPermission("lmke-wallet.wallet")
public class WalletCommand extends CommandBase {
    Economy eco = LMKEWallet.getEconomy();

    public WalletCommand() {
        super(
            new WalletHelpCommand(),
            new WalletSetCommand(),
            new WalletAddCommand(),
            new WalletWithdrawCommand(),
            new WalletPayCommand()
       );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String[] args) {

        // Balance of players own wallet
        if (args.length < 1) {
            // Console can't execute this because console has no wallet
            if (!(sender instanceof Player)) {
                sender.sendMessage(MsgLocaleManager.getChatText("errors.invalid"));
                return true;
            }

            double balance = eco.getBalance((Player) sender);

            sender.sendMessage(MsgLocaleManager.getChatText("wallet.balance", Utils.getCurrencyString(balance)));

            return true;
        }

        if (!sender.hasPermission("lmke-wallet.wallet.others")) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.missing_permission"));
            return true;
        }

        WalletObject w = DAL.getWalletByPlayerName(args[0]);

        // Player wallet not found
        if (w == null) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.not_found"));
            return true;
        }

        sender.sendMessage(MsgLocaleManager.getChatText("wallet.balance_other", w.playerName, Utils.getCurrencyString(w.balance)));

        return true;
    }
}
