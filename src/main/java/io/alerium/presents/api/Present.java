package io.alerium.presents.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

/**
 * Created by Chazmondo
 */
@AllArgsConstructor
public class Present {

    @Getter private final UUID uuid;
    @Getter @Setter private List<String> rewards;
    @Getter private final Location location;
    
}
