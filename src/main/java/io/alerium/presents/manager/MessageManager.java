package io.alerium.presents.manager;

import io.alerium.presents.PixelPresents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Chazmondo
 */
public class MessageManager {

    private final PixelPresents core;
    public MessageManager() {
        this.core = (PixelPresents) JavaPlugin.getProvidingPlugin(PixelPresents.class);
    }

    public String getMessage(String path) {
        String message = "§cMessage not found.";

        if(core.getConfig().getString(path) != null && !core.getConfig().getString(path).isEmpty())
            message = core.getConfig().getString(path);
        
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
