package de.allround.map;

import de.allround.GameManager;
import de.allround.Minigame;

public class MapManager {  //TODO: map system fehlt noch komplett
    private final GameManager gameManager;
    private final BlockManager blockManager;

    public MapManager(BlockManager blockManager) {
        this.blockManager = blockManager;
        this.gameManager = Minigame.getInstance().getGameManager();
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    private GameManager getGameManager(){
        return this.gameManager;
    }
}
