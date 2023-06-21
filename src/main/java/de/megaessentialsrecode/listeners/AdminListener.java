package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.EconomyProvider;
import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class AdminListener implements Listener {
    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();
    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§4ADMIN")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.GOLD_INGOT) {
                if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6GELD") {
                    Inventory inv = Bukkit.createInventory(null, 9 * 1, "§4ADMIN§7-§6GELD");
                    ItemStack glass_pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§b").build();
                    for(int i = 0; i < 27; i++) {
                        inv.setItem(i, glass_pane);
                    }
                    inv.setItem(7, new ItemBuilder(Material.SUNFLOWER).setDisplayName("§6Pay * 10.000").setLore("§6Sei ein §bEhren§e-§4Admin §6und paye jedem registriertem Spieler §a10.000 §b€").build());
                    p.openInventory(inv);

                }
            }
        }
        if (e.getView().getTitle().equals("§4ADMIN§7-§6GELD")) {
            if (e.getCurrentItem().getType() == Material.SUNFLOWER) {
                if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Pay * 10.000")
                    p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.BLAZE_SHOOT, 1);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                Double amount = Double.parseDouble("10000");
                List<String> registeredPlayers = DataBase.getAllRegisteredPlayers();
                for (String playerUUID : registeredPlayers) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                    if (DataBase.exist(offlinePlayer)) {
                        DataBase.addEconomy(offlinePlayer, amount);
                        if (offlinePlayer.isOnline()) {
                            Player target = Bukkit.getPlayer(offlinePlayer.getName());
                            target.sendMessage(MegaEssentials.Prefix + "§6" + p.getName() + " §bhat dir §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §agegeben.");
                        }
                    }
                }
                p.sendMessage(MegaEssentials.Prefix + "§6" + registeredPlayers.stream().count() + " §bSpieler haben jeweils §6" + this.economyProvider.format(amount) + " §b" + this.economyProvider.currencyNameSingular() + " §aerhalten.");
            }
        }
    }
}
