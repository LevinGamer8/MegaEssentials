package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class gamemode implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender.hasPermission("megacraft.command.gamemode"))) {
            sender.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 2) {

            Player target = Bukkit.getPlayer(args[1]);

            if ((target == null)) {
                sender.sendMessage(MegaEssentials.Prefix + "Der Spieler ist nicht auf " + MegaEssentials.getServerName() + " online.");
                return true;
            }

            switch (args[0]) {

                case "survival":
                case "s":
                case "0":
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.SURVIVAL + " §bgeändert");
                    p.sendMessage(MegaEssentials.Prefix + "§6" + target.getName() + "'s §3Spielmodus §bwurde zu §e" + GameMode.SURVIVAL + " §bgeändert");
                    break;
                case "creative":
                case "c":
                case "1":
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.CREATIVE + " §bgeändert");
                    p.sendMessage(MegaEssentials.Prefix + "§6" + target.getName() + "'s §3Spielmodus §bwurde zu §e" + GameMode.CREATIVE + " §bgeändert");
                    break;
                case "adventure":
                case "a":
                case "2":
                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.ADVENTURE + " §bgeändert");
                    p.sendMessage(MegaEssentials.Prefix + "§6" + target.getName() + "'s §3Spielmodus §bwurde zu §e" + GameMode.ADVENTURE + " §bgeändert");
                    break;
                case "spectator":
                case "sp":
                case "3":
                    target.setGameMode(GameMode.SPECTATOR);
                    target.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.SPECTATOR + " §bgeändert");
                    p.sendMessage(MegaEssentials.Prefix + "§6" + target.getName() + "'s §3Spielmodus §bwurde zu §e" + GameMode.SPECTATOR + " §bgeändert");
                    break;
            }


        } else if (args.length == 1) {

            switch (args[0]) {

                case "survival":
                case "s":
                case "0":
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.SURVIVAL + " §bgeändert");
                    break;
                case "creative":
                case "c":
                case "1":
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.CREATIVE + " §bgeändert");
                    break;
                case "adventure":
                case "a":
                case "2":
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.ADVENTURE + " §bgeändert");
                    break;
                case "spectator":
                case "sp":
                case "3":
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(MegaEssentials.Prefix + "§6Dein §3Spielmodus §bwurde zu §e" + GameMode.SPECTATOR + " §bgeändert");
                    break;
            }

        } else {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/gamemode §e[Spielmodus] §6<Spieler>");
        }


        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                for (GameMode gameMode : GameMode.values()) {
                    commands.add(gameMode.name().toLowerCase());
                }
            } else if (args.length == 2) {
                commands.addAll(getOnlinePlayerNames());
            }
        }
        return commands;
    }

    private List<String> getOnlinePlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}
