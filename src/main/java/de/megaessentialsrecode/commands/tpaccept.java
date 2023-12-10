package de.megaessentialsrecode.commands;


import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tpaccept implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 1)) {
            player.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpaccept §7[§aSpieler§7]");
            return true;
        }

        String requesterName = args[0];
        TPAUtils tpaRequest = TPAHandler.getLatestRequest(player);

        if (tpaRequest != null && tpaRequest.getSender().getName().equalsIgnoreCase(requesterName)) {
            Player requester = tpaRequest.getSender();

            if (requester != null && requester.isOnline()) {
                if (tpaRequest.isTpHere()) {
                    player.teleport(requester);
                } else {
                    requester.teleport(player);
                }
                TPAHandler.cancelRequest(tpaRequest);
            } else {
                player.sendMessage(MegaEssentials.Prefix + "§cDer Spieler §e" + requesterName + " §cist nicht mehr online§7.");
            }
        } else {
            player.sendMessage(MegaEssentials.Prefix + "§cKeine ausstehende TPA-Anfrage von " + requesterName + "§7.");
        }

        return true;
    }
}