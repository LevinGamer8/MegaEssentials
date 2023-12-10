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
    private final String GITHUB_API_URL_VIAREWIND = "https://api.github.com/repos/ViaVersion/ViaRewind/releases/latest";
    private final String GITHUB_API_URL_VIABACKWARDS = "https://api.github.com/repos/ViaVersion/ViaBackwards/releases/latest";


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
                    PluginUtils pluginUtils = new PluginUtils();
                    pluginUtils.unload(oldviaVersionPlugin, sender);
                    updateUTILS.deleteOldVersion("viaversion", currentVersion, sender);
                    sender.sendMessage(MegaEssentials.Prefix + "§aViaVersion-" + latestVersion + " erfolgreich heruntergeladen.");
                } else {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Du hast bereits die neueste Version von ViaVersion installiert.");
                }
            } catch (IOException e) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Herunterladen der Datei: " + e.getMessage());
            }
            
            
        } else if (args[0].equalsIgnoreCase("viarewind")) {
            try {
                URL VIAVERSION_URL = new URL(GITHUB_API_URL_VIAREWIND);
                String currentVersion = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ViaRewind")).getDescription().getVersion();
                String latestVersion = updateUTILS.getLatestVersion(VIAVERSION_URL);
                if (!latestVersion.equals(currentVersion)) {
                    String downloadUrl = updateUTILS.getDownloadUrlForVersion(latestVersion, VIAVERSION_URL, "ViaRewind");
                    String destination = this.plugin.getDataFolder().getParentFile().getPath() + "/ViaRewind-" + latestVersion + ".jar";
                    updateUTILS.downloadFile(downloadUrl, destination);
                    Plugin oldviaVersionPlugin = Bukkit.getPluginManager().getPlugin("ViaRewind");
                    PluginUtils pluginUtils = new PluginUtils();
                    pluginUtils.unload(oldviaVersionPlugin, sender);
                    updateUTILS.deleteOldVersion("viarewind", currentVersion, sender);
                    sender.sendMessage(MegaEssentials.Prefix + "§aViaRewind-" + latestVersion + " erfolgreich heruntergeladen.");
                } else {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Du hast bereits die neueste Version von ViaRewind installiert.");
                }
            } catch (IOException e) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Herunterladen der Datei: " + e.getMessage());
            }
        } else if (args[0].equalsIgnoreCase("viabackwards")) {
        try {
            URL VIAVERSION_URL = new URL(GITHUB_API_URL_VIABACKWARDS);
            String currentVersion = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ViaBackwards")).getDescription().getVersion();
            String latestVersion = updateUTILS.getLatestVersion(VIAVERSION_URL);
            if (!latestVersion.equals(currentVersion)) {
                String downloadUrl = updateUTILS.getDownloadUrlForVersion(latestVersion, VIAVERSION_URL, "ViaBackwards");
                String destination = this.plugin.getDataFolder().getParentFile().getPath() + "/ViaBackwards-" + latestVersion + ".jar";
                updateUTILS.downloadFile(downloadUrl, destination);
                Plugin oldviaVersionPlugin = Bukkit.getPluginManager().getPlugin("ViaBackwards");
                PluginUtils pluginUtils = new PluginUtils();
                pluginUtils.unload(oldviaVersionPlugin, sender);
                updateUTILS.deleteOldVersion("viabackwards", currentVersion, sender);
                sender.sendMessage(MegaEssentials.Prefix + "§aViaBackwards-" + latestVersion + " erfolgreich heruntergeladen.");
            } else {
                sender.sendMessage(MegaEssentials.Prefix + "§4Du hast bereits die neueste Version von ViaBackwards installiert.");
            }
        } catch (IOException e) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Herunterladen der Datei: " + e.getMessage());
        }
    }


        return true;
    }
    
    
}