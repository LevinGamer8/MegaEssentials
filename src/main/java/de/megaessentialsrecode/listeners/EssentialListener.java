package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.Locations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class EssentialListener implements org.bukkit.event.Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!(DataBase.exist(p))) {
            DataBase.setup(p);
        }
        e.setJoinMessage("");
        if (MegaEssentials.getInstance().getConfig().getBoolean("spawn.enabled")) {
            Locations.teleportToSpawn(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }


    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/") && e.getMessage().contains(":")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(MegaEssentials.Prefix + "§4Dieser Command wurde nicht gefunden!");
        }
        final List<String> blockedCommands = MegaEssentials.getInstance().getConfig().getStringList("blocked_commands");
        if (!(e.getPlayer().hasPermission("megacraft.commands.blocked.bypass"))) {
            for (final String command : blockedCommands) {
                if (e.getMessage().toLowerCase().startsWith("/" + command)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(MegaEssentials.Prefix + "§4Dieser Command wurde nicht gefunden!");
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
       if (e.getSender() instanceof Player) {
           Player p = (Player) e.getSender();
           String[] args = e.getBuffer().split(" ");
           String command = args[0].toLowerCase();
           final List<String> blockedCommands = MegaEssentials.getInstance().getConfig().getStringList("blocked_commands");
           if (blockedCommands.contains(command)) {
               e.setCancelled(true);
               return;
           }

           List<String> completions = new ArrayList<>(e.getCompletions());
           completions.removeIf(completion -> completion.startsWith("plugin"));
           e.setCompletions(completions);
       }
    }

}
