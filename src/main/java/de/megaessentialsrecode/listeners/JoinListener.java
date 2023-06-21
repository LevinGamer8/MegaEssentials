package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.utils.DataBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {


        Player p = e.getPlayer();

        if (!(DataBase.exist(p))) {
            DataBase.setup(p);
        }

    }

}
