package de.allround.player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerManager {
    private final HashMap<UUID, MinigamePlayer> cachedPlayers;

    public PlayerManager() {
        this.cachedPlayers = new HashMap<>();
    }

    public List<MinigamePlayer> getPlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> getPlayer(player.getUniqueId()));
        return new ArrayList<>(cachedPlayers.values());
    }

    public List<MinigamePlayer> getPlayers(Predicate<MinigamePlayer> predicate) {
        return getPlayers().stream().filter(predicate).collect(Collectors.toList());
    }

    public List<MinigamePlayer> getWinner() {
        return getPlayers(MinigamePlayer::hasWon);
    }

    public List<MinigamePlayer> getSpectators() {
        return getPlayers(MinigamePlayer::isSpectator);
    }

    public MinigamePlayer getPlayer(UUID uuid) {
        return cachedPlayers.computeIfAbsent(uuid, MinigamePlayer::new);
    }
}
