package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class setSpawn implements CommandExecutor {

    private final Plugin plugin;
    public setSpawn(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;
        if (p.hasPermission("megacraft.command.setspawn")) {
                plugin.getConfig().set("spawn.x", p.getLocation().getX());
                plugin.getConfig().set("spawn.y", p.getLocation().getY());
                plugin.getConfig().set("spawn.z", p.getLocation().getZ());
                plugin.getConfig().set("spawn.world", p.getWorld().getName());
                plugin.saveConfig();
                p.sendMessage(MegaEssentials.Prefix + "§bDer Spawn wurde gesetzt!");
        }
        return false;
    }
}
