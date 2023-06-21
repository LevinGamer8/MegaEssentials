package de.megaessentialsrecode.commands;

import java.util.ArrayList;
import java.util.List;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public class enchant implements CommandExecutor, TabCompleter {


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        if (!(sender.hasPermission("megacraft.command.enchant"))) {
            sender.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }

            if (!(args.length == 1) && (!(args.length == 2))) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/enchant §a[Verzauberung] §a<Stufe>");
                return true;
            }

            if (args.length == 1) {
                Player player = (Player)sender;
                if (player.getInventory().getItemInMainHand().getType().isAir()) {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Du musst ein Item in der Hand halten!");
                } else {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                        Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
                        if (itemMeta.hasEnchant(enchantment)) {
                            player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                            sender.sendMessage(MegaEssentials.Prefix + "§bDu hast die Verzauberung " + enchantment.getName() + " §4entfernt");
                        } else {
                            player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, 1);
                            sender.sendMessage(MegaEssentials.Prefix + "§bDu hast die Verzauberung " + enchantment.getName() + " §emit Stufe §61 §ahinzufügt");
                        }
                    }
                }
                if (args.length == 2) {
                    Player player = (Player)sender;
                    if (player.getInventory().getItemInMainHand().getType().isAir()) {
                        sender.sendMessage(MegaEssentials.Prefix + "§4Du musst ein Item in der hand halten!");
                    } else {
                        Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
                        if (Integer.valueOf(args[1]).intValue() > 0) {
                            player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, Integer.valueOf(args[1]).intValue());
                            sender.sendMessage(MegaEssentials.Prefix + "§bDu hast die Verzauberung " + enchantment.getName() + " §emit Stufe §6" + args[1] + " §ahinzufügt");
                        } else {
                            player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                            sender.sendMessage(MegaEssentials.Prefix + "§bDu hast die Verzauberung " + enchantment.getName() + " §4entfernt");
                        }
                    }
                }
            return true;
        }
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
            if (sender instanceof Player) {
                if (sender.hasPermission("megacraft.command.enchant")) {
                    if (args.length == 1)
                        for (Enchantment enchantment : Enchantment.values())
                            commands.add(enchantment.getName().toLowerCase());
                    if (args.length == 2)
                        commands.add(String.valueOf(Enchantment.getByName(args[0].toUpperCase()).getMaxLevel()));
                }
            }
            return commands;
        }
}
