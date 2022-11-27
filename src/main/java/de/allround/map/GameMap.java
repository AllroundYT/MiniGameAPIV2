package de.allround.map;

import org.bukkit.Location;

import java.util.Map;
import java.util.function.BiPredicate;

public interface GameMap { //TODO: in class umwandeln
    Location getLocation(BiPredicate<String, Location> predicate);

    Map<String, Location> getLocations();

    void saveLocation(String name, Location location);

    String getName();

    // static GameMap load(FileConfiguration cfg);

    String getBuilder();

    // static GameMap save(Path path);
    
    int getMaxTeamAmount();
}
