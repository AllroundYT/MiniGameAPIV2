package de.allround.gui;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PageableGui {
    private final String name;
    private final Gui[] pages;


    public PageableGui(String name, Gui... pages) {
        this.pages = pages;
        this.name = name;
    }

    public void open(Player player, int page) {
        if (pages.length - 1 < page) open(player);
        player.getInventory().close();
        pages[page].open(player);
    }

    public void open(Player player) {
        open(player, 0);
    }
}
