package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
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
        }


        return false;
    }
}
