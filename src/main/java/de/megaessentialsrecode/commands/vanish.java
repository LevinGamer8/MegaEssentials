package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class vanish implements CommandExecutor {

    private Plugin plugin;

    public vanish(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {

            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("megacraft.command.vanish"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

        if (args.length == 0) {
            PlayerData senderP = new PlayerData(p.getName());
            if (senderP.isVanished()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(plugin, p);
                }
                senderP.setVanish(false);
                p.sendMessage(MegaEssentials.Prefix + "§bDu bist nun §csichtbar§7!");
            } else {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!(player.hasPermission("megacraft.commands.vanish.see"))) {
                        player.hidePlayer(plugin, p);
                    }
                }
                senderP.setVanish(true);
                p.sendMessage(MegaEssentials.Prefix + "§bDu bist nun §aunsichtbar§7!");
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            PlayerData targetDP = new PlayerData(target.getName());
            if (targetDP.isVanished()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(plugin, target);
                }
                targetDP.setVanish(false);
                target.sendMessage(MegaEssentials.Prefix + "§bDu bist nun §csichtbar§7!");
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!(player.hasPermission("megacraft.commands.vanish.see"))) {
                        player.hidePlayer(plugin, target);
                    }
                }
                p.sendMessage("§6" + target.getName() + "§bist nun §aunsichtbar§7!");
                target.sendMessage(MegaEssentials.Prefix + "§bDu bist nun §aunsichtbar§7!");
            }
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutze §b/vanish <Spieler>");
        }

        return true;
    }
}
