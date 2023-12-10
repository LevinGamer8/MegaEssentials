package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tpdeny implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpdeny §7[§aSpieler§7]");
            return true;
        }

        TPAUtils latestRequest = TPAHandler.getLatestRequest(p);
        if (latestRequest == null) {
            return true;
        }
        TPAHandler.cancelRequest(latestRequest);


        return true;
    }
}