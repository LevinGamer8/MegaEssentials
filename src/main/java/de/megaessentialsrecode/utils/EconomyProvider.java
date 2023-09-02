package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class EconomyProvider implements Economy {
    private final MegaEssentials plugin;

    public EconomyProvider(MegaEssentials plugin) {
        this.plugin = plugin;
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
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        return pd.exists();
    }

    public boolean hasAccount(String playerName) {
        PlayerData pd = new PlayerData(playerName);
        return pd.exists();
    }

    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    public double getBalance(OfflinePlayer offlinePlayer) {
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        return pd.getEconomy();
    }

    public double getBalance(String playerName) {
        PlayerData pd = new PlayerData(playerName);
        return pd.getEconomy();
    }

    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        return (pd.getEconomy() >= amount);
    }

    public boolean has(String playerName, double amount) {
        PlayerData pd = new PlayerData(playerName);
        return (pd.getEconomy() >= amount);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        if (!(pd.exists()))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player cannot be null!");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        pd.removeEconomy(amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        PlayerData pd = new PlayerData(playerName);
        if (playerName == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        pd.removeEconomy(amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        if (!(pd.exists()))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player can not be null.");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        pd.addEconomy(amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        PlayerData pd = new PlayerData(playerName);
        if (playerName == null)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        pd.addEconomy(amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        PlayerData pd = new PlayerData(offlinePlayer.getName());
        pd.createPlayer(offlinePlayer.getName());
        return true;
    }

    public boolean createPlayerAccount(String playerName) {
        PlayerData pd = new PlayerData(playerName);
        pd.createPlayer(playerName);
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
        return formatNumberWithThousandsSeparator(balance);
    }


    public String formatNumberWithThousandsSeparator(double number) {
        long integerPart = (long) number;
        return String.format("%,d", integerPart).replace(",", ".");
    }

}