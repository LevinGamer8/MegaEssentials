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
            PlayerData pd = new PlayerData(Bukkit.getPlayer(args[0]).getName());
            double economy = pd.getEconomy();
            int numPlayers = 0;
            double totalAmount;
            List<String> registeredPlayers = pd.getAllRegisteredPlayers();
            for (String playerUUID : registeredPlayers) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                    if (!offlinePlayer.getUniqueId().equals(p.getUniqueId())) {
                        if (!(isNumeric(args[1]))) {
                            sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                            return true;
                        }
                        if (!economyProvider.has(p, amount * numPlayers)) {
                            p.sendMessage(MegaEssentials.Prefix + "§6Du §bhast §cnicht genug §aGeld.");
                            p.sendTitle("§cDir fehlen §e" + this.economyProvider.format(amount * numPlayers - economy) + " §b" + economyProvider.currencyNameSingular(), "");
                            return true;
                        }
                        pd.addEconomy(amount);
                        if (offlinePlayer.isOnline()) {
                            Player target = Bukkit.getPlayer(offlinePlayer.getName());
                            target.sendTitle("§6" + p.getName(), " §bhat dir §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben.");
                        }
                        numPlayers++;
                    }
            }
            totalAmount = amount * numPlayers;

            if (numPlayers > 0) {
                pd.removeEconomy(totalAmount);
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
        PlayerData pd = new PlayerData(p.getName());
        double economy = pd.getEconomy();
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
            pd.removeEconomy(Double.parseDouble(args[1]));
            PlayerData pd2 = new PlayerData(target1.getName());
            pd2.addEconomy(Double.parseDouble(args[1]));
            target1.sendTitle("§6" + p.getName(), " §bhat dir §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben.");
            p.sendTitle("§6Du §bhast " + target1.getName(), "§6 " + this.economyProvider.format(amount1) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben");
            return true;
        } else {
            PlayerData pd2 = new PlayerData(target.getName());
            if (!(pd2.exists())) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                return true;
            }
            if (!(isNumeric(args[1]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein.");
                return true;
            }
            pd.removeEconomy(Double.parseDouble(args[1]));
            pd2.addEconomy(Double.parseDouble(args[1]));
            p.sendTitle("§6Du §bhast " + target.getName(), "§6 " + this.economyProvider.format(amount1) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben");
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
                completions.add("100000");
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
