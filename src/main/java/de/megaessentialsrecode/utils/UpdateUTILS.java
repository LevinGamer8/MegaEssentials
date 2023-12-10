package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateUTILS {


    private final Plugin plugin;

    public UpdateUTILS(Plugin plugin) {
        this.plugin = plugin;
    }




    public void downloadFile(String url, String destination) throws IOException {
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

    public String getLatestVersion(URL downloadURL) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)downloadURL.openConnection();
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

    public String getDownloadUrlForVersion(String version, URL url, String plugin) throws IOException {
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
                    if (assetName.equals(plugin + "-" + version + ".jar")) {
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

    public void deleteOldVersion(String plugin,String version, CommandSender sender) {
        File oldFile = new File(this.plugin.getDataFolder().getParentFile().getPath(), plugin + "-" + version + ".jar");
        if (oldFile.exists()) {
            try {
                if (oldFile.delete()) {
                    sender.sendMessage(MegaEssentials.Prefix + "§bAlte Version (v" + version + ") von §a" + plugin + "erfolgreich §cgelöscht");
                } else {
                    sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Löschen alten Version (v" + version + ") von §a" + plugin + ": Delete-Fehler");
                }
            } catch (SecurityException e) {
                sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Lder alten Version (v" + version + ") von §a" + plugin + ": Zugriffsfehler");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(MegaEssentials.Prefix + "§4Fehler beim Lder alten Version (v" + version + ") von §a" + plugin + ": Datei nicht gefunden");
        }
    }


}
