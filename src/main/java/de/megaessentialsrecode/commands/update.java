package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.PluginUtils;
import de.megaessentialsrecode.utils.UpdateUTILS;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.util.Objects;

public class update implements CommandExecutor {

    private final Plugin plugin;
    
    private final UpdateUTILS updateUTILS;

    private final String GITHUB_API_URL_VIAVERSION = "https://api.github.com/repos/ViaVersion/ViaVersion/releases/latest";


    public update(Plugin plugin, UpdateUTILS updateUTILS) {
        this.plugin = plugin;
        this.updateUTILS = updateUTILS;
    }
    
    

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args[0].equalsIgnoreCase("viaversion")) {
            try {
                URL VIAVERSION_URL = new URL(GITHUB_API_URL_VIAVERSION);
                String currentVersion = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ViaVersion")).getDescription().getVersion();
                String latestVersion = updateUTILS.getLatestVersion(VIAVERSION_URL);
                if (!latestVersion.equals(currentVersion)) {
                    String downloadUrl = updateUTILS.getDownloadUrlForVersion(latestVersion, VIAVERSION_URL, "ViaVersion");
                    String destination = this.plugin.getDataFolder().getParentFile().getPath() + "/ViaVersion-" + latestVersion + ".jar";
                    updateUTILS.downloadFile(downloadUrl, destination);
                    Plugin oldviaVersionPlugin = Bukkit.getPluginManager().getPlugin("ViaVersion");
                    PluginUtils pluginUtils = new PluginUtils(plugin);
                    pluginUtils.unload(oldviaVersionPlugin, sender);
                    updateUTILS.deleteOldVersion(currentVersion, sender);
                    sender.sendMessage(MegaEssentials.Prefix + "§aViaVersion-" + latestVersion + " erfolgreich heruntergeladen.");
                } else {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Du hast bereits die neueste Version von ViaVersion installiert.");
                }
            } catch (IOException e) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Herunterladen der Datei: " + e.getMessage());
            }
            
            
        } else if (args[0].equalsIgnoreCase("worldedit")) {
            
        }


        return true;
    }
    
    
}