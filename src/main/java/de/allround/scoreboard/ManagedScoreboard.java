package de.allround.scoreboard;

import de.allround.player.MinigamePlayer;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public interface ManagedScoreboard {
    boolean isDisplayed();

    void setDisplayed(boolean display);

    List<MinigamePlayer> getPlayers();

    void addPlayer(MinigamePlayer minigamePlayer);
    
    Scoreboard getScoreboard();

    void update();

    void setLine(int line, String text);

    void setTitle(String title);
    
    void startUpdateSchedule();
    
    void cancelUpdateSchedule();
}
