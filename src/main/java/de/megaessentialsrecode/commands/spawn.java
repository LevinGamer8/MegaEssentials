package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.Locations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 0 && (!(args.length == 1)))) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutze§7: §bspawn <Spieler>");
            return true;
        }

        if (args.length == 0) {
            Locations.teleportToSpawn(p);
            return true;
        }

        if (p.hasPermission("megacraft.command.spawn.tpothers")) {
            if (!(args.length == 1)) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutze§7: §bspawn <Spieler>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            Locations.teleportToSpawn(target);
        }


        return false;
    }
}
