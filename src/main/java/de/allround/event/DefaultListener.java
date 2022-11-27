package de.allround.event;

import de.allround.gui.Gui;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

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
    }
}
