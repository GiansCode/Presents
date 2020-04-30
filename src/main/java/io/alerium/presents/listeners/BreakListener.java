package io.alerium.presents.listeners;

import io.alerium.presents.PixelPresents;
import io.alerium.presents.api.Present;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Chazmondo
 */
public class BreakListener implements Listener {

    private final PixelPresents core;
    public BreakListener(PixelPresents core){
        this.core = core;
        Bukkit.getPluginManager().registerEvents(this, core);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Present present = core.getPresentManager().get(block.getLocation());
        if(present != null){
            event.setCancelled(true);
            core.getPresentManager().dealActions(player, present);
            return;
        }

        if (!player.hasPermission("pixelpresents.admin"))
            return;
        
        if (!core.getPlayersEditing().containsKey(player.getUniqueId()))
            return;
        
        event.setCancelled(true);
        core.getPlayersEditing().remove(player.getUniqueId());
        core.getPresentManager().create(block.getLocation());
        player.sendMessage(core.getMessageManager().getMessage("setup"));
    }
}
