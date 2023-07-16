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
            double newBalance = currentBalance + 100;
            DataBase.setEconomy(player, newBalance);
            player.sendMessage(MegaEssentials.Prefix + "§6Du hast §a100 §b€ §6für deine §bSpielzeit §6erhalten.");
        }
    }
}

