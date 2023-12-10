package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderProvider extends PlaceholderExpansion {

    private final EconomyProvider economyProvider = MegaEssentials.getEconomyProvider();
    @Override
    public String getIdentifier() {
        return MegaEssentials.getPluginName();
    }
    @Override
    public String getAuthor() {
        return "LevinGamer8";
    }
    @Override
    public String getVersion() {
        return "1.0";
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) {
            return "";
        }
        if (params.equals("money")) {
            if (MegaEssentials.getEconomyProvider() == null) {
                return " ";
            }
            PlayerData pd = new PlayerData(player.getName());
            return this.economyProvider.format(pd.getMoney());
        } else if (params.equals("players")) {
            int players = Bukkit.getOnlinePlayers().size();

            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData pd = new PlayerData(p.getName());
                if (pd.isVanished()) {
                    players--;
                }
            }
            return String.valueOf(players);
        }
        return super.onPlaceholderRequest(player, params);
    }
}