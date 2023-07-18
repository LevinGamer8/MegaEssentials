package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class tpahere implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;
        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpahere §a[Spieler]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if ((target == null)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Der Spieler ist nicht auf §3" + MegaEssentials.getPluginName() + " §aonline.");
            return true;
        }

        target.sendMessage(MegaEssentials.Prefix + "§a" + p.getName() + " §6fragt, ob du dich zu ihm §bteleportieren §6möchtest§7.\n§bZum annehmen /tpaccept");
        p.sendMessage(MegaEssentials.Prefix + "§6Du hast §b" + target.getName() + " §agefragt ob er sich zu §6dir §bteleportieren §amöchte§7.");
        Location tpaloc = p.getLocation();

        TPAUtils tpaUtils = new TPAUtils(target, p, tpaloc, true);
        TPAHandler.initiateRequest(tpaUtils);

        return false;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.tpahere")) {
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
