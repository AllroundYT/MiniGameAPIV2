package de.allround.event;

import de.allround.Minigame;
import de.allround.gui.Gui;
import de.allround.player.MinigamePlayer;
import org.bukkit.block.Container;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class DefaultListener {
    public void register() {
        EventManager.listen(
                InventoryClickEvent.class,
                event -> ((Gui) Objects.requireNonNull(event.getInventory().getHolder())).onClick(event),
                event -> event.getInventory().getHolder() instanceof Gui
        );

        EventManager.listen(
                InventoryCloseEvent.class,
                event -> ((Gui) Objects.requireNonNull(event.getInventory().getHolder())).onClose(event),
                event -> event.getInventory().getHolder() instanceof Gui
        );

        EventManager.listen(
                InventoryDragEvent.class,
                event -> ((Gui) Objects.requireNonNull(event.getInventory().getHolder())).onDrag(event),
                event -> event.getInventory().getHolder() instanceof Gui
        );

        EventManager.listen(
                InventoryMoveItemEvent.class,
                event -> {
                    if (event.getDestination().getHolder() instanceof Gui gui) gui.onMove(event);
                    else if (event.getInitiator().getHolder() instanceof Gui gui) gui.onMove(event);
                },
                event -> event.getInitiator().getHolder() instanceof Gui || event.getDestination().getHolder() instanceof Gui
        );

        EventManager.listen(
                PlayerDropItemEvent.class,
                event -> ((Gui) Objects.requireNonNull(event.getPlayer().getOpenInventory().getTopInventory().getHolder())).onDrop(event),
                event -> event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof Gui
        );

        EventManager.listen(
                BlockBreakEvent.class,
                event -> {
                    MinigamePlayer minigamePlayer = Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(event.getPlayer());
                    event.setCancelled(Minigame.getInstance().getGameManager().getMapManagement().getBlockManager().canBreak(minigamePlayer,event.getBlock()));
                }
        );

        EventManager.listen(
                BlockPlaceEvent.class,
                event -> {
                    MinigamePlayer minigamePlayer = Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(event.getPlayer());
                    event.setCancelled(Minigame.getInstance().getGameManager().getMapManagement().getBlockManager().canPlace(minigamePlayer,event.getBlock()));
                }
        );

        EventManager.listen(
                PlayerInteractEvent.class,
                event -> {
                    MinigamePlayer minigamePlayer = Minigame.getInstance().getGameManager().getPlayerManagement().getPlayer(event.getPlayer());
                    Minigame.getInstance().getGameManager().getMapManagement().getBlockManager().onBlockInteract(minigamePlayer,event);
                },
                event -> event.getClickedBlock() != null
        );


    }
}
