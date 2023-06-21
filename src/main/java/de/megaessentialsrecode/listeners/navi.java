package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.utils.Locations;
import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class navi implements Listener {


    private Plugin instance;

    public navi(Plugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals("§bNavigator")) {
            e.setCancelled(true);

            if(e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                MegaEssentials.connect(p, "CB-1");
                p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.ENDER_SIGNAL , 1);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1,2);
            }

            if(e.getCurrentItem().getType() == Material.TNT) {
                e.setCancelled(true);
                p.sendTitle("§4TNTRun", "§4ist in Wartungen!");
            }

            if(e.getCurrentItem().getType() == Material.RED_BED) {
                MegaEssentials.connect(p, "Bedwars-1");
                p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.ENDER_SIGNAL , 1);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1,2);
            }

            if(e.getCurrentItem().getType() == Material.RESPAWN_ANCHOR) {
                Bukkit.dispatchCommand(p, "spawn");
                Locations.teleportToSpawn(p);
                p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.ENDER_SIGNAL , 1);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1,2);
            }

        }

    }

}
