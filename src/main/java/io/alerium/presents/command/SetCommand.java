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
public class SetCommand extends BaseCommand {

    private final PixelPresents core;
    public SetCommand(PixelPresents core){
        this.core = core;
        core.getCmdManager().registerCommand(this, true);
    }

    @Subcommand("set")
    public void onCommand(Player player){
        if (!player.hasPermission("pixelpresents.admin")) {
            player.sendMessage(core.getMessageManager().getMessage("permission"));
            return;
        }
        
        player.sendMessage(core.getMessageManager().getMessage("create"));
        core.getPlayersEditing().put(player.getUniqueId(), System.currentTimeMillis());
    }
}
