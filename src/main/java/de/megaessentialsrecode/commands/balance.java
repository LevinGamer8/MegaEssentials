package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.EconomyProvider;
import de.megaessentialsrecode.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class balance implements CommandExecutor, TabCompleter {

    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

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
            PlayerData pd = new PlayerData(player.getName());
            player.sendMessage(MegaEssentials.Prefix + "§bDein §6Bargeld§7:§a " + this.economyProvider.format(pd.getMoney()) + " §b" + this.economyProvider.currencyNameSingular());
        } else {
            if (player.hasPermission("megacraft.command.balance.others")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                PlayerData pd = new PlayerData(target.getName());
                player.sendMessage(MegaEssentials.Prefix + "§b" + target.getName() + "'s §6Bargeld§7:§a " + (this.economyProvider.format(pd.getMoney()) + " §b" + this.economyProvider.currencyNameSingular()));
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.balance.others")) {
                if (args.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            }
        }
        return completions;
    }
}
