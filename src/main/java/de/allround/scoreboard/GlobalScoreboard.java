package de.allround.scoreboard;

import de.allround.Minigame;
import de.allround.player.MinigamePlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class GlobalScoreboard implements ManagedScoreboard {
    private final int updateTicks;
    private final Map<Integer, String> lines;
    private final List<MinigamePlayer> players;
    private final BukkitRunnable bukkitRunnable;
    private boolean displayed;
    private String title;
    private boolean runnableStarted;
    private final Scoreboard scoreboard;
    private Objective objective;
    private final String name;

    public GlobalScoreboard(int updateTicks, String title, String name) {
        this.updateTicks = updateTicks;
        this.title = title;
        this.name = name;
        this.players = new ArrayList<>();
        this.lines = new HashMap<>();
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        };
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }



    @Override
    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    @Override
    public List<MinigamePlayer> getPlayers() {
        return null;
    }

    @Override
    public void addPlayer(MinigamePlayer minigamePlayer) {
        this.players.add(minigamePlayer);
    }

    @Override
    public void update() {
        if (objective == null){
            objective = getScoreboard().registerNewObjective("global-board-"+name, Criteria.DUMMY,title);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setRenderType(RenderType.INTEGER);
        }

        objective.setDisplayName(title);
        this.lines.forEach((lineIndex, text) -> {
            //TODO: Wie kann man den gleichen text in mehrere zeilen packen?
        });
    }

    @Override
    public void setLine(int line, String text) {
        lines.put(line, text);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void startUpdateSchedule() {
        bukkitRunnable.runTaskTimer(Minigame.getInstance(), updateTicks, updateTicks);
        runnableStarted = true;
    }

    @Override
    public void cancelUpdateSchedule() {
        if (!runnableStarted) return;
        bukkitRunnable.cancel();
    }
}
