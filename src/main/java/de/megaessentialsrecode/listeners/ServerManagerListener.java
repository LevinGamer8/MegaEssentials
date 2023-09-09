package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ServerManagerListener implements Listener {

    @EventHandler
    public void onInteractMain(InventoryClickEvent e) {
        if (e.getView().getTitle() != null && e.getView().getTitle().equals("§4Server§7-§4Manager")) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                    // Code für das Klicken auf den Diamantblock hier hinzufügen
                } else if (e.getCurrentItem().getType() == Material.RESPAWN_ANCHOR) {
                    Inventory lobby = Bukkit.createInventory(null, 1 * 9, "§aLobby");
                    lobby.setItem(2, new ItemBuilder(Material.RED_WOOL).setDisplayName("§4Stoppen").build());
                    lobby.setItem(4, new ItemBuilder(Material.GREEN_WOOL).setDisplayName("§aStarten").build());
                    lobby.setItem(6, new ItemBuilder(Material.CYAN_WOOL).setDisplayName("§bNeu§7-§aStarten").build());
                    e.getWhoClicked().openInventory(lobby);
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getType() == Material.GRASS_BLOCK) {
                    // Code für das Klicken auf den Grasblock hier hinzufügen
                } else if (e.getCurrentItem().getType() == Material.IRON_AXE) {
                    // Code für das Klicken auf die Eisennaxt hier hinzufügen
                }
            }
        }
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("§aLobby")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.RED_WOOL) {
                // Code für das Klicken auf das rote Wollitem zum Stoppen des Servers hier hinzufügen
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("§bCB-1")) {
            // Code für das Klicken auf das Inventar "§bCB-1" hier hinzufügen
        }
    }
}
