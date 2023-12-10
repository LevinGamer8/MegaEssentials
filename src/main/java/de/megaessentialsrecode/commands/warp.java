package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class warp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;


        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/warp §7[§bWARP§7]");
            return true;
        }

        PlayerData pd = new PlayerData(p.getName());

        if (pd.getWarpNames().contains(args[0])) {
            if (pd.isWarpActive(args[0])) {
                p.teleport(pd.getWarpLocation(args[0]));
                p.sendMessage(MegaEssentials.Prefix + "§aDu wurdest zum §bWarp " + args[0] + " §bteleportiert§7.");
            }
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§cDieser Warp existiert nicht§7.\n§aHier eine Liste mit allen verfügbaren §bWarps§7.\n" + pd.getWarpNames());
        }



        return true;
    }
}
