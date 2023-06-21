package de.megaessentialsrecode.commands;


import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class tpaccept implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 0)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpaccept");
            return true;
        }

        TPAUtils latestRequest = TPAHandler.getLatestRequest(p);
        if (latestRequest == null) {
            return true;
        }
        if (!latestRequest.isTpHere())
            latestRequest.setLocation(p.getLocation());
        latestRequest.getSender().teleport(latestRequest.getLocation());
        TPAHandler.cancelRequest(latestRequest);
        p.playSound(p, Sound.BLOCK_BELL_USE, 1.0F, 1.0F);
        latestRequest.getSender().playSound(latestRequest.getSender(), Sound.ENTITY_SHULKER_TELEPORT, 1.0F, 2.0F);


        return false;
    }
}
