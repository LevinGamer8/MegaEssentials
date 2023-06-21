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
            inv.setItem(7, new ItemBuilder(Material.SUNFLOWER).setDisplayName("§6Pay * 10.000").setLore("§6Sei ein §bEhren§e-§4Admin §6und paye jedem registriertem Spieler §a10.000 §b€").build());
            p.openInventory(inv);
        } else {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
        }
        return false;
    }
}
