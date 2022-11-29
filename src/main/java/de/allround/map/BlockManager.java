package de.allround.map;

import de.allround.player.MinigamePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface BlockManager {
    boolean canBreak(MinigamePlayer player, Block block);

    boolean canPlace(MinigamePlayer player, Block block);

    void onBlockInteract(MinigamePlayer player, PlayerInteractEvent event);
}
