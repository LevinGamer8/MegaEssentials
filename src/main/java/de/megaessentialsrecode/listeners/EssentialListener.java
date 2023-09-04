package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.Locations;
import de.megaessentialsrecode.utils.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EssentialListener implements org.bukkit.event.Listener {

    private Plugin plugin;
    public EssentialListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerData pd = new PlayerData(p.getName());
        pd.createPlayer(p.getName());
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
    public void onRPLoad(PlayerResourcePackStatusEvent e) {
        if (e.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED) {
            e.getPlayer().setMetadata("RPLoading", new FixedMetadataValue(plugin, true));
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            e.getPlayer().removeMetadata("RPLoading", plugin);
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            //kick Player via Bungeecord
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            //kick Player via Bungeecord
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();
            if (p.hasMetadata("RPLoading")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (p.hasMetadata("RPLoading")) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args[0].startsWith("/") && args[0].contains(":")) {
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
           List<String> completions = new ArrayList<>(e.getCompletions());
           completions.removeIf(completion -> completion.startsWith("plugin"));
           e.setCompletions(completions);
       }
    }

    @EventHandler
    public void onList(PlayerCommandSendEvent event) {
        if (!event.getPlayer().hasPermission("megacraft.blockedcommands.bypass")) {
            Iterator<String> it = event.getCommands().iterator();
            String str;
                while (it.hasNext()) {
                    str = (String) it.next();
                    if (str.contains(":")) {
                        it.remove();
                    }
                }
                for (int i = 0; i < MegaEssentials.getInstance().getConfig().getStringList("blocked_commands").size(); i++) {
                    if (!event.getPlayer().hasPermission("megacraft.blockedcommands.bypass"))
                        event.getCommands().remove(MegaEssentials.getInstance().getConfig().getStringList("blocked_commands").get(i));
                }
        }
    }

}
