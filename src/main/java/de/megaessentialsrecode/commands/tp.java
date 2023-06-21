package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class tp implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;


        if (!(p.hasPermission("megacraft.command.tp"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 1) && (!(args.length == 2))) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/tp [Spieler] <Spieler>");
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == p) {
                p.sendMessage(MegaEssentials.Prefix + "§4Du darfst dich nicht zu dir selbst teleportieren!");
            }
            p.sendMessage(MegaEssentials.Prefix + "§bDu wirst zu §6" + target.getName() + " §bteleportiert");
            p.teleport(target.getLocation());
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            Player target2 = Bukkit.getPlayer(args[1]);
            if (target == p || target2 == p) {
                p.sendMessage(MegaEssentials.Prefix + "§4Du darfst dich nicht zu dir selbst teleportieren!");
            }
            p.sendMessage(MegaEssentials.Prefix + "§6" + target.getName() + " §bwird zu §6" + target2.getName() + " §bteleportiert");
            target.teleport(target2.getLocation());
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player && args.length == 1) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }
        return completions;
    }

}
