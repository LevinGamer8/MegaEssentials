package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BattlePassListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if(e.getView().getTitle().equals("§bBattle§7-§bPass")) {
            if (item.getType() == Material.DIAMOND_BLOCK) {
                if (item.getItemMeta().getDisplayName() == "§bDiamant Block") {
                    e.setCancelled(true);
                    e.getView().close();
                    p.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bDiamant Block").setLore("§3MegaCraft").build());
                    p.sendTitle("§bDiamant Block", "§aerfolgreich eingefordert");
                }
            }
        }
    }

}
