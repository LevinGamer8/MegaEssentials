package de.megaessentialsrecode.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestShopListener implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && isWallSign(clickedBlock.getType())) {
            Sign sign = (Sign) clickedBlock.getState();

            String line1 = sign.getLine(0);
            String line2 = sign.getLine(1);
            String line3 = sign.getLine(2);
            String line4 = sign.getLine(3);

            Player user = (Player) Bukkit.getOfflinePlayer(line1);
            int amount = Integer.parseInt(line2);
            boolean isBuying = line3.startsWith("K");
            double price = Double.parseDouble(line3.substring(1));

            if (!(user == player)) {

                player.sendMessage("Menge: " + amount);
                player.sendMessage(isBuying ? "Kaufen" : "Verkaufen");
                player.sendMessage("Preis: " + price);
            }
        }
    }

    private boolean isWallSign(Material material) {
        return material == Material.ACACIA_WALL_SIGN ||
                material == Material.OAK_WALL_SIGN ||
                material == Material.BIRCH_WALL_SIGN ||
                material == Material.JUNGLE_WALL_SIGN ||
                material == Material.WARPED_WALL_SIGN ||
                material == Material.CRIMSON_WALL_SIGN ||
                material == Material.DARK_OAK_WALL_SIGN ||
                material == Material.MANGROVE_WALL_SIGN ||
                material == Material.SPRUCE_WALL_SIGN;
    }
}
