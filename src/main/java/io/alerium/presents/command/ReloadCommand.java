package io.alerium.presents.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.alerium.presents.PixelPresents;
import org.bukkit.command.CommandSender;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command")
public class ReloadCommand extends BaseCommand {

    private final PixelPresents core;
    public ReloadCommand(PixelPresents core){
        this.core = core;
        core.getCmdManager().registerCommand(this, true);
    }

    @Subcommand("reload")
    public void onCommand(CommandSender sender){
        if (!sender.hasPermission("pixelpresents.admin")) {
            sender.sendMessage(core.getMessageManager().getMessage("permission"));
            return;
        }
        
        core.reloadConfig();
        core.setupPresents();
        sender.sendMessage(core.getMessageManager().getMessage("reloaded"));
    }
}
