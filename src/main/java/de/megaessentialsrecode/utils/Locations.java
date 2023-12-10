package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Vector;
import java.util.logging.Level;

public class Locations {

    private static World world;
    private static float yaw = 180;

    public static void Locations(World world) {
        world = world;
    }

    public static void teleportToSpawn(Player p) {

        double x = Double.parseDouble(Objects.requireNonNull(MegaEssentials.getInstance().getConfig().getString("spawn.x")));
        double y = Double.parseDouble(Objects.requireNonNull(MegaEssentials.getInstance().getConfig().getString("spawn.y")));
        double z = Double.parseDouble(Objects.requireNonNull(MegaEssentials.getInstance().getConfig().getString("spawn.z")));
        World world = Bukkit.getServer().getWorld(Objects.requireNonNull(MegaEssentials.getInstance().getConfig().getString("spawn.world")));
        if (world != null) {
            Location spawnLocation = new Location(world, x, y, z);
            spawnLocation.setYaw(yaw);
            p.teleport(spawnLocation);
        } else {
            MegaEssentials.logger().log(Level.WARNING, "Die angegebene World wurde nicht gefunden");
        }
    }
}

