package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    private boolean getVanished() {
        return vanished;
    }
    private void setVanished(boolean vanished) {
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


    public void createPlayer(String playername) {
        setName(playername);
        if (exists()) {
            return;
        }
        try (Connection conn = source.getConnection(); PreparedStatement ps = conn.prepareStatement("INSERT INTO playerdata (Name,money,vanish) VALUES(?,?,?) ON DUPLICATE KEY UPDATE Name=Name")) {
            ps.setString(1, playername); // name
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

    //Eco Start ---------------------------------------------------------------------------------


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

    //Eco Ende ---------------------------------------------------------------------------------


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



    //Vanish Ende ---------------------------------------------------------------------------------




    public void addHome(String homeName, String world, double x, double y, double z, float yaw, float pitch) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO homes (Name, home_name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, this.getName());
            ps.setString(2, homeName);
            ps.setString(3, world);
            ps.setDouble(4, x);
            ps.setDouble(5, y);
            ps.setDouble(6, z);
            ps.setFloat(7, yaw);
            ps.setFloat(8, pitch);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHomeNames() {
        List<String> homeNames = new ArrayList<>();
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT home_name FROM homes WHERE Name = ?")) {
            ps.setString(1, this.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String homeName = rs.getString("home_name");
                homeNames.add(homeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homeNames;
    }

    public Location getHomeLocation(String homeName) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE Name = ? AND home_name = ?")) {
            ps.setString(1, this.getName());
            ps.setString(2, homeName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String world = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeHome(String homeName, Player p) {
        if (!(isHomeExisting(homeName))) {
            p.sendMessage(MegaEssentials.Prefix + "§cDein §bHome " + homeName + " §cexistiert nicht!");
            return;
        }

        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM homes WHERE Name = ? AND home_name = ?")) {
            ps.setString(1, this.getName());
            ps.setString(2, homeName);
            ps.executeUpdate();
            p.sendMessage(MegaEssentials.Prefix + "§eDu §ahast dein §bHome " + homeName + " §aerfolgreich §cgelöscht§7.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isHomeExisting(String homeName) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE Name = ? AND home_name = ?")) {
            ps.setString(1, this.getName());
            ps.setString(2, homeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Home Ende ------------------------------------------------------------------------------------------------------------------------





    public void addWarp(String warpName, String world, double x, double y, double z, float yaw, float pitch, boolean active) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO warps (Name, world, x, y, z, yaw, pitch, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, warpName);
            ps.setString(2, world);
            ps.setDouble(3, x);
            ps.setDouble(4, y);
            ps.setDouble(5, y);
            ps.setFloat(6, yaw);
            ps.setFloat(7, pitch);
            ps.setBoolean(8, active);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWarpNames() {
        List<String> warpNames = new ArrayList<>();
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Name FROM warps")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String warpName = rs.getString("Name");
                warpNames.add(warpName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warpNames;
    }

    public Location getWarpLocation(String warpName) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps WHERE Name = ?")) {
            ps.setString(1, warpName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String world = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeWarp(String warpName, Player p) {
        if (!(isWarpExisting(warpName))) {
            p.sendMessage(MegaEssentials.Prefix + "§cDer §bWarp " + warpName + " §cexistiert nicht!");
            return;
        }

        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM warps WHERE Name = ?")) {
            ps.setString(1, warpName);
            ps.executeUpdate();
            p.sendMessage(MegaEssentials.Prefix + "§eDu §ahast den §bWarp " + warpName + " §aerfolgreich §cgelöscht§7.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isWarpExisting(String warpName) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps WHERE Name = ?")) {
            ps.setString(1, warpName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isWarpActive(String warpName) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT active FROM warps WHERE Name = ?")) {
            ps.setString(1, warpName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("active");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasEnderchest() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM enderchests WHERE Name = ?" )) {
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


    public void createEnderchest() {
        Player p = Bukkit.getPlayer(this.getName());
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO enderchests (Name, enderchest_data, size) VALUES (?, ?, ?)")) {
            ps.setString(1, this.getName());
            ps.setString(2, "");
            ps.setDouble(3, MegaEssentials.getEnderChestUtils().getSize(p));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int loadSize() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT size FROM enderchests WHERE Name = ?")) {
            ps.setString(1, this.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getEnderchestString() {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT enderchest_data FROM enderchests WHERE Name = ?")) {
            ps.setString(1, this.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("enderchest_data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveEnderchest(Inventory inv) {
        int storageSize = loadSize();
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE enderchests SET enderchest_data = ?, size = ? WHERE Name = ?")) {
        if (inv.getSize() >= storageSize) {
                ps.setString(1, encodeInventory(inv, this.getName()));
                ps.setInt(2, inv.getSize());
                ps.setString(3, this.getName());
                ps.executeUpdate();
            } else {

            Inventory storageInv = decodeInventory(getEnderchestString(), null, storageSize);
            for (int i = 0; i < inv.getSize(); i++) {
                storageInv.setItem(i, inv.getItem(i));
            }
            ps.setString(1, encodeInventory(storageInv, this.getName()));
            ps.setInt(2, storageSize);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean loadEnderChest(Inventory endInv) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM enderchests WHERE Name = ?")) {
            ps.setString(1, this.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        Inventory mysqlInv = decodeInventory(rs.getString("enderchest_data"), this.getName(), rs.getInt("size"));

                        for (int i = 0; i < endInv.getSize(); i++) {
                            ItemStack item = mysqlInv.getItem(i);
                            endInv.setItem(i, item);
                        }
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    private String encodeInventory(Inventory inv, String playerName) {
        if (MegaEssentials.getModdedSerializer() != null) {
            try {
                return MegaEssentials.getModdedSerializer().toBase64(inv.getContents());
            } catch (Exception e) {
                MegaEssentials.logger().severe("Failed to save enderchest data for " + playerName + "! Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                return EncodingUtil.toBase64(inv);
            } catch (Exception e) {
                MegaEssentials.logger().severe("Failed to save enderchest data for " + playerName + "! Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }


    private void saveEnderchest(String playerName, int chestSize) {
        try (Connection conn = source.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE enderchests SET enderchest_data = ?, size = ? WHERE Name = ?")) {
            ps.setString(1, encodeInventory(Bukkit.getServer().createInventory(null, chestSize), playerName));
            ps.setInt(2, chestSize);
            ps.setString(3, playerName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Inventory decodeInventory(String rawData, String playerName, int chestSize) {
        if (MegaEssentials.getModdedSerializer() != null) {
            try {
                ItemStack[] items = MegaEssentials.getModdedSerializer().fromBase64(rawData);
                Inventory inv = Bukkit.getServer().createInventory(null, chestSize);
                if (chestSize >= items.length) {
                    inv.setContents(items);
                } else {
                    for (int i=0; i<chestSize; i++) {
                        if (items[i] != null) {
                            inv.addItem(items[i]);
                        }
                    }
                }

                return inv;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    return EncodingUtil.fromBase64(rawData);
                } catch (Exception ex) {
                    MegaEssentials.logger().severe("Failed to decode inventory for " + playerName + "! Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                return EncodingUtil.fromBase64(rawData);
            } catch (Exception e) {
                MegaEssentials.logger().severe("Failed to decode inventory for " + playerName + "! Error: " + e.getMessage());
                //TODO
                saveEnderchest(playerName, chestSize);
            }
        }
        return null;
    }








}