package de.allround.voting;

import de.allround.player.MinigamePlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class VotingOption {
    private final String name;
    private int votes;
    private final List<UUID> voters;

    public VotingOption(String name) {
        this.voters = new ArrayList<>();
        this.name = name;
    }

    public void vote(MinigamePlayer minigamePlayer){
        if (voters.contains(minigamePlayer.getUuid())) return;
        voters.add(minigamePlayer.getUuid());
        addVotes(1);
    }

    public void unvote(MinigamePlayer minigamePlayer){
        if (!voters.contains(minigamePlayer.getUuid())) return;
        voters.remove(minigamePlayer.getUuid());
        removeVotes(1);
    }

    public void vote(UUID uuid){
        if (voters.contains(uuid)) return;
        voters.add(uuid);
        addVotes(1);
    }

    public void unvote(UUID uuid){
        if (!voters.contains(uuid)) return;
        voters.remove(uuid);
        removeVotes(1);
    }

    public void addVotes(int amount){
        this.votes += amount;
    }

    public void removeVotes(int amount){
        this.votes -= amount;
        if (votes < 0) this.votes = 0;
    }
}
