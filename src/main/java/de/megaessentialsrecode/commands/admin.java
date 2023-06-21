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

public class admin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if (!(args.length == 0)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/admin");
            return true;
        }
        if (p.hasPermission("megacraft.command.admin")) {
            Inventory inv = Bukkit.createInventory(null, 3 * 9, "§4ADMIN");
            ItemStack glass_pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§b").build();
            for(int i = 0; i < 27; i++) {
                inv.setItem(i, glass_pane);
            }
            inv.setItem(2, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§6GELD").setLore("§6Gebe den Spielern Geld!").build());
            p.openInventory(inv);
        } else {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
        }
        return false;
    }
}
