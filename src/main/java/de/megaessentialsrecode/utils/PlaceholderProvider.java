package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
            return this.economyProvider.format(DataBase.getEconomy(player));
        }
        return super.onPlaceholderRequest(player, params);
    }
}