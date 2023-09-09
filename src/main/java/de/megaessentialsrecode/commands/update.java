package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class update implements CommandExecutor {

    private final Plugin plugin;

    private static final String GITHUB_OWNER = "ViaVersion";

    private static final String GITHUB_REPO = "ViaVersion";

    private static final String GITHUB_API_URL = "https://api.github.com/repos/ViaVersion/ViaVersion/releases/latest";

    private String newversion;

    public update(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        try {
            String currentVersion = Bukkit.getPluginManager().getPlugin("ViaVersion").getDescription().getVersion();
            String latestVersion = getLatestVersion();
            if (!latestVersion.equals(currentVersion)) {
                String downloadUrl = getDownloadUrlForVersion(latestVersion);
                String destination = this.plugin.getDataFolder().getParentFile().getPath() + "/ViaVersion-" + latestVersion + ".jar";
                this.newversion = latestVersion;
                downloadFile(downloadUrl, destination);
                Plugin oldviaVersionPlugin = Bukkit.getPluginManager().getPlugin("ViaVersion");
                PluginUtils pluginUtils = new PluginUtils(plugin);
                pluginUtils.unload(oldviaVersionPlugin.getName());
                deleteOldVersion(currentVersion);
                sender.sendMessage("ViaVersion-" + latestVersion + " erfolgreich heruntergeladen.");
            } else {
                sender.sendMessage("Du hast bereits die neueste Version von ViaVersion installiert.");
            }
        } catch (IOException e) {
            sender.sendMessage("Fehler beim Herunterladen der Datei: " + e.getMessage());
        }
        return true;
    }

    public static void downloadFile(String url, String destination) throws IOException {
        URL fileUrl = new URL(url);
        BufferedInputStream in = new BufferedInputStream(fileUrl.openStream());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destination);
            try {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1)
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                fileOutputStream.close();
            } catch (Throwable throwable) {
                try {
                    fileOutputStream.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
            in.close();
        } catch (Throwable throwable) {
            try {
                in.close();
            } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
            }
            throw throwable;
        }
    }

    private String getLatestVersion() throws IOException {
        URL url = new URL("https://api.github.com/repos/ViaVersion/ViaVersion/releases/latest");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            String latestVersion = responseBody.contains("tag_name") ? responseBody.split("\"tag_name\":\"")[1].split("\"")[0] : null;
            return latestVersion;
        }
        throw new IOException("Fehler beim Abrufen der API-Antwort. Response Code: " + responseCode);
    }

    private String getDownloadUrlForVersion(String version) throws IOException {
        URL url = new URL("https://api.github.com/repos/ViaVersion/ViaVersion/releases/latest");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            String downloadUrl = null;
            JSONObject jsonObject = new JSONObject(responseBody);
            if (jsonObject.has("assets")) {
                JSONArray assetsArray = jsonObject.getJSONArray("assets");
                for (int i = 0; i < assetsArray.length(); i++) {
                    JSONObject asset = assetsArray.getJSONObject(i);
                    String assetName = asset.getString("name");
                    if (assetName.equals("ViaVersion-" + version + ".jar")) {
                        downloadUrl = asset.getString("browser_download_url");
                        break;
                    }
                }
            }
            if (downloadUrl != null)
                return downloadUrl;
            throw new IOException("Download-Link fdie angegebene Version nicht gefunden.");
        }
        throw new IOException("Fehler beim Abrufen der API-Antwort. Response Code: " + responseCode);
    }

    private void deleteOldVersion(String version) {
        File oldFile = new File(this.plugin.getDataFolder().getParentFile().getPath(), "ViaVersion-" + version + ".jar");
        if (oldFile.exists()) {
            try {
                if (oldFile.delete()) {
                    this.plugin.getLogger().info("Alte Version (v" + version + ") von ViaVersion erfolgreich gelöscht");
                } else {
                    this.plugin.getLogger().warning("Fehler beim Lder alten Version (v" + version + ") von ViaVersion: Delete-Fehler");
                }
            } catch (SecurityException e) {
                this.plugin.getLogger().warning("Fehler beim Lder alten Version (v" + version + ") von ViaVersion: Zugriffsfehler");
                e.printStackTrace();
            }
        } else {
            this.plugin.getLogger().warning("Fehler beim Lder alten Version (v" + version + ") von ViaVersion: Datei nicht gefunden");
        }
    }
}