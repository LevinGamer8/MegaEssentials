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

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class eco implements CommandExecutor, TabCompleter {

    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender.hasPermission("megacraft.command.eco"))) {
            sender.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length >= 2)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: /eco [give/remove/set/reset] [Spieler] [Betrag]");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (!(isNumeric(args[2]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein");
                return true;
            }
            Double amount = Double.valueOf(args[2]);
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if (target.isOnline()) {
                Player target1 = Bukkit.getPlayer(args[1]);
                PlayerData targetPD = new PlayerData(target1.getName());
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.addEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target.getName() + "§6 " + this.economyProvider.format(amount) + " §b€ §agegeben");
                target1.sendMessage(MegaEssentials.Prefix + "§bDir wurden §6" + this.economyProvider.format(amount) + " §b€ §agegeben");
            } else {
                PlayerData targetPD = new PlayerData(target.getName());
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.addEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target.getName() + "§6 " + this.economyProvider.format(amount) + " §b€ §agegeben");
            }

        } else if (args[0].equalsIgnoreCase("remove")) {
            Double amount = Double.valueOf(args[2]);
            if (!(isNumeric(args[2]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein");
                return true;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            PlayerData targetPD = new PlayerData(target.getName());
            if (target.isOnline()) {
                Player target1 = Bukkit.getPlayer(args[1]);
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.removeEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target.getName() + "§6 " + this.economyProvider.format(amount) + " §b€ §cabgezogen");
                target1.sendMessage(MegaEssentials.Prefix + "§bDir wurden §6" + this.economyProvider.format(amount) + " §b€ §cabgezogen");
            } else {
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.removeEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du §bhast " + target.getName() + "§6 " + this.economyProvider.format(amount) + " §b€ §cabgezogen");
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            Double amount = Double.valueOf(args[2]);
            if (!(isNumeric(args[2]))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Bitte gebe eine gültige Zahl ein");
                return true;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            PlayerData targetPD = new PlayerData(target.getName());
            if (target.isOnline()) {
                Player target1 = Bukkit.getPlayer(args[1]);
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.setEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du hast §b" + target.getName() + " 's §6Kontostand §bauf §6 " + this.economyProvider.format(amount) + " §b€ gesetzt");
                target1.sendMessage(MegaEssentials.Prefix + "§6Dein Kontostand §bwude auf §6" + this.economyProvider.format(amount) + " §b€ gesetzt");
            } else {
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.setEconomy(amount);
                sender.sendMessage(MegaEssentials.Prefix + "§6Du hast §b" + target.getName() + " 's §6Kontostand §bauf §6 " + this.economyProvider.format(amount) + " §b€ gesetzt");
            }
        } else if (args[0].equalsIgnoreCase("reset")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            PlayerData targetPD = new PlayerData(target.getName());
            if (target.isOnline()) {
                Player target1 = Bukkit.getPlayer(args[1]);
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.resetEconomy();
                sender.sendMessage(MegaEssentials.Prefix + "§6Du hast §b" + target.getName() + " 's §6Kontostand zurückgesetzt");
                target1.sendMessage(MegaEssentials.Prefix + "§6Dein Kontostand §bwurde zurückgesetzt");
            } else {
                if (!(targetPD.exists())) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                    return true;
                }
                targetPD.resetEconomy();
                sender.sendMessage(MegaEssentials.Prefix + "§6Du hast §b" + target.getName() + " 's §6Kontostand zurückgesetzt");
            }
        } else {
            sender.sendMessage(MegaEssentials.Prefix + "§4Nutze: §b/eco <give/remove/set/reset> <Spieler> <Betrag>");
        }

        return true;
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
                if (sender.hasPermission("megacraft.command.eco.add"))
                    completions.add("give");
                if (sender.hasPermission("megacraft.command.eco.remove"))
                    completions.add("remove");
                if (sender.hasPermission("megacraft.command.eco.reset"))
                    completions.add("reset");
                if (sender.hasPermission("megacraft.command.eco.set"))
                    completions.add("set");
            } else if (args.length == 2) {
                completions.addAll(getOnlinePlayerNames());
            } else if (args.length == 3) {
                if (!(args[1].equalsIgnoreCase("reset"))) {
                    completions.add("10000");
                    completions.add("1000");
                    completions.add("100");
                }
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
