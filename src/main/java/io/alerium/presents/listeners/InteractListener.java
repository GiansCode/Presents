package io.alerium.presents.listeners;

import io.alerium.presents.PixelPresents;
import io.alerium.presents.api.Present;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by Chazmondo
 */
public class InteractListener implements Listener {

    private final PixelPresents core;
    public InteractListener(PixelPresents core){
        this.core = core;
        Bukkit.getPluginManager().registerEvents(this, core);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!Bukkit.getVersion().contains("1.8") && event.getHand() == EquipmentSlot.OFF_HAND) 
            return;

        Present present = core.getPresentManager().get(block.getLocation());
        if (present == null)
            return;
        
        event.setCancelled(true);
        core.getPresentManager().dealActions(player, present);
    }
}
