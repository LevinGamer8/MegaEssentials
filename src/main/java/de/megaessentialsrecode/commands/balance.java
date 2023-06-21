package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.EconomyProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class balance implements CommandExecutor {

    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();
    private final DataBase dataBase;

    public balance(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 0 || args.length == 1)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: /balance §b<Spieler>");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(MegaEssentials.Prefix + "§bDein §6Kontostand§7:§a " + this.economyProvider.format(this.dataBase.getEconomy(player)) + " §b" + this.economyProvider.currencyNameSingular());
        } else if (args.length == 1 ) {
            OfflinePlayer target =  Bukkit.getOfflinePlayer(args[0]);
            player.sendMessage(MegaEssentials.Prefix + "§b" + target.getName() + "'s §6Kontostand§7:§a " + (this.economyProvider.format(this.dataBase.getEconomy(target)) + " §b" + this.economyProvider.currencyNameSingular()));
        }
        return true;
    }
}
