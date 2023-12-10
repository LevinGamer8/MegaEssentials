package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PlayerData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class home implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;

        if (!(args.length >= 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/home §7<§aadd §7[§bNAME§7] | §adelete §7[§bNAME§7] | §alist §7| [§bNAME§7]>");
            return true;
        }
        PlayerData pd = new PlayerData(p.getName());

        if (args[0].equalsIgnoreCase("add")) {

            if (!(args.length == 2)) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/home §aadd §7[§bNAME§7]");
                return true;
            }

            pd.addHome(args[1], p.getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.sendMessage(MegaEssentials.Prefix + "§aDu hast erfolgreich dein §bHome " + args[1] + " §aerstellt.");

        } else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")) {

            if (!(args.length == 2)) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/home §cdelete §7[§bNAME§7]");
                return true;
            }
            pd.removeHome(args[1], p);

        } else if (args[0].equalsIgnoreCase("list")) {

            List<String> homeNames = pd.getHomeNames();
            TextComponent homeMessage = new TextComponent("§aDeine §bHomes:\n");

            for (String homeName : homeNames) {
                TextComponent homeNameComponent = new TextComponent("\n§7- §b" + homeName + "\n");
                homeNameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("§aKlicke, um dich zu §b" + homeName + " zu teleportieren.").create()));
                homeNameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + homeName));

                homeMessage.addExtra(homeNameComponent);
            }

            p.spigot().sendMessage(homeMessage);

        } else if (!(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("list"))) {
            if (!(args.length == 1)) {
                p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/home §7[§bNAME§7]");
                return true;
            }
            if (pd.isHomeExisting(args[0])) {
                p.teleport(pd.getHomeLocation(args[0]));
                p.sendMessage(MegaEssentials.Prefix + "§aDu wurdest zu deinem §bHome " + args[0] + " §bteleportiert§7.");
            }
        }
        return true;
    }




    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                completions.add("add");
                completions.add("delete");
                completions.add("list");
                completions.add("[NAME]");
            } else if (args.length == 2) {
                completions.add("[NAME]");
            }
        }
        return completions;
    }


}