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
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class balancetop implements CommandExecutor {
    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();
    private final DataBase dataBase;

    public balancetop(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 0)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: §b/balancetop");
            return true;
        }
        List<String> registeredPlayers = DataBase.getAllRegisteredPlayers();
        Map<String, Double> balances = new HashMap<>();

        for (String playerUUID : registeredPlayers) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
            double balance = DataBase.getEconomy(offlinePlayer);

            balances.put(offlinePlayer.getName(), balance);
        }
        List<Map.Entry<String, Double>> sortedBalances = new ArrayList<>(balances.entrySet());
        sortedBalances.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int count = 0;
        sender.sendMessage(MegaEssentials.Prefix + "§6Top §b10 §aKontostände:");

        for (Map.Entry<String, Double> entry : sortedBalances) {
            String playerName = entry.getKey();
            double balance = entry.getValue();

            sender.sendMessage(MegaEssentials.Prefix + "§6" + playerName + "§7: §a" + economyProvider.format(balance) + " §b€");

            count++;
            if (count >= 10) {
                break;
            }
        }

        return true;
    }
}
