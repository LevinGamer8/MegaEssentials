package de.megaessentialsrecode.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginUtils {

    private final Plugin plugin;


    public PluginUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    public void unload(String pluginName) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        Plugin targetPlugin = pluginManager.getPlugin(pluginName);

        if (targetPlugin != null) {
            pluginManager.disablePlugin(targetPlugin);
        }
    }
}
