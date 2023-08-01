package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ping implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Command ist nur für Spieler!");
            return true;
        }
        Player p = (Player) sender;

        if (!(args.length == 0 || args.length == 1)) {
            if (p.hasPermission("megacraft.command.ping.others")) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutze §b/ping <Spieler>");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutze §b/ping");
            }
            return true;
        }

        if (args.length == 0) {
            int ping = p.getPing();
            p.sendTitle("§bPing", "§aDein §bPing §7ist §b" + ping + "§7ms");
        } else {
            if (p.hasPermission("megacraft.command.ping.others")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(MegaEssentials.Prefix + "§4Der Spieler §6" + target.getName() + " §4existiert nicht!");
                }
                int ping = target.getPing();
                p.sendTitle("§bPing", "§6" + target.getName() + "§7's §bPing §7ist §b" + ping + "§7ms");
            }
        }


        return false;
    }
}
