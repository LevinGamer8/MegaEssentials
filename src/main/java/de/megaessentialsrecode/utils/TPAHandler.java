package de.megaessentialsrecode.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class TPAHandler {

    private static List<TPAUtils> activeRequests = new ArrayList<>();

    public static void initiateRequest(TPAUtils request) {
        if (request == null)
            throw new NullPointerException();
        try {
            for (TPAUtils r : activeRequests) {
                if (r.getReceiver().getUniqueId().toString().equalsIgnoreCase(request.getReceiver().getUniqueId().toString()))
                    cancelRequest(r);
            }
        } catch (ConcurrentModificationException ignored) {}
        activeRequests.add(request);
    }

    public static void cancelRequest(TPAUtils request) {
        if (request == null)
            throw new NullPointerException();
        if (!activeRequests.contains(request))
            throw new IllegalArgumentException();
        try {
            activeRequests.remove(request);
        } catch (ConcurrentModificationException concurrentModificationException) {}
    }

    public static TPAUtils getLatestRequest(Player receiver) {
        if (receiver == null)
            throw new NullPointerException();
        for (TPAUtils r : activeRequests) {
            if (r.getReceiver().getUniqueId().toString().equalsIgnoreCase(receiver.getUniqueId().toString()))
                return r;
        }
        return null;
    }

}
