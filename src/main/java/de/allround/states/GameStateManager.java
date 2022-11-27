package de.allround.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GameStateManager {
    private final List<GameState> gameStates;
    private GameState runningState;
    public GameStateManager() {
        this.gameStates = new ArrayList<>();
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }

    public void registerGameStates(GameState... gameStates){
        this.gameStates.addAll(Arrays.asList(gameStates));
    }

    public Optional<GameState> getGameState(String name){
        return getGameStates().stream().filter(gameState -> gameState.getName().equals(name)).findFirst();
    }

    public GameState getRunningState() {
        return runningState;
    }

    public void startGameState(String state){
        Optional<GameState> gameState = getGameState(state);
        if (gameState.isEmpty()) return;
        this.runningState.stop();
        this.runningState = gameState.get();
        this.runningState.start();
    }
}
