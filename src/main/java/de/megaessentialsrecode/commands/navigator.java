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

public class navigator implements CommandExecutor {
    ItemStack glass_pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§b").build();
    @Override
    public boolean onCommand(CommandSender sender,  Command command,  String s,  String[] args) {
        Player p = (Player) sender;
        if (!(args.length == 0)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/navi");
            return true;
        }
        Inventory inv = Bukkit.createInventory(null, 3 * 9, "§bNavigator");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, glass_pane);
        }
        inv.setItem(2, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bCityBuild").setLore("§aBaue deine Welt auf Grundstücken§7.").build());
        inv.setItem(4, new ItemBuilder(Material.RED_BED).setDisplayName("§cBedwars").setLore("§eKlassiches Bedwars, wie man es kennt§7.").build());
        if (MegaEssentials.getInstance().getConfig().getBoolean("spawn.enabled")) {
            inv.setItem(13, new ItemBuilder(Material.RESPAWN_ANCHOR).setDisplayName("§eSpawn").setLore("§aTeleportiere dich zurück zum spawn§7.").build());
        }
        p.openInventory(inv);
        return false;
    }
}
