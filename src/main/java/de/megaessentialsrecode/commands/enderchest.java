package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class enderchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 0 || args.length == 1)) {

            if (p.hasPermission("megacraft.commands.enderchest.others")) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/enderchest <Spieler>");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/enderchest");
            }
            return true;
        }

        if (args.length == 0) {
            Inventory enderchest = p.getEnderChest();
            p.openInventory(enderchest);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            Inventory enderchest = target.getEnderChest();
            p.openInventory(enderchest);
        }

        return true;
    }
}
