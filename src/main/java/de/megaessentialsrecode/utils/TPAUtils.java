package de.megaessentialsrecode.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TPAUtils {

    private final Player sender;

    private final Player receiver;

    private Location location;

    private boolean tpHere = false;

    public TPAUtils(Player sender, Player receiver, Location location, boolean tpHere) {
        this.sender = sender;
        this.receiver = receiver;
        this.location = location;
        this.tpHere = tpHere;
    }

    public Player getSender() {
        return this.sender;
    }

    public Player getReceiver() {
        return this.receiver;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isTpHere() {
        return this.tpHere;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}