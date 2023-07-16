package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.scoreboard.Teams;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.Locations;
import de.megaessentialsrecode.utils.Ränge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!(DataBase.exist(p))) {
            DataBase.setup(p);
        }
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Teams.create(p.getScoreboard());
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(MegaEssentials.getInstance(), () -> Teams.set(p.getScoreboard()), 1, 10);
        Ränge.putInTeamSchedulers(p, bukkitTask);
        e.setJoinMessage("");
        if (MegaEssentials.getInstance().getConfig().getBoolean("spawn.enabled")) {
            Locations.teleportToSpawn(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("");

        if (Ränge.getTeamScheduler().containsKey(p)) {
            Ränge.getTeamScheduler().get(p).cancel();
            Ränge.getTeamScheduler().remove(p);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }

}
