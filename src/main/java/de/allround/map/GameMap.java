package de.allround.map;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;

@Getter
@Setter
public class GameMap {
    private final HashMap<String, Location> locations;
    private final String name;
    private final int maxTeamAmount;
    private String builder;
    
    public GameMap(String name, String builder, int maxTeamAmount) {
        this.name = name;
        this.builder = builder;
        this.maxTeamAmount = maxTeamAmount;
        this.locations = new HashMap<>();
    }

    public String getLocationName(Location location){
        assert  locations.containsValue(location);
        AtomicReference<String> out = new AtomicReference<>();
        this.locations.forEach((s, location1) -> {
            if (location1.equals(location)) {
                out.set(s);
            }
        });
        return out.get();
    }
    public static GameMap load(Path path) {
        String json = "";

        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(path))){
            json = new String(bufferedInputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GsonBuilder().setPrettyPrinting().create().fromJson(json,GameMap.class);
    }

    public void save(Path path) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        try (final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(path))){
            bufferedOutputStream.write(json.getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(BiPredicate<String, Location> predicate) {
        HashMap<String, Location> outputMap = new HashMap<>();
        locations.forEach((s, location) -> {
            if (predicate.test(s, location)) {
                outputMap.put(s, location);
            }
        });
        return outputMap.values().toArray(new Location[0])[0];
    }

    public List<Location> getLocations(BiPredicate<String, Location> predicate){
        HashMap<String, Location> outputMap = new HashMap<>();
        locations.forEach((s, location) -> {
            if (predicate.test(s, location)) {
                outputMap.put(s, location);
            }
        });
        return new ArrayList<>(outputMap.values());
    }

    public void saveLocation(String name, Location location) {
        this.locations.put(name, location);
    }
}
