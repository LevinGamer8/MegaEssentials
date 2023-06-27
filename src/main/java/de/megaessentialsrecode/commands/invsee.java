package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class invsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;


        if (!(p.hasPermission("megacraft.command.invsee"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/invsee [Spieler]");
            return true;
        }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == p) {
                p.sendMessage(MegaEssentials.Prefix + "§4Du darfst nicht in dein eigenes Inventar schauen");
                return true;
            }

            if (target == null) {
                p.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + MegaEssentials.getPluginName() + " online.");
            } else {
                final Inventory inv = target.getInventory();
                p.openInventory(inv);
            }
        return false;
    }
}