package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class heal implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.heal"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(args.length == 0 || args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §4/heal <Spieler>");
                return true;
        }

            if (args.length == 0) {
                if (p.getHealth() == 20) {
                    p.sendMessage(MegaEssentials.Prefix + "§4Du bist schon voll §ageheilt");
                } else {
                    p.setHealth(20);
                p.sendMessage(MegaEssentials.Prefix + "§aDu wurdest geheilt.");
            }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + MegaEssentials.getPluginName() + " online.");
                } else {
                    target.setHealth(20);
                    p.sendMessage(MegaEssentials.Prefix + "§b" + target.getName() + " §awurde geheilt.");
                    target.sendMessage(MegaEssentials.Prefix + "§aDu wurdest von §b" + p.getName() + " §ageheilt.");
                }
            }
        return true;
    }
}
