package de.allround.states;

import de.allround.states.events.GameStateStartEvent;
import de.allround.states.events.GameStateStopEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
@Getter
public abstract class GameState {
    private final String name;
    private boolean running;

    public boolean isRunning() {
        return running;
    }

    /**
     * Executed when this GameState starts.
     */
    public abstract void onStart();

    /**
     * Executed when this GameState stopps.
     */
    public abstract void onStop();

    public void start() {
        if (isRunning()) return;
        GameStateStartEvent gameStateStartEvent = new GameStateStartEvent(this);
        Bukkit.getPluginManager().callEvent(gameStateStartEvent);
        if (gameStateStartEvent.isCancelled()) return;
        onStart();
        this.running = true;
    }

    public void stop() {
        if (!isRunning()) return;
        GameStateStopEvent gameStateStopEvent = new GameStateStopEvent(this);
        Bukkit.getPluginManager().callEvent(gameStateStopEvent);
        if (gameStateStopEvent.isCancelled()) return;
        onStop();
        this.running = false;
    }
}
