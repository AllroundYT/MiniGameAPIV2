package de.allround.player;

import de.allround.Minigame;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamManager {

    private final List<Team> teams;

    public TeamManager() {
        this.teams = new ArrayList<>();
    }

    public TeamManager registerTeam(Team team) {
        if (teams.stream().noneMatch(team1 -> team1.name.equals(team.name))) this.teams.add(team);
        return this;
    }

    public Optional<Team> getTeam(String name) {
        return this.teams.stream().filter(team -> team.name.equals(name)).findFirst();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Optional<Team> getTeamOf(MinigamePlayer minigamePlayer) {
        return this.teams.stream().filter(team -> team.members.contains(minigamePlayer)).findFirst();
    }

    public Optional<Team> getTeamOf(UUID uuid) {
        return getTeamOf(Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(uuid));
    }

    public boolean isInTeam(MinigamePlayer minigamePlayer) {
        return getTeamOf(minigamePlayer).isPresent();
    }

    public boolean isInTeam(UUID uuid) {
        return getTeamOf(Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(uuid)).isPresent();
    }

    @Getter
    public static class Team {
        private final String name;
        private final int size;
        private final List<MinigamePlayer> members;

        public Team(String name, int size) {
            this.name = name;
            this.size = size;
            this.members = new ArrayList<>();
        }

        public void join(MinigamePlayer minigamePlayer) {
            if (members.contains(minigamePlayer)) return;
            if (Minigame.getInstance().getGameManager().getTeamManagement().isInTeam(minigamePlayer)) {
                Minigame.getInstance().getGameManager().getTeamManagement().getTeamOf(minigamePlayer).get().leave(minigamePlayer);
            }
            this.members.add(minigamePlayer);
        }

        public void join(UUID uuid) {
            join(Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(uuid));
        }

        public void leave(UUID uuid) {
            leave(Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(uuid));
        }

        public void leave(MinigamePlayer minigamePlayer) {
            this.members.remove(minigamePlayer);
        }
    }
}
