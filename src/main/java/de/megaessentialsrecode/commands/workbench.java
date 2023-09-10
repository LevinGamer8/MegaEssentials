package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class workbench implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            p.openWorkbench(p.getLocation(), true);
        } else if (args.length == 1) {
            if (!(p.hasPermission("megacraft.command.workbench"))) {
                p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(MegaEssentials.Prefix + "§4Der §6Spieler " + args[0] + " §bist nicht §aonline§4!");
            }

            target.openWorkbench(target.getLocation(), true);

        }
        return true;
    }
}
