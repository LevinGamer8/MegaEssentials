package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.DataBase;
import de.megaessentialsrecode.utils.EconomyProvider;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.UUID;

public class AdminListener implements Listener {

    private EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§4ADMIN")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.SUNFLOWER) {
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
