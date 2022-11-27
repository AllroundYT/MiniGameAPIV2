package de.allround.states;

import de.allround.states.events.GameStateStartEvent;
import de.allround.states.events.GameStateStopEvent;
import org.bukkit.Bukkit;

public interface GameState {
    void onStart();
    void onStop();

    default void start(){
        GameStateStartEvent gameStateStartEvent = new GameStateStartEvent(this);
        Bukkit.getPluginManager().callEvent(gameStateStartEvent);
        if (gameStateStartEvent.isCancelled()) return;
        onStart();
    }

    default void stop(){
        GameStateStopEvent gameStateStopEvent = new GameStateStopEvent(this);
        Bukkit.getPluginManager().callEvent(gameStateStopEvent);
        if (gameStateStopEvent.isCancelled()) return;
        onStop();
    }
}
