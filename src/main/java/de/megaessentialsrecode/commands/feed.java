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

public class feed implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand( CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }


        Player p = (Player) sender;

        if(p.hasPermission("megacraft.command.feed")) {
            if (!(args.length == 0 || args.length == 1)) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §4/heal <Spieler>");
                return true;
            }
            if (args.length == 0) {
                p.setFoodLevel(20);
                p.sendMessage(MegaEssentials.Prefix + "§aDein Hunger wurde gestillt.");
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if ((target == null)) {
                    p.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + p.getServer().getName() + " oder nicht online.");
                } else {
                    target.setFoodLevel(20);
                    p.sendMessage(MegaEssentials.Prefix + "§b" + target.getName() + " §awurde gefüttert.");
                    target.sendMessage(MegaEssentials.Prefix + "§aDein Hunger wurde gestillt.");
                }
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §4/feed <Spieler>");
            }

        } else {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.feed.others")) {
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
