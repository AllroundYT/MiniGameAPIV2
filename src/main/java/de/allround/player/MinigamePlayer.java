package de.allround.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MinigamePlayer {
    private final UUID uuid;
    private final HashMap<String, Object> dataFields;

    public MinigamePlayer(UUID uuid) {
        this.uuid = uuid;
        this.dataFields = new HashMap<>();
        setSpectator(false);
        setWinner(false);
    }

    public void setField(String field, Object value) {
        dataFields.put(field, value);
    }

    public boolean existsField(String field) {
        return this.dataFields.containsKey(field);
    }

    public boolean hasWon() {
        return getField("@has_won", boolean.class);
    }

    public void setWinner(boolean winner) {
        setField("@has_won", winner);
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getField(String field, Class<T> type) {
        return (T) this.dataFields.getOrDefault(field, null);
    }

    public boolean isSpectator() {
        return getField("@spectator", boolean.class);
    }

    public void setSpectator(boolean spectator) {
        setField("@spectator", spectator);
    }

    public Location getSpawnLocation() {
        return getField("@spawn_location", Location.class);
    }

    public void setSpawnLocation(Location location) {
        setField("@spawn_location", location);
    }

    public Player asPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }
}
