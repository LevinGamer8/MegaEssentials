package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class heal implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.heal"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 0 || args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §4/heal <Spieler>");
                return true;
        }

            if (args.length == 0) {
                if (p.getHealth() == 20) {
                    p.sendMessage(MegaEssentials.Prefix + "§4Du bist schon voll §ageheilt");
                } else {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                p.sendMessage(MegaEssentials.Prefix + "§aDu wurdest geheilt und dein Hunger wurde gestillt.");
            }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + MegaEssentials.getPluginName() + " online.");
                } else {
                    target.setHealth(20);
                    p.sendMessage(MegaEssentials.Prefix + "§b" + target.getName() + " §awurde geheilt.");
                    target.sendMessage(MegaEssentials.Prefix + "§aDu wurdest von §b" + p.getName() + " §ageheilt.");
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
            if (p.hasPermission("megacraft.command.heal.others")) {
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

