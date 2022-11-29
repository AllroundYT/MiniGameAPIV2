package de.allround.voting;

import de.allround.player.MinigamePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Voting {
    private final List<VotingOption> options;
    private final String name;

    public Voting(String name) {
        this.name = name;
        this.options = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public VotingOption getWinner(){
        return options.stream().sorted(Comparator.comparingInt(VotingOption::getVotes)).toList().get(options.size() - 1);
    }

    public void registerVotingOptions(VotingOption... votingOptions){
        this.options.addAll(List.of(votingOptions));
    }

    public List<VotingOption> getOptions() {
        return options;
    }

    public Optional<VotingOption> getOption(String name){
        return this.options.stream().filter(votingOption -> votingOption.getName().equals(name)).findFirst();
    }

    public boolean hasVoted(MinigamePlayer minigamePlayer){
        return hasVoted(minigamePlayer.getUuid());
    }

    public boolean hasVoted(UUID uuid){
        return options.stream().anyMatch(option -> option.getVoters().contains(uuid));
    }
}
