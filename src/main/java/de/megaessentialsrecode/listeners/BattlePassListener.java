package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class BattlePassListener implements Listener {

    private final Plugin plugin;

    private final String EKILLS_METADATA = "BPS.EKILLS";
    private final String STONEBROKEN_METADATA = "BPS.STONEBROKEN";
    private final String BPS_1_METADATA = "BPS.1";
    private final String BPS_2_METADATA = "BPS.2";

    public BattlePassListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            setEntityKills(p, getEntityKills(p) + 1);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (block.getType() == Material.STONE) {
            setStoneBroken(p, getStonebrokens(p) + 1);
        }
    }

    private void setStoneBroken(Player player, int stonebrokens) {
        player.setMetadata(STONEBROKEN_METADATA, new FixedMetadataValue(plugin, stonebrokens));
    }

    public int getStonebrokens(Player player) {
        if (player.hasMetadata(STONEBROKEN_METADATA)) {
            return player.getMetadata(STONEBROKEN_METADATA).get(0).asInt();
        }
        return 0;
    }

    public void setEntityKills(Player player, int kills) {
        player.setMetadata(EKILLS_METADATA, new FixedMetadataValue(plugin, kills));
    }

    public int getEntityKills(Player player) {
        if (player.hasMetadata(EKILLS_METADATA)) {
            return player.getMetadata(EKILLS_METADATA).get(0).asInt();
        }
        return 0;
    }

    public void checkPass(Player p) {

        if (p.hasMetadata("BPS.0")) {
        if (getEntityKills(p) >= 50) {
            p.setMetadata(BPS_1_METADATA, new FixedMetadataValue(plugin, true));
            p.removeMetadata(BPS_2_METADATA, plugin);
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§bDu hast §a" + getEntityKills(p) + "§7/§b50");
        }
        } else if (p.hasMetadata(BPS_1_METADATA)) {
            if (getStonebrokens(p) >= 10) {
                p.setMetadata(BPS_2_METADATA, new FixedMetadataValue(plugin, true));
            } else {
                p.sendMessage(MegaEssentials.Prefix + "§bDu hast §a" + getEntityKills(p) + "§7/§b100");
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getView().getTitle().equals("§bBattle§7-§bPass")) {
            checkPass(p);
            if (p.hasMetadata(BPS_1_METADATA)) {
                if (item.getType() == Material.DIAMOND_BLOCK) {
                    e.setCancelled(true);
                    if (item.getItemMeta().getDisplayName().equals("§bDiamant Block")) {
                        e.getView().close();
                        p.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bDiamant Block").setLore("§7Signiert von §3MegaCraft").build());
                        p.sendTitle("§bDiamant Block", "§aErfolgreich eingefordert§7!");
                        p.removeMetadata("BPS.0", plugin);
                    }
                }
                checkPass(p);
            } else if (p.hasMetadata(BPS_2_METADATA)) {
                if (item.getType() == Material.EMERALD_ORE) {
                    e.setCancelled(true);
                    if (item.getItemMeta().getDisplayName().equals("§aSmaragd Erz")) {
                        e.getView().close();
                        p.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§aSmaragd Erz").setLore("§7Signiert von §3MegaCraft").build());
                        p.sendTitle("§aSmaragd Erz", "§aErfolgreich eingefordert§7!");
                        p.removeMetadata(BPS_2_METADATA, plugin);
                    }
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
}
