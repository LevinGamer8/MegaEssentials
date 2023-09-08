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
        registerTeam(scoreboard, "0001Owner", "§f" + Ranks.Ownerprefix);
        registerTeam(scoreboard, "0002Admin", Ranks.Adminprefix);
        registerTeam(scoreboard, "0003Moderator", Ranks.Moderatorprefix);
        registerTeam(scoreboard, "0004Builder", Ranks.Builderprefix);
        registerTeam(scoreboard, "0005Supporter", Ranks.Supporterprefix);
        registerTeam(scoreboard, "0006Partner", Ranks.Partnerprefix);
        registerTeam(scoreboard, "0007T-Team", Ranks.Tteamprefix);
        registerTeam(scoreboard, "0008MEGA", Ranks.Megaprefix);
        registerTeam(scoreboard, "0009Premium", Ranks.Premiumprefix);
        registerTeam(scoreboard, "0099Spieler", Ranks.Spielerprefix);
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

    private static void registerTeam(Scoreboard scoreboard, String name, String prefix) {
        Team team = scoreboard.registerNewTeam(name);
        team.setPrefix(prefix);
    }
}
