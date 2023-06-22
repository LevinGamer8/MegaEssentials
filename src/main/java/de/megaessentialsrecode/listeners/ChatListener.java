package de.megaessentialsrecode.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat( AsyncPlayerChatEvent event) {
        String name = event.getPlayer().getName();
        String message = event.getMessage();
        LuckPerms luckPerms = LuckPermsProvider.get();
        String group = luckPerms.getUserManager().getUser(event.getPlayer().getUniqueId()).getPrimaryGroup();
        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());

        String playerpref = metaData.getPrefix();
        event.setFormat(playerpref.replace('&', '§') + "§7 | " +  ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
    }
}
