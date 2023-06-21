package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class EconomyProvider implements Economy {
    private final DecimalFormat decimalFormat;
    private final MegaEssentials plugin;

    public EconomyProvider(MegaEssentials plugin) {
        this.plugin = plugin;
        this.decimalFormat = new DecimalFormat("#,###.##");
    }

    public boolean isEnabled() {
        return this.plugin.isEnabled();
    }

    public String getName() {
        return this.plugin.getName();
    }

    public boolean hasBankSupport() {
        return false;
    }

    public int fractionalDigits() {
        return 2;
    }

    public String format(double amount) {
        return formatBalance(amount);
    }

    public String currencyNamePlural() {
        return currencyNameSingular();
    }

    public String currencyNameSingular() {
        return "€";
    }

    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return DataBase.exist(offlinePlayer);
    }

    public boolean hasAccount(String playerName) {
        return DataBase.exist(this.plugin.getServer().getOfflinePlayer(playerName));
    }

    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    public double getBalance(OfflinePlayer offlinePlayer) {
        return DataBase.getEconomy(offlinePlayer);
    }

    public double getBalance(String playerName) {
        return DataBase.getEconomy(this.plugin.getServer().getOfflinePlayer(playerName));
    }

    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return (DataBase.getEconomy(offlinePlayer) >= amount);
    }

    public boolean has(String playerName, double amount) {
        return (DataBase.getEconomy(this.plugin.getServer().getOfflinePlayer(playerName)) >= amount);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (offlinePlayer == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player cannot be null!");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        DataBase.removeEconomy(offlinePlayer, amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (playerName == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        DataBase.removeEconomy(this.plugin.getServer().getOfflinePlayer(playerName), amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (offlinePlayer == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player can not be null.");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        DataBase.addEconomy(offlinePlayer, amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (playerName == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        DataBase.addEconomy(this.plugin.getServer().getOfflinePlayer(playerName), amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        DataBase.setup(offlinePlayer);
        return true;
    }

    public boolean createPlayerAccount(String playerName) {
        DataBase.setup(this.plugin.getServer().getOfflinePlayer(playerName));
        return true;
    }

    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }

    public List<String> getBanks() {
        return Collections.emptyList();
    }

    private String formatBalance(double balance) {
        return decimalFormat.format(balance);
    }
}