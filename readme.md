# LMKE Wallet Spigot Plugin
**Game Version:** 1.15  
**Tested Versions:** 1.15 - 1.19

> The simple economy plugin with Vault support and localization

![lmke warps banner](/lmke-wallet-banner.png)

## Features
- Basic economy features (see balance, pay, ...)
- Highly customizable
- MySQL and SQLite support
- Translations (german, english)
- Vault support
- Clear wallet on player death
- Limit pay distance

## Commands
**Alias:** /money

| command                                     | description                                 |
|---------------------------------------------|---------------------------------------------|
| `/wallet`                                   | See your own balance                        |
| `/wallet <player>`                          | See the balance of another player           |
| `/wallet help`                              | Print help                                  |
| `/wallet pay <player> <amount>`             | Give money to a player from your own wallet |
| `/wallet set <player/amount> [amount]`      | Set wallet amount of a player               |
| `/wallet add <player/amount> [amount]`      | Add money to a player's wallet              |
| `/wallet withdraw <player/amount> [amount]` | Remove money from a player's wallet         |

## Permissions

- `lmke-wallet.wallet` Wallet and help
- `lmke-wallet.wallet.pay`
- `lmke-wallet.wallet.set`
- `lmke-wallet.wallet.add`
- `lmke-wallet.wallet.withdraw`