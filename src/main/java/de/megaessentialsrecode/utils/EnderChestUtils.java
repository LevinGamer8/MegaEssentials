package de.megaessentialsrecode.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnderChestUtils {

    private MegaEssentials megaEssentials;

    public EnderChestUtils(MegaEssentials megaEssentials) {
        this.megaEssentials = megaEssentials;
    }

    public String getTitle(Player p) {

        List<String> chestTitle = new ArrayList<>();
        chestTitle.add(megaEssentials.getConfig().getString("enderChestTitle.enderChestName"));

        if (p.hasPermission("CustomEnderChest.level.5")) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level5")));
        } else if (p.hasPermission("CustomEnderChest.level.4") && !p.isOp()) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level4")));
        } else if (p.hasPermission("CustomEnderChest.level.3") && !p.isOp()) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level3")));
        } else if (p.hasPermission("CustomEnderChest.level.2") && !p.isOp()) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level2")));
        } else if (p.hasPermission("CustomEnderChest.level.1") && !p.isOp()) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level1")));
        } else if (p.hasPermission("CustomEnderChest.level.0") && !p.isOp()) {
            chestTitle.set(0, chestTitle + Objects.requireNonNull(megaEssentials.getConfig().getString("enderChestTitle.level0")));
        }

        chestTitle.set(0, chestTitle.get(0).replaceAll("%player", p.getName()));

        if (chestTitle.get(0).length() <= 32) {
            String enderChestTitle = chestTitle.get(0).replaceAll("&", "§");
            return enderChestTitle;
        } else {
            String enderChestTitle = chestTitle.get(0).substring(0, 32).replaceAll("&", "§");
            return enderChestTitle;
        }
    }

    public String getCmdTitle(Player p) {
        String chestTitle = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "" + p.getName() + "'s " + ChatColor.LIGHT_PURPLE + "Ender Chest";

        if (chestTitle.length() <= 32) {
            return chestTitle.replaceAll("&", "§");
        } else {
            return chestTitle.substring(0, 32).replaceAll("&", "§");
        }
    }


    private String loadECName(Player p) {
        return "";
    }

    public Integer getSize(Player p) {

        if (p.hasPermission("CustomEnderChest.level.5")) {
            return 54;
        }
        if (p.hasPermission("CustomEnderChest.level.4") && !p.isOp()) {
            return 45;
        }
        if (p.hasPermission("CustomEnderChest.level.3") && !p.isOp()) {
            return 36;
        }
        if (p.hasPermission("CustomEnderChest.level.2") && !p.isOp()) {
            return 27;
        }
        if (p.hasPermission("CustomEnderChest.level.1") && !p.isOp()) {
            return 18;
        }
        if (p.hasPermission("CustomEnderChest.level.0") && !p.isOp()) {
            return 9;
        }

        return 0;
    }

    public void openMenu(Player p) {
        PlayerData pd = new PlayerData(p.getName());
        //Cancel vanilla enderchest
        p.closeInventory();

        int size = megaEssentials.getEnderChestUtils().getSize(p);
        //No enderchest permission
        if (size == 0) {
            return;
        }
        Inventory inv = megaEssentials.getDataHandler().getData(p.getName());
        if (inv == null) {
            String enderChestTitle = megaEssentials.getEnderChestUtils().getTitle(p);
            inv = Bukkit.getServer().createInventory(p, size, enderChestTitle);
        } else if (inv.getSize() != size) {
            String enderChestTitle = megaEssentials.getEnderChestUtils().getTitle(p);
            Inventory newInv = Bukkit.getServer().createInventory(p, size, enderChestTitle);
            if (size > inv.getSize()) {
                //TODO run this async to prevent tps drops on slow connections
                    pd.loadEnderChest(newInv);
                inv = newInv;
            } else {
                for (int i = 0; i < size; i++) {
                    ItemStack item = inv.getItem(i);
                    newInv.setItem(i, item);
                }
                inv = newInv;
            }
        }
        megaEssentials.getDataHandler().setData(p.getName(), inv);
        p.openInventory(inv);
    }

}