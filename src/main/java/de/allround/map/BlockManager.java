package de.allround.map;

import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;

public interface BlockManager {
    boolean canBreak(Player player, Block block);
    boolean canPlace(Player player,Block block);
    void onContainerOpen(Player player, Container container);
    void onBlockInteract(Player player,Block block);
}
