package dev.lmke.mc.wallet.commands.wallet;

import dev.lmke.mc.wallet.annotations.HasPermission;
import dev.lmke.mc.wallet.annotations.IsPlayerCommand;
import dev.lmke.mc.wallet.annotations.SubCommand;
import dev.lmke.mc.wallet.commands.WalletCommand;
import dev.lmke.mc.wallet.database.DAL;
import dev.lmke.mc.wallet.dto.WalletObject;
import dev.lmke.mc.wallet.utils.CommandBase;
import dev.lmke.mc.wallet.utils.ConfigManager;
import dev.lmke.mc.wallet.utils.MsgLocaleManager;
import dev.lmke.mc.wallet.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand("pay")
@IsPlayerCommand
@HasPermission("lmke-wallet.wallet.pay")
public class WalletPayCommand extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.missing_args"));
            return true;
        }

        if (!Utils.isEconomyNumber(args[1])) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.invalid"));
            return true;
        }

        WalletObject walletReceiver = DAL.getWalletByPlayerName(args[0]);

        if (walletReceiver == null) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.not_found"));
            return true;
        }

        Player p = (Player) sender;

        // check if player wants to pay itself money
        if (walletReceiver.player.equals(p.getUniqueId())) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.invalid_target"));
            return true;
        }

        // Online check
        if (ConfigManager.getConfig().getBoolean("pay_require_online")) {
            Player receiverPlayer = Bukkit.getPlayer(walletReceiver.player);

            if (receiverPlayer == null || !receiverPlayer.isOnline()) {
                sender.sendMessage(MsgLocaleManager.getChatText("errors.offline"));
                return true;
            }
        }

        // distance check
        if (ConfigManager.getConfig().getBoolean("pay_distance.enable")) {
            Player receiverPlayer = Bukkit.getPlayer(walletReceiver.player);
            int maxDistance = ConfigManager.getConfig().getInt("pay_distance.distance");

            if (receiverPlayer == null || p.getLocation().distance(receiverPlayer.getLocation()) > maxDistance) {
                sender.sendMessage(MsgLocaleManager.getChatText("errors.too_far_away"));
                return true;
            }
        }

        WalletObject walletSender = DAL.getWalletByPlayerId(p.getUniqueId());

        double amount = Double.parseDouble(args[1]);

        if (walletSender.balance < amount) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.no_money"));
            return true;
        }

        walletSender.balance -= amount;
        walletReceiver.balance += amount;

        boolean success = DAL.updateWallet(walletSender);

        if (!success) {
            sender.sendMessage(MsgLocaleManager.getChatText("errors.failed"));
            return true;
        }

        DAL.updateWallet(walletReceiver);

        sender.sendMessage(MsgLocaleManager.getChatText("wallet.pay", Utils.getCurrencyString(amount), walletReceiver.playerName));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 0.4F);

        Player receiverPlayer = Bukkit.getPlayer(walletReceiver.player);

        if (receiverPlayer != null) {
            receiverPlayer.sendMessage(MsgLocaleManager.getChatText("wallet.pay_receive", Utils.getCurrencyString(amount), p.getDisplayName()));
            receiverPlayer.playSound(receiverPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.6F);
        }

        return true;
    }
}
