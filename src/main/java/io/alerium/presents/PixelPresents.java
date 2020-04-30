package io.alerium.presents;

import co.aikar.commands.PaperCommandManager;
import io.alerium.presents.api.Present;
import io.alerium.presents.command.*;
import io.alerium.presents.hook.PAPIHook;
import io.alerium.presents.listeners.BreakListener;
import io.alerium.presents.listeners.InteractListener;
import io.alerium.presents.manager.MessageManager;
import io.alerium.presents.manager.PresentManager;
import io.samdev.actionutil.ActionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Chazmondo
 */
public class PixelPresents extends JavaPlugin {

    @Getter private Map<UUID, Long> playersEditing;
    @Getter private PaperCommandManager cmdManager;
    @Getter private List<Present> presents;
    
    @Getter private PresentManager presentManager;
    @Getter private MessageManager messageManager;
    @Getter private ActionUtil actionUtil;
    
    @Override
    public void onEnable(){
        saveDefaultConfig();
        playersEditing = new HashMap<>();
        presents = new ArrayList<>();

        cmdManager = new PaperCommandManager(this);
        cmdManager.getCommandReplacements().addReplacement("command", "pixelpresents|present|presents");
        
        presentManager = new PresentManager();
        messageManager = new MessageManager();
        
        actionUtil = ActionUtil.init(this);

        // Commands
        new MainCommand(this);
        new SetCommand(this);
        new ReloadCommand(this);
        new ListCommand(this);
        new TPCommand(this);

        // Listeners
        new BreakListener(this);
        new InteractListener(this);

        setupPresents();

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            debug("PlaceholderAPI has been found!");
            new PAPIHook(this).hook();
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Present present : getPresents()) {
                Location center = present.getLocation().clone().add(0.5D, 0.75, 0.5);
                World world = center.getWorld();
                
                world.spawnParticle(Particle.VILLAGER_HAPPY, center, 15);
            }
        }, 20, 20);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }


    public void debug(String msg) {
        getLogger().info("[DEBUG] " + msg);
    }
    
    public void setupPresents() {
        presents.clear();

        ConfigurationSection section = getConfig().getConfigurationSection("present");
        if (section == null)
            return;
        
        for (String id : section.getKeys(false)) {
            ConfigurationSection presentSection = section.getConfigurationSection(id);
            Location location = new Location(
                    Bukkit.getWorld(presentSection.getString("location.world")),
                    presentSection.getInt("location.x"),
                    presentSection.getInt("location.y"),
                    presentSection.getInt("location.z")
            );

            Present present = new Present(UUID.fromString(id), presentSection.getStringList("reward"), location);
            presents.add(present);
        }
    }
}
