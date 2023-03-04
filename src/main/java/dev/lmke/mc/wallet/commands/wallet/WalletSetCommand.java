package dev.lmke.mc.wallet.commands.wallet;

import dev.lmke.mc.wallet.LMKEWallet;
import dev.lmke.mc.wallet.annotations.HasPermission;
import dev.lmke.mc.wallet.annotations.SubCommand;
import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.CommandBase;
import dev.lmke.mc.wallet.utils.MsgLocaleManager;
import dev.lmke.mc.wallet.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand("set")
@HasPermission("lmke-wallet.wallet.set")
public class WalletSetCommand extends CommandBase {
    Economy eco = LMKEWallet.getEconomy();

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.missing_args"));
            return true;
        }

        // Player wants to set his own balance
        if (Utils.isEconomyNumber(args[0])) {
            // Console can't execute this because console has no wallet
            if (!(sender instanceof Player)) {
                sender.sendMessage(MsgLocaleManager.getChatText("errors.invalid"));
                return true;
            }

            double desiredBalance = Double.parseDouble(args[0]);

            WalletObject w = DAL.getWalletByPlayerId(((Player) sender).getUniqueId());

            w.balance = desiredBalance;

            DAL.updateWallet(w);

            sender.sendMessage(MsgLocaleManager.getChatText("wallet.set", Utils.getCurrencyString(desiredBalance)));

            return true;
        }

        // Set balance of another player

        if (args.length < 2) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.missing_args"));
            return true;
        }

        // second argument not a number
        if (!Utils.isEconomyNumber(args[1])) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.invalid"));
            return true;
        }

        WalletObject w = DAL.getWalletByPlayerName(args[0]);

        // Player wallet not found
        if (w == null) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.not_found"));
            return true;
        }

        double desiredBalance = Double.parseDouble(args[1]);

        w.balance = desiredBalance;

        DAL.updateWallet(w);

        sender.sendMessage(MsgLocaleManager.getChatText("wallet.set_other", w.playerName, Utils.getCurrencyString(desiredBalance)));

        return true;
    }
}