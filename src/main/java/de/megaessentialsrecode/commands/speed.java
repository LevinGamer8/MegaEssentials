package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PlaceholderProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class speed implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.speed"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }


        if (args.length == 1) {
            if (p.isFlying()) {
                if (!isNumeric(args[1])) {
                    p.sendMessage(MegaEssentials.Prefix + "§4Gebe eine gültige Zahl ein!");
                    return true;
                }
                p.setFlySpeed(Integer.parseInt(args[1]));
            } else {
                if (!isNumeric(args[1])) {
                    p.sendMessage(MegaEssentials.Prefix + "§4Gebe eine gültige Zahl ein!");
                    return true;
                }
                p.setWalkSpeed(Integer.parseInt(args[1]));
            }
        } else if (args.length == 2) {
            if (!isNumeric(args[1])) {
                p.sendMessage(MegaEssentials.Prefix + "§4Gebe eine gültige Zahl ein!");
                return true;
            }
            int speed = Integer.parseInt(args[1]);

            if (args[0].equalsIgnoreCase("walk")) {
                p.setWalkSpeed(speed);
            } else if (args[0].equalsIgnoreCase("fly")) {
                p.setFlySpeed(speed);
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutze §b/speed <§ageschwindigkeit§7/§3walk§7/§3speed> <§3geschwindigkeit>");
                return true;
            }

        }

        return true;
    }


    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
