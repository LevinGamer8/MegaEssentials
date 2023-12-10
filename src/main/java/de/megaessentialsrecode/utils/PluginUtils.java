package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginUtils {




    public void unload(Plugin plugin, CommandSender sender) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.disablePlugin(plugin);
        sender.sendMessage(MegaEssentials.Prefix + "§bWenn keine §4Fehler §baufgekommen sind kannst du den §3Server §anun neustarten.");
    }

}
