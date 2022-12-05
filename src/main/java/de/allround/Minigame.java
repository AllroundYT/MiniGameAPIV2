package de.allround;

import de.allround.event.DefaultListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public abstract class Minigame{

    public static Minigame instance;
    private final IGameManager IGameManager;
    private final Plugin plugin;

    public Plugin getPlugin() {
        return plugin;
    }

    public Minigame(Plugin plugin, Class<? extends IGameManager> iGameManagerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Minigame.instance = this;
        this.plugin = plugin;
        this.IGameManager = iGameManagerClass.getDeclaredConstructor().newInstance();
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

    protected abstract void onInit();

    protected abstract void onStart();

    protected abstract void onStop();

    public final void load() {
        onInit();
    }

    public final void enable() {
        new DefaultListener().register();
        onStart();
    }

    public final void disable() {
        onStop();
    }
}