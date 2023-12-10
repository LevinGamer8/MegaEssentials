package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setWarp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;


        if (!(p.hasPermission("megacraft.command.setwarp"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/setwarp §7[§bNAME§7]");
            return true;
        }

        PlayerData pd = new PlayerData(p.getName());
        pd.addWarp(args[0], p.getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), true);
        p.sendMessage(MegaEssentials.Prefix + "§aDu hast den §bWarp " + args[0] + " §aerfolgreich gesetzt§7.");


        return true;
    }
}
