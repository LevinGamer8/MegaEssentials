package de.megaessentialsrecode.scoreboard;

import de.megaessentialsrecode.utils.Ranks;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Teams {
    public Teams() {
    }

    public static void create(Scoreboard scoreboard) {
        registerTeam(scoreboard, "0001Owner", "§f" + Ranks.Ownerprefix, ChatColor.GREEN);
        registerTeam(scoreboard, "0002Admin", "§f" + Ranks.Adminprefix, ChatColor.GREEN);
        registerTeam(scoreboard, "0003Moderator", "§f" + Ranks.Moderatorprefix, ChatColor.GREEN);
        registerTeam(scoreboard, "0004Builder", "§f" + Ranks.Builderprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0005Supporter", "§f" + Ranks.Supporterprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0006Partner", "§f" + Ranks.Partnerprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0007T-Team", "§f" + Ranks.Tteamprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0008MEGA", "§f" + Ranks.Megaprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0009Premium", "§f" + Ranks.Premiumprefix, ChatColor.GRAY);
        registerTeam(scoreboard, "0099Spieler","§f" +  Ranks.Spielerprefix, ChatColor.GRAY);
    }

    public static void set(Scoreboard scoreboard) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        Bukkit.getOnlinePlayers().forEach((player) -> {
            String team;
            switch (luckPerms.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup()) {
                case "owner":
                    team = "0001Owner";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "admin":
                    team = "0002Admin";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "moderator":
                    team = "0003Moderator";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "builder":
                    team = "0004Builder";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "supporter":
                    team = "0005Supporter";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "partner":
                    team = "0006Partner";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "t-team":
                    team = "0006T-Team";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "mega":
                    team = "0008MEGA";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "premium":
                    team = "0009Premium";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
                    break;
                case "default":
                    team = "0099Spieler";
                    if (!scoreboard.getTeam(team).hasEntry(player.getName())) {
                        scoreboard.getTeam(team).addEntry(player.getName());
                    }
            }

        });
    }

    private static void registerTeam(Scoreboard scoreboard, String name, String prefix, ChatColor chatColor) {
        Team team = scoreboard.registerNewTeam(name);
        team.setPrefix(prefix);
        team.setColor(chatColor);
    }
}
