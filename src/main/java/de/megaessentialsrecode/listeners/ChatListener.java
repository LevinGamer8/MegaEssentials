package de.megaessentialsrecode.listeners;

import de.megaessentialsrecode.MegaEssentials;
import de.megaessentialsrecode.utils.Ranks;
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

        if (MegaEssentials.getInstance().getConfig().getBoolean("rankprefix.enabled")) {

            String name = event.getPlayer().getName();
            String message = event.getMessage();
            LuckPerms luckPerms = LuckPermsProvider.get();
            String group = luckPerms.getUserManager().getUser(event.getPlayer().getUniqueId()).getPrimaryGroup();

            switch (group) {

                case "owner":
                    event.setFormat(Ranks.Ownerprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "admin":
                    event.setFormat(Ranks.Adminprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "moderator":
                    event.setFormat(Ranks.Moderatorprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "builder":
                    event.setFormat(Ranks.Builderprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "supporter":
                    event.setFormat(Ranks.Supporterprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "partner":
                    event.setFormat(Ranks.Partnerprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "t-team":
                    event.setFormat(Ranks.Tteamprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "mega":
                    event.setFormat(Ranks.Megaprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "premium":
                    event.setFormat(Ranks.Premiumprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message.replace('&', '§'));
                    break;
                case "default":
                    event.setFormat(Ranks.Spielerprefix + ChatColor.GRAY + name + " §8-> " + ChatColor.GRAY + message);
            }
        }
    }
}
