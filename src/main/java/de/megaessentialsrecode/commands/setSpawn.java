package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class setSpawn implements CommandExecutor {

    private Plugin plugin;
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
            if (plugin.getConfig().get("spawn.x") == "1" && plugin.getConfig().get("spawn.y") == "1" && plugin.getConfig().get("spawn.z") == "1") {
                plugin.getConfig().set("spawn.x", p.getLocation().getBlockX());
                plugin.getConfig().set("spawn.y", p.getLocation().getBlockY());
                plugin.getConfig().set("spawn.z", p.getLocation().getBlockZ());
                plugin.getConfig().set("spawn.world", p.getWorld().getName());
                p.sendMessage(MegaEssentials.Prefix + "§bDer Spawn wurde gesetzt!");
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§bDer Spawn wurde bereits gesetzt, um ihn erneut zu ändern, tue dies in der Config");
            }
        }
        return false;
    }
}
