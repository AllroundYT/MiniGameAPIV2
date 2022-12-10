package de.allround;

import de.allround.event.DefaultListener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Simple self explaining class for creating a minigame on a PaperSpigot Minecraft server.
 */
public abstract class Minigame {

    public static Minigame instance;
    private final IGameManager IGameManager;
    private final Plugin plugin;

    /**
     * @param plugin            The {@link Plugin} this minigame is referred to.
     * @param iGameManagerClass The {@link IGameManager} class which should be used by this minigame instance.
     * @throws Exception When creating a new instance of {@param iGameManagerClass} isn't possible.
     */
    public Minigame(Plugin plugin, @NotNull Class<? extends IGameManager> iGameManagerClass) throws Exception {
        Minigame.instance = this;
        this.plugin = plugin;
        this.IGameManager = iGameManagerClass.getDeclaredConstructor().newInstance();
    }

    /**
     * Returns this minigame instance.
     *
     * @return This minigame instance.
     */
    public static Minigame getInstance() {
        return instance;
    }

    /**
     * Returns the {@link Plugin} this minigame is referred to.
     *
     * @return the {@link Plugin} this minigame is referred to.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Returns this minigame instance's {@link IGameManager} instance.
     *
     * @return This minigame instance's {@link IGameManager} instance.
     */
    public IGameManager getGameManager() {
        return IGameManager;
    }

    /**
     * Executed on minigame initialization.
     */
    protected abstract void onInit();

    /**
     * Executed on minigame start.
     */
    protected abstract void onStart();

    /**
     * Executed on minigame stop.
     */
    protected abstract void onStop();

    /**
     * Loads the minigame.
     */
    public final void load() {
        onInit();
    }

    /**
     * Starts the minigame.
     */
    public final void enable() {
        new DefaultListener().register();
        onStart();
    }

    /**
     * Stopps the minigame.
     */
    public final void disable() {
        onStop();
    }
}