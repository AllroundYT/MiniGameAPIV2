package de.allround.states.events;

import de.allround.states.GameState;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class GameStateEvent extends Event {
    private final GameState gameState;
    private final HandlerList handlers;

    public GameStateEvent(GameState gameState) {
        this.gameState = gameState;
        this.handlers = new HandlerList();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
