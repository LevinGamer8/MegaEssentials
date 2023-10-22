package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class gift implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (!(luckPerms.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().equals("mega"))) {
            p.sendMessage(MegaEssentials.Prefix + "§cLeider hast du den §bMEGA-Rang §cnicht :(");
            return true;
        }

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutze: §b/gift [Spielername]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (luckPerms.getUserManager().getUser(args[0]) == null) {
            p.sendMessage(MegaEssentials.Prefix + "§4Dieser §6Spieler §4existiert nicht!");
            return true;
        }

        Objects.requireNonNull(luckPerms.getUserManager().getUser(args[0])).setPrimaryGroup("premium");



        p.sendMessage(MegaEssentials.Prefix + "§bDu hast §6" + target.getName() + " §bden §6Premium-Rang §ageschenkt§7!");
        if (target.isOnline()) {
            target.sendMessage(MegaEssentials.Prefix + "§6" + p.getName() + "§bhat dir den §6Premium-Rang §ageschenkt§7!");
        }




        return true;
    }
}
