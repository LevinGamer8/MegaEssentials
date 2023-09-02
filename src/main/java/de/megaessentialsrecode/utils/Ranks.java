package de.megaessentialsrecode.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Ranks {

    public static String Ownerprefix = "§x§1§a§c§6§e§4O§x§1§4§c§b§d§9w§x§0§d§c§f§c§en§x§0§7§d§4§c§2e§x§0§0§d§8§b§7r §8| ";
    public static String Adminprefix = "§x§e§4§2§8§1§5A§x§d§7§2§3§1§3d§x§c§b§1§d§1§1m§x§b§e§1§8§0§fi§x§b§1§1§2§0§dn §8| ";
    public static String Moderatorprefix = "§x§e§4§3§e§0§1M§x§e§3§3§8§0§2o§x§e§3§3§3§0§3d§x§e§2§2§d§0§4e§x§e§2§2§7§0§6r§x§e§1§2§1§0§7a§x§e§0§1§c§0§8t§x§e§0§1§6§0§9o§x§d§f§1§0§0§ar §8| ";
    public static String Builderprefix = "§x§2§c§e§4§1§5B§x§2§a§d§7§1§9u§x§2§8§c§a§1§ci§x§2§7§b§d§2§0l§x§2§5§a§f§2§3d§x§2§3§a§2§2§7e§x§2§1§9§5§2§ar §8| ";
    public static String Supporterprefix = "§x§f§e§f§c§2§2S§x§d§2§d§0§2§eu§x§a§5§a§4§3§9p §8| ";
    public static String Partnerprefix = "§x§f§b§b§7§1§fP§x§f§7§c§0§2§0a§x§f§4§c§a§2§1r§x§f§0§d§3§2§2t§x§e§c§d§c§2§3n§x§e§9§e§6§2§4e§x§e§5§e§f§2§5r";
    public static String Tteamprefix = "§x§0§b§3§9§f§eT§x§1§1§3§6§f§1-§x§1§6§3§2§e§4T§x§1§c§2§f§d§8e§x§2§1§2§b§c§ba§x§2§7§2§8§b§em §8| ";
    public static String Megaprefix = "§x§7§7§e§7§d§aM§x§5§a§d§1§c§4E§x§3§d§b§b§a§fG§x§2§0§a§5§9§9A §8| ";
    public static String Premiumprefix = "§x§d§d§a§d§4§3P§x§c§a§a§0§4§0r§x§b§8§9§2§3§ce§x§a§5§8§5§3§9m§x§9§2§7§8§3§6i§x§8§0§6§a§3§2u§x§6§d§5§d§2§fm §8| ";
    public static String Spielerprefix = "§x§1§d§f§e§1§fS§x§1§d§f§2§2§5p§x§1§d§e§5§2§bi§x§1§d§d§9§3§1e§x§1§c§c§d§3§6l§x§1§c§c§0§3§ce§x§1§c§b§4§4§2r §8| ";



    private static Map<Player, BukkitTask> teamSchedulers = new HashMap<>();

    public static void putInTeamSchedulers(Player player, BukkitTask bukkitTask) {
        teamSchedulers.put(player, bukkitTask);
    }

    public static Map<Player, BukkitTask> getTeamScheduler() {
        return teamSchedulers;
    }

}
