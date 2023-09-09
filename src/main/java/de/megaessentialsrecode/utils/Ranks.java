package de.megaessentialsrecode.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Ranks {

    public static String Ownerprefix = "ꐞ §7| §r";
    public static String Adminprefix = "ぁ §7| §r";
    public static String Moderatorprefix = "あ §7| §r";
    public static String Builderprefix = "ぃ §7| §r";
    public static String Supporterprefix = "う §7| §r";
    public static String Partnerprefix = "え §7| §r";
    public static String Tteamprefix = "§x§0§b§3§9§f§eT§x§1§1§3§6§f§1-§x§1§6§3§2§e§4T§x§1§c§2§f§d§8e§x§2§1§2§b§c§ba§x§2§7§2§8§b§em §8| ";
    public static String Megaprefix = "ン §7| §r";
    public static String Premiumprefix = "§x§d§d§a§d§4§3P§x§c§a§a§0§4§0r§x§b§8§9§2§3§ce§x§a§5§8§5§3§9m§x§9§2§7§8§3§6i§x§8§0§6§a§3§2u§x§6§d§5§d§2§fm §8| ";
    public static String Spielerprefix = "ヺ §7| §r";



    private static Map<Player, BukkitTask> teamSchedulers = new HashMap<>();

    public static void putInTeamSchedulers(Player player, BukkitTask bukkitTask) {
        teamSchedulers.put(player, bukkitTask);
    }

    public static Map<Player, BukkitTask> getTeamScheduler() {
        return teamSchedulers;
    }

}
