package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cc implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] ars) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("megacraft.command.chatclear")) {
                p.sendMessage(MegaEssentials.Prefix + "§4Du hast keine Rechte!");
            }

            for(int i = 0; i < 1000; ++i) {
                Bukkit.broadcast("", "");
            }

            Bukkit.broadcast(MegaEssentials.Prefix + "§eDer Chat §7wurde §bgecleart§7.", "");
        } else {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Command geht nur als Spieler");
        }

        return false;
    }
}
