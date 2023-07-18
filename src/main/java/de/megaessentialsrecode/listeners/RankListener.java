package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.scoreboard.Teams;
import de.megaessentialsrecode.utils.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class RankListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Teams.create(p.getScoreboard());
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(MegaEssentials.getInstance(), () -> Teams.set(p.getScoreboard()), 1, 10);
        Ranks.putInTeamSchedulers(p, bukkitTask);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Ranks.getTeamScheduler().containsKey(p)) {
            Ranks.getTeamScheduler().get(p).cancel();
            Ranks.getTeamScheduler().remove(p);
        }
    }

}
