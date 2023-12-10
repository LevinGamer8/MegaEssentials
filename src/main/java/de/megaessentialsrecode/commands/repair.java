package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;

public class repair implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        if (!(sender.hasPermission("megacraft.command.repair"))) {
            sender.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 0)) {
            player.sendMessage(MegaEssentials.Prefix + "§4Nutze: §b/repair");
            return true;
        }

        if (player.getItemInHand().getType().isAir()) {
            player.sendMessage(MegaEssentials.Prefix + "§4Du musst ein Item in der hand halten!");
            return true;
        }

        Damageable damageable = (Damageable)player.getInventory().getItemInMainHand().getItemMeta();
        damageable.setDamage(0);

        player.getItemInHand().setItemMeta(damageable);

        return true;
    }
}
