package de.megaessentialsrecode.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ChestShopListener implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (isWallSign(clickedBlock.getType())) {
            Sign sign = (Sign) clickedBlock.getState();

            player.sendMessage("test");

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

    @EventHandler
    public void SignCreate(SignChangeEvent e) {
        Block changedBlock = e.getBlock();
        Player creator = e.getPlayer();
        String line1 = e.getLine(0);
        String line2 = e.getLine(1);
        String line3 = e.getLine(2);
        String line4 = e.getLine(3);
        Sign sign = (Sign) changedBlock.getState();

        if (line1.equals("") || line1.startsWith(" ")) {
            e.setLine(0, creator.getName());
            line1.replace("", creator.getName());
            line1.replace(" ", creator.getName());
        }


        Player owner = Bukkit.getPlayer(line1);


        owner.sendMessage("hi");

        if (line3.startsWith("K")) {

            line3.split(" ");

            Double.parseDouble(line3);

            creator.sendMessage("test");


        } else if (line3.startsWith("V")) {

        }

        if (line4.contains("?")) {
            Block attachedBlock = changedBlock.getRelative(0,0 ,-1);
            if (attachedBlock.getType() == Material.CHEST ) {
                Chest chest = (Chest) attachedBlock.getState();
                Material item = chest.getBlockInventory().getItem(1).getType();
                String itemName = item.name().toString();
                e.setLine(3, itemName);
            }
        }


        Material item = Material.matchMaterial(line4);

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
                material == Material.SPRUCE_WALL_SIGN ||
                material == Material.ACACIA_SIGN ||
                material == Material.OAK_SIGN ||
                material == Material.BIRCH_SIGN ||
                material == Material.JUNGLE_SIGN ||
                material == Material.WARPED_SIGN ||
                material == Material.CRIMSON_SIGN ||
                material == Material.DARK_OAK_SIGN ||
                material == Material.MANGROVE_SIGN ||
                material == Material.SPRUCE_SIGN;

    }
}
