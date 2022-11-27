package de.allround;

import de.allround.event.DefaultListener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Minigame extends JavaPlugin {

    public static Minigame instance;
    private final GameManager gameManager;

    public Minigame(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static Minigame getInstance(Class<? extends Minigame> mainClass) {
        return mainClass.cast(instance);
    }

    public static Minigame getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public abstract void onInit();

    public abstract void onStart();

    public abstract void onStop();

    @Override
    public final void onLoad() {
        onInit();
    }

    @Override
    public final void onEnable() {
        new DefaultListener().register();
        onStart();
    }

    @Override
    public final void onDisable() {
        onStop();
    }
}