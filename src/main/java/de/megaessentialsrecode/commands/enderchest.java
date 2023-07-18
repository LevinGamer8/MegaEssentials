package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class enderchest implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 0 || args.length == 1)) {

            if (p.hasPermission("megacraft.commands.enderchest.others")) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/enderchest <Spieler>");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/enderchest");
            }
            return true;
        }

        if (args.length == 0) {
            Inventory enderchest = p.getEnderChest();
            p.openInventory(enderchest);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            Inventory enderchest = target.getEnderChest();
            p.openInventory(enderchest);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.enderchest.others")) {
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