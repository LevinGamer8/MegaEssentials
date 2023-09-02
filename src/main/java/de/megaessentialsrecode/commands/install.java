package de.megaessentialsrecode.commands;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class install implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MegaEssentials.Prefix + "§4Dieser Command ist nur für Spieler!");
            return true;
        }


        Player p = (Player) sender;
        if (!(p.hasPermission("megacraft.command.install"))) {
            p.sendMessage(MegaEssentials.Prefix + MegaEssentials.noPerms);
            return true;
        }


        if (!(args.length == 1)) {
            p.sendMessage(MegaEssentials.Prefix + "§4Nutzung§7: §b/install [PLUGIN]");
            return true;
        }


        if (args[0].equalsIgnoreCase("worldedit")) {
            String WE_LINK = "https://drive.google.com/u/0/uc?id=1Cm8R3svK-_SrZfSP6qO0vPB1-fTpnuZd&export=download";
            try {
                downloadFile(WE_LINK, String.valueOf(MegaEssentials.getInstance().getDataFolder()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            p.sendMessage(MegaEssentials.Prefix + "§4Im Moment gibt es nur §bWorldEdit §4zur auswahl!");
        }




        return true;
    }



    public static void downloadFile(String fileURL, String outputPath) throws IOException {

        Path outputDirectory = Paths.get(outputPath).getParent();
        Files.createDirectories(outputDirectory);

        OutputStream os;
        InputStream is;
        // create a url object
        URL url = new URL(fileURL);
        // connection to the file
        URLConnection connection = url.openConnection();
        // get input stream to the file
        is = connection.getInputStream();
        // get output stream to download file
        os = Files.newOutputStream(Paths.get(outputPath));
        final byte[] b = new byte[4096];
        int length;
        // read from input stream and write to output stream
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        // close streams
        is.close();
        os.close();
    }

}
