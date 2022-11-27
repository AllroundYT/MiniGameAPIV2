package de.allround.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/*
  Inventory Slot indexes:
  0 , 1, 2, 3, 4, 5, 6, 7, 8
  9 ,10,11,12,13,14,15,16,17
  18,19,20,21,22,23,24,25,26
  27,28,29,30,31,32,33,34,35
  36,37,38,39,40,41,42,43,44
  45,46,47,48,49,50,51,52,53
 */
public interface Gui extends InventoryHolder {
    String getTitel();

    int getRows();


    @NotNull Inventory getInventory();

    default void open(Player player) {
        update(player);
        onOpen(player);
        player.openInventory(getInventory());
    }

    void onOpen(Player player);

    void onClose(InventoryCloseEvent event);

    default void close() {
        getInventory().close();
    }

    void update(Player player);

    void onClick(InventoryClickEvent event);

    void onDrag(InventoryDragEvent event);

    void onMove(InventoryMoveItemEvent event);

    void onDrop(PlayerDropItemEvent event);
}
