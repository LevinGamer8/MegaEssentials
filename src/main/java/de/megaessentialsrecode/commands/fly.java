package de.megaessentialsrecode.commands;
import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class fly implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.fly"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (args.length == 0) {
            toggleFlight(p);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null) {
                toggleFlight(target);
                p.sendMessage(MegaEssentials.Prefix + "Der Flugmodus von " + target.getName() + " wurde geändert.");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + MegaEssentials.getPluginName() + " online.");
            }
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/fly §a<Spieler>");
        }

        return true;
    }

    private void toggleFlight(Player player) {
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(MegaEssentials.Prefix + "§6Du §4kannst nun nicht mehr §bFliegen");
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(MegaEssentials.Prefix + "§6Du §akannst nun §bFliegen");
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.fly.others")) {
                if (args.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            }
        }
        return completions;
    }
}
