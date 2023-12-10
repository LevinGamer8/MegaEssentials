package de.megaessentialsrecode.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DataHandler {

    private final Map<String, Inventory> liveData = new HashMap<>();
    private final Set<Player> joinDelay = new HashSet<>();
    private final MegaEssentials pl;

    public DataHandler(MegaEssentials plugin) {
        this.pl = plugin;
        loadAlreadyOnlinePlayers();
    }

    public void addJoinDelay(Player p) {
        joinDelay.add(p);
    }

    public void removeJoinDelay(Player p) {
        joinDelay.remove(p);
    }

    public boolean hasJoinDelay(Player p) {
        return joinDelay.contains(p);
    }

    public Inventory getData(String playerName) {
        return liveData.get(playerName);
    }

    public void setData(String playerName, Inventory enderchestInventory) {
        liveData.put(playerName, enderchestInventory);
    }

    public void removeData(String playerName) {
        liveData.remove(playerName);
    }

    public boolean isLiveEnderchest(Inventory inventory) {
        return liveData.containsValue(inventory);
    }

    public void clearLiveData() {
        liveData.clear();
    }

    public void loadPlayerFromStorage(Player p) {
        PlayerData pd = new PlayerData(p.getName());
        if (p.isOnline()) {
            int size = pl.getEnderChestUtils().getSize(p);
            if (size == 0) {
                size = 9;
            }
            String enderChestTitle = pl.getEnderChestUtils().getTitle(p);
            Inventory inv = Bukkit.getServer().createInventory(p, size, enderChestTitle);
            if (pd.exists()) {
                pd.loadEnderChest(inv);
            }
            setData(p.getName(), inv);
        }
    }

    private void loadAlreadyOnlinePlayers() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            MegaEssentials.logger().info("Loading data for online players...");
            for (Player p : Bukkit.getOnlinePlayers()) {
                loadPlayerFromStorage(p);
            }
        }
    }

}