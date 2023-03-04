package dev.lmke.mc.wallet.utils;

import dev.lmke.mc.wallet.LMKEWallet;
import net.milkbowl.vault.economy.Economy;

import java.util.regex.Pattern;

public class Utils {
    private static final Pattern economyNumber = Pattern.compile("^\\d*\\.?\\d{0,2}$");
    private static final Economy eco = LMKEWallet.getEconomy();

    public static boolean isEconomyNumber(String text) {
        if (text == null) {
            return false;
        }
        return economyNumber.matcher(text).matches();
    }

    public static String getCurrencyString(double value) {
        if (value == 1) {
            return value + " " + eco.currencyNameSingular();
        }
        return value + " " + eco.currencyNamePlural();
    }
}
