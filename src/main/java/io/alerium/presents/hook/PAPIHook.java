package io.alerium.presents.hook;

import io.alerium.presents.PixelPresents;
import io.alerium.presents.manager.UserManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * Created by Chazmondo
 */
public class PAPIHook extends PlaceholderExpansion {
    
    private final PixelPresents core;
    
    public PAPIHook(PixelPresents core) {
        this.core = core;
    }
    
    @Override
    public String getIdentifier() {
        return "pixelpresents";
    }

    @Override
    public String getPlugin() {
        return core.getDescription().getName();
    }

    @Override
    public String getAuthor() {
        return core.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return core.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        UserManager um = new UserManager(player.getUniqueId());
        if(identifier.equals("found"))
            return Integer.toString(um.getConfig().getStringList("found").size());

        if(identifier.equals("total"))
            return Integer.toString(core.getPresents().size());

        if(identifier.equals("left"))
            return Integer.toString(core.getPresents().size() - um.getConfig().getStringList("found").size());

        return null;
    }

    /*private final PixelPresents core;
    public PAPIHook(PixelPresents core) {
        super(core, "pixelpresents");
        this.core = core;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        UserManager um = new UserManager(p.getUniqueId());
        if(identifier.equals("found"))
            return Integer.toString(um.getConfig().getStringList("found").size());
        
        if(identifier.equals("total"))
            return Integer.toString(core.getPresents().size());
        
        if(identifier.equals("left"))
            return Integer.toString(core.getPresents().size() - um.getConfig().getStringList("found").size());
        
        return null;
    }*/
}
