package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class tpa implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpa §a[Spieler]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        target.sendMessage(MegaEssentials.Prefix + "§a" + p.getName() + " §6möchte sich zu dir §bteleportieren§7.\n§bZum annehmen /tpaccept");
        p.sendMessage(MegaEssentials.Prefix + "§§6Du hast §b" + target.getName() + " §agefragt ob du dich zu §6ihm §bteleportieren §adarfst§7.");


        Location tpaloc = target.getLocation();


        TPAUtils tpaUtils = new TPAUtils(p, target, tpaloc, false);
        TPAHandler.initiateRequest(tpaUtils);

        return false;
    }



}
