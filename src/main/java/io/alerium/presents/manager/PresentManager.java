package io.alerium.presents.manager;

import io.alerium.presents.PixelPresents;
import io.alerium.presents.api.Present;
import io.alerium.presents.api.event.PresentFoundEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Chazmondo
 */
public class PresentManager {

    private final PixelPresents core;
    public PresentManager(){
        this.core = (PixelPresents) JavaPlugin.getProvidingPlugin(PixelPresents.class);
    }

    public PixelPresents getCore() {
        return core;
    }

    public void create(Location loc){
        Present present = new Present(UUID.randomUUID(), Collections.singletonList("[MESSAGE] &aPresent Found!"), loc);
        
        core.getConfig().set("present." + present.getUuid() + ".reward", present.getRewards());

        core.getConfig().set("present." + present.getUuid() + ".location.x", loc.getBlockX());
        core.getConfig().set("present." + present.getUuid() + ".location.y", loc.getBlockY());
        core.getConfig().set("present." + present.getUuid() + ".location.z", loc.getBlockZ());
        core.getConfig().set("present." + present.getUuid() + ".location.world", loc.getWorld().getName());
        
        core.saveConfig();
        
        core.getPresents().add(present);
    }

    public void remove(Location loc){
        Present present = get(loc);
        if (present == null)
            return;
        
        remove(present);
    }

    public void remove(Present present) {
        core.getPresents().remove(present);
        core.getConfig().set("present." +present.getUuid(), null);
        
        core.saveConfig();
    }
    
    public Present get(Location loc){
        for(Present present : core.getPresents()) {
            if(present.getLocation().equals(loc))
                return present;
        }
        return null;
    }

    public void dealActions(Player player, Present present){
        UserManager um = new UserManager(player.getUniqueId());
        if(player.hasPermission("pixelpresents.admin") && player.isSneaking()){
            player.sendMessage(core.getMessageManager().getMessage("shift-broken"));
            remove(present);
            return;
        }

        if (um.getConfig().getStringList("found").contains(present.getUuid().toString())) {
            core.getActionUtil().executeActions(player, core.getConfig().getStringList("already-found"));
            return;
        }
        
        List<String> found = um.getConfig().getStringList("found");
        found.add(present.getUuid().toString());
        
        boolean last = found.size() == core.getPresents().size();
        PresentFoundEvent event = new PresentFoundEvent(player, present.getLocation(), last);
        Bukkit.getPluginManager().callEvent(event);
        
        if (event.isCancelled())
            return;
        
        um.getConfig().set("found", found);
        um.save();

        core.getActionUtil().executeActions(player, present.getRewards());
        if(last)
            core.getActionUtil().executeActions(player, core.getConfig().getStringList("complete-reward"));
    }
}
