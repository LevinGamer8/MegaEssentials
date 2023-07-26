package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class BattlePassListener implements Listener {

    private final Plugin plugin;

    int ekills = 0;

    public BattlePassListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            ekills++;
            setEntityKills(p, ekills);
        }
    }



    public void setEntityKills(Player player, int kills) {
        player.setMetadata("EKILLS", new FixedMetadataValue(plugin, kills));
    }

    public int getEntityKills(Player player) {
        if (player.hasMetadata("EKILLS")) {
            return player.getMetadata("EKILLS").get(0).asInt();
        }
        return 0;
    }

    public void checkPass(Player p) {
        if (getEntityKills(p) >= 50) {
            p.setMetadata("BPS.1", new FixedMetadataValue(plugin, true));
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§bDu hast §a" + getEntityKills(p) + "§7/§b50");
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if(e.getView().getTitle().equals("§bBattle§7-§bPass")) {
            checkPass(p);

            if (p.hasMetadata("BPS.1")) {
                if (item.getType() == Material.DIAMOND_BLOCK) {
                    e.setCancelled(true);
                    if (item.getItemMeta().getDisplayName().equals("§bDiamant Block")) {
                        e.getView().close();
                        p.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bDiamant Block").setLore("§7Signiert von §3MegaCraft").build());
                        p.sendTitle("§bDiamant Block", "§aErfolgreich eingefordert§7!");
                    }
                }
            } else {
                e.setCancelled(true);
            }





        }
    }

}
