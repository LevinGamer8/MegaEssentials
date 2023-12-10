package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.utils.TPAHandler;
import de.megaessentialsrecode.utils.TPAUtils;
import de.megaessentialsrecode.MegaEssentials;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class tpahere implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Befehl ist nur für Spieler!");
            return true;
        }

        Player p = (Player) sender;
        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §6/tpahere §a[Spieler]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if ((target == null)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Der Spieler ist nicht auf §3" + MegaEssentials.getPluginName() + " §aonline.");
            return true;
        }




        target.sendMessage(MegaEssentials.Prefix + "§7Du hast eine §eTPAHere §7von §e" + p.getName() + " §7erhalten.");

        TextComponent acceptMSG = new TextComponent(MegaEssentials.Prefix + "§7Führe §a/tpaccept §e" + p.getName() + " §7aus, um die Anfrage §aanzunehmen§7.");
        acceptMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7Klicke, um die Anfrage §aanzunehmen.").create()));
        acceptMSG.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + p.getName()));
        target.spigot().sendMessage(acceptMSG);

        TextComponent denyMSG = new TextComponent(MegaEssentials.Prefix + "§7Führe §c/tpdeny §7aus, um die Anfrage §cabzulehnen.");
        denyMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7Klicke, um die Anfrage §cabzulehnen.").create()));
        denyMSG.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + p.getName()));
        target.spigot().sendMessage(denyMSG);


        p.sendMessage(MegaEssentials.Prefix + "§6Du hast §e" + target.getName() + " §agefragt ob er sich zu §6dir §bteleportieren §amöchte§7.");


        Location tpaloc = p.getLocation();
        TPAUtils tpaUtils = new TPAUtils(p, target, tpaloc, true);
        TPAHandler.initiateRequest(tpaUtils);

        return false;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("megacraft.command.tpahere")) {
                if (args.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            }
        }
        return completions;
    }
}
