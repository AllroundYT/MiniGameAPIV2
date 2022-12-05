package de.allround.map;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;

@Getter
@Setter
public class GameMap {
    private final List<MapLocation> locations;
    private final String name;
    private final int maxTeamAmount;
    private String builder;

    public GameMap(String name, String builder, int maxTeamAmount) {
        this.name = name;
        this.builder = builder;
        this.maxTeamAmount = maxTeamAmount;
        this.locations = new ArrayList<>();
    }

    public GameMap(List<MapLocation> locations, String name, int maxTeamAmount, String builder) {
        this.locations = locations;
        this.name = name;
        this.maxTeamAmount = maxTeamAmount;
        this.builder = builder;
    }

    public static GameMap load(Path path) {
        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(path))){
            return new GsonBuilder().setPrettyPrinting().create().fromJson(new String(bufferedInputStream.readAllBytes()),GameMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Path path) {
        try (final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(path))){
            bufferedOutputStream.write(new GsonBuilder().setPrettyPrinting().create().toJson(this).getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getLocationName(Location location) {
        assert locations.stream().anyMatch(mapLocation -> mapLocation.toLocation().equals(location));
        return locations.stream().filter(mapLocation -> mapLocation.toLocation().equals(location)).findFirst().get().name;
    }


    public Optional<MapLocation> getLocation(BiPredicate<String, Location> predicate) {
        return locations.stream().filter(mapLocation -> predicate.test(mapLocation.name,mapLocation.toLocation())).findFirst();
    }

    public List<Location> getLocations(BiPredicate<String, Location> predicate) {
        List<Location> out = new ArrayList<>();
        locations.forEach((location) -> {
            if (predicate.test(location.name, location.toLocation())) {
                out.add(location.toLocation());
            }
        });
        return out;
    }

    public void saveLocation(String name, Location location) {
        this.locations.add(new MapLocation(location,name));
    }


    @RequiredArgsConstructor
    @Getter
    public static class MapLocation {
        private final double x,y,z;
        private final String world;
        private final float pitch,yaw;
        private final String name;

        public Location toLocation(){
            return new Location(Bukkit.getWorld(world),x,y,z,yaw,pitch);
        }

        public MapLocation(Location location,String name){
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.world = location.getWorld().getName();
            this.pitch = location.getPitch();
            this.yaw = location.getYaw();
            this.name = name;
        }
    }
}
