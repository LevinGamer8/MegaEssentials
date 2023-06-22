package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.EconomyProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class bank implements CommandExecutor, TabCompleter {

    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();
    private final DataBase dataBase;

    public bank(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 0 || args.length == 2)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: /bank §b<einzahlen/auszahlen> §a<Betrag>");
            return true;
        }


        if (args.length == 0 ) {
            player.sendMessage(MegaEssentials.Prefix + "§6BANK§7: §bDein §6Kontostand§7:§a " + this.economyProvider.format(this.dataBase.getEconomyBank(player)) + " §b" + this.economyProvider.currencyNameSingular());
        } else if (args.length == 2) {
            if (Objects.equals(args[0], "einzahlen") || Objects.equals(args[0], "auszahlen")) {
                Double amount = Double.parseDouble(args[1]);
                if (Objects.equals(args[0], "einzahlen")) {
                    DataBase.removeEconomy(player, amount);
                    DataBase.addEconomyBank(player, amount);
                    player.sendMessage(MegaEssentials.Prefix + "§6Du hast §a" + this.economyProvider.format(amount) + "§beingezahlt");
                } else if (Objects.equals(args[0], "auszahlen")) {
                    DataBase.removeEconomyBank(player, amount);
                    DataBase.addEconomy(player, amount);
                    player.sendMessage(MegaEssentials.Prefix + "§6Du hast §a" + this.economyProvider.format(amount) + "§bausgezahlt");
                }
            } else {
                sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: /bank §b<einzahlen/auszahlen> §a<Betrag>");
                return true;
            }

        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                completions.add("einzahlen");
                completions.add("auszahlen");
            } else if (args.length == 2) {
                completions.add("10000");
                completions.add("1000");
                completions.add("100");
            }
        }
        return completions;
    }
}
