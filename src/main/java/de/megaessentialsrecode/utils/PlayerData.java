package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.OfflinePlayer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlayerData {

    private String name;
    private final DataSource source;
    private double money;

    private boolean vanished = false;

    public PlayerData(String playerName) {
        this.name = playerName;
        this.source = MegaEssentials.getInstance().getDataSource();
        loadData();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean getVanished() {
        return vanished;
    }
    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }


    private void loadData() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM playerdata WHERE Name = ?")) {
            ps.setString(1, getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                setMoney(rs.getDouble("money"));
                setVanished(rs.getBoolean("vanish"));
            }
        } catch (SQLException e) {
            MegaEssentials.logger().log(Level.WARNING, "could not load playerdata", e);
        }
    }


    public void createPlayer(String name) {
        setName(name);
        if (exists()) {
            return;
        }
        try (Connection conn = source.getConnection(); PreparedStatement ps = conn.prepareStatement("INSERT INTO playerdata (Name,money,vanish) VALUES(?,?,?) ON DUPLICATE KEY UPDATE Name=Name")) {
            ps.setString(1, getName()); // name
            ps.setDouble(2, 10000); // money
            ps.setBoolean(3, false);
            ps.executeUpdate();
        } catch (SQLException e) {
            MegaEssentials.logger().log(Level.WARNING, "could not create playerdata", e);
        }
    }


    public boolean exists() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM playerdata WHERE Name = ?" )) {
            ps.setString(1, this.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public double getEconomy() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT money FROM playerdata WHERE Name = ?")) {
            ps.setString(1, this.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public void addEconomy(double amount) {
        double currentBalance = getEconomy();
        setEconomy( currentBalance + amount);
    }


    public void removeEconomy(double amount) {
        double currentBalance = getEconomy();
        setEconomy( currentBalance - amount);
    }


    public void setEconomy(double amount) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE playerdata SET money = ? WHERE Name = ?")) {
            ps.setDouble(1, amount);
            ps.setString(2, this.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void resetEconomy() {
        setEconomy(Double.parseDouble("10000"));
    }


    public boolean isVanished() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT vanish FROM playerdata WHERE Name = ?")) {
            ps.setString(1, this.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("vanish");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setVanish(boolean vanish) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE playerdata SET vanish = ? WHERE Name = ?")) {
            ps.setBoolean(1, vanish);
            ps.setString(2, this.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllRegisteredPlayers() {
        List<String> registeredPlayers = new ArrayList<>();
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM playerdata")) {
            ps.setString(1, this.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String playerName =  rs.getString("Name");
                registeredPlayers.add(playerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registeredPlayers;
    }
}
