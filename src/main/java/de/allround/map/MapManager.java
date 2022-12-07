package de.allround.map;

import de.allround.IGameManager;
import de.allround.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MapManager {
    private final IGameManager IGameManager;
    private final BlockManager blockManager;
    private final List<GameMap> maps;
    private GameMap mapToPlay;
    private Location lobbySpawn;

    public MapManager(BlockManager blockManager) {
        this.blockManager = blockManager;
        this.IGameManager = Minigame.getInstance().getGameManager();
        this.maps = new ArrayList<>();

        getMapsDirectory().toFile().mkdirs();
    }

    public Path getMapsDirectory(){
        return Path.of("plugins",Minigame.getInstance().getPlugin().getName(),"Maps");
    }

    public List<GameMap> getPlayableMaps(){
        return this.maps.stream().filter(this::isPlayAble).collect(Collectors.toList());
    }

    public Location getLobbySpawn() {
        if (lobbySpawn == null) return Bukkit.getWorlds().get(0).getSpawnLocation();
        return lobbySpawn;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public Optional<GameMap> getMapToPlay(){
        return Optional.ofNullable(mapToPlay);
    }

    public void setMapToPlay(GameMap mapToPlay) {
        if (!isPlayAble(mapToPlay)) return;
        this.mapToPlay = mapToPlay;
    }


    public void registerMaps(GameMap... gameMaps){
        maps.addAll(List.of(gameMaps));
    }

    public void unregisterMaps(String... maps){
        for (String map : maps) {
            if (getMap(map).isPresent()){
                this.maps.remove(getMap(map).get());
            }
        }
    }

    public Optional<GameMap> getMap(String name){
        return maps.stream().filter(gameMap -> gameMap.getName().equals(name)).findFirst();
    }

    public List<GameMap> getMapsByBuilder(String builder){
        return maps.stream().filter(gameMap -> gameMap.getBuilder().equals(builder)).collect(Collectors.toList());
    }

    public abstract boolean isPlayAble(GameMap gameMap);

    public List<GameMap> getMaps() {
        return maps;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    private IGameManager getGameManager() {
        return this.IGameManager;
    }
}
