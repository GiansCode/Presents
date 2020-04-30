package io.alerium.presents.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.alerium.presents.PixelPresents;
import org.bukkit.entity.Player;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command")
public class TPCommand extends BaseCommand {

    private final PixelPresents core;
    public TPCommand(PixelPresents core){
        this.core = core;
        core.getCmdManager().registerCommand(this, true);
    }

    @Subcommand("tp")
    public void onCommand(Player player, Integer id){
        if (!player.hasPermission("pixelpresents.admin")) {
            player.sendMessage(core.getMessageManager().getMessage("permission"));
            return;
        }
        
        player.sendMessage(core.getMessageManager().getMessage("tp").replace("%id%", ""+id));
        player.teleport(core.getPresents().get(id-1).getLocation());
    }
}
