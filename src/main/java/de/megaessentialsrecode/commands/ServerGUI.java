package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class ServerGUI {

    Inventory inv = Bukkit.createInventory(null, 1*9, "§4Server§7-§4Manager");

    public Inventory ServerGUI() {
        createGUI();
        return inv;
    }

    private void createGUI() {
        inv.setItem(1, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bCB-1").build());
        inv.setItem(3, new ItemBuilder(Material.RESPAWN_ANCHOR).setDisplayName("§aLobby").build());
        inv.setItem(5, new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("§bFW-1").build());
        inv.setItem(7, new ItemBuilder(Material.IRON_AXE).setDisplayName("§4GTA").build());

    }



}
