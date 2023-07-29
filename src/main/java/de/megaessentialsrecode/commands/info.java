package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class info implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Command ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.player"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/player [Spieler]");
            return true;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            p.sendMessage(MegaEssentials.Prefix + "§4Dieser Spieler ist nicht §aonline");
        }

        Player target = Bukkit.getPlayer(args[0]);

        p.sendMessage(MegaEssentials.Prefix + "§bName§7: §6" + target.getName());
        p.sendMessage(MegaEssentials.Prefix + "§bUUID§7: §6" + target.getUniqueId());
        p.sendMessage(MegaEssentials.Prefix + "§bIP§7: §6" + target.getAddress().getAddress().getHostAddress());
        p.sendMessage(MegaEssentials.Prefix + "§bPing§7: §6" + target.getPing());
        p.sendMessage(MegaEssentials.Prefix + "§bHerzen§7: §6" + target.getHealth());
        p.sendMessage(MegaEssentials.Prefix + "§bHunger§7: §6" + target.getSaturation());
        p.sendMessage(MegaEssentials.Prefix + "§bWelt§7: §6" + target.getWorld().getName());
        p.sendMessage(MegaEssentials.Prefix + "§bKoordinaten§7: §aX§7:§6 " + target.getLocation().getBlockX() + " §aY§7: §6" + target.getLocation().getBlockY() + " §aZ§7: §6" + target.getLocation().getBlockZ());

        return false;
    }
}
