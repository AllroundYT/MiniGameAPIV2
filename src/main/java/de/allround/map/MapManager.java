package de.allround.map;

import de.allround.GameManager;
import de.allround.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MapManager {
    private final GameManager gameManager;
    private final BlockManager blockManager;
    private final List<GameMap> maps;
    private GameMap mapToPlay;
    private Location lobbySpawn;

    public MapManager(BlockManager blockManager) {
        this.blockManager = blockManager;
        this.gameManager = Minigame.getInstance().getGameManager();
        this.maps = new ArrayList<>();
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

    private GameManager getGameManager() {
        return this.gameManager;
    }
}
