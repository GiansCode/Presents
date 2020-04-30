package io.alerium.presents.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.alerium.presents.PixelPresents;
import io.alerium.presents.api.Present;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command")
public class ListCommand extends BaseCommand {

    private final PixelPresents core;
    public ListCommand(PixelPresents core){
        this.core = core;
        core.getCmdManager().registerCommand(this, true);
    }

    @Subcommand("list")
    public void onCommand(Player player){
        if (!player.hasPermission("pixelpresents.admin")) {
            player.sendMessage(core.getMessageManager().getMessage("permission"));
            return;
        }
        
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lListing Presents&r &7(" + core.getPresents().size() + "&7)"));
        player.sendMessage("");
        
        int id = 1;
        for (Present present : core.getPresents()) {
            Location loc = present.getLocation();

            String format = " &8• &fPresent #" + id + " &8- &cX: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ();

            TextComponent message = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', format)));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/present tp " + id));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick to teleport!").create()));
            
            player.sendMessage(message);
            id++;
        }
    }
}
