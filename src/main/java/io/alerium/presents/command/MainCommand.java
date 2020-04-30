package io.alerium.presents.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import io.alerium.presents.PixelPresents;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Chazmondo
 */

public class MainCommand extends BaseCommand {

    private final PixelPresents core;
    public MainCommand(PixelPresents core){
        this.core = core;
        core.getCmdManager().registerCommand(this, true);
    }

    @CommandAlias("%command")
    public void onCommand(CommandSender sender) {
        if (!sender.hasPermission("pixelpresents.admin")) {
            sender.sendMessage(core.getMessageManager().getMessage("permission"));
            return;
        }

        for (String msg : core.getConfig().getStringList("help"))
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("%version%", core.getDescription().getVersion()));
    }
}
