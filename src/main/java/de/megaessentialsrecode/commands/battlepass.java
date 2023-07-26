package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class battlepass implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        ItemStack glass_pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§b").build();

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Command ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length == 0)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/battlepass");
        }

        if (args.length == 0) {
            Inventory inv = Bukkit.createInventory(null, 5*9, "§bBattle§7-§bPass");

            for (int i = 0; i < 45; i++) {
                inv.setItem(i, glass_pane);
            }

            inv.setItem(19, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bDiamant Block").build());
            inv.setItem(20, new ItemBuilder(Material.EMERALD_ORE).setDisplayName("§aSmaragd Erz").build());
            p.openInventory(inv);
        }


        return true;
    }
}
