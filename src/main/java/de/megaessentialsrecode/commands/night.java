package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class night implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "Dieser Befehl ist nur für Spieler!");
            return true;
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("megacraft.command.night"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }
        Bukkit.getWorld(p.getWorld().getUID()).setTime(10000);
        p.sendMessage(MegaEssentials.Prefix + "§bEs ist nun §0Nacht");
        return false;
    }

}
