package de.allround;

import de.allround.item.ItemAbilityRegister;
import de.allround.kit.KitManager;
import de.allround.map.MapManager;
import de.allround.player.PlayerManager;
import de.allround.player.TeamManager;
import de.allround.states.GameStateManager;

public interface GameManager {
    PlayerManager getPlayerManagement();

    MapManager getMapManagement();

    TeamManager getTeamManagement();

    ItemAbilityRegister getItemAbilityRegister();

    KitManager getKitManagement();

    GameStateManager getGameStateManagement();

    boolean isGameOver();
}
