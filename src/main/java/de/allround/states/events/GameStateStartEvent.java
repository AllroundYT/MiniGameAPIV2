package de.allround.states.events;

import de.allround.states.GameState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

@Getter
public class GameStateStartEvent extends GameStateEvent implements Cancellable {
    private boolean cancelled;

    public GameStateStartEvent(GameState gameState) {
        super(gameState);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
