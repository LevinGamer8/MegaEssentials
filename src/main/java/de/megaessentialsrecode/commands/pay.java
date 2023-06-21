package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.EconomyProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class pay implements CommandExecutor, TabCompleter {

    private EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "Dieser Befehl ist nur für Spieler!");
            return true;
        }
        if (!(args.length == 2)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: §b/pay <Spieler> <Betrag>");
            return true;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("*")) {
            if (!(isNumeric(args[1]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                return true;
            }
            double amount = Double.parseDouble(args[1]);
            double economy = DataBase.getEconomy(p);
            int numPlayers = 0;
            double totalAmount;
            List<String> registeredPlayers = DataBase.getAllRegisteredPlayers();
            for (String playerUUID : registeredPlayers) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                if (DataBase.exist(offlinePlayer)) {
                    if (!offlinePlayer.getUniqueId().equals(p.getUniqueId())) {
                        if (!(isNumeric(args[1]))) {
                            sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                            return true;
                        }
                        if (!economyProvider.has(p, amount * numPlayers)) {
                            p.sendMessage(MegaEssentials.Prefix + "§6Du §bhast §cnicht genug §aGeld.");
                            p.sendMessage(MegaEssentials.Prefix + "§cDir fehlen §e" + this.economyProvider.format(amount * numPlayers - economy) + " §b" + economyProvider.currencyNameSingular());
                            return true;
                        }
                        DataBase.addEconomy(offlinePlayer, amount);
                        if (offlinePlayer.isOnline()) {
                            Player target = Bukkit.getPlayer(offlinePlayer.getName());
                            target.sendMessage(MegaEssentials.Prefix + "§6" + p.getName() + " §bhat dir §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben.");
                        }
                        numPlayers++;
                    }
                }
            }
            totalAmount = amount * numPlayers;

            if (numPlayers > 0) {
                DataBase.removeEconomy(p, totalAmount);
                p.sendMessage(MegaEssentials.Prefix + "§6" + numPlayers + " §bSpieler haben jeweils §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §aerhalten.");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Es gibt keine Spieler in der Datenbank, an die du Geld senden kannst.");
            }
            return true;
        }
        if (!(isNumeric(args[1]))) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
            return true;
        }
        double amount = Double.parseDouble(args[1]);
        double economy = DataBase.getEconomy(p);
        int missing = (int) (amount - economy);
        Double amount1 = Double.valueOf(args[1]);
        if (!economyProvider.has(p, amount)) {
            p.sendMessage(MegaEssentials.Prefix + "§6Du §bhast §cnicht genug §aGeld.");
            p.sendMessage(MegaEssentials.Prefix + "§cDir fehlen §e" + this.economyProvider.format(missing) + " §b" + economyProvider.currencyNameSingular());
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == p) {
            p.sendMessage(MegaEssentials.Prefix + "§4Du kannst dir nicht selbst Geld geben!");
            return true;
        }
        if (target.isOnline()) {
            Player target1 = Bukkit.getPlayer(args[0]);
            if (!(isNumeric(args[1]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                return true;
            }
            if (target1 == p) {
                p.sendMessage(MegaEssentials.Prefix + "§4Du kannst dir nicht selbst Geld geben!");
                return true;
            }
            DataBase.removeEconomy(p, Double.parseDouble(args[1]));
            DataBase.addEconomy(target1, Double.parseDouble(args[1]));
            target1.sendMessage(MegaEssentials.Prefix + "§6" + p.getName() + " §bhat dir §6" + this.economyProvider.format(amount1) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben.");
            p.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target1.getName() + "§6 " + this.economyProvider.format(amount1) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben");
            return true;
        } else {
            if (!(DataBase.exist(target))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                return true;
            }
            if (!(isNumeric(args[1]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                return true;
            }
            DataBase.removeEconomy(p, Double.parseDouble(args[1]));
            DataBase.addEconomy(target, Double.parseDouble(args[1]));
            p.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target.getName() + "§6 " + this.economyProvider.format(amount1) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben");
            return true;
        }
    }
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                completions.addAll(getOnlinePlayerNames());
            } else if (args.length == 2) {
                completions.add("10000");
                completions.add("1000");
                completions.add("100");
            }
        }
        return completions;
    }
    private List<String> getOnlinePlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}
