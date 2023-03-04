package dev.lmke.mc.wallet.commands.wallet;

import dev.lmke.mc.wallet.annotations.HasPermission;
import dev.lmke.mc.wallet.annotations.SubCommand;
import dev.lmke.mc.wallet.utils.CommandBase;
import dev.lmke.mc.wallet.utils.MsgLocaleManager;
import org.bukkit.command.CommandSender;

import java.util.List;

@SubCommand("help")
@HasPermission("lmke-wallet.wallet")
public class WalletHelpCommand extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String[] args) {
        List<String> helpRows = MsgLocaleManager.getTextListRaw("help");
        String header = MsgLocaleManager.getTextRaw("common.header");

        sender.sendMessage(header);
        sender.sendMessage(helpRows.toArray(new String[0]));

        return true;
    }
}
