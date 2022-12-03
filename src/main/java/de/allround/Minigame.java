package de.allround;

import de.allround.event.DefaultListener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Minigame {

    public static Minigame instance;
    private final IGameManager IGameManager;

    public Minigame(IGameManager IGameManager) {
        this.IGameManager = IGameManager;
    }

    public static Minigame getInstance(Class<? extends Minigame> mainClass) {
        return mainClass.cast(instance);
    }

    public static Minigame getInstance() {
        return instance;
    }

    public IGameManager getGameManager() {
        return IGameManager;
    }

    public abstract void onInit();

    public abstract void onStart();

    public abstract void onStop();

    public final void onLoad() {
        onInit();
    }

    public final void onEnable() {
        new DefaultListener().register();
        onStart();
    }

    public final void onDisable() {
        onStop();
    }
}