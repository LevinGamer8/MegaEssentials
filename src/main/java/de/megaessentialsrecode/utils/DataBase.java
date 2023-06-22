package de.megaessentialsrecode.utils;


import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static MegaEssentials plugin;
    private static Connection connection = MySQLConnection.getConnection();

    public DataBase(MegaEssentials plugin) {
        this.plugin = plugin;
    }

    public static boolean exist(OfflinePlayer offlinePlayer) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM player_money WHERE uuid = ?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM player_money WHERE name = ?")) {
            statement.setString(1, offlinePlayer.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void setup(OfflinePlayer offlinePlayer) {
        if (!exist(offlinePlayer)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO player_money (uuid, name, money) VALUES (?, ?, ?)")) {
                statement.setString(1, offlinePlayer.getUniqueId().toString());
                statement.setString(2, offlinePlayer.getName());
                statement.setString(3, String.valueOf(Double.parseDouble("10000")));
                statement.setString(3, String.valueOf(Double.parseDouble("0")));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getEconomy(OfflinePlayer offlinePlayer) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT money FROM player_money WHERE uuid = ?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("money");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getEconomyBank(OfflinePlayer offlinePlayer) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT bank FROM player_money WHERE uuid = ?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("bank");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addEconomy(OfflinePlayer offlinePlayer, double amount) {
        double currentBalance = getEconomy(offlinePlayer);
        setEconomy(offlinePlayer, currentBalance + amount);
    }

    public static void addEconomyBank(OfflinePlayer offlinePlayer, double amount) {
        double currentBalance = getEconomyBank(offlinePlayer);
        setEconomyBank(offlinePlayer, currentBalance + amount);
    }

    public static void removeEconomy(OfflinePlayer offlinePlayer, double amount) {
        double currentBalance = getEconomy(offlinePlayer);
        setEconomy(offlinePlayer, currentBalance - amount);
    }

    public static void removeEconomyBank(OfflinePlayer offlinePlayer, double amount) {
        double currentBalance = getEconomyBank(offlinePlayer);
        setEconomyBank(offlinePlayer, currentBalance - amount);
    }

    public static void setEconomy(OfflinePlayer offlinePlayer, double amount) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE player_money SET money = ? WHERE uuid = ?")) {
            statement.setDouble(1, amount);
            statement.setString(2, offlinePlayer.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setEconomyBank(OfflinePlayer offlinePlayer, double amount) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE player_money SET bank = ? WHERE uuid = ?")) {
            statement.setDouble(1, amount);
            statement.setString(2, offlinePlayer.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetEconomy(OfflinePlayer offlinePlayer) {
        setEconomy(offlinePlayer, Double.parseDouble("10000"));
    }

    public static void resetEconomyBank(OfflinePlayer offlinePlayer) {
        setEconomy(offlinePlayer, Double.parseDouble("0"));
    }

    public static List<String> getAllRegisteredPlayers() {
        List<String> registeredPlayers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM player_money")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String playerUUID = resultSet.getString("uuid");
                    registeredPlayers.add(playerUUID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registeredPlayers;
    }

}
