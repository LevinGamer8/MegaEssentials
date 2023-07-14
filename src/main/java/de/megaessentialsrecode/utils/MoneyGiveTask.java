package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MoneyGiveTask extends BukkitRunnable {

    private EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double currentBalance = economyProvider.getBalance(player);
            double newBalance = currentBalance + 500;
            DataBase.setEconomy(player, newBalance);
            player.sendMessage(MegaEssentials.Prefix + "§6Du hast §a500 §b€ §6 für deine Spielzeit erhalten.");
        }
    }
}

